import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class LabelBean {
	
    private Map<Integer, String> sizeLabels;
    private Map<Integer, String> statusLabels;
    private Map<Integer, String> userRoleLabels;
    private Map<Integer, String> userNameLabels;

    private final DataController dataController = new DataController();

    public LabelBean() throws ClassNotFoundException {
    	this.sizeLabels = dataController.getLabels(1);
    	this.statusLabels = dataController.getLabels(2);
    	this.userRoleLabels = dataController.getLabels(3);
    	this.userNameLabels = dataController.getLabels(4);
    }
    
    public Map<Integer, String> getSizeLabels() {
    	return sizeLabels;
    }
    
    public Map<Integer, String> getStatusLabels() {
    	return statusLabels;
    }
    
    public Map<Integer, String> getUserRoleLabels() {
    	return userRoleLabels;
    }
    
    public Map<Integer, String> getUserNameLabels() {
    	return userNameLabels;
    }
 
    public String getSizeLabelById(Integer inputSizeId, Integer inputLanguageId) {
    	return this.dataController.getLabel(this, 1, inputSizeId, inputLanguageId);
    }
    
    public String getStatusLabelById(Integer inputStatusId, Integer inputLanguageId) {
    	return this.dataController.getLabel(this, 2, inputStatusId, inputLanguageId);
    }
    
    public String getUserRoleLabelById(Integer inputUserRoleId, Integer inputLanguageId) {
    	return this.dataController.getLabel(this, 3, inputUserRoleId, inputLanguageId);
    }
    
    public String getUserNameLabelById(Integer inputUserId) {
    	return this.dataController.getLabel(this, 4, inputUserId, 1);
    }
  

    }
