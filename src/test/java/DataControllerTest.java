import dto.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.exceptions.base.MockitoException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataControllerTest {

    private final String uri = "https://twtransfer.energytransfer.com/ipost/capacity/operationally-available?f=csv&extension=csv&asset=TW&gasDay=08%2F09%2F2022&cycle=5&searchType=NOM&searchString=&locType=ALL&locZone=ALL";

    @Test
    void itFetchDataToTheCorrectWebsiteUri() throws URISyntaxException, IOException, InterruptedException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();

        HttpResponse mockResponse = mock(HttpResponse.class);
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn("");

        try (MockedStatic<HttpClient> mockStaticClient = mockStatic(HttpClient.class)) {
            mockStaticClient.when(HttpClient::newHttpClient).thenReturn(mockClient);

            DataController.fetchData();
            verify(mockClient).send(eq(request), any());
        }
    }

    @Test
    void itFormatAndReturnTheInformationFromTheCSV() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse mockResponse = mock(HttpResponse.class);
        HttpClient mockClient = mock(HttpClient.class);

        when(mockClient.send(any(), any())).thenReturn(mockResponse);
        when(mockResponse.body()).thenReturn(
                "\"Loc\",\"Loc Zn\",\"Loc Name\",\"Loc Purp Desc\",\"Loc/QTI\",\"Flow Ind\",\"DC\",\"OPC\",\"TSQ\",\"OAC\",\"IT\",\"Auth Overrun Ind\",\"Nom Cap Exceed Ind\",\"All Qty Avail\",\"Qty Reason\"\n" +
                "\"100734\",\"WEST TEXAS\",\"3 BEAR LEA\",\"M2\",\"RPQ\",\"R\",\"40000\",\"60000\",\"45475\",\"14525\",\"Y\",\"N\",\"N\",\"Y\",\"\"\n" +
                "\"60152\",\"CENTRAL\",\"ABO FUEL DEL\",\"MQ\",\"DPQ\",\"D\",\"5000\",\"5000\",\"1100\",\"3900\",\"Y\",\"N\",\"N\",\"Y\",\"\""
        );

        try (MockedStatic<HttpClient> mockStaticClient = mockStatic(HttpClient.class)) {
            mockStaticClient.when(HttpClient::newHttpClient).thenReturn(mockClient);

            final String[] result = DataController.fetchData().stream().map(Location::toString).toArray(String[]::new);
            final String[] expected = new ArrayList<Location>() {{
                add(new Location(100734, "'WEST TEXAS'", "'3 BEAR LEA'", "'M2'","'RPQ'","'R'",40000,60000,45475,14525, true,false,false,true,"''"));
                add(new Location(60152,"'CENTRAL'","'ABO FUEL DEL'","'MQ'","'DPQ'","'D'",5000,5000,1100,3900,true,false,false,true,"''"));
            }}.stream().map(Location::toString).toArray(String[]::new);

            assertArrayEquals(expected, result);
        }

    }
}
