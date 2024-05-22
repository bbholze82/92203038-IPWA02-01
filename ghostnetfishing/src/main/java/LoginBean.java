import jakarta.enterprise.context.SessionScoped;
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

    
    //
    private String errorMessage;
    private String isLoggedInAs;
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

    public String getIsLoggedInAs() {
        return isLoggedInAs;
    }

    public void setIsLoggedInAs(String isLoggedInAs) {
        this.isLoggedInAs = isLoggedInAs;
    }
    
    public Boolean getIsLoggedIn() {
    	return this.isLoggedIn;
    }
    
    public void setIsLoggedIn(Boolean inputVal) {
    	this.isLoggedIn = inputVal;
    }
    
    
    public String login() throws ClassNotFoundException {
        DataController dataController = new DataController();
        Boolean loginSuccessfully = dataController.loginUser(this);
        
        if (loginSuccessfully) {
        	this.setIsLoggedIn(true);
        	
        	// admin
        	if (this.role == 1) 
        		return "admin.xhtml?faces-redirect=true";
        	
        	// hunter
        	if (this.role == 2) 
        		return "hunt.xhtml?faces-redirect=true";
        	
        	// reporter
        	if (this.role == 4) 
        		return "submit.xhtml?faces-redirect=true";
        	
        	return "login.xhtml?faces-redirect=true";
        } else {
        	return "login.xhtml?faces-redirect=true";
        }
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
        this.isLoggedInAs = null;
        this.isLoggedIn = null;
        this.adminPrivileges = null;
        
    	return "index.xhtml?faces-redirect=true";
    }
    
    
    public Integer getRole() {
    	return this.role;
    }
    
    public String getRoleLabel() {
    	
    	String labelTxt = "Unknown";
    	
    	// provisorische Implementierung, sollte aus der Datenbank kommen
    	switch (this.role) {
    	
    	// admin
    	case 1:
    		labelTxt = "Admin";
    		break;
        // hunter
    	case 2:
    		labelTxt = "Hunter";
    		break;
        // reporter
    	case 4:
    		labelTxt = "Reporter";
    		break;
    	}
    	
    	return labelTxt;
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
