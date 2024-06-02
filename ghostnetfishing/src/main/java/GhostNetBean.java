import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class GhostNetBean implements Serializable {

    private int id;
    private String posLongitude;
    private String posLatitude;
    private Integer size;
    private ReportBean firstReportBean;
    private ReportBean latestReportBean;

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

    public Integer getSize() {
    	return this.size;
    }

    public void setSize(Integer size) {
    	this.size = size;
    }

    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {
    	return dataController.getAllGhostNets();
    }

    public List<GhostNetBean> getGhostNetsByStatusId(Integer inputStatusId) throws ClassNotFoundException {
    	return dataController.getGhostNetsByStatusId(inputStatusId);
    }

    public List<GhostNetBean> getGhostNetsReportedMissing() throws ClassNotFoundException {
    	List<GhostNetBean> results = dataController.getGhostNetsByStatusId(4);
    	return results;
    }

    public String submitData(Integer inputUserId) throws ClassNotFoundException {
      	// new Id
      	this.id = this.getLatestId() + 1;
    	dataController.sendNewGhostNetData(this.id, posLatitude, this.posLongitude, this.size);

      	this.createReport(inputUserId, 1);
        return "view.xhtml?faces-redirect=true";
    }

    public ReportBean getFirstReportBean() {
    	return this.firstReportBean;
    }

    public void setFirstReportBean(ReportBean firstReportBean) {
    	this.firstReportBean = firstReportBean;
    }

    public ReportBean getLatestReportBean() {
    	return this.latestReportBean;
    }

    public void setLatestReportBean(ReportBean latestReportBean) {
    	this.latestReportBean = latestReportBean;
    }

    public void createReport(Integer inputUserId, int statusId)  throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, statusId);
    }

    public String getSnippetForMapMarker() {
    	String htmlSnippet = dataController.getSnippetForMapMarker(this.latestReportBean.getStatusId());
    	return htmlSnippet;
    }

    public Integer getLatestId() throws ClassNotFoundException {
    	Integer latestId = dataController.getLatestGhostNetId();
    	return latestId;
    }

    public void setStatusToReported(Integer inputUserId) throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, 1);
    }

    public void setStatusToSalvagePending(Integer inputUserId) throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, 2);
    }

    public void setStatusToRecovered(Integer inputUserId) throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, 3);
    }

    public void setStatusToReportedMissing(Integer inputUserId) throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, 4);
    }

    public void setStatusToMissing(Integer inputUserId) throws ClassNotFoundException {
    	dataController.createReportForGhostNet(inputUserId, this.id, 5);
    }     

    public String getAgeOfReport() {
    	Integer currentUnixTime = dataController.getCurrentUnixTime();
    	String ageLabel = dataController.getDurationHumanReadable(this.latestReportBean.getTimestamp() , currentUnixTime);

    	return ageLabel;
    }

    public List<ReportBean> getReportsByUserId(Integer inputUserId) throws ClassNotFoundException {
    	return dataController.getReportsByUserId(inputUserId);
    }

    public String getLabelForPosition() throws ClassNotFoundException {
    	String workLabel = dataController.getLabelForPositionFromCache(this.id);
    	Integer workGeoNameId = null;

    	if (workLabel == null) {
    		workLabel = dataController.getLabelForPositionFromService(this.id, this.posLatitude, this.posLongitude);
    		dataController.saveGeoNameResultToCache(this.id, workLabel);
    	}

    	return workLabel;
    }


    public List<GhostNetBean> getAllActiveGhostNets() throws ClassNotFoundException {
    	return dataController.getGhostNetsByStatusId(12);

    }

    public List<GhostNetBean> getAllRecoveredGhostNetsByUserId(Integer inputUserId) throws ClassNotFoundException {
    	return dataController.getAllRecoveredGhostNetsByUserId(inputUserId);
    }

    public String getDetailsForMapMarker(Integer inputFirstReportedTimeStampLabel, String inputUserLabel, String inputStatusLabel, Integer inputLanguageId) throws ClassNotFoundException {
       return dataController.getDetailsForMapMarker(inputFirstReportedTimeStampLabel, inputUserLabel, inputStatusLabel, inputLanguageId);
    }


    public String getViewDetailsLink() {
    	String javaScript = "onclick='highlightRowAndScroll(\\\"" + "id" + this.id + "\\\")'";
    	String resultTxt =  "<a href='#id" + this.id + "'" + " " + javaScript + ">View details</>";

    	//String resultTxt =  "<a href='./details.xhtml'>View details</>";
    	return resultTxt;
    }

}