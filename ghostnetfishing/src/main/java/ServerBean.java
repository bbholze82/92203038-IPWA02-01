import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ServerBean {

    private LocalDateTime startTime;
    private Integer requestCounter;
    private Integer entriesinDB;
    Boolean debugMode;

    private final DataController dataController = new DataController();

	public ServerBean() {
        this.startTime = LocalDateTime.now();
        this.requestCounter = 0;
        this.entriesinDB = 0;
        this.debugMode = true;
	}

	public String getStartTime() {
        String pattern = "dd.MM.yyyy HH:mm.ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        String formattedTime = startTime.format(formatter);
        return formattedTime;
	}

	public void setRequestCounterUpByOne() {
		requestCounter++;
	}

	public Integer getRequestCounter() {
		return requestCounter;
	}

	public Integer sumEntriesinDBByStatus(Integer statusValue) throws ClassNotFoundException {
		entriesinDB = dataController.sumEntriesInDBByStatus(statusValue);
		return entriesinDB;
	}

	public Boolean getDebugMode() {
		return this.debugMode;
	}

	public void setDebugMode(Boolean inputVal) {
		this.debugMode = inputVal;
	}

}
