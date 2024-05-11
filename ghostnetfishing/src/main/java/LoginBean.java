import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@SessionScoped
public class LoginBean implements Serializable {
	private String id;
    private String username;
    private String password;
    private String firstname;
    private String surname;
    private Integer role;
    private String errorMessage;
    private String isLoggedInAs;
    private Boolean isLoggedIn;

    public LoginBean() {
    	this.id = "3";
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
        String status = dataController.loginUser(username, password);
        if (status.equals("admin")){
            isLoggedInAs = status;
            this.setIsLoggedIn(true);
            return "admin";
        } else if (status.equals("hunter")) {
            isLoggedInAs = status;
            this.setIsLoggedIn(true);
            return "hunter";
        } else {
            username = null;
            password = null;
            isLoggedInAs = null;
            errorMessage = status;
        }
        return status;
    }

    public String logout() {

        username = null;
        password = null;
        errorMessage = null;
        isLoggedInAs = null;

        this.setIsLoggedIn(false);
        
        return "index";
    }
    
    
    public Integer getRole() {
    	return this.role;
    }
    
    public void setRole(Integer role) {
    	this.role = role;
    }
    
    public String getFristName() {
    	return this.firstname;
    }
    
    public void setFristName(String firstname) {
    	this.firstname = firstname;
    }
    
    public String getSurName() {
    	return this.surname;
    }
    
    public void setSurName(String surname) {
    	this.surname = surname;
    }
    
    public String getId() {
    	return this.id;
    }
    
    public void setId(String id) {
    	this.id = id;
    }
    
}
