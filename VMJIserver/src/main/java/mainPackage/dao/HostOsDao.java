package mainPackage.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import mainPackage.AbstractDbInterface;
import mainPackage.entity.HostOsEntity;
import mainPackage.entity.HostSearch;

public class HostOsDao extends AbstractDbInterface<HostOsEntity> {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<HostOsEntity> SelectEntity(){			
		Query query = manager.createQuery("from HostOsEntity");
		return query.getResultList();
	}
	
	public List<HostOsEntity> HostOsSearchId(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<HostSearch> HostOsSearchAll(String sql){
		Query query = manager.createNamedQuery(sql);
		return query.getResultList();
	}
	
	public List<HostSearch> HostOsSearchCalcHostOsId(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
			
	public void addEntity(HostOsEntity entity) {
		hostOsRepository.save(entity);
		hostOsRepository.flush();
	}
	public void updateEntity(HostOsEntity entity) {
		
	}
	public void removeEntity(HostOsEntity data) {
		
	}
	public void removeEntity(Long id) {
		
	}
}
