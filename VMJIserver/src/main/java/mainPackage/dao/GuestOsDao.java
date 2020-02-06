package mainPackage.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import mainPackage.AbstractDbInterface;
import mainPackage.entity.GuestOsEntity;
import mainPackage.entity.GuestSearch;

public class GuestOsDao extends AbstractDbInterface<GuestOsEntity> {

	@PersistenceContext
	private EntityManager manager;
			
	public List<GuestOsEntity> SelectEntity(){
		Query query = manager.createQuery("from GuestOsEntity");
		return query.getResultList();
	}
	
	public List<GuestSearch> GuestOsSearchAll(String sql){
		Query query = manager.createNamedQuery(sql);
		return query.getResultList();
	}
	
	public List<GuestSearch> GuestOsSearchUserId(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<GuestSearch> GuestOsSearchHostOsId(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<GuestOsEntity> GuestOsSearchUuid(String sql,String id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<GuestOsEntity> GuestOsSearchGuestOsid(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
		
	public void addEntity(GuestOsEntity entity) {
		guestOsRepository.save(entity);
		guestOsRepository.flush();
		
		/*
		EntityManager manager = factory.getNativeEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		manager.flush();
		transaction.commit();	
		*/
	}
	public void updateEntity(GuestOsEntity entity) {
		
	}
	public void removeEntity(GuestOsEntity data) {
		
	}
	public void removeEntity(Long id) {
		
	}
}
