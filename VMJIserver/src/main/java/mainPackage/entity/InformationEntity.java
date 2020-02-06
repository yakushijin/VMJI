package mainPackage.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.SequenceGenerator;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="T_Information")
/*
@NamedQueries({
	@NamedQuery(name = OperationLogSearchAdmin.Name,query = OperationLogSearchAdmin.Query)
	,@NamedQuery(name = OperationLogSearchUser.Name,query = OperationLogSearchUser.Query)
	})
	*/
public class InformationEntity {

	@Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "T_Information_InfoId_seq")
        @SequenceGenerator(
            name = "T_Information_InfoId_seq",
            sequenceName = "T_Information_InfoId_seq",
            initialValue = 1,
            allocationSize = 1)
	@Column(length=50,nullable=false)
	@Expose
	private int InfoId;
			
	@Column(length=50,nullable=false)
	@Expose
	private Date InfoDate;
	
	@Column(length=50,nullable=false)
	@Expose
	private int InfoLevel;
	
	@Column(length=50,nullable=false)
	@Expose
	private int InfoAuthority;

	@Column(length=200,nullable=false)
	@Expose
	private String InfoContents;
	
	public InformationEntity(int InfoId,Date InfoDate,int InfoLevel,int InfoAuthority,String InfoContents) {
		this();
		this.InfoId = InfoId;
		this.InfoDate = InfoDate;
		this.InfoLevel = InfoLevel;
		this.InfoAuthority = InfoAuthority;
		this.InfoContents = InfoContents;
	}
	
	public InformationEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public int getInfoId() {
		return InfoId;
	}
	public void setInfoId(int InfoId) {
		this.InfoId = InfoId;
	}
	
	public Date getInfoDate() {
		return InfoDate;
	}
	public void setInfoDate(Date InfoDate) {
		this.InfoDate = InfoDate;
	}
	
	public int getInfoLevel() {
		return InfoLevel;
	}
	public void setInfoLevel(int InfoLevel) {
		this.InfoLevel = InfoLevel;
	}
	
	public int getInfoAuthority() {
		return InfoAuthority;
	}
	public void setInfoAuthority(int InfoAuthority) {
		this.InfoAuthority = InfoAuthority;
	}
	
	public String getInfoContents() {
		return InfoContents;
	}
	public void setInfoContents(String InfoContents) {
		this.InfoContents = InfoContents;
	}
	

}


