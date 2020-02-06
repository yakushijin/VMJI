package mainPackage.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Service;

import com.google.gson.annotations.Expose;

import java.util.List;

import mainPackage.SqlQuerys.GuestOsSearchUserHostOsAll;
import mainPackage.SqlQuerys.GuestOsSearchUserHostOsUserId;
import mainPackage.SqlQuerys.GuestOsSearchUserHostOsHostOsId;
import mainPackage.SqlQuerys.GuestOsSearchUuid;
import mainPackage.SqlQuerys.GuestOsSearchGuestOsid;

@Entity
@Table(name="M_GuestOs")
@NamedQueries({
	@NamedQuery(name = GuestOsSearchUserHostOsAll.Name, query = GuestOsSearchUserHostOsAll.Query)
	,@NamedQuery(name = GuestOsSearchUserHostOsUserId.Name, query = GuestOsSearchUserHostOsUserId.Query)
	,@NamedQuery(name = GuestOsSearchUserHostOsHostOsId.Name, query = GuestOsSearchUserHostOsHostOsId.Query)
	,@NamedQuery(name = GuestOsSearchUuid.Name, query = GuestOsSearchUuid.Query)	
	,@NamedQuery(name = GuestOsSearchGuestOsid.Name, query = GuestOsSearchGuestOsid.Query)
	})
public class GuestOsEntity {

	@Id
	@Column(length=50,nullable=false)
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "M_GuestOs_GuestOsId_seq")
    @SequenceGenerator(
            name = "M_GuestOs_GuestOsId_seq",
            sequenceName = "M_GuestOs_GuestOsId_seq",
            initialValue = 1,
            allocationSize = 1)
	@Expose
	private int GuestOsId;
	
	@Column(length=50,nullable=false)
	private String Uuid;

	@Column(length=100,nullable=false)
	@Expose
	private String GuestOsName;
	
	@Column(length=100,nullable=true)
	@Expose
	private String Status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "HostOsId")
	@Expose
	private HostOsEntity HostOsId;
	
	@Column(length=100,nullable=true)
	@Expose
	private String GuestOsHostName;
	
	@Column(length=100,nullable=true)
	@Expose
	private String Os;
	
	@Column(length=100,nullable=true)
	@Expose
	private String Ip;
	
	@Column(length=100,nullable=true)
	@Expose
	private String LanIp;

	@Column(length=100,nullable=true)
	private String VlanId;
	
	@Column(length=100,nullable=true)
	@Expose
	private String LoginUser;
	
	@Column(length=100,nullable=true)
	@Expose
	private String LoginPassword;
	
	@Expose
	@Column(length=100,nullable=true)
	private String LoginPort;
	
	@Column(nullable=true)
	private boolean Kanshi;
	
	@Column(length=100,nullable=false)
	@Expose
	private int Cpu;
	
	@Column(length=100,nullable=false)
	@Expose
	private int Mem;
	
	@Column(length=100,nullable=false)
	@Expose
	private int Disk;
	
	@Column(length=100,nullable=true)
	@Expose
	private String Remarks;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "UserId")
	private UserEntity UserId;
			
	
	public GuestOsEntity(int GuestOsId,String Uuid,String GuestOsName,String Status,HostOsEntity HostOsId,String GuestOsHostName,
			String Os,String Ip,String LanIp,String VlanId,String LoginUser,String LoginPassword,String LoginPort,boolean Kanshi,
			int Cpu,int Mem,int Disk,String Remarks,UserEntity UserId) {
		this();
		this.GuestOsId = GuestOsId;
		this.Uuid = Uuid;
		this.GuestOsName = GuestOsName;
		this.Status = Status;
		this.HostOsId = HostOsId;
		this.GuestOsHostName = GuestOsHostName;
		this.Os = Os;
		this.Ip = Ip;
		this.LanIp = LanIp;
		this.VlanId = VlanId;
		this.LoginUser = LoginUser;
		this.LoginPassword = LoginPassword;
		this.LoginPort = LoginPort;
		this.Kanshi = Kanshi;
		this.Cpu = Cpu;
		this.Mem = Mem;
		this.Disk = Disk;
		this.Remarks = Remarks;
		this.UserId = UserId;
	}
	
	public GuestOsEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getGuestOsId() {
		return GuestOsId;
	}
	public void setGuestOsId(int GuestOsId) {
		this.GuestOsId = GuestOsId;
	}

	public String getUuid() {
		return Uuid;
	}
	public void setUuid(String Uuid) {
		this.Uuid = Uuid;
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
	
	public HostOsEntity getHostOsId() {
		return HostOsId;
	}
	public void setHostOsId(HostOsEntity HostOsId) {
		this.HostOsId = HostOsId;
	}
	
	public String getGuestOsHostName() {
		return GuestOsHostName;
	}
	public void setGuestOsHostName(String GuestOsHostName) {
		this.GuestOsHostName = GuestOsHostName;
	}
	
	public String getOs() {
		return Os;
	}
	public void setOs(String Os) {
		this.Os = Os;
	}
	
	public String getIp() {
		return Ip;
	}
	public void setIp(String Ip) {
		this.Ip = Ip;
	}
	
	public String getLanIp() {
		return Ip;
	}
	public void setLanIp(String LanIp) {
		this.LanIp = LanIp;
	}
	
	public String getVlanId() {
		return VlanId;
	}
	public void setVlanId(String VlanId) {
		this.VlanId = VlanId;
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
	
	public String getLoginPort() {
		return LoginPort;
	}
	public void setLoginPort(String LoginPort) {
		this.LoginPort = LoginPort;
	}
	
	public boolean getKanshi() {
		return Kanshi;
	}
	public void setKanshi(boolean Kanshi) {
		this.Kanshi = Kanshi;
	}
	public int getCpu() {
		return Cpu;
	}
	public void setCpu(int Cpu) {
		this.Cpu = Cpu;
	}
	
	public int getMem() {
		return Mem;
	}
	public void setMem(int Mem) {
		this.Mem = Mem;
	}
	
	public int getDisk() {
		return Disk;
	}
	public void setDisk(int Disk) {
		this.Disk = Disk;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}
	
	/*
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}
	*/
	
	public UserEntity getUserId() {
		return UserId;
	}
	public void setUserId(UserEntity UserId) {
		this.UserId = UserId;
	}
	/*
	@Override
	public String toString() {
		return UserId + Password + UserName + AdminFlag + Remarks;
	}
	*/
	
}


