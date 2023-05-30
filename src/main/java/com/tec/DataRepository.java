package com.tec;

import com.tec.dao.Facility;
import com.tec.dao.Zone;
import com.tec.dao.Location;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Properties;

public class DataRepository {

    private final Connection conn;
    private final String database = System.getenv("DATABASE_NAME");
    private final String host = System.getenv("DATABASE_HOST");
    private final int port = Integer.parseInt(System.getenv("DATABASE_PORT"));
    private final String username = System.getenv("DATABASE_USER");
    private final String password = System.getenv("DATABASE_PASSWORD");
    private String date;
    private int cycle;

    public DataRepository() throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        conn = DriverManager.getConnection(url, properties);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void saveAllLocations(ArrayList<com.tec.dto.Location> locations) {
        locations.forEach(location -> {
            if (saveFacility(location.extractFacility())) {
            } else if (saveLocation(location.extractLocation())) {
                saveFacility(location.extractFacility());
            } else {
                saveZone(location.extractZone());
                saveLocation(location.extractLocation());
                saveFacility(location.extractFacility());
            }
        });
    }

    public boolean saveFacility(Facility facility) {
        try {
            PreparedStatement insertLocation = conn.prepareStatement(
                    """
                        INSERT INTO Facilities VALUES(
                            ?,
                            ?,
                            (
                                SELECT loc
                                FROM Locations
                                WHERE locName = ?
                            ),
                            ?,?,?,?,?,?,?,?,?,?,?,?
                        )
                    """);
            insertLocation.setDate(1, new Date((new SimpleDateFormat("MM/dd/yyy").parse(date)).getTime()));
            insertLocation.setInt(2, cycle);
            insertLocation.setString(3, facility.getLocName());
            insertLocation.setObject(4, facility.getLocPurpDesc(), Types.OTHER);
            insertLocation.setObject(5, facility.getLocQty(), Types.OTHER);
            insertLocation.setObject(6, facility.getFlowInd(), Types.OTHER);
            checkNullInt(insertLocation, 7, facility.getDc());
            checkNullInt(insertLocation, 8, facility.getOpc());
            checkNullInt(insertLocation, 9, facility.getTsq());
            checkNullInt(insertLocation, 10, facility.getOac());
            insertLocation.setBoolean(11, facility.isIt());
            insertLocation.setBoolean(12, facility.isAuthOverrunInd());
            insertLocation.setBoolean(13, facility.isNomCapExceedInd());
            insertLocation.setBoolean(14, facility.isAllQtyAvail());
            insertLocation.setString(15, facility.getQtyReason());

            final int result = insertLocation.executeUpdate();

            insertLocation.close();

            return result > 0;
        } catch (SQLException e) {
            return false;
        } catch (ParseException e) {
            System.out.println("Enter correct date formatted as MM/dd/yyyy");
            System.exit(1);
            return false;
        }
    }

    private void checkNullInt(PreparedStatement statement, int index, int value) throws SQLException {
        if (value < 0) {
            statement.setNull(index, Types.NULL);
        } else {
            statement.setInt(index, value);
        }
    }

    public void saveZone(Zone zone) {
        try {
            PreparedStatement insertZone = conn.prepareStatement(
                    """
                        INSERT INTO zones (loczn) VALUES(
                            ?
                        )
                    """);
            insertZone.setString(1, zone.getLocZn());

            insertZone.executeUpdate();
            insertZone.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean saveLocation(Location location) {
        try {
            PreparedStatement insertLocation = conn.prepareStatement(
                    """
                        INSERT INTO locations VALUES(
                            ?,
                            ?,
                            (
                                SELECT id
                                FROM zones
                                WHERE loczn = ?
                            )
                        )
                    """);
            insertLocation.setInt(1, location.getLoc());
            insertLocation.setString(2, location.getLocName());
            insertLocation.setString(3, location.getLocZn());

            final int result = insertLocation.executeUpdate();
            insertLocation.close();

            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
