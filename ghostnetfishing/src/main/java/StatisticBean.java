import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class StatisticBean implements Serializable {

	private Integer ghostNetsinDB;
	private Integer ghostNetsWithStatusReported;
	private Integer ghostNetsWithStatusSalvagePending;
	private Integer ghostNetsWithStatusRecovered;
	private Integer ghostNetsWithStatusReportedMissing;
	private Integer ghostNetsWithStatusMissing;

    private final DataController dataController = new DataController();

	public Integer getGhostNetsInDB() throws ClassNotFoundException {
		this.ghostNetsinDB = dataController.sumEntriesInDBByStatus(0);
		return this.ghostNetsinDB;
	}

	public Integer getGhostNetsWithStatusReported() throws ClassNotFoundException {
		this.ghostNetsWithStatusReported = dataController.sumEntriesInDBByStatus(1);
		return this.ghostNetsWithStatusReported;
	}

	public Integer getGhostNetsWithStatusSalvagePending() throws ClassNotFoundException {
		this.ghostNetsWithStatusSalvagePending = dataController.sumEntriesInDBByStatus(2);
		return this.ghostNetsWithStatusSalvagePending;
	}

	public Integer getGhostNetsWithStatusRecovered() throws ClassNotFoundException {
		this.ghostNetsWithStatusRecovered = dataController.sumEntriesInDBByStatus(3);
		return this.ghostNetsWithStatusRecovered;
	}

	public Integer getGhostNetsWithStatusReportedMissing() throws ClassNotFoundException {
		this.ghostNetsWithStatusReportedMissing = dataController.sumEntriesInDBByStatus(4);
		return this.ghostNetsWithStatusReportedMissing;
	}

	public Integer getGhostNetsWithStatusMissing() throws ClassNotFoundException {
		this.ghostNetsWithStatusMissing = dataController.sumEntriesInDBByStatus(5);
		return this.ghostNetsWithStatusMissing;
	}
}