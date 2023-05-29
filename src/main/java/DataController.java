import dto.Location;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DataController {
    private static final String uri = "https://twtransfer.energytransfer.com/ipost/capacity/operationally-available";

    private static String buildUri(String gasDay, int cycle) {
        return uri + String.format("?f=csv&extension=csv&asset=TW&gasDay=%s&cycle=%d&searchType=NOM&searchString=&locType=ALL&locZone=ALL", URLEncoder.encode(gasDay), cycle);
    }

    public static ArrayList<Location> fetchData(String day, int cycle) throws URISyntaxException, IOException, InterruptedException {
        final String uri = buildUri(day, cycle);
        System.out.println(uri);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(uri))
                .GET()
                .build();

        final HttpClient client = HttpClient.newHttpClient();
        final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Arrays.stream(response.body().split("\n"))
                .skip(1)
                .map(line -> new Location(line.split(",")))
                .peek(System.out::println)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
