package dto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private final String[] lines = new String[]{
            "\"100734\",\"WEST TEXAS\",\"3 BEAR LEA\",\"M2\",\"RPQ\",\"R\",\"40000\",\"55000\",\"34100\",\"20900\",\"Y\",\"N\",\"N\",\"Y\",\"\"",
            "\"60152\",\"CENTRAL\",\"ABO FUEL DEL\",\"MQ\",\"DPQ\",\"D\",\"5000\",\"5000\",\"1000\",\"4000\",\"N\",\"N\",\"N\",\"Y\",\"\""
    };

    @Test
    void itRemovesQuotesFromStringValue() {
        final String value = "\"1234\"";
        final String result = Location.removeQuotes(value);
        assertEquals("1234", result);
    }

    @ParameterizedTest
    @CsvSource({"0,100734", "1,60152"})
    void itFormatNumberValueIntoAnIntFromAString(int i, int expected) {
        final String[] info = lines[i].split(",");
        final Location location = new Location(info);
        assertEquals(expected, location.getLoc());
    }

    @ParameterizedTest
    @CsvSource({"0, WEST TEXAS", "1,CENTRAL"})
    void itFormatStringValueIntoStringWithSingleQuotes(int i, String expected) {
        final String[] info = lines[i].split(",");
        final Location location = new Location(info);
        assertEquals("'" + expected + "'", location.getLocZn());
    }

    @ParameterizedTest
    @CsvSource({"0,True", "1,False"})
    void itFormatBooleanValueIntoBooleanFromAString(int i, boolean expected) {
        final String[] info = lines[i].split(",");
        final Location location = new Location(info);
        assertEquals(expected, location.isIt());
    }
}
