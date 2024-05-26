import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private Integer id;
    private Integer role;
    private String username;
    private String usedPassword;
    private String hashedpassword;
    private String salt;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private Boolean adminPrivileges;

    private final DataController dataController = new DataController();

    private String errorMessage;
    private Boolean isLoggedIn;

    public LoginBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
    	this.errorMessage = errorMessage;
    }
    
    public Boolean getIsLoggedIn() {
    	return this.isLoggedIn;
    }
    
    public void setIsLoggedIn(Boolean inputVal) {
    	this.isLoggedIn = inputVal;
    }
    
    
    public String login() throws ClassNotFoundException {
        DataController dataController = new DataController();
        String loginStatus = dataController.loginUser(this);
        
        if (loginStatus.equals("backend")) {
        	this.setIsLoggedIn(true);
        	
        	if (this.role == 1)  {
        		//
        	}
        	
        	if (this.role == 2)  {
        		//
        	}
        } else {
            this.errorMessage = "The username or password is incorrect.";  
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Have you tried turning your memory off and on again?",  "Username or password is incorrect."));       
            loginStatus = "failed";
        }
        
        return loginStatus;
    }
    
    
    

    public String logout() {

    	this.id = null;
        this.role = null;
        this.username = null;
        this.usedPassword = null;
        this.hashedpassword = null;
        this.salt = null;
        this.firstname = null;
        this.lastname = null;
        
        //
        this.errorMessage = null;
        this.isLoggedIn = null;
        this.adminPrivileges = null;
    	return "index.xhtml?faces-redirect=true";

    }
    
    
    public Integer getRole() {
    	return this.role;
    }

    public String getRoleLabel() throws ClassNotFoundException {
    	return dataController.getLabelById(3, this.role);
    }
    
    public void setRole(Integer role) {
    	this.role = role;
    }
    
    public String getFirstname() {
    	return this.firstname;
    }
    
    public void setFirstname(String firstname) {
    	this.firstname = firstname;
    }
    
    public String getLastname() {
    	return this.lastname;
    }
    
    public void setLastname(String lastname) {
    	this.lastname = lastname;
    }
    
    public Integer getId() {
    	return this.id;
    }
    
    public void setId(Integer id) {
    	this.id = id;
    }
    
    public String getPhonenumber() {
    	return this.phonenumber;
    }
    
    public void setPhonenumber(String phonenumber) {
    	this.phonenumber = phonenumber;
    }
    
    public void setHashedPassword(String hashedpassword) {
    	this.hashedpassword = hashedpassword;
    }
    
    public String getHashedPassword() {
    	return this.hashedpassword;
    }
    
    public void setSalt(String salt) {
    	this.salt = salt;
    }
    
    public String getSalt() {
    	return this.salt;
    }
    
    public void setUsedPassword(String password) {
    	this.usedPassword = password;
    }
    
    public String getUsedPassword() {
    	return this.usedPassword;
    }
    
    public void cleanUpUsedPassword() {
    	this.usedPassword = null;
    }
    
    public Boolean getAdminPrivileges() {
    	return this.adminPrivileges;
    }
    
    public void setAdminPrivileges(Boolean adminPrivileges) {
    	this.adminPrivileges = adminPrivileges;
    }
    
}
