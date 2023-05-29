import dao.Facility;
import dao.Zone;
import dao.Location;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class DataRepository {

    private final Connection conn;

    private final String database = "tec";
    private final String host = "localhost";
    private final int port = 5432;
    private final String username = "philippe";
    private final String password = "";

    public DataRepository() throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%d/%s", host, port, database);
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", password);

        conn = DriverManager.getConnection(url, properties);
    }

    public void saveAllLocations(ArrayList<dto.Location> locations) {
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
                            (
                                SELECT loc
                                FROM Locations
                                WHERE locName = ?
                            ),
                            ?,?,?,?,?,?,?,?,?,?,?,?
                        )
                    """);
            insertLocation.setString(1, facility.getLocName());
            insertLocation.setObject(2, facility.getLocPurpDesc(), Types.OTHER);
            insertLocation.setObject(3, facility.getLocQty(), Types.OTHER);
            insertLocation.setObject(4, facility.getFlowInd(), Types.OTHER);
            insertLocation.setInt(5, facility.getDc());
            insertLocation.setInt(6, facility.getOpc());
            insertLocation.setInt(7, facility.getTsq());
            insertLocation.setInt(8, facility.getOac());
            insertLocation.setBoolean(9, facility.isIt());
            insertLocation.setBoolean(10, facility.isAuthOverrunInd());
            insertLocation.setBoolean(11, facility.isNomCapExceedInd());
            insertLocation.setBoolean(12, facility.isAllQtyAvail());
            if (facility.getQtyReason().equals("\r")) {
                insertLocation.setNull(13, Types.NULL);
            } else {
                insertLocation.setString(13, facility.getQtyReason());
            }

            final int result = insertLocation.executeUpdate();

            insertLocation.close();

            return result > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean saveZone(Zone zone) {
        try {
            PreparedStatement insertZone = conn.prepareStatement(
                    """
                        INSERT INTO zones (loczn) VALUES(
                            ?
                        )
                    """);
            insertZone.setString(1, zone.getLocZn());

            final int result = insertZone.executeUpdate();
            insertZone.close();

            return result > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
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
