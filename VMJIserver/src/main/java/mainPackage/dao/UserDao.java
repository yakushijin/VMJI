package mainPackage.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import mainPackage.AbstractDbInterface;
import mainPackage.SqlQuerys;
import mainPackage.entity.UserEntity;
import mainPackage.entity.UserListSearch;

public class UserDao extends AbstractDbInterface<UserEntity> {
	
	@PersistenceContext
	private EntityManager manager;
			
	public List<UserEntity> SelectEntity(){
		Query query = manager.createQuery("from UserEntity");
		return query.getResultList();
	}
		
	public List<UserEntity> UserSearchId(String sql,String id){
		List<UserEntity> a = userRepository.findAll();
		
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<UserEntity> UserSearchUserId(String sql,int id){
		Query query = manager.createNamedQuery(sql).setParameter(1, id);
		return query.getResultList();
	}
	
	public List<UserListSearch> UserListSearchAll(String sql){
		Query query = manager.createNamedQuery(sql);
		return query.getResultList();
	}
	
	public List<UserListSearch> UserListConditionsSearchAnd(String username,String loginid,String adminflag){
		SqlQuerys.UserListConditionsSearchAnd userListConditionsSearchAnd = (new SqlQuerys()).new UserListConditionsSearchAnd();
		Query query = manager.createQuery(userListConditionsSearchAnd.CreateSql(username,loginid,adminflag));
		return query.getResultList();
	}
			
	public void addEntity(UserEntity entity) {
		userRepository.save(entity);
		userRepository.flush();
		
	}

	public void removeEntity(Long id) {
		
	}

	@Override
	public void updateEntity(UserEntity entity) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void removeEntity(UserEntity data) {
		// TODO 自動生成されたメソッド・スタブ
		
	}
}
