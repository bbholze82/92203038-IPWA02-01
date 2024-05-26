import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.primefaces.shaded.json.JSONObject;

public class DataServiceGeoNames {

    private static final String DB_URL="jdbc:mysql://localhost:3306/ghostnetdata";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";
	private static final String GEONAMESUSERNAME = "sheasepherd";

    public String getPositionLabelFromService(String inputLatitude, String inputLongitude) {
    	  String resultLabel = null;
    	  String baseUrl = "http://api.geonames.org/oceanJSON";

          try (CloseableHttpClient client = HttpClients.createDefault()) {
              String url = String.format("%s?lat=%s&lng=%s&username=%s", baseUrl, inputLatitude, inputLongitude, GEONAMESUSERNAME);
              HttpGet request = new HttpGet(url);
              try (CloseableHttpResponse response = client.execute(request)) {
            	  String jsonResponse = EntityUtils.toString(response.getEntity());
                  JSONObject obj = new JSONObject(jsonResponse);
                  resultLabel = obj.getJSONObject("ocean").getString("name");
              }
          } catch (Exception e) {
              e.printStackTrace();

          }

    	return resultLabel;
    }

    	public String getLabelForPositionFromCache(int inputGhostNetId) throws ClassNotFoundException {
    		String resultLabel = null;

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "SELECT * FROM geonamescache WHERE ghostnetid = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, inputGhostNetId);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                	resultLabel = resultSet.getString("name");
                }

                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

    		return resultLabel;
    	}

    	public void saveGeoNameResultToCache(Integer inputGhostNetId, String inputGeoNameLabel) throws ClassNotFoundException {

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "INSERT INTO geonamescache (ghostnetid, name, timestamp) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                //
                statement.setInt(1, inputGhostNetId);
                statement.setString(2, inputGeoNameLabel);
                statement.setInt(3, getCurrentUnixTime());
                //
                statement.executeUpdate();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

    	}

    	//
    	// helper
    	//
        public Integer getCurrentUnixTime() {
            // System.currentTimeMillis() gibt die aktuelle Zeit in Millisekunden zurück
            long currentTimeMillis = System.currentTimeMillis();
            // Unixzeit in Sekunden berechnen
            long unixTimeSeconds = currentTimeMillis / 1000L;
            // Unixzeit als String zurückgeben
            int unixTime = (int) unixTimeSeconds;
            return unixTime;
        }



    }


