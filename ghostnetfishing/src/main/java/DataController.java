import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class DataController {
    public DataController() {
    }
    private final DataService dataService = new DataService();

    public String loginUser(String username, String password) throws ClassNotFoundException {
        return dataService.login(username, password);
    }
    
    public void sendNewGhostNetData(String inputLatitude, String inputLongitude) throws ClassNotFoundException {
    	dataService.sendNewGhostNetData(inputLatitude, inputLongitude);
    }
    
    public Integer getSumofGhostNetEntriesInDB() throws ClassNotFoundException {
    	return dataService.getSumofGhostNetEntriesInDB();
    }

    public List<GhostNetBean> getAllGhostNets() throws ClassNotFoundException {
    	return dataService.getAllGhostNets();
    }
    
    public void earmarkGhostNet(String inputId, String inputEarmarkedBy) throws ClassNotFoundException {
    	dataService.earmarkGhostNet(inputId, inputEarmarkedBy);
    }
    
    
}
