import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

@Named
@RequestScoped
public class GhostNetBean implements Serializable {
    private Integer id;
    private String posLongitude;
    private String posLatitude;
    private Integer size; 
    private String author;
    private Integer statusCode;
    private Integer reportedByUserId;
    private String reportTimestamp;
    private Boolean reportedByKnownUser;
    private Integer salvageClaimedByUserId;
    private Boolean salvageIsClaimed;
    private String claimedTimestamp;
    private String lastEditTimestamp;
    
    private final DataController dataController = new DataController();

    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public String getPosLatitude() {
        return this.posLatitude;
    }
    
    public void setPosLatitude(String posLatitude) {
    	this.posLatitude = posLatitude;
    }
    
    
    public String getPosLongitude() {
        return this.posLongitude;
    }
    
    public void setPosLongitude(String posLongitude) {
    	this.posLongitude = posLongitude;
    }
    
    public String getAuthor() {
        return this.author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public void setStatusCode(Integer statusCode) {
    	this.statusCode = statusCode;
    }
    
    public Integer getStatusCode() {
    	return this.statusCode;
    }
    
    public String getStatusCodeLabel() {
    	
    	// Label-Text sollte aus der Datenbank ausgelesen. Dies ist nur eine provisorische Loesung.
    	switch (this.size) {
    	case 1:
    		return "Reported";
		case 2:
    		return "Salvage pending";
		case 3:
    		return "Recovered";
		default:
    		return "Missing";
    	}
    }
    
    public String getCodeForMarker() {
    	
    	String markerColor = "";
    	switch (this.statusCode) {
    		
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
    	default:
    		markerColor = "greyIcon";
    		break;
    	}
    	
    	String markerCode = "{icon: " + markerColor + "}";
    	return markerCode;
    }
    
    public List<GhostNetBean> getAllGhostNets(int modeswitch) throws ClassNotFoundException {
    	return dataController.getAllGhostNets(modeswitch);
    }

    
    public Integer getSalvageClaimedByUserId() {
    	return this.salvageClaimedByUserId;
    }

    public void setSalvageClaimedByUserId(Integer salvageClaimedByUserId) {
    	this.salvageClaimedByUserId = salvageClaimedByUserId;
    }
 
    
    public String submitData(String inputUserId) throws ClassNotFoundException { 
      	dataController.sendNewGhostNetData(this.posLatitude, this.posLongitude, this.size, Integer.valueOf(inputUserId));
    	return "view.xhtml?faces-redirect=true";
    }
    
    public Integer getReportedByUserId() {
    	return this.reportedByUserId;
    }
    
    public void setReportedByUserId(Integer reportedByUserId) {
    	this.reportedByUserId = reportedByUserId;
    }
    
    public String getReportTimestamp() {
        // Parse the Unixzeit string into a long value
       long unixSeconds = Long.parseLong(this.reportTimestamp);
        
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
    
    public void setReportTimestamp(String reportTimestamp) {
    	this.reportTimestamp = reportTimestamp;
    }
    
    public String claimSalvage(Integer userId) throws ClassNotFoundException {
    	this.salvageClaimedByUserId = userId;
    	dataController.editSalvageStatusOfGhostNet(this, userId, 1);
    	return "hunter.xhtml?faces-redirect=true";
    }
    
    public String cancelSalvage(Integer userId) throws ClassNotFoundException {
    	dataController.editSalvageStatusOfGhostNet(this, userId, 2);
    	return "hunter.xhtml?faces-redirect=true";
    }

    
    public String markAsRecovered(Integer userId) throws ClassNotFoundException {
    	dataController.editSalvageStatusOfGhostNet(this, userId, 3);
    	return "hunter.xhtml?faces-redirect=true";
    }
    
    public Boolean getSalvageIsClaimed() {
    	return this.salvageIsClaimed;
    }
    
    public void setSalvageIsClaimed(Boolean inputVal) {
    	this.salvageIsClaimed = inputVal;
    }
    
    public Boolean getReportedByKnowUser() {
    	return this.reportedByKnownUser;
    }
    
    public void setReportedByKnowUser(Boolean inputVal) {
    	this.reportedByKnownUser = inputVal;
    }
    
    public String getSalvageClaimedByUsername() throws ClassNotFoundException {
    	String result = "N.N.";
    	
    	if (!this.salvageIsClaimed) {
    		return result;
    	}
    	
    	if (this.salvageClaimedByUserId >= 1) {
    		return dataController.getAttributesFromDBUsers(this.salvageClaimedByUserId, 1);
    	} else {
    		return result;
    	}
    }
    
    public String getReportedByUsername() throws ClassNotFoundException {
    	String result = "N.N.";
    	
    	if (!this.reportedByKnownUser) {
    		return result;
    	}
    	
    	if (this.reportedByUserId >= 1) {
    		return dataController.getAttributesFromDBUsers(this.reportedByUserId , 1);
    	} else {
    		return result;
    	}
    }
       
    public Integer getSize() {
    	return this.size;
    }
    
    public String getSizeLabel() {
    	
    	// Label-Text sollte aus der Datenbank ausgelesen. Dies ist nur eine provisorische Loesung.
    	switch (this.size) {
    	case 1:
    		return "Small";
		case 2:
    		return "Middle";
		case 3:
    		return "Huge";
		default:
    		return "Unknown";
    	}
    }
    
    public void setSize(Integer sizeVal) {
    	this.size = sizeVal;
    }
    
    public String getClaimedTimestamp() {
    	return this.claimedTimestamp;
    }

    public void setClaimedTimestamp() {
    	long currentTimeMillis = System.currentTimeMillis();
        long unixTimeSeconds = currentTimeMillis / 1000L;    	
    	this.claimedTimestamp =  Long.toString(unixTimeSeconds);
    }
    
    public void setClaimedTimestamp(String timestamp) {    	
    	this.claimedTimestamp = timestamp;
    }
    
    public void removeClaimedTimestamp() {    	
    	this.claimedTimestamp = null;
    }
    
    
    public String getLastEditTimestamp() {
       long unixSeconds = Long.parseLong(this.lastEditTimestamp);
       Date date = new Date(unixSeconds * 1000L);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
       sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
       String resultTxt = sdf.format(date);
       return resultTxt;
    }
    
    public void setLastEditTimestamp() {
    	long currentTimeMillis = System.currentTimeMillis();
        long unixTimeSeconds = currentTimeMillis / 1000L;    	
    	this.lastEditTimestamp =  Long.toString(unixTimeSeconds);
    }
    
    public void setLastEditTimestamp(String timestamp) {  	
    	this.lastEditTimestamp = timestamp;
    }
    
    
}