import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class GhostNetBean implements Serializable {
    private Integer id;
    private String posLongitude;
    private String posLatitude;
    private String author;
    private Integer statusCode;
    private Integer reportedByUserId;
    private Integer salvageClaimedByUserId;
    private String reportTimestamp;
    
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
    
    public String getCodeForMarker() {
    	
    	String markerColor = "";
    	switch (this.statusCode) {
    		
    	case 1:
    		markerColor = "greenIcon";
    		break;
    	case 2:
    		markerColor = "yellowIcon";
    		break;
    	case 3:
    		markerColor = "redIcon";
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
 
    
    public String submitData() throws ClassNotFoundException { 
    	dataController.sendNewGhostNetData(this.posLatitude, this.posLongitude);	
    	return "view.xhtml?faces-redirect=true";
    }
    
    public Integer getReportedByUserId() {
    	return this.reportedByUserId;
    }
    
    public void setReportedByUserId(Integer reportedByUserId) {
    	this.reportedByUserId = reportedByUserId;
    }
    
    public String getReportTimestamp() {
    	return this.reportTimestamp;
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
    
    
    public String getSalvageClaimedByUsername() throws ClassNotFoundException {
    	String result = "N.N.";
    	if (this.salvageClaimedByUserId >= 0) {
    		return dataController.getAttributesFromDBUsers(this.salvageClaimedByUserId, 1);
    	} else {
    		return result;
    	}
    }
    
    public String getReportedByUsername() throws ClassNotFoundException {
    	String result = "N.N.";
    	if (this.salvageClaimedByUserId >= 0) {
    		return dataController.getAttributesFromDBUsers(this.reportedByUserId , 1);
    	} else {
    		return result;
    	}
    }
   
   
}