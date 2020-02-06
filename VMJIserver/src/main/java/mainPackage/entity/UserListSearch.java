package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class UserListSearch {

	@Expose
	private int UserId;
	
	@Expose
	private String UserName;

	@Expose
	private String LoginId;

	@Expose
	private String Password;

	@Expose
	private boolean AdminFlag;

	@Expose
	private String OperationDate;
    
	@Expose
	private boolean GrantUserCreate;

	@Expose
	private boolean GrantUserUpdate;

	@Expose
	private boolean GrantUserDelete;

	@Expose
	private boolean GrantUserAuthBrowse;

	@Expose
	private boolean GrantGuestOsAllGet;

	@Expose
	private boolean GrantGuestOsStart;

	@Expose
	private boolean GrantGuestOsStop;

	@Expose
	private boolean GrantGuestOsAuthBrowse;

	@Expose
	private boolean GrantGuestOsUpdate;

	@Expose
	private boolean GrantHostOsCreate;

	@Expose
	private boolean GrantHostOsUpdate;

	@Expose
	private boolean GrantHostOsDelete;
	
	@Expose
	private String Remarks;
	
	public UserListSearch(int UserId,String UserName,String LoginId,String Password,boolean AdminFlag,String OperationDate
						,boolean GrantUserCreate,boolean GrantUserUpdate,boolean GrantUserDelete,boolean GrantUserAuthBrowse
						,boolean GrantGuestOsAllGet,boolean GrantGuestOsStart,boolean GrantGuestOsStop,boolean GrantGuestOsAuthBrowse
						,boolean GrantGuestOsUpdate,boolean GrantHostOsCreate,boolean GrantHostOsUpdate,boolean GrantHostOsDelete,String Remarks ) {
		this.UserId = UserId;
		this.LoginId = LoginId;
		this.Password = Password;
		this.UserName = UserName;
		this.AdminFlag = AdminFlag;
		this.Remarks = Remarks;
		this.OperationDate = OperationDate;
		this.GrantUserCreate = GrantUserCreate;
		this.GrantUserUpdate = GrantUserUpdate;
		this.GrantUserDelete = GrantUserDelete;
		this.GrantUserAuthBrowse = GrantUserAuthBrowse;
		this.GrantGuestOsAllGet = GrantGuestOsAllGet;
		this.GrantGuestOsStart = GrantGuestOsStart;
		this.GrantGuestOsStop = GrantGuestOsStop;
		this.GrantGuestOsAuthBrowse = GrantGuestOsAuthBrowse;
		this.GrantGuestOsUpdate = GrantGuestOsUpdate;
		this.GrantHostOsCreate = GrantHostOsCreate;
		this.GrantHostOsUpdate = GrantHostOsUpdate;
		this.GrantHostOsDelete = GrantHostOsDelete;
	}
	
	public String getLoginId() {
		return LoginId;
	}
	public void setLoginId(String LoginId) {
		this.LoginId = LoginId;
	}
	
	public String getPassword() {
		return Password;
	}
	public void setPassword(String Password) {
		this.Password = Password;
	}
	
	public String getOperationDate() {
		return OperationDate;
	}
	public void setOperationDate(String OperationDate) {
		this.OperationDate = OperationDate;
	}

}


