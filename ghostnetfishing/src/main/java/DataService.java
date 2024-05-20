import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class DataService {

    private static final String DB_URL="jdbc:mysql://localhost:3306/ghostnetdata";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";


    public Boolean login(LoginBean inputBean) throws ClassNotFoundException {
        Boolean loginSuccessfully = false;
        String workUsername = inputBean.getUsername();
        String workInputPassword = inputBean.getUsedPassword();
        inputBean.cleanUpUsedPassword();
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "SELECT * FROM users WHERE BINARY username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, workUsername);

            ResultSet resultSet = statement.executeQuery();
            
            // Benutzername gefunden
            if (resultSet.next()) {
            	
            	String workSalt =  resultSet.getString("salt");
            	String workDBHashedPassword = resultSet.getString("hashedpassword");
            	
            	Boolean passwordCheckSuccessfully = verifyPassword(workInputPassword, workSalt, workDBHashedPassword);
            	
            	if (passwordCheckSuccessfully) {
            		Integer id = Integer.valueOf(resultSet.getString("id"));
                	Integer role = Integer.valueOf(resultSet.getString("role"));
                	String firstname = resultSet.getString("firstname");
                	String lastname = resultSet.getString("lastname");
                	String phonenumber = resultSet.getString("phonenumber");
                	String hashedpassword = resultSet.getString("hashedpassword");
                	String salt = resultSet.getString("salt");
                	
                	inputBean.setId(id);
                	inputBean.setRole(role);
                	inputBean.setFirstname(firstname);
                	inputBean.setLastname(lastname);
                	inputBean.setPhonenumber(phonenumber);
                	inputBean.setHashedPassword(hashedpassword);
                	inputBean.setSalt(salt);

                    // Login von Usern der Systemrolle (3) blockieren
                    if (inputBean.getRole() == 3) {
                    	return loginSuccessfully;
                    } else {
                    	loginSuccessfully = true;                   
                    }
            	}
            }
            
            statement.close();
        
        //
        } catch (SQLException e) {
            e.printStackTrace();
        //
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return loginSuccessfully;
    }

    
    // Methode zum Hashen des Passworts mit der Salt
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Methode zur Überprüfung des Passworts
    public static boolean verifyPassword(String enteredPassword, String storedSalt, String storedHash) throws NoSuchAlgorithmException {
        String hashedEnteredPassword = hashPassword(enteredPassword, storedSalt);
        return hashedEnteredPassword.equals(storedHash);
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
        	case 2:
        		query = "SELECT * FROM ghostnets WHERE statuscode = ? OR statuscode = ?";
        		statement = connection.prepareStatement(query);
        		statement.setString(1, "1");
        		statement.setString(2, "2");
        		break;
    		case 3:
    			query = "SELECT * FROM ghostnets WHERE statuscode = ?";
    			statement = connection.prepareStatement(query);
    			statement.setString(1, "3");
    			break;
    	}
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	
                GhostNetBean workGhostNet = new GhostNetBean();

                Integer id = Integer.valueOf(resultSet.getString("id"));
                String latitude =  resultSet.getString("latitude");
                String longitude =  resultSet.getString("longitude");
                //String author = resultSet.getString("author");
                
                String statusCode = resultSet.getString("statuscode");
            	Integer size = Integer.valueOf(resultSet.getString("size"));
            	String reportts = resultSet.getString("reportts");

                
                Integer reportedByUserId;
                try {
                	 reportedByUserId = Integer.valueOf(resultSet.getString("reportedby"));
                     workGhostNet.setReportedByUserId(reportedByUserId);
                     workGhostNet.setReportedByKnowUser(true);
                } catch (Exception e) {
                    workGhostNet.setReportedByKnowUser(false);
                }
                
                Integer salvageClaimedByUserId;
                try {
                	salvageClaimedByUserId = Integer.valueOf(resultSet.getString("claimedby"));
                    workGhostNet.setSalvageClaimedByUserId(salvageClaimedByUserId);
                    workGhostNet.setSalvageIsClaimed(true);
                } catch (Exception e) {
                	workGhostNet.setSalvageIsClaimed(false);
                }
                
                String reportTimestamp = resultSet.getString("reportts");
                String claimedTimestamp = resultSet.getString("claimedts");
                String lastEditTimestamp = resultSet.getString("lasteditts");

                
                workGhostNet.setId(id);
                workGhostNet.setPosLatitude(latitude);
                workGhostNet.setPosLongitude(longitude);
                workGhostNet.setSize(size);
                workGhostNet.setStatusCode(Integer.valueOf(statusCode));
                workGhostNet.setReportTimestamp(reportts);
                workGhostNet.setClaimedTimestamp(claimedTimestamp);
                workGhostNet.setLastEditTimestamp(lastEditTimestamp);
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
        	
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE ghostnets SET claimedby = ?, statuscode = ?, claimedts = ?, lasteditts = ? WHERE id = ?");
           
            switch (modeSwitch)  {
            case 1:
                updateStatement.setString(1, inputUserId.toString());
                updateStatement.setString(2, "2");
                updateStatement.setString(3, getCurrentUnixTime());
                updateStatement.setString(4, getCurrentUnixTime());
                updateStatement.setString(5, inputGhostNet.getId().toString());

            	break;
            case 2:
                updateStatement.setString(1, null);
                updateStatement.setString(2, "1");
                updateStatement.setString(3, null);
                updateStatement.setString(4, getCurrentUnixTime());
                updateStatement.setString(5, inputGhostNet.getId().toString());
            	break;
            
        	case 3:
        		updateStatement.setString(1, null);
        		updateStatement.setString(2, "3");
                updateStatement.setString(3, getCurrentUnixTime());
                updateStatement.setString(4, getCurrentUnixTime());
        		updateStatement.setString(5, inputGhostNet.getId().toString());

        		break;
        	}
            
            updateStatement.executeUpdate();
            updateStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void sendNewGhostNetData(String inputLatitude, String inputLongitude, Integer inputSize, Integer reportedByUserId) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO ghostnets (latitude, longitude, size, statuscode, reportedBy, reportts, lasteditts) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            
            statement.setString(1, inputLatitude);
            statement.setString(2, inputLongitude);
            statement.setString(3, inputSize.toString());
            statement.setString(4, "1");
            
            if (reportedByUserId == 0) {
            	statement.setString(5, null);
            } else {
                statement.setString(5, reportedByUserId.toString());
            }
            
            statement.setString(6, getCurrentUnixTime());
            statement.setString(7, getCurrentUnixTime());

            statement.executeUpdate();
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
    
    
    public Integer sumEntriesInDBByStatus(Integer statusValue) throws ClassNotFoundException {
    	Integer result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "";
            
            switch (statusValue) {
            
            case 0:
            	query= "SELECT * FROM ghostnets";
            	break;
            case 1:
            	query= "SELECT * FROM ghostnets WHERE statuscode=1";
            	break;
            case 2:
            	query= "SELECT * FROM ghostnets WHERE statuscode=2";
            	break;
            case 3:
            	query= "SELECT * FROM ghostnets WHERE statuscode=3";
            	break;
            case 4:
            	query= "SELECT * FROM ghostnets WHERE statuscode=4";
            	break;
            }
            
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
    
    
    public static String getCurrentUnixTime() {
        // System.currentTimeMillis() gibt die aktuelle Zeit in Millisekunden zurück
        long currentTimeMillis = System.currentTimeMillis();
        // Unixzeit in Sekunden berechnen
        long unixTimeSeconds = currentTimeMillis / 1000L;
        // Unixzeit als String zurückgeben
        return Long.toString(unixTimeSeconds);
    }

}