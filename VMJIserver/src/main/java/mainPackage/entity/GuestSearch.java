package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class GuestSearch {
	
	@Expose
	private int GuestOsId;
	
	@Expose
	private String GuestOsName;
	
	@Expose
	private String Status;
	
	@Expose
	private String GuestOsHostName;
	
	@Expose
	private String Os;
	
	@Expose
	private int Cpu;
	
	@Expose
	private int Mem;

	@Expose
	private int Disk;
	
	@Expose
	private String Ip;
	
	@Expose
	private String LanIp;
	
	@Expose
	private String VlanId;
	
	@Expose
	private String LoginUser;

	@Expose
	private String LoginPassword;
	
	@Expose
	private String LoginPort;
	
	@Expose
	private boolean Kanshi;
	
	@Expose
	private int HostOsId;
	
	@Expose
	private String HostName;
	
	@Expose
	private int UserId;
	
	@Expose
	private String UserName;

	@Expose
	private String Remarks;
	
	public GuestSearch(int GuestOsId,String GuestOsName,String Status,String GuestOsHostName,String Os,int Cpu,int Mem,int Disk,
			String Ip,String LanIp,String VlanId,String LoginUser,String LoginPassword,String LoginPort,boolean Kanshi,
			int HostOsId,String HostName,int UserId,String UserName,String Remarks) {

		this.GuestOsId = GuestOsId;
		this.GuestOsName = GuestOsName;
		this.Status = Status;		
		this.GuestOsHostName = GuestOsHostName;
		this.Os = Os;
		this.Cpu = Cpu;
		this.Mem = Mem;
		this.Disk = Disk;
		this.Ip = Ip;
		this.LanIp = LanIp;
		this.VlanId = VlanId;
		this.LoginUser = LoginUser;
		this.LoginPassword = LoginPassword;
		this.LoginPort = LoginPort;
		this.Kanshi = Kanshi;
		this.UserId = UserId;
		this.UserName = UserName;
		this.HostOsId = HostOsId;
		this.HostName = HostName;
		this.Remarks = Remarks;
	}
	
	public int getGuestOsId() {
		return GuestOsId;
	}
	public void setGuestOsId(int GuestOsId) {
		this.GuestOsId = GuestOsId;
	}
			
	public String getGuestOsName() {
		return GuestOsName;
	}
	public void setGuestOsName(String GuestOsName) {
		this.GuestOsName = GuestOsName;
	}
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String Status) {
		this.Status = Status;
	}
	
	public String getIp() {
		return Ip;
	}
	public void setIp(String Ip) {
		this.Ip = Ip;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
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
	
	public String getHostOsId() {
		return UserName;
	}
	public void setHostOsId(int HostOsId) {
		this.HostOsId = HostOsId;
	}
	
	public String getHostName() {
		return HostName;
	}
	public void setHostName(String HostName) {
		this.HostName = HostName;
	}
	
	public String getLoginUser() {
		return LoginUser;
	}
	public void setLoginUser(String LoginUser) {
		this.LoginUser = LoginUser;
	}
	
	public String getLoginPassword() {
		return LoginPassword;
	}
	public void setLoginPassword(String LoginPassword) {
		this.LoginPassword = LoginPassword;
	}
	
}


