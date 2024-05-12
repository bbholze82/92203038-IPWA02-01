import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class DataController {
    public DataController() {
    }
    private final DataService dataService = new DataService();

    public Boolean loginUser(LoginBean inputBean) throws ClassNotFoundException {
        return dataService.login(inputBean);
    }
    
    public void sendNewGhostNetData(String inputLatitude, String inputLongitude) throws ClassNotFoundException {
    	dataService.sendNewGhostNetData(inputLatitude, inputLongitude);
    }
    
    public Integer getSumofGhostNetEntriesInDB() throws ClassNotFoundException {
    	return dataService.getSumofGhostNetEntriesInDB();
    }

    public List<GhostNetBean> getAllGhostNets(int modeSwitch) throws ClassNotFoundException {
    	return dataService.getAllGhostNets(modeSwitch);
    }
    
    public void editSalvageStatusOfGhostNet(GhostNetBean inputGhostNet, Integer inputUserid, int modeSwitch) throws ClassNotFoundException {
    	dataService.editSalvageStatusOfGhostNet(inputGhostNet, inputUserid, modeSwitch);
    }
        
    
    public String getAttributesFromDBUsers(Integer inputUserId, int modeSwitch) throws ClassNotFoundException {
    	return dataService.getAttributesFromDBUsers(inputUserId, modeSwitch);
    }
    
}
