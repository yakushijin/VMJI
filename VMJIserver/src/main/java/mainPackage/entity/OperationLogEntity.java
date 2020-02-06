package mainPackage.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import com.google.gson.annotations.Expose;
import mainPackage.SqlQuerys.OperationLogSearchAdmin;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;
import mainPackage.SqlQuerys.OperationLogSearchLogid;


@Entity
@Table(name="T_OperationLog")
@NamedQueries({
	@NamedQuery(name = OperationLogSearchAdmin.Name,query = OperationLogSearchAdmin.Query)
	,@NamedQuery(name = OperationLogSearchUser.Name,query = OperationLogSearchUser.Query)
	,@NamedQuery(name = OperationLogSearchNewLogidUser.Name,query = OperationLogSearchNewLogidUser.Query)
	,@NamedQuery(name = OperationLogSearchLogid.Name,query = OperationLogSearchLogid.Query)
	})
public class OperationLogEntity {

	@Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "T_OperationLog_LogId_seq")
        @SequenceGenerator(
            name = "T_OperationLog_LogId_seq",
            sequenceName = "T_OperationLog_LogId_seq",
            initialValue = 1,
            allocationSize = 1)
	@Column(length=50,nullable=false)
	@Expose
	private int LogId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "UserId")
	@Expose
	private UserEntity UserId;
	
	@Column(nullable=true)
	@Expose
	private String GuestOsName;
	
	@Column(length=50,nullable=false)
	@Expose
	private Date OperationDate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "OperationId")
	@Expose
	private OperationContentsEntity OperationId;
	
	public OperationLogEntity(int LogId,UserEntity UserId,String GuestOsName,Date OperationDate,OperationContentsEntity OperationId) {
		this();
		this.LogId = LogId;
		this.UserId = UserId;
		this.GuestOsName = GuestOsName;
		this.OperationDate = OperationDate;
		this.OperationId = OperationId;
	}
	
	public OperationLogEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getLogId() {
		return LogId;
	}
	public void setLogId(int LogId) {
		this.LogId = LogId;
	}
	
	public UserEntity getUserId() {
		return UserId;
	}
	public void setUserId(UserEntity UserId) {
		this.UserId = UserId;
	}
	
	public String getGuestOsName() {
		return GuestOsName;
	}
	public void setGuestOsName(String GuestOsName) {
		this.GuestOsName = GuestOsName;
	}
	
	public Date getOperationDate() {
		return OperationDate;
	}
	public void setOperationDate(Date OperationDate) {
		this.OperationDate = OperationDate;
	}

	public OperationContentsEntity getOperationId() {
		return OperationId;
	}
	public void setOperationId(OperationContentsEntity OperationId) {
		this.OperationId = OperationId;
	}
	

	
}


