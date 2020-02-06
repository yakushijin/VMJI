package mainPackage.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ColumnTransformer;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import com.google.gson.annotations.Expose;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.SqlQuerys.UserSearchUserId;
import mainPackage.SqlQuerys.UserListSearchAll;
import mainPackage.SqlQuerys.UserListConditionsSearchAnd;

@Entity
@Table(name="M_User")
@NamedQueries({
	@NamedQuery(name = UserSearchId.Name, query = UserSearchId.Query)	
	,@NamedQuery(name = UserSearchUserId.Name, query = UserSearchUserId.Query)
	,@NamedQuery(name = UserListSearchAll.Name, query = UserListSearchAll.Query)
	//,@NamedQuery(name = UserListConditionsSearchAnd.Name, query =  UserListConditionsSearchAnd.Query)
})
public class UserEntity {

	@Id
	@Column(length=50,nullable=false)
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "M_User_UserId_seq")
    @SequenceGenerator(
            name = "M_User_UserId_seq",
            sequenceName = "M_User_UserId_seq",
            initialValue = 1,
            allocationSize = 1)
	@Expose
	private int UserId;
	
	@Column(length=50,nullable=false)
	private String LoginId;

	@ColumnTransformer(
			read="pgp_sym_decrypt(Password::bytea, 'TnmKFq3LzD!g')", 
		    write="pgp_sym_encrypt(?, 'TnmKFq3LzD!g')"
		)
	private String Password;
	
	@Column(length=100,nullable=true)
	@Expose
	private String UserName;
	
	@Column(length=1,nullable=true)
	private boolean AdminFlag;
	
	@Column(length=100,nullable=true)
	@Expose
	private String Remarks;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantUserCreate;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantUserUpdate;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantUserDelete;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantUserAuthBrowse;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantGuestOsAllGet;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantGuestOsStart;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantGuestOsStop;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantGuestOsAuthBrowse;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantGuestOsUpdate;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantHostOsCreate;
	
	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantHostOsUpdate;

	@Column(length=1,nullable=true)
	@Expose
	private boolean GrantHostOsDelete;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "UserId")
	@Expose
	private List<GuestOsEntity> GuestOsEntity;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "UserId")
	private List<OperationLogEntity> OperationLogEntity;
	
	
	public UserEntity(int UserId,String LoginId,String Password,String UserName,boolean AdminFlag,String Remarks
					,boolean GrantUserCreate,boolean GrantUserUpdate,boolean GrantUserDelete,boolean GrantUserAuthBrowse
					,boolean GrantGuestOsAllGet,boolean GrantGuestOsStart,boolean GrantGuestOsStop,boolean GrantGuestOsAuthBrowse
					,boolean GrantGuestOsUpdate,boolean GrantHostOsCreate,boolean GrantHostOsUpdate,boolean GrantHostOsDelete) {
		this();
		this.UserId = UserId;
		this.LoginId = LoginId;
		this.Password = Password;
		this.UserName = UserName;
		this.AdminFlag = AdminFlag;
		this.Remarks = Remarks;
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
	
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int UserId) {
		this.UserId = UserId;
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
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String UserName) {
		this.UserName = UserName;
	}
	
	public boolean getAdminFlag() {
		return AdminFlag;
	}
	public void setAdminFlag(boolean AdminFlag) {
		this.AdminFlag = AdminFlag;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}
	
	public boolean getGrantUserCreate() {
		return GrantUserCreate;
	}
	public void setGrantUserCreate(boolean GrantUserCreate) {
		this.GrantUserCreate = GrantUserCreate;
	}
	public boolean getGrantUserUpdate() {
		return GrantUserUpdate;
	}
	public void setGrantUserUpdate(boolean GrantUserUpdate) {
		this.GrantUserUpdate = GrantUserUpdate;
	}
	public boolean getGrantUserDelete() {
		return GrantUserDelete;
	}
	public void setGrantUserDelete(boolean GrantUserDelete) {
		this.GrantUserDelete = GrantUserDelete;
	}
	public boolean getGrantUserAuthBrowse() {
		return GrantUserAuthBrowse;
	}
	public void setGrantUserAuthBrowse(boolean GrantUserAuthBrowse) {
		this.GrantUserAuthBrowse = GrantUserAuthBrowse;
	}
	public boolean getGrantGuestOsAllGet() {
		return GrantGuestOsAllGet;
	}
	public void setGrantGuestOsAllGet(boolean GrantGuestOsAllGet) {
		this.GrantGuestOsAllGet = GrantGuestOsAllGet;
	}
	public boolean getGrantGuestOsStart() {
		return GrantGuestOsStart;
	}
	public void setGrantGuestOsStart(boolean GrantGuestOsStart) {
		this.GrantGuestOsStart = GrantGuestOsStart;
	}
	public boolean getGrantGuestOsStop() {
		return GrantGuestOsStop;
	}
	public void setGrantGuestOsStop(boolean GrantGuestOsStop) {
		this.GrantGuestOsStop = GrantGuestOsStop;
	}
	public boolean getGrantGuestOsAuthBrowse() {
		return GrantGuestOsAuthBrowse;
	}
	public void setGrantGuestOsAuthBrowse(boolean GrantGuestOsAuthBrowse) {
		this.GrantGuestOsAuthBrowse = GrantGuestOsAuthBrowse;
	}
	public boolean getGrantGuestOsUpdate() {
		return GrantGuestOsUpdate;
	}
	public void setGrantGuestOsUpdate(boolean GrantGuestOsUpdate) {
		this.GrantGuestOsUpdate = GrantGuestOsUpdate;
	}
	public boolean getGrantHostOsCreate() {
		return GrantHostOsCreate;
	}
	public void setGrantHostOsCreate(boolean GrantHostOsCreate) {
		this.GrantHostOsCreate = GrantHostOsCreate;
	}
	public boolean getGrantHostOsUpdate() {
		return GrantHostOsUpdate;
	}
	public void setGrantHostOsUpdate(boolean GrantHostOsUpdate) {
		this.GrantHostOsUpdate = GrantHostOsUpdate;
	}
	public boolean getGrantHostOsDelete() {
		return GrantHostOsDelete;
	}
	public void setGrantHostOsDelete(boolean GrantHostOsDelete) {
		this.GrantHostOsDelete = GrantHostOsDelete;
	}
	
	public List<GuestOsEntity> getGuestOsEntity() {
		return GuestOsEntity;
	}
	public void setGuestOsEntity(List<GuestOsEntity> GuestOsEntity) {
		this.GuestOsEntity = GuestOsEntity;
	}
	
	public List<OperationLogEntity> getOperationLogEntity() {
		return OperationLogEntity;
	}
	public void setOperationLogEntity(List<OperationLogEntity> OperationLogEntity) {
		this.OperationLogEntity = OperationLogEntity;
	}
	
	/*
	@Override
	public String toString() {
		//return UserId + Password + UserName + AdminFlag + Remarks;
		return guestOsEntity.toString();
	}
	*/
	
}


