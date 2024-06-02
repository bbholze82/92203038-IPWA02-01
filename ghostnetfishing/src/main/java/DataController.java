import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DataController {
    public DataController() {
    }
    private final DataService dataService = new DataService();
    private final DataServiceGeoNames dataServiceGeoNames = new DataServiceGeoNames();

    public String loginUser(LoginBean inputBean) throws ClassNotFoundException {
        return dataService.login(inputBean);
    }

    public void sendNewGhostNetData(Integer inputNewId, String inputLatitude, String inputLongitude, Integer inputSize) throws ClassNotFoundException {
    	dataService.sendNewGhostNetData(inputNewId, inputLatitude, inputLongitude, inputSize);
    }

    public Integer sumEntriesInDBByStatus(Integer statusValue) throws ClassNotFoundException {
    	return dataService.sumEntriesInDBByStatus(statusValue);
    }

    public List<GhostNetBean> getAllRecoveredGhostNetsByUserId(Integer inputUserId) throws ClassNotFoundException {
    	return dataService.getAllRecoveredGhostNetsByUserId(inputUserId);
    }

    public GhostNetBean getGhostNetBeanById(int inputId) throws ClassNotFoundException {
    	return dataService.getGhostNetBeanById(inputId);
    }

    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {
    	return dataService.getAllGhostNets();
    }

    public String getAttributesFromDBUsers(Integer inputUserId, int modeSwitch) throws ClassNotFoundException {
    	return dataService.getAttributesFromDBUsers(inputUserId, modeSwitch);
    }

    public void createReportForGhostNet(Integer inputUserId, Integer inputGhostNetId, int inputStatusId) throws ClassNotFoundException {
    	dataService.createReportForGhostNet(inputUserId, inputGhostNetId, inputStatusId);
    }

    public String getSnippetForMapMarker(Integer inputStatusId) {
    	return dataService.getSnippetForMapMarker(inputStatusId);
    }

    public int getLatestGhostNetId() throws ClassNotFoundException {
    	return dataService.getLatestGhostNetId();
    }

    public List<ReportBean> getReportsByUserId(Integer inputUserId) throws ClassNotFoundException {
    	return dataService.getReportsByUserId(inputUserId);
    }

    public Integer getCurrentUnixTime() {
    	return dataService.getCurrentUnixTime();
    }

    public String getDurationHumanReadable (Integer inputTimestampA, Integer inputTimestampB) {
    	return dataService.getDurationHumanReadable(inputTimestampA, inputTimestampB);
    }

    public String getLabelForPositionFromCache(int inputGhostNetId) throws ClassNotFoundException {
    	return dataServiceGeoNames.getLabelForPositionFromCache(inputGhostNetId);
    }

    public String getLabelForPositionFromService(Integer inputGhostNetId, String inputLatitude, String inputLongitude) throws ClassNotFoundException {
        return dataServiceGeoNames.getPositionLabelFromService(inputLatitude, inputLongitude);
    }

    public void saveGeoNameResultToCache(Integer inputGhostNetId,  String inputGeoNameLabel) throws ClassNotFoundException {
    	dataServiceGeoNames.saveGeoNameResultToCache(inputGhostNetId, inputGeoNameLabel);
    }

    public List<GhostNetBean> getGhostNetsByStatusId(Integer inputStatusId) throws ClassNotFoundException {
    	return dataService.getGhostNetsByStatusId(inputStatusId);
    }
    
    public Map<Integer, String> getLabels(Integer inputModeSwitch) throws ClassNotFoundException {
    	return dataService.getLabels(inputModeSwitch);
    }
    
    public String getLabel(LabelBean inputLabelBean, Integer inputModeSwitch, Integer inputWorkVal, Integer inputLanguageId) {
    	return dataService.getLabel(inputLabelBean, inputModeSwitch, inputWorkVal, inputLanguageId);
    }
    
    public String getDetailsForMapMarker(Integer inputFirstReportedTimeStampLabel, String inputUserNameLabel, String inputStatusLabel, Integer inputLanguageId) {
    	return dataService.getDetailsForMapMarker(inputFirstReportedTimeStampLabel, inputUserNameLabel, inputStatusLabel, inputLanguageId);
    }
    
    public Boolean testCompareLatestStatusId(int inputGhostNetId, int inputCompareStatusVal) throws ClassNotFoundException {
    	return dataService.testCompareLatestStatusId(inputGhostNetId, inputCompareStatusVal);
    }
    
    public void testDeleteGhostNet(Integer inputValueId) throws ClassNotFoundException {
    	dataService.testDeleteGhostNet(inputValueId);
    }
    
    public void testDeleteReports(Integer inputValueId) throws ClassNotFoundException {
    	dataService.testDeleteReports(inputValueId);
    }
    
    public String getUnixTimestampHumanReadableForLog() {
    	return dataService.getUnixTimestampHumanReadableForLog();
    }
    
}
