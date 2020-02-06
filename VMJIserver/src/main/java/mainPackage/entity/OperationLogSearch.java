package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class OperationLogSearch {
	
	@Expose
	private int LogId;
	
	@Expose
	private int UserId;
	
	@Expose
	private String UserName;
	
	@Expose
	private String GuestOsName;
	
	@Expose
	private String OperationDate;

	@Expose
	private int OperationId;
	
	@Expose
	private String OperationContents;
	
	public OperationLogSearch(int LogId,int UserId,String UserName,String GuestOsName,String OperationDate,int OperationId,String OperationContents) {

		this.LogId = LogId;
		this.UserId = UserId;
		this.UserName = UserName;
		this.GuestOsName = GuestOsName;
		this.OperationDate = OperationDate;
		this.OperationId = OperationId;
		this.OperationContents = OperationContents;
		
	}
	
	public int getLogId() {
		return LogId;
	}
	public void setLogId(int LogId) {
		this.LogId = LogId;
	}
			
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int UserId) {
		this.UserId = UserId;
	}
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	
	public String getGuestOsName() {
		return GuestOsName;
	}
	public void setGuestOsName(String GuestOsName) {
		this.GuestOsName = GuestOsName;
	}
	
	public String getOperationDate() {
		return OperationDate;
	}
	public void setOperationDate(String OperationDate) {
		this.OperationDate = OperationDate;
	}
	
	public int getOperationId() {
		return OperationId;
	}
	public void setOperationId(int OperationId) {
		this.OperationId = OperationId;
	}
	
	public String getOperationContents() {
		return OperationContents;
	}
	public void setOperationContents(String OperationContents) {
		this.OperationContents = OperationContents;
	}
}


