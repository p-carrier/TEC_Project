import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class DayTest {

    @Test
    void itExtractTodayDateWhenGetTodayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expected = formatter.format(LocalDateTime.now());

        String result = Day.getTodayDate();

        assertEquals(expected, result);
    }

    @Test
    void itExtractYesterdayDateWhenGetYesterdayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expected = LocalDateTime.now().minus(1, ChronoUnit.DAYS).format(formatter);

        String result = Day.getYesterdayDate();

        assertEquals(expected, result);
    }

    @Test
    void itExtractDayBeforeYesterdayDateWhenGetBeforeYesterdayDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expected = LocalDateTime.now().minus(2, ChronoUnit.DAYS).format(formatter);

        String result = Day.getBeforeYesterdayDate();

        assertEquals(expected, result);
    }
}
