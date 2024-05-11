import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private static final String DB_URL="jdbc:mysql://localhost:3306/ghostnetdata";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";


    public String login(String username, String password) throws ClassNotFoundException {
        String userStatus = "";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE BINARY username = ? AND BINARY password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Benutzername und Passwort stimmen überein
                if (resultSet.getString("username").equals("admin")){
                    userStatus = "admin";
                    
                } else if (resultSet.getString("username").equals("hunter")) {
            	userStatus = "hunter";
                }
            } else {
                // Benutzername und Passwort stimmen nicht überein
                userStatus = "Wrong username or password";
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userStatus;
    }

    
    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {

        List<GhostNetBean> results = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM ghostnets";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String id = resultSet.getString("id");
                    String latitude =  resultSet.getString("latitude");
                    String longitude =  resultSet.getString("longitude");
                    //String author = resultSet.getString("author");
                    String statusCode = resultSet.getString("statuscode");
                    String reportTimestamp = resultSet.getString("reportts");
                    GhostNetBean workGhostNet = new GhostNetBean();
                    
                    workGhostNet.setId(id);
                    workGhostNet.setPosLatitude(latitude);
                    workGhostNet.setPosLongitude(longitude);
                    workGhostNet.setStatusCode(Integer.valueOf(statusCode));
                    workGhostNet.setReportTimestamp(reportTimestamp);
                    workGhostNet.setReportedBy(3);
                    
                    results.add(workGhostNet);
                }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    	
    }
    
    
    public void sendNewGhostNetData(String inputLatitude, String inputLongitude) throws ClassNotFoundException {
        //String pendingStatus = "";
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO ghostnets (latitude, longitude, statuscode) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputLatitude);
            statement.setString(2, inputLongitude);
            statement.setString(3, "1");
            if (statement.executeUpdate() == 0){
                //pendingStatus = "Request failed";
            }else{
                //pendingStatus = "Request sent successfully";
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
    public Integer getSumofGhostNetEntriesInDB() throws ClassNotFoundException {
    	Integer result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM ghostnets";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {              
                result++;
            }
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public void earmarkGhostNet(String inputId, String inputEarmarkedBy) throws ClassNotFoundException {
    	
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ghostnets SET earmarkedby = ?, statuscode = ? WHERE id = ?");
            updateStatement.setString(1, inputEarmarkedBy);
            updateStatement.setString(2, "2");
            updateStatement.setString(3, inputId);
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    	
       
}