import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class GhostNetBean implements Serializable {
    private String id;
    private String posLongitude;
    private String posLatitude;
    private String author;
    private Integer statusCode;
    private Integer reportedBy;
    private String earmarkedBy;
    private String reportTimestamp;
    
    private final DataController dataController = new DataController();

    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
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
    
    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {
    	return dataController.getAllGhostNets();
    }
    
    public String getEarmarkedBy() {
    	return this.earmarkedBy;
    }

    public void setEarmarkedBy(String username) {
    	this.earmarkedBy = username;
    }
    
    public String submitData() throws ClassNotFoundException { 
    	dataController.sendNewGhostNetData(this.posLatitude, this.posLongitude);	
    	return "view.xhtml?faces-redirect=true";
    }
    
    public Integer getReportedBy() {
    	return this.reportedBy;
    }
    
    public void setReportedBy(Integer hunterBean) {
    	this.reportedBy = hunterBean;
    }
    
    public String getReportTimestamp() {
    	return this.reportTimestamp;
    }
    
    public void setReportTimestamp(String reportTimestamp) {
    	this.reportTimestamp = reportTimestamp;
    }
    
    public String earmarkIt(String userId) throws ClassNotFoundException {
    	this.setStatusCode(2);
    	dataController.earmarkGhostNet(this.id, userId);
    	return "hunter.xhtml?faces-redirect=true";
    }
   
   
}