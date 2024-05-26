import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ReportBean implements Serializable {

	private Integer id;
	private Integer userId;
	private String userNameLabel;
	private Integer ghostNetId;
	private Integer statusId;
	private String statusLabel;
	private Integer timestamp;
	private String timestampLabel;

    private final DataController dataController = new DataController();


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer idValue) {
		this.id = idValue;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userIdValue) {
		this.userId = userIdValue;
	}

	public String getUserNameLabel() throws ClassNotFoundException {
		return dataController.getAttributesFromDBUsers(this.userId, 1);
	}

	public void setUserNameLabel(String userNameLabel) {
		this.userNameLabel = userNameLabel;
	}

	public Integer getGhostNetId() {
		return this.ghostNetId;
	}

	public void setGhostNetId(Integer ghostNetId) {
		 this.ghostNetId = ghostNetId;
	}

	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusVal) {
		this.statusId = statusVal;
	}

	public Integer getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Integer timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestampLabel() {
		return this.timestampLabel;
	}

	public void setTimestampLabel(String timestampLabel) {
		this.timestampLabel = timestampLabel;
	}

    public String getStatusLabel() throws ClassNotFoundException {
        this.statusLabel = dataController.getLabelById(2, this.statusId);
    	return this.statusLabel;
    }

    public String getAgeOfReport() {
    	Integer currentUnixTime = dataController.getCurrentUnixTime();
    	String ageLabel = dataController.getDurationHumanReadable(this.timestamp, currentUnixTime);

    	return ageLabel;
    }

}