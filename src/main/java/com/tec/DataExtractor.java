package com.tec;

import com.tec.dto.Location;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class DataExtractor {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, SQLException, ClassNotFoundException {
        DataRepository repository = new DataRepository();

        for (String date: new String[]{Day.getTodayDate(), Day.getYesterdayDate(), Day.getBeforeYesterdayDate()}) {
            for (int cycle : IntStream.range(0, 6).toArray()) {
                ArrayList<Location> locations = DataController.fetchData(date, cycle);
                repository.setDate(date);
                repository.setCycle(cycle);
                repository.saveAllLocations(locations);
            }
        }
    }
}
