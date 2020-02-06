package mainPackage.businessLogic;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Logic;
import mainPackage.SqlQuerys.UserSearchUserId;
import mainPackage.entity.GuestSearch;
import mainPackage.entity.UserEntity;
import mainPackage.entity.UserListSearch;

/**
 * ユーザ関連のビジネスロジック
 */
public class UserLogic extends Logic<UserEntity>{
	@Autowired
	private UserLogic userLogic;
	
	public UserLogic() {
		init();
	}
	
	/**
	 * ユーザ情報を新規登録する為のデータ設定を実施する
	 */
	public void AddLogic(UserEntity entity) {
		System.out.println("create");
		//追加可能な値を設定
		userLogic.userEntity.setUserId(entity.getUserId());
		userLogic.userEntity.setUserName(entity.getUserName());
		userLogic.userEntity.setLoginId(entity.getLoginId());
		userLogic.userEntity.setPassword(entity.getPassword());
		userLogic.userEntity.setAdminFlag(entity.getAdminFlag());
		userLogic.userEntity.setRemarks(entity.getRemarks());
		
		userLogic.userEntity.setGrantUserCreate(entity.getGrantUserCreate());
		userLogic.userEntity.setGrantUserUpdate(entity.getGrantUserUpdate());
		userLogic.userEntity.setGrantUserDelete(entity.getGrantUserDelete());
		userLogic.userEntity.setGrantUserAuthBrowse(entity.getGrantUserAuthBrowse());
		userLogic.userEntity.setGrantGuestOsAllGet(entity.getGrantGuestOsAllGet());
		userLogic.userEntity.setGrantGuestOsStart(entity.getGrantGuestOsStart());
		userLogic.userEntity.setGrantGuestOsStop(entity.getGrantGuestOsStop());
		userLogic.userEntity.setGrantGuestOsAuthBrowse(entity.getGrantGuestOsAuthBrowse());
		userLogic.userEntity.setGrantGuestOsUpdate(entity.getGrantGuestOsUpdate());
		userLogic.userEntity.setGrantHostOsCreate(entity.getGrantHostOsCreate());
		userLogic.userEntity.setGrantHostOsUpdate(entity.getGrantHostOsUpdate());
		userLogic.userEntity.setGrantHostOsDelete(entity.getGrantHostOsDelete());
		
		//DBを更新
		userLogic.userDao.addEntity(userLogic.userEntity);
	};
	
	/**
	 * ユーザ情報を更新する為のデータ設定を実施する
	 */
	public void UpdateLogic(UserEntity entity) {
		System.out.println("update");
		//ユーザIDから対象のユーザ情報を取得する
		List<UserEntity> userList = userLogic.userDao.UserSearchUserId(UserSearchUserId.Name,entity.getUserId());

		//更新可能な値を設定
		userLogic.userEntity.setUserId(userList.get(0).getUserId());
		userLogic.userEntity.setUserName(entity.getUserName());
		userLogic.userEntity.setLoginId(entity.getLoginId());
		userLogic.userEntity.setPassword(entity.getPassword());
		userLogic.userEntity.setAdminFlag(entity.getAdminFlag());
		userLogic.userEntity.setRemarks(entity.getRemarks());
		userLogic.userEntity.setGuestOsEntity(userList.get(0).getGuestOsEntity());
		userLogic.userEntity.setOperationLogEntity(userList.get(0).getOperationLogEntity());
		
		//DBを更新
		userLogic.userDao.addEntity(userLogic.userEntity);
	};
	
	/**
	 * ユーザ情報の対象のカラムを暗号化する
	 */
	public List<UserListSearch> UserEncryption(List<UserListSearch> userlist){		
		for (UserListSearch list :userlist) {
			list.setLoginId("*******");
			list.setPassword("*******");
		}		
		return userlist;
	}
	
	/**
	 * ユーザ情報の最終ログインカラムにて一度もログインしたことが無い場合は固定文言を表示する
	 */
	public List<UserListSearch> UserLoginHistoryUpdate(List<UserListSearch> userlist){		
		for (UserListSearch list :userlist) {
			if(list.getOperationDate() == null) {
				list.setOperationDate("ログインなし");
			}
		}		
		return userlist;
	}
	
}
