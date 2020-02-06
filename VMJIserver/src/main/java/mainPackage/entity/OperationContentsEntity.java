package mainPackage.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import com.google.gson.annotations.Expose;

@Entity
@Table(name="M_OperationContents")
/*
@NamedQueries({
	@NamedQuery(name = OperationLogSearchAdmin.Name,query = OperationLogSearchAdmin.Query)
	,@NamedQuery(name = OperationLogSearchUser.Name,query = OperationLogSearchUser.Query)
	})
	*/
public class OperationContentsEntity {

	@Id
	@Column(nullable=false)
	@Expose
	private int OperationId;
	
	@Column(length=50,nullable=false)
	@Expose
	private String OperationContents;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "OperationId")
	private List<OperationLogEntity> OperationLogEntity1;
	
	public OperationContentsEntity(int OperationId,String OperationContents) {
		this();
		this.OperationId = OperationId;
		this.OperationContents = OperationContents;
	}
	
	public OperationContentsEntity() {
		// TODO Auto-generated constructor stub
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


