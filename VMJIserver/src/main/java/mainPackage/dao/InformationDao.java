package mainPackage.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import mainPackage.AbstractDbInterface;
import mainPackage.entity.InformationEntity;

public class InformationDao extends AbstractDbInterface<InformationEntity> {

	@PersistenceContext
	private EntityManager manager;
	
	public List<InformationEntity> SelectEntity(){
		Query query = manager.createQuery("from InformationEntity");
		return query.getResultList();
	}
	
	public List<InformationEntity> OperationLogSearch(String sql){
		Query query = manager.createNamedQuery(sql);
		return query.getResultList();
	}
			
	public List<InformationEntity> OperationLogSearch(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter("where", id);
		return query.getResultList();
	}
			
	@Transactional
	public void addEntity(InformationEntity entity) {

	}
	public void updateEntity(InformationEntity entity) {
		
	}
	public void removeEntity(InformationEntity data) {
		
	}
	public void removeEntity(Long id) {
		
	}
		
}
