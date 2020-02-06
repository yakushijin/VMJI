package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class OperationLogSearchNewLogid {
	@Expose
	private int LogId;
	
	public OperationLogSearchNewLogid(int LogId) {

		this.LogId = LogId;
		
	}
	
	public int getLogId() {
		return LogId;
	}
	public void setLogId(int LogId) {
		this.LogId = LogId;
	}
}
