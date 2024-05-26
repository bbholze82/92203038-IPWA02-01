import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.TimeZone;

public class DataService {

    private static final String DB_URL="jdbc:mysql://localhost:3306/ghostnetdata";
    private static final String DB_USERNAME="root";
    private static final String DB_PASSWORD="";


    public String login(LoginBean inputBean) throws ClassNotFoundException {
        String loginCase = "";
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
                	String hashedpassword = resultSet.getString("hashedpassword");
                	String salt = resultSet.getString("salt");
                	
                	inputBean.setId(id);
                	inputBean.setRole(role);
                	inputBean.setFirstname(getAttributesFromDBUsers(id, 2));
                	inputBean.setLastname(getAttributesFromDBUsers(id, 3));
                	inputBean.setPhonenumber(getAttributesFromDBUsers(id, 4));
                	inputBean.setHashedPassword(hashedpassword);
                	inputBean.setSalt(salt);

                	
                	if (role == 1) {
                        inputBean.setAdminPrivileges(true);
                	} else {
                        inputBean.setAdminPrivileges(false);
                	}
                	
                    // Login von Usern der Systemrolle (2) blockieren
                    if (inputBean.getRole() == 2) {
                    	loginCase = "failed";
                    	return loginCase;
                    	
                    } else {
                    	loginCase = "backend";
                    	inputBean.setIsLoggedIn(true);
                    	return loginCase;
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
        
        return loginCase;
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
    
    public GhostNetBean getGhostNetBeanById(int inputId) throws ClassNotFoundException {

        GhostNetBean workGhostNetBean = new GhostNetBean();
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            query = "SELECT * FROM ghostnets WHERE id=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, inputId);
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	
            	GhostNetBean workGhostNet = new GhostNetBean();

                Integer id = Integer.valueOf(resultSet.getString("id"));
                String latitude =  resultSet.getString("latitude");
                String longitude =  resultSet.getString("longitude");
                Integer size =  resultSet.getInt("size");

                workGhostNet.setId(id);
                workGhostNet.setPosLatitude(latitude);
                workGhostNet.setPosLongitude(longitude);
                workGhostNet.setSize(size);
                
                workGhostNet.setFirstReportBean(getReportBeanForGhostNet(id, 1));
                workGhostNet.setLatestReportBean(getReportBeanForGhostNet(id, 99));  
                
        }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return workGhostNetBean;
    }
    
    
    public List<GhostNetBean> getAllRecoveredGhostNetsByUserId(Integer inputUserId) throws ClassNotFoundException {
    	
    	String defaultQuery = "SELECT gn.id, gn.latitude, gn.longitude, gn.size" + " " +
          		 "FROM ghostnets gn" + " " +
          		 "JOIN (" + " " +
          		 "    SELECT ghostnet, MAX(timestamp) AS max_timestamp" + " " +
          		 "    FROM reports" + " " +
          		 "    GROUP BY ghostnet" + " " +
          		 ") latest_reports ON gn.id = latest_reports.ghostnet" + " " +
          		 "JOIN reports rep ON rep.ghostnet = gn.id AND rep.timestamp = latest_reports.max_timestamp" + " " +
          		 "WHERE rep.status = 3 AND rep.user = ?;";
       	
       	String workQuery;
      	workQuery = defaultQuery;
      	List<GhostNetBean> results = new ArrayList<>();

      	Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        PreparedStatement statement = connection.prepareStatement(workQuery);
        statement = connection.prepareStatement(workQuery);
        statement.setInt(1, inputUserId);

        ResultSet resultSet = statement.executeQuery();
                
        while (resultSet.next()) {
                	
	    	GhostNetBean workGhostNet = new GhostNetBean();
	
	        Integer id = Integer.valueOf(resultSet.getString("id"));
	        String latitude =  resultSet.getString("latitude");
	        String longitude =  resultSet.getString("longitude");
	        Integer size =  resultSet.getInt("size");
	
	        workGhostNet.setId(id);
	        workGhostNet.setPosLatitude(latitude);
	        workGhostNet.setPosLongitude(longitude);
	        workGhostNet.setSize(size);
	        
	        workGhostNet.setFirstReportBean(getReportBeanForGhostNet(id, 1));
	        workGhostNet.setLatestReportBean(getReportBeanForGhostNet(id, 99));    
	        
	        results.add(workGhostNet);          
        	}

        	statement.close();
        	
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
       return results;	
    }
    
    
    public List<GhostNetBean> getGhostNetsByStatusId(int inputStatusId) throws ClassNotFoundException {
    	
    	String defaultQuery = "SELECT gn.id, gn.latitude, gn.longitude, gn.size" + " " +
       		 "FROM ghostnets gn" + " " +
       		 "JOIN (" + " " +
       		 "    SELECT ghostnet, MAX(timestamp) AS max_timestamp" + " " +
       		 "    FROM reports" + " " +
       		 "    GROUP BY ghostnet" + " " +
       		 ") latest_reports ON gn.id = latest_reports.ghostnet" + " " +
       		 "JOIN reports rep ON rep.ghostnet = gn.id AND rep.timestamp = latest_reports.max_timestamp" + " " +
       		 "WHERE rep.status = ?;";

    	String allActive12 = "SELECT gn.id, gn.latitude, gn.longitude, gn.size" + " " +
          		 "FROM ghostnets gn" + " " +
           		 "JOIN (" + " " +
           		 "    SELECT ghostnet, MAX(timestamp) AS max_timestamp" + " " +
           		 "    FROM reports" + " " +
           		 "    GROUP BY ghostnet" + " " +
           		 ") latest_reports ON gn.id = latest_reports.ghostnet" + " " +
           		 "JOIN reports rep ON rep.ghostnet = gn.id AND rep.timestamp = latest_reports.max_timestamp" + " " +
           		 "WHERE rep.status = ? OR rep.status = ?;";
    	
    	String workQuery;
    	
    	List<GhostNetBean> results = new ArrayList<>();
         Class.forName("com.mysql.cj.jdbc.Driver");
         try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
             String query = null;
             PreparedStatement statement = null;
             ResultSet resultSet = null;
             
             switch (inputStatusId) {
             case 12:
            	 workQuery = allActive12;
            	 break;
             default:
            	 workQuery = defaultQuery;
            	 break;
             }

             statement = connection.prepareStatement(workQuery);
             
             switch (inputStatusId) {
             case 12:
            	 Integer value1 = Integer.valueOf(String.valueOf(inputStatusId).substring(0,1));
            	 Integer value2 = Integer.valueOf(String.valueOf(inputStatusId).substring(1));
                 statement.setInt(1, value1);
                 statement.setInt(2, value2);
                 break;
             default:
                 statement.setInt(1, inputStatusId);
                 break;
             }
             
             resultSet = statement.executeQuery();
             
             while (resultSet.next()) {
             	
             	GhostNetBean workGhostNet = new GhostNetBean();

                Integer id = Integer.valueOf(resultSet.getString("id"));
                String latitude =  resultSet.getString("latitude");
                String longitude =  resultSet.getString("longitude");
                Integer size =  resultSet.getInt("size");

                workGhostNet.setId(id);
                workGhostNet.setPosLatitude(latitude);
                workGhostNet.setPosLongitude(longitude);
                workGhostNet.setSize(size);
                 
                workGhostNet.setFirstReportBean(getReportBeanForGhostNet(id, 1));
                workGhostNet.setLatestReportBean(getReportBeanForGhostNet(id, 99));    
                 
                results.add(workGhostNet);
                 
         }

             statement.close();
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return results;
    }
    
    
    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {

        List<GhostNetBean> results = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            query = "SELECT * FROM ghostnets";
            statement = connection.prepareStatement(query);
            
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	
            	GhostNetBean workGhostNet = new GhostNetBean();

                Integer id = Integer.valueOf(resultSet.getString("id"));
                String latitude =  resultSet.getString("latitude");
                String longitude =  resultSet.getString("longitude");
                Integer size =  resultSet.getInt("size");
                
                workGhostNet.setId(id);
                workGhostNet.setPosLatitude(latitude);
                workGhostNet.setPosLongitude(longitude);
                workGhostNet.setSize(size);
                
                workGhostNet.setFirstReportBean(getReportBeanForGhostNet(id, 1));
                workGhostNet.setLatestReportBean(getReportBeanForGhostNet(id, 99));
                		
        		results.add(workGhostNet);                
        }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    	
    }
     
