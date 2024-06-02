import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class TestBean {

	private List<String> outputLog = new ArrayList<>();
    private final DataController dataController = new DataController();

	
	public TestBean() {
		this.outputLog.add("No log available");
    }
	

	public String createEntryForLog(String inputTxt) {
		
		if (inputTxt.equals("")) {
			return inputTxt;
		}
		
		String workTime = dataController.getUnixTimestampHumanReadableForLog();
		String resultLine = workTime + ":" + " " + inputTxt;
		
		return resultLine;
	}
	
	
	public String startTest() throws ClassNotFoundException, InterruptedException {
		List<String> workOutputLog = new ArrayList<>();
		
		Boolean case1Result = false;
		Boolean case2Result = false;
		
		
		workOutputLog.add(createEntryForLog("Tests started"));
		// GhostNetBean vorbereiten
		Integer workGhostNetId = null;
		GhostNetBean workGhostNetBean = new GhostNetBean();
		workGhostNetBean.setPosLatitude("66.5431");
		workGhostNetBean.setPosLongitude("25.8477");
		workGhostNetBean.setSize(1);

		//
		// 1. Fall: Methode "submit"
		//
		// Ghostnetz als anonymer Nutzer (2) anlegen u
		workOutputLog.add(createEntryForLog("Test 1 started"));
	    workGhostNetBean.submitData(2);
	    
	    // ID einlesen
	    workGhostNetId = workGhostNetBean.getId();
	    
	    workOutputLog.add(createEntryForLog("GhostNetBean created."));
	    String workSummary = "ID: " + workGhostNetBean.getId() + ", " + "Latitude: " + workGhostNetBean.getPosLatitude() + ", " + "Longtude: " + workGhostNetBean.getPosLongitude() + ", " + "Size: " + workGhostNetBean.getSize();
	    workOutputLog.add(createEntryForLog(workSummary));

	    // Prüfen des Status
	    case1Result = dataController.testCompareLatestStatusId(workGhostNetId, 1);
		workOutputLog.add(createEntryForLog("submitData: " + case1Result.toString()));

	    //
	    // 2. Fall: Methode "setStatus*"-Methoden
	    //
		workOutputLog.add(createEntryForLog("Test 2 started"));

	    if (case1Result){
	    	
	    	Boolean runTest = true;
	    	Boolean testResult = true;
	    	
	    	while(runTest) {
	    		
	    		// Fall: Initialisierung
	    		workGhostNetBean.setStatusToReported(1);
	    		// erwarteter Wert ist 1
	    		testResult = dataController.testCompareLatestStatusId(workGhostNetId, 1);
	    		workOutputLog.add(createEntryForLog("setStatusToReported: " + testResult.toString()));
	    		
	    		// Fall: Abbruch der Bergung und zurücksetzen
	    		workGhostNetBean.setStatusToSalvagePending(1);
	    		// erwarteter Wert ist 2
	    		testResult = dataController.testCompareLatestStatusId(workGhostNetId, 2);
	    		workOutputLog.add(createEntryForLog("setStatusToSalvagePending: " + testResult.toString()));
	    		// Zurücksetzen auf Status 1
	    		workGhostNetBean.setStatusToReported(1);
	
	    		// Fall: Erfolgreiche Bergung 
	    		workGhostNetBean.setStatusToSalvagePending(1);
	    		workGhostNetBean.setStatusToRecovered(1);
	    		// erwarteter Wert ist 3
	    		testResult = dataController.testCompareLatestStatusId(workGhostNetId, 3);
	    		workOutputLog.add(createEntryForLog("setStatusToRecovered: " + testResult.toString()));
	    		// Zurücksetzen auf Status 1
	    		workGhostNetBean.setStatusToReported(1);
	    		
	    		// Fall: Nicht gefunden / abgelehnt / bestätigt
	    		workGhostNetBean.setStatusToReportedMissing(1);
	    		workGhostNetBean.setStatusToMissing(1);
	    		workGhostNetBean.setStatusToReportedMissing(1);
	    		// erwarteter Wert ist 4
	    		testResult = dataController.testCompareLatestStatusId(workGhostNetId, 4);
	    		workOutputLog.add(createEntryForLog("setStatusToReportedMissing: " + testResult.toString()));
	    		// Zurücksetzen auf Status 1
	    		workGhostNetBean.setStatusToReported(1);
	    		workGhostNetBean.setStatusToReportedMissing(1);
	    		workGhostNetBean.setStatusToMissing(1);
	    		// erwarteter Wert ist 5
	    		testResult = dataController.testCompareLatestStatusId(workGhostNetId, 5);	
	    		workOutputLog.add(createEntryForLog("testCompareLatestStatusId: " + testResult.toString()));
	    		// Ende
	    		runTest = false;
	    	}
	    	
	    	case2Result = testResult;
	    }
	    
		workOutputLog.add(createEntryForLog("End of tests"));
		this.outputLog = workOutputLog;
		
		// Aufräumen in der Datenbank
		dataController.testDeleteGhostNet(workGhostNetId);	
		dataController.testDeleteReports(workGhostNetId);
		workOutputLog.add(createEntryForLog("Database cleaned up"));

        return "test?faces-redirect=true";
	}
	
	public List<String> getOutputLog() {
		return this.outputLog;
	}
	
	public void setOutputLog(List<String> outputLog) {
		this.outputLog = outputLog;
	}

	
	public Boolean sumbmitGhostNet() {
		Boolean result = true;
		//dataController.sendNewGhostNetData(null, outputTxt, outputTxt, null);
		return result;
	
}



}