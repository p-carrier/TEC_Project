import java.text.SimpleDateFormat;
import java.util.Date;

public class Day {

    private static int timeInDayMillis = 24*60*60*1000;
    private static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    public static String getTodayDate() {
        return getDate(0);
    }

    public static String getYesterdayDate() {
        return getDate(1);
    }

    public static String getBeforeYesterdayDate() {
        return getDate(2);
    }

    private static String getDate(long days) {
        Date date = new Date(System.currentTimeMillis() - days * timeInDayMillis);
        return formatter.format(date);
    }
}
