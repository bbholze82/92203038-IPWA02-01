import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class ServerBean {

    private LocalDateTime startTime;
    private Integer requestCounter;
    private Integer entriesinDB;
    
    private final DataController dataController = new DataController();
    
	public ServerBean() {
        startTime = LocalDateTime.now();
        requestCounter = 0;
        entriesinDB = 0;
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
	
	public Integer countEntriesinDB() throws ClassNotFoundException {
		entriesinDB = dataController.getSumofGhostNetEntriesInDB();
		return entriesinDB;
	}
	
}
