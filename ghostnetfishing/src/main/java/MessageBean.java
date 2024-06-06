import java.io.Serializable;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class MessageBean implements Serializable {

	Integer id;
	Integer senderId;
	Integer recipientId;
	String text;
	Integer timestamp; 
	
    private final DataController dataController = new DataController();

    public MessageBean() {
    }
    
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getTimestamp() {
		return this.timestamp;
	}
	
	public String getTimestampForChat() {
		return dataController.encodeUnixTimestampHumanReadableForChat(this.timestamp);
	}
	
	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}
	
	public Boolean checkIfMessageIsFromCurrentUser(Integer userId) {
		Boolean result = false;
		
		if (this.senderId == userId) {
			result = true;
		}
		
		return result;
	}
	
	public Integer getRecipientId() {
		return this.recipientId;
	}
	
	public void setRecipientId(Integer recipientId) {
		this.recipientId = recipientId;
	}
	
	public Integer getSenderId() {
		return this.senderId;
	}
	
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

}