package mainPackage.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import mainPackage.AbstractDbInterface;
import mainPackage.entity.OperationContentsEntity;
import mainPackage.entity.OperationLogEntity;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

public class OperationLogDao extends AbstractDbInterface<OperationLogEntity> {

	@PersistenceContext
	private EntityManager manager;

	public List<OperationLogEntity> SelectEntity(){
		Query query = manager.createQuery("from OperationLogEntity");
		return query.getResultList();
	}
	
	public List<OperationLogSearch> OperationLogSearch(String sql){
		Query query = manager.createNamedQuery(sql).setMaxResults(20);
		return query.getResultList();
	}
			
	public List<OperationLogSearch> OperationLogSearch(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter("where", id).setMaxResults(20);
		return query.getResultList();
	}

	public List<OperationLogSearchNewLogid> OperationLogSearchNewLogidUser(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter("where", id);
		return query.getResultList();
	}
	
	@Transactional
	public void addEntity(OperationLogEntity entity) {
		List<OperationLogEntity> ope2 = new ArrayList<OperationLogEntity>();
		ope2 = operationLogRepository.findAll();
		//Optional<GuestOsEntity> guestOsEntity;

		UserEntity UserId = entity.getUserId();
		int u = UserId.getUserId();

		OperationContentsEntity operationId = entity.getOperationId();
		int o = operationId.getOperationId();
		
		String GuestOsName = entity.getGuestOsName();
		
		//List<GuestOsEntity> d = new ArrayList<GuestOsEntity>();
		
		UserEntity userEntity = new UserEntity();
		OperationLogEntity operationLogEntity = new OperationLogEntity();
		
		OperationContentsEntity operationContentsEntity = new OperationContentsEntity();
		
		//Optional<GuestOsEntity> guestOsEntity;
		userEntity = userRepository.findById(u).get();
		operationContentsEntity = operationContentsRepository.findById(o).get();

		//guestOsEntity =  guestOsRepository.getOne("GuestId2");
		//userEntity = userRepository.getOne(10001);
		
		//operationLogEntity.setLogId();
		Date date = new Date();
		operationLogEntity.setOperationId(operationContentsEntity);
		operationLogEntity.setOperationDate(date);
		operationLogEntity.setUserId(userEntity);
		operationLogEntity.setGuestOsName(GuestOsName);
		
		//guestOsEntity = guestOsRepository.findById("GuestId2");
		
		operationLogRepository.save(operationLogEntity);
		
		operationLogRepository.flush();
		
		List<OperationLogEntity> ope = new ArrayList<OperationLogEntity>();
		ope = operationLogRepository.findAll();
		
		/*
		EntityManager manager = factory.getNativeEntityManagerFactory().createEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		manager.persist(entity);
		manager.flush();
		transaction.commit();	
		*/
	}
	public void updateEntity(OperationLogEntity entity) {
		
	}
	public void removeEntity(OperationLogEntity data) {
		
	}
	public void removeEntity(Long id) {
		
	}
		
}
