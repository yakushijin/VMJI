package mainPackage;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import SocketData.*;
import apiInterface.*;
import mainPackage.dao.*;
import mainPackage.entity.*;

/**
 * ビジネスロジックの基底クラス
 */
public abstract class Logic<T> {

	protected Utility utility;
	
	public void setUtility(Utility utility) {
		this.utility = utility;
	}
	
	protected UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	protected GuestOsDao guestOsDao;
	
	public void setGuestOsDao(GuestOsDao guestOsDao) {
		this.guestOsDao = guestOsDao;
	}

	protected HostOsDao hostOsDao;
	
	public void setHostOsDao(HostOsDao hostOsDao) {
		this.hostOsDao = hostOsDao;
	}
	
	protected OperationLogDao operationLogDao;
	
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	protected InformationDao informationDao;
	
	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
	}
	
	protected UserEntity userEntity;
	
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	protected GuestOsEntity guestOsEntity;
	
	public void setGuestOsEntity(GuestOsEntity guestOsEntity) {
		this.guestOsEntity = guestOsEntity;
	}
	
	protected HostOsEntity hostOsEntity;
	
	public void setHostOsEntity(HostOsEntity hostOsEntity) {
		this.hostOsEntity = hostOsEntity;
	}
	
	protected OperationLogEntity operationLogEntity;
	
	public void setOperationLogEntity(OperationLogEntity operationLogEntity) {
		this.operationLogEntity = operationLogEntity;
	}
	
	protected OperationContentsEntity operationContentsEntity;
	
	public void setOperationContentsEntity(OperationContentsEntity operationContentsEntity) {
		this.operationContentsEntity = operationContentsEntity;
	}
	
	protected InformationEntity informationEntity;
	
	public void setInformationEntity(InformationEntity informationEntity) {
		this.informationEntity = informationEntity;
	}
	
	
	protected ClientGuestListGet clientGuestListGet;
	
	public void setClientGuestListGet(ClientGuestListGet clientGuestListGet) {
		this.clientGuestListGet = clientGuestListGet;
	}
	protected ClientGuestOperation clientGuestOperation;
	
	public void setClientGuestOperation(ClientGuestOperation clientGuestOperation) {
		this.clientGuestOperation = clientGuestOperation;
	}
	/*以下は調査、検証が必要
	protected GuestListGetData guestListGetData;
	
	public void setGuestListGetData(GuestListGetData guestListGetData) {
		this.guestListGetData = guestListGetData;
	}
	protected GuestOperationData guestOperationData;
	
	public void setGuestOperationData(GuestOperationData guestOperationData) {
		this.guestOperationData = guestOperationData;
	}
	protected SocketData<T> socketData;
	
	public void setSocketData(SocketData<T> socketData) {
		this.socketData = socketData;
	}*/
	
	
	//初期化用メソッド
	public void init()  {
		//bean読み込み用
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	//DB登録関連ロジック
	protected abstract void AddLogic(T data);
	
	//DB更新関連ロジック
	protected abstract void UpdateLogic(T data);

}
