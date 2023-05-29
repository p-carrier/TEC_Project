import dto.Location;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataExtractor {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, SQLException {
        ArrayList<Location> locations = DataController.fetchData();
        DataRepository repository = new DataRepository();
        repository.saveAllLocations(locations);
    }
}
