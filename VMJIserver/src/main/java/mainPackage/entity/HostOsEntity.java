package mainPackage.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.google.gson.annotations.Expose;
import mainPackage.SqlQuerys.HostOsSearchCalcAll;
import mainPackage.SqlQuerys.HostOsSearchId;
import mainPackage.SqlQuerys.HostOsSearchCalcHostOsId;

@Entity
@Table(name="M_HostOs")
@NamedQueries({
	@NamedQuery(name = HostOsSearchCalcAll.Name, query = HostOsSearchCalcAll.Query)
	,@NamedQuery(name = HostOsSearchCalcHostOsId.Name, query = HostOsSearchCalcHostOsId.Query)
	,@NamedQuery(name = HostOsSearchId.Name,query = HostOsSearchId.Query)
	})
public class HostOsEntity {

	@Id
	@Column(length=50,nullable=false)
	@GeneratedValue(
	        strategy = GenerationType.SEQUENCE,
	        generator = "M_HostOs_HostOsId_seq")
    @SequenceGenerator(
            name = "M_HostOs_HostOsId_seq",
            sequenceName = "M_HostOs_HostOsId_seq",
            initialValue = 1,
            allocationSize = 1)
	@Expose
	private int HostOsId;
	
	@Column(length=50,nullable=false)
	//@Expose
	private String HostName;
	
	@Column(length=100,nullable=false)
	//@Expose
	private String Ip;
	
	@Column(length=100,nullable=false)
	//@Expose
	private double Cpu;
	
	@Column(length=100,nullable=false)
	//@Expose
	private double Mem;
	
	@Column(length=100,nullable=false)
	//@Expose
	private double Disk;
	
	@Column(length=100,nullable=true)
	private String Remarks;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "HostOsId")
	private List<GuestOsEntity> guestOsEntity;
	
	
	public HostOsEntity(int HostOsId,String HostName,String Ip,double Cpu,double Mem,double Disk,String Remarks) {
		this();
		this.HostOsId = HostOsId;
		this.HostName = HostName;
		this.Ip = Ip;
		this.Cpu = Cpu;
		this.Mem = Mem;
		this.Disk = Disk;
		this.Remarks = Remarks;
	}
	
	public HostOsEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getHostOsId() {
		return HostOsId;
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
	
	public String getIp() {
		return Ip;
	}
	public void setIp(String Ip) {
		this.Ip = Ip;
	}
	
	public double getCpu() {
		return Cpu;
	}
	public void setCpu(double Cpu) {
		this.Cpu = Cpu;
	}
	
	public double getMem() {
		return Mem;
	}
	public void setMem(double Mem) {
		this.Mem = Mem;
	}
	
	public double getDisk() {
		return Disk;
	}
	public void setDisk(double Disk) {
		this.Disk = Disk;
	}
	
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}

	public List<GuestOsEntity> getGuestOsEntity() {
		return guestOsEntity;
	}
	public void setGuestOsEntity(List<GuestOsEntity> GuestOsEntity) {
		this.guestOsEntity = GuestOsEntity;
	}
	
}


