import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private Integer id;
    private Integer role;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phonenumber;

    
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        	
        	if (this.role == 1) 
        		return "admin.xhtml?faces-redirect=true";
        	
        	if (this.role == 2) 
        		return "hunter.xhtml?faces-redirect=true";
        	
        	return "login.xhtml?faces-redirect=true";
        } else {
        	return "login.xhtml?faces-redirect=true";
        }
    }

    public String logout() {

    	this.id = null;
        this.role = null;
        this.username = null;
        this.password = null;
        this.firstname = null;
        this.lastname = null;
        
        //
        this.errorMessage = null;
        this.isLoggedInAs = null;
        this.isLoggedIn = null;

        this.setIsLoggedIn(false);
        
    	return "index.xhtml?faces-redirect=true";
    }
    
    
    public Integer getRole() {
    	return this.role;
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
    
}