    public ReportBean getReportBeanForGhostNet(Integer ghostNetId, Integer modeswitch) throws ClassNotFoundException {
    	ReportBean workReportBean = new ReportBean();
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        	
            String query = "SELECT * FROM reports";
            
            switch (modeswitch) {
            case 1:
                query = "SELECT * FROM reports WHERE ghostnet=? ORDER BY timestamp ASC LIMIT 1";
            	break;
            case 99:
                query = "SELECT * FROM reports WHERE ghostnet=? ORDER BY timestamp DESC LIMIT 1";
            	break;
            }
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, ghostNetId.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
            	Integer workReportId = Integer.valueOf(resultSet.getString("id"));
            	Integer workUserId = Integer.valueOf(resultSet.getString("user"));
            	Integer workGhostNetId = Integer.valueOf(resultSet.getString("ghostnet"));
            	Integer workStatusId = Integer.valueOf(resultSet.getString("status"));
            	Integer workTimestamp = Integer.valueOf(resultSet.getString("timestamp"));
            	String workTimestampLabel = encodeUnixTimestampHumanReadable(workTimestamp.toString());
            	
            	workReportBean.setId(workReportId);
            	workReportBean.setUserId(workUserId);
            	workReportBean.setStatusId(workStatusId);
            	workReportBean.setTimestamp(workTimestamp);
            	workReportBean.setTimestampLabel(workTimestampLabel);
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	return workReportBean;
    } 
    
