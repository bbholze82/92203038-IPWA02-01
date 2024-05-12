import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private static final String DB_URL="jdbc:mysql://localhost:3306/ghostnetdata";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";


    public Boolean login(LoginBean inputBean) throws ClassNotFoundException {
        Boolean loginSuccessfully = false;
        String workUsername = inputBean.getUsername();
        String workPassword = inputBean.getPassword();
        
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE BINARY username = ? AND BINARY password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, workUsername);
            statement.setString(2, workPassword);

            ResultSet resultSet = statement.executeQuery();
            
            // Benutzername und Passwort stimmen überein
            if (resultSet.next()) {
            	
            	Integer id = Integer.valueOf(resultSet.getString("id"));
            	Integer role = Integer.valueOf(resultSet.getString("role"));
            	String firstname = resultSet.getString("firstname");
            	String lastname = resultSet.getString("lastname");
            	String phonenumber = resultSet.getString("phonenumber");

            	
            	inputBean.setId(id);
            	inputBean.setRole(role);
            	inputBean.setFirstname(firstname);
            	inputBean.setLastname(lastname);
            	inputBean.setPhonenumber(phonenumber);
            	
                // Login von Usern der Systemrolle (3) blockieren
                if (inputBean.getRole() == 3) {
                	return loginSuccessfully;
                } else {
                	loginSuccessfully = true;                   
                }
            	
            }
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return loginSuccessfully;
    }

    
    public List<GhostNetBean> getAllGhostNets(int modeSwitch) throws ClassNotFoundException {

        List<GhostNetBean> results = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            switch (modeSwitch) {
            case 0:
            	query = "SELECT * FROM ghostnets";
            	statement = connection.prepareStatement(query);
            	break;
            case 1:
            	query = "SELECT * FROM ghostnets WHERE statuscode = ?";
            	statement = connection.prepareStatement(query);
                statement.setString(1, "1");
            	break;
            }
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Integer id = Integer.valueOf(resultSet.getString("id"));
                String latitude =  resultSet.getString("latitude");
                String longitude =  resultSet.getString("longitude");
                //String author = resultSet.getString("author");
                String statusCode = resultSet.getString("statuscode");
               
                Integer reportedByUserId;
                try {
                	 reportedByUserId = Integer.valueOf(resultSet.getString("reportedby"));
                } catch (Exception e) {
                	reportedByUserId = 2;
                }
                
                Integer salvageClaimedByUserId;
                try {
                	salvageClaimedByUserId = Integer.valueOf(resultSet.getString("claimedby"));
                } catch (Exception e) {
                	salvageClaimedByUserId = 2;
                }
                
                String reportTimestamp = resultSet.getString("reportts");


                GhostNetBean workGhostNet = new GhostNetBean();
                
                workGhostNet.setId(id);
                workGhostNet.setPosLatitude(latitude);
                workGhostNet.setPosLongitude(longitude);
                workGhostNet.setStatusCode(Integer.valueOf(statusCode));
                workGhostNet.setReportTimestamp(reportTimestamp);
                workGhostNet.setReportedByUserId(reportedByUserId);
                workGhostNet.setSalvageClaimedByUserId(salvageClaimedByUserId);

                
                
                results.add(workGhostNet);
        }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    	
    }
    
    
    public void editSalvageStatusOfGhostNet(GhostNetBean inputGhostNet, Integer inputUserId, int modeSwitch) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        	
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ghostnets SET  claimedby = ?, statuscode = ? WHERE id = ?");
           
            switch (modeSwitch)  {
            case 1:
                updateStatement.setString(1, inputUserId.toString());
                updateStatement.setString(2, "2");
                updateStatement.setString(3, inputGhostNet.getId().toString());
            	break;
            case 2:
                updateStatement.setString(1, null);
                updateStatement.setString(2, "1");
                updateStatement.setString(3, inputGhostNet.getId().toString());
            	break;
            }
            
            updateStatement.executeUpdate();
            updateStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void sendNewGhostNetData(String inputLatitude, String inputLongitude) throws ClassNotFoundException {
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
    
    
    public String getAttributesFromDBUsers(Integer inputUserId, int modeSwitch) throws ClassNotFoundException {
    	String result = "";
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE BINARY id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUserId.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

            	String username = resultSet.getString("username");

                switch (modeSwitch) {
                	case 1:
                		result = username;
                		break;
                }
            }
            
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	return result;
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
    

}