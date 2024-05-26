import java.util.List;

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

    public String getLabelById(int modeeSwitch, int inputId) throws ClassNotFoundException {
    	return dataService.getLabelById(modeeSwitch, inputId);
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
}