    public void createReportForGhostNet(Integer inputUserId, Integer inputGhostNetId, Integer inputStatusId) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO reports (user, ghostnet, status, timestamp) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            // id, ghostnet, status, timestamp
            statement.setInt(1, inputUserId);
            statement.setInt(2, inputGhostNetId);
            statement.setInt(3, inputStatusId);
            statement.setInt(4, Integer.valueOf(getCurrentUnixTime()));
            //
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void sendNewGhostNetData(Integer inputNewId, String inputLatitude, String inputLongitude, Integer inputSize) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "INSERT INTO ghostnets (id, latitude, longitude, size) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            //
            statement.setInt(1, inputNewId);
            statement.setString(2, inputLatitude);
            statement.setString(3, inputLongitude);
            statement.setInt(4, inputSize);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public String getAttributesFromDBUsers(Integer inputUserId, int modeSwitch) throws ClassNotFoundException {
    	String resultTxt = "";
    	
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "";
            
            switch(modeSwitch) {
            case 1:
       		     query = "SELECT * FROM users WHERE id = ?";
            	break;
            case 2:
        		query = "SELECT * FROM userdetails WHERE userid = ?";
            	break;
            case 3:
        		query = "SELECT * FROM userdetails WHERE userid = ?";
            	break;
            case 4:
        		query = "SELECT * FROM userdetails WHERE userid = ?";
            	break;	
            }
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, inputUserId.toString());
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                switch (modeSwitch) {
                	case 1:
                    	resultTxt = resultSet.getString("username");
                		break;
                	case 2:
                    	resultTxt = resultSet.getString("firstname");
                		break;
                	case 3:
                    	resultTxt = resultSet.getString("lastname");
                		break;
                	case 4:
                    	resultTxt = resultSet.getString("phonenumber");
                		break;

                }
            }
            
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	return resultTxt;
    }    
    
    public Integer sumEntriesInDBByStatus(Integer statusValue) throws ClassNotFoundException {
    	Integer result = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
        	String simpleQuery = "SELECT * FROM ghostnets";
            String extendedQuery = "SELECT COUNT(*) AS result" + " " +
            		"FROM ghostnets g" + " " +
            		"JOIN (" + " " +
            		    "SELECT r.ghostnet, MAX(r.timestamp) AS max_timestamp" + " " +
            		    "FROM reports r" + " " +
            		    "GROUP BY r.ghostnet" + " " +
            		") latest_reports ON g.id = latest_reports.ghostnet" + " " +
            		"JOIN reports r ON g.id = r.ghostnet AND latest_reports.max_timestamp = r.timestamp" + " " +
            		"WHERE r.status = ?;";

            PreparedStatement statement;
            
            if (statusValue == 0) {
               statement = connection.prepareStatement(simpleQuery);

            } else {
                statement = connection.prepareStatement(extendedQuery);
                statement.setInt(1, statusValue);
            }
            
			ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {              
                result = resultSet.getInt("result");
            }
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    

    public String getDurationHumanReadable(Integer timestampA, Integer timestampB) {
        try {
            long unixTimestampA = timestampA.longValue();
            long unixTimestampB = timestampB.longValue();

            LocalDateTime dateTimeA = LocalDateTime.ofEpochSecond(unixTimestampA, 0, ZoneOffset.UTC);
            LocalDateTime dateTimeB = LocalDateTime.ofEpochSecond(unixTimestampB, 0, ZoneOffset.UTC);

            Duration duration = Duration.between(dateTimeA, dateTimeB);

            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;

            if (days > 0) {
                return days + " day(s)";
            } else if (hours > 0) {
                return hours + " hour(s) and " + minutes + " minute(s)";
            } else {
                return minutes + " minute(s)";
            }
        //
        } catch (NumberFormatException e) {
            return "Invalid format";
        }
    }
    
    public List<ReportBean> getReportsByUserId(Integer inputUserId) throws ClassNotFoundException {
        List<ReportBean> results = new ArrayList<>();
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = null;
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            query = "SELECT * FROM reports WHERE user = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, inputUserId);
            resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	
            	ReportBean workReportBean = new ReportBean();

                Integer id = Integer.valueOf(resultSet.getString("id"));
                Integer ghostnet =  resultSet.getInt("ghostnet");
                Integer status =  resultSet.getInt("status");
                Integer timestamp =  resultSet.getInt("timestamp");

                workReportBean.setId(id);
                workReportBean.setGhostNetId(ghostnet);
                workReportBean.setStatusId(status);
                workReportBean.setTimestamp(timestamp);
                
                results.add(workReportBean);
        }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
    
    
    public Integer getLatestGhostNetId() throws ClassNotFoundException {
    	Integer lastestId = 0;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query= "SELECT id FROM ghostnets ORDER BY id DESC LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {              
            	lastestId = resultSet.getInt("id");
            }
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lastestId;
    }
    
    public String getLabelById(int modeswitch, int inputId) throws ClassNotFoundException {
        String labelTxt = "";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String query = "";
            
            switch(modeswitch) {
                case 1:
                    query= "SELECT label FROM ghostnetsizes WHERE id=?";
                    break;
                case 2:
                    query= "SELECT label FROM statuscodes WHERE id=?";
                    break;
                case 3:
                    query= "SELECT label FROM userroles WHERE id=?";
                    break;
                default:
                    throw new IllegalArgumentException("Invalid mode switch value");
            }
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, inputId);
            ResultSet resultSet = statement.executeQuery();
   
            if (resultSet.next()) {              
                labelTxt = resultSet.getString("label");
            }
            
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labelTxt;
    }
    
    //
    // helper
    //
    public String encodeUnixTimestampHumanReadable(String unixTimestamp) {
        // Parse the Unixzeit string into a long value
       long unixSeconds = Long.parseLong(unixTimestamp);
        
       // Create a Date object from the Unix seconds
       Date date = new Date(unixSeconds * 1000L);
         
       // Define the desired date format
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            
       // Optionally set the timezone if needed, here using UTC
       sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
         
       // Format the date into the desired format and return the string
        String resultTxt = sdf.format(date);
        
    	return resultTxt;
    }
    
    public Integer getCurrentUnixTime() {
        // System.currentTimeMillis() gibt die aktuelle Zeit in Millisekunden zurück
        long currentTimeMillis = System.currentTimeMillis();
        // Unixzeit in Sekunden berechnen
        long unixTimeSeconds = currentTimeMillis / 1000L;
        // Unixzeit als String zurückgeben
        Integer unixTime = (int) unixTimeSeconds;
        return unixTime;
    }
    
    public String getSnippetForMapMarker(Integer inputStatusId) {
    	String markerColor = "blackIcon";
    	switch (inputStatusId) {
    	//	
    	case 1:
    		markerColor = "redIcon";
    		break;
    	case 2:
    		markerColor = "yellowIcon";
    		break;
    	case 3:
    		markerColor = "greenIcon";
    		break;
    	case 4:
    		markerColor = "greyIcon";
    		break;
    	case 5:
    		markerColor = "blackIcon";
    		break;
    	}
    	String markerCode = "{icon: " + markerColor + "}";
    	return markerCode;
    }
    


    
}    
