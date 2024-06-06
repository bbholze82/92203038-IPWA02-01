import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ChatBean implements Serializable {

	private Integer id;
	private Integer chatPartnerUserId;
	private String newMessage;
	private List<MessageBean> messages;
	private Boolean setHasMessages;
	
    private final DataController dataController = new DataController();
	
	public ChatBean() {
		this.messages = new ArrayList<>();
		this.newMessage = "";
	}
	
	public List<MessageBean> getMessages() {
		return this.messages;
	}
	
	public Integer getChatId() {
		return this.id;
	}
	
	public void setChatId(Integer id) {
		this.id = id;
	}
	
	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}
	public String sendNewMessage(Integer senderUserId, Integer recipientUserId) throws ClassNotFoundException {
		MessageBean workMessage = new MessageBean();
		
		String workText = this.newMessage;
		Integer workTimestamp = dataController.getCurrentUnixTime();
		
		workMessage.setSenderId(senderUserId);
		workMessage.setRecipientId(recipientUserId);
		workMessage.setText(workText);
		workMessage.setTimestamp(workTimestamp);
		
		return dataController.sendNewMessage(senderUserId, recipientUserId, workText, workTimestamp);
	}
	
	public Integer getChatPartnerUserId() {
		return this.chatPartnerUserId;
	}
	
	public String getNewMessage() {
		return this.newMessage;
	}
	
	public void setNewMessage(String newMessage) {
		this.newMessage = newMessage;
	}
	
	public String clearChat() {
		this.messages = new ArrayList<>();
		return "chats.xhtml?faces-redirect=true";
	}
	
	public List<Integer> getUserIdsOfChatpatners(Integer inputUserId) throws ClassNotFoundException {
		return dataController.getUserIdsOfChatpatners(inputUserId);
	}
	
	public List<ChatBean> getChatsForUser(Integer inputUserId) throws ClassNotFoundException {
		return dataController.getChatsForUser(inputUserId);
	}
	
	public Boolean getChatHasMessages(Integer inputUserId, Integer inputChatPartnerId) throws ClassNotFoundException {
		return dataController.getChatHasMessages(inputUserId, inputChatPartnerId);
	}
	
	public List<MessageBean> getMessagesForChat(Integer inputUserId, Integer inputChatPartnerId) throws ClassNotFoundException {
		return dataController.getMessagesForChat(inputUserId, inputChatPartnerId);
	}
	
	public String startNewChat(LoginBean LoginBean, Integer recipientUserId) throws ClassNotFoundException {
		MessageBean workMessage = new MessageBean();
		
		LoginBean.setChatPartnerUserId(recipientUserId);
		
		Integer workTimestamp = dataController.getCurrentUnixTime();
		
		workMessage.setSenderId(LoginBean.getId());
		workMessage.setRecipientId(recipientUserId);
		workMessage.setTimestamp(workTimestamp);
		
		workMessage.setText("Chat started");
		
		return dataController.sendNewMessage(LoginBean.getId(), recipientUserId, workMessage.getText(), workTimestamp);
	}

	public Boolean getHasMessages() {
		return this.setHasMessages;
	}
	
	public void setHasMessages(Boolean setHasMessages) {
		this.setHasMessages = setHasMessages;
	}
}