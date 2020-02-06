package mainPackage.businessLogic;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import SocketData.GuestListGetData;
import SocketData.GuestOperationData;
import SocketData.SocketData;
import mainPackage.Logic;
import mainPackage.GlobalParameter;
import mainPackage.GlobalParameter.GUESTOSAUTHORITY;
import mainPackage.SqlQuerys.GuestOsSearchGuestOsid;
import mainPackage.SqlQuerys.GuestOsSearchUuid;
import mainPackage.SqlQuerys.HostOsSearchId;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.SqlQuerys.UserSearchUserId;
import mainPackage.entity.GuestOsEntity;
import mainPackage.entity.GuestSearch;
import mainPackage.entity.HostOsEntity;
import mainPackage.entity.UserEntity;

/**
 * ゲストOS関連のビジネスロジック
 */
public class GuestOsLogic extends Logic<GuestOsEntity>{
	
	@Autowired
	private GuestOsLogic guestOsLogic;

	public GuestOsLogic() {
		init();
	}

	/**
	 * 全ての最新のゲストOS情報を取得する
	 * ・全てのホストOSのエージェントに接続し、全てのゲストOS情報を取得する
	 * ・取得したゲストOS情報のデータをゲストOSDBに更新する
	 */
	public void GuestOsGetRecord() {
		//データベースから全てのホストOS情報を取得
		List<HostOsEntity> ｈostOsList = guestOsLogic.hostOsDao.SelectEntity();
		
		//取得したホストOS情報を展開	
		for (HostOsEntity hostSearch : ｈostOsList) {
			//エージェントに受け渡すデータを作成する
			SocketData<GuestListGetData> socketData = ApiConnectInit(101,hostSearch.getIp());
			
			//エージェントとの通信を実施しゲストOS情報を取得
			socketData = guestOsLogic.clientGuestListGet.SendStart(socketData);
			
			//エージェントから取得したゲストOS情報をリストに変換			
			String splitResultData[][] = guestOsLogic.utility.CsvStringListFormat(socketData.getResultData());
			
			//変換したゲストOS情報をDBに登録
			EgentGetDataGuestOsDbAdd(splitResultData,hostSearch.getHostOsId());	
		}
	}
	
	/**
	 * ユーザIDに紐づく最新のゲストOS情報を取得する
	 * ・ホストOSのエージェントに接続し、ユーザIDに紐づくゲストOS情報を取得する
	 * ・取得したゲストOS情報のデータをゲストOSDBに更新する
	 * ・更新された最新のユーザ情報（ゲストOS情報が含まれている）を返却する
	 */
	public List<UserEntity> UserGuestOsGetRecord(int id){
		//データベースから、ユーザIDに紐づくゲストOS情報を取得する
		List<UserEntity> userList = guestOsLogic.userDao.UserSearchUserId(UserSearchUserId.Name,id);
		List<GuestOsEntity> guestOsList = userList.get(0).getGuestOsEntity();
		
		//取得したゲストOS情報を展開	
		for (int i = 0;guestOsList.size() > i; i++) {
			//対象のIDを設定
			int guestosid = guestOsList.get(i).getGuestOsId();
			int hostOsId = guestOsList.get(i).getHostOsId().getHostOsId();
			
			//ホストOSのエージェントに接続し、対象のゲストOS情報を取得し、DBを更新する
			GuestOsOperation(guestosid,hostOsId,GUESTOSAUTHORITY.GETUNIT.getContentsid());
		}
	
		//データベースから、ユーザIDに紐づく更新されたゲストOS情報を取得し返却する
		List<UserEntity> newuserList = guestOsLogic.userDao.UserSearchUserId(UserSearchUserId.Name,id);		
		return newuserList;
	}
	
	/**
	 * 対象のゲストOSに対し指定した命令を実行する
	 * ・指定したホストOSのエージェントに接続し、指定したゲストOSに対し、命令を実行する
	 * ・対象となったゲストOS名を返却する
	 */
	public String GuestOsOperation(int guestosid,int hostosid,int opid) {
		//データベースからホストOSIDに紐づくIPを取得する
		List<HostOsEntity> ｈostOsList = guestOsLogic.hostOsDao.HostOsSearchId(HostOsSearchId.Name,hostosid);
		String HostOsIp = ｈostOsList.get(0).getIp();
		
		//データベースからゲストOSIDに紐づくゲストOS名を取得する
		List<GuestOsEntity> guestOsList = guestOsLogic.guestOsDao.GuestOsSearchGuestOsid(GuestOsSearchGuestOsid.Name,guestosid);	
		String GuestOsName = guestOsList.get(0).getGuestOsName();
		
		//エージェントに受け渡すデータを作成する
		SocketData<GuestOperationData> socketData = ApiConnectInit(opid,HostOsIp,GuestOsName);
	
		//エージェントとの通信を実施し指定した命令を実行する
		socketData = guestOsLogic.clientGuestOperation.SendStart(socketData);
		
		//データ取得の命令の場合、取得したデータを基にDBを更新する
		if(opid == GUESTOSAUTHORITY.GETUNIT.getContentsid()) {

			//エージェントから取得したゲストOS情報をリストに変換	
			String splitResultData[][] = guestOsLogic.utility.CsvStringListFormat(socketData.getResultData());
			//変換したゲストOS情報をDBに登録
			EgentGetDataGuestOsDbAdd(splitResultData,hostosid);	
		}
		
		//ゲストOS名を返却する
		return GuestOsName;
	}
	
	/**
	 * エージェントから取得したゲストOS情報をDBに登録する
	 * ・取得したゲストOS情報がDBに存在するかチェックし、存在しない場合は新規登録、存在する場合は更新する
	 * ・新規登録の場合、登録時のユーザはシステムユーザとし、エージェントから取得できるデータ以外は空の値を登録する
	 * ・更新の場合、エージェントから取得できるデータ以外は、DBから取得した値で更新する
	 */
	private void EgentGetDataGuestOsDbAdd(String[][] guestOsData,int HostOsId) {
		//取得したゲストOSリストを展開
		for(int i = 0;guestOsData.length > i;i++) {					
			//取得したゲストOS情報の、UUIDをDBにて検索
			List<GuestOsEntity> guestOsList = guestOsLogic.guestOsDao.GuestOsSearchUuid(GuestOsSearchUuid.Name,guestOsData[i][1]);
			
			//ヒットしたしなかった場合、新規登録
			if(guestOsList.size() == 0) {
				//登録用エンティティ生成
				guestOsLogic.userEntity.setUserId(GlobalParameter.SystemUserId);
				guestOsLogic.hostOsEntity.setHostOsId(HostOsId);
				//各値を設定
				GuestOsEntity guestOsEntity = new GuestOsEntity(
					1000
					,guestOsData[i][1]
					,guestOsData[i][0]
					,guestOsData[i][2]		
					,guestOsLogic.hostOsEntity
					,""
					,""
					,""
					,""
					,""
					,""
					,""
					,""
					,false
					,Integer.parseInt(guestOsData[i][3])
					,Integer.parseInt(guestOsData[i][4])
					,Integer.parseInt(guestOsData[i][5])
					,""
					,guestOsLogic.userEntity
					);
				//DBを更新
				guestOsLogic.guestOsDao.addEntity(guestOsEntity);
			//ヒットした場合更新
			}else {
				/*
				//ユーザID情報から対象のユーザ情報を取得し値をセットする
				List<UserEntity> userList = guestOsLogic.userDao.UserSearchUserId(UserSearchUserId.Name,guestOsList.get(0).getUserId().getUserId());
				userEntity.setUserId(userList.get(0).getUserId());
				userEntity.setUserName(userList.get(0).getUserName());
				userEntity.setLoginId(userList.get(0).getLoginId());
				userEntity.setPassword(userList.get(0).getPassword());
				userEntity.setAdminFlag(userList.get(0).getAdminFlag());
				userEntity.setRemarks(userList.get(0).getRemarks());
				*/
				
				
				//各値を設定		
				guestOsLogic.guestOsEntity.setGuestOsId(guestOsList.get(0).getGuestOsId());
				guestOsLogic.guestOsEntity.setUuid(guestOsData[i][1]);
				guestOsLogic.guestOsEntity.setGuestOsName(guestOsData[i][0]);
				guestOsLogic.guestOsEntity.setStatus(guestOsData[i][2]);
				guestOsLogic.guestOsEntity.setHostOsId(guestOsList.get(0).getHostOsId());
				guestOsLogic.guestOsEntity.setGuestOsHostName(guestOsList.get(0).getGuestOsHostName());
				guestOsLogic.guestOsEntity.setOs(guestOsList.get(0).getOs());
				guestOsLogic.guestOsEntity.setIp(guestOsList.get(0).getIp());
				guestOsLogic.guestOsEntity.setLanIp(guestOsList.get(0).getLanIp());
				guestOsLogic.guestOsEntity.setVlanId(guestOsList.get(0).getVlanId());
				guestOsLogic.guestOsEntity.setLoginUser(guestOsList.get(0).getLoginUser());
				guestOsLogic.guestOsEntity.setLoginPassword(guestOsList.get(0).getLoginPassword());
				guestOsLogic.guestOsEntity.setLoginPort(guestOsList.get(0).getLoginPort());
				guestOsLogic.guestOsEntity.setKanshi(guestOsList.get(0).getKanshi());				
				guestOsLogic.guestOsEntity.setCpu(Integer.parseInt(guestOsData[i][3]));
				guestOsLogic.guestOsEntity.setMem(Integer.parseInt(guestOsData[i][4]));
				guestOsLogic.guestOsEntity.setDisk(Integer.parseInt(guestOsData[i][5]));
				guestOsLogic.guestOsEntity.setRemarks(guestOsList.get(0).getRemarks());
				guestOsLogic.guestOsEntity.setUserId(guestOsList.get(0).getUserId());
				
				//DBを更新
				guestOsLogic.guestOsDao.addEntity(guestOsEntity);
			}					
	    }		
	}
		
	public void AddLogic(GuestOsEntity entity) {
		
	};
	
	/**
	 * ゲストOS情報を更新する為のデータ設定を実施する
	 */
	public void UpdateLogic(GuestOsEntity entity) {	
		//ユーザID情報から対象のユーザ情報を取得し値をセットする
		List<UserEntity> userList = guestOsLogic.userDao.UserSearchUserId(UserSearchUserId.Name,entity.getUserId().getUserId());
		userEntity.setUserId(userList.get(0).getUserId());
		userEntity.setUserName(userList.get(0).getUserName());
		userEntity.setLoginId(userList.get(0).getLoginId());
		userEntity.setPassword(userList.get(0).getPassword());
		userEntity.setAdminFlag(userList.get(0).getAdminFlag());
		userEntity.setRemarks(userList.get(0).getRemarks());
		
		//ゲストOSIDから対象のゲストOS情報を取得する
		List<GuestOsEntity> guestOsList = guestOsLogic.guestOsDao.GuestOsSearchGuestOsid(GuestOsSearchGuestOsid.Name,entity.getGuestOsId());
			
		//更新可能な値を設定
		guestOsEntity.setGuestOsId(guestOsList.get(0).getGuestOsId());
		guestOsEntity.setUuid(guestOsList.get(0).getUuid());
		guestOsEntity.setGuestOsName(guestOsList.get(0).getGuestOsName());
		guestOsEntity.setStatus(guestOsList.get(0).getStatus());
		guestOsEntity.setHostOsId(guestOsList.get(0).getHostOsId());
		guestOsEntity.setGuestOsHostName(entity.getGuestOsHostName());
		guestOsEntity.setOs(entity.getOs());
		guestOsEntity.setIp(entity.getIp());
		guestOsEntity.setLanIp(entity.getLanIp());
		guestOsEntity.setVlanId(entity.getVlanId());
		guestOsEntity.setLoginUser(entity.getLoginUser());
		guestOsEntity.setLoginPassword(entity.getLoginPassword());
		guestOsEntity.setLoginPort(entity.getLoginPort());
		guestOsEntity.setKanshi(entity.getKanshi());				
		guestOsEntity.setCpu(guestOsList.get(0).getCpu());
		guestOsEntity.setMem(guestOsList.get(0).getMem());
		guestOsEntity.setDisk(guestOsList.get(0).getDisk());
		guestOsEntity.setRemarks(entity.getRemarks());
		guestOsEntity.setUserId(userEntity);
		
		//DBを更新
		guestOsLogic.guestOsDao.addEntity(guestOsEntity);
	};
	
	public List<GuestSearch> GuestOsEncryption(List<GuestSearch> guestoslist){
		
		for (GuestSearch list :guestoslist) {
			list.setLoginUser("*******");
			list.setLoginPassword("*******");
		}
		
		return guestoslist;
	}
	
	public List<UserEntity> UserGestOsEncryption(List<UserEntity> userlist){
		
		for (GuestOsEntity list :userlist.get(0).getGuestOsEntity()) {
			list.setLoginUser("*******");
			list.setLoginPassword("*******");
		}		
		
		return userlist;
	}
	
	/**
	 * エージェントと通信するデータを設定する（全てのホストOS上のすべてのゲストOSデータを取得する場合）
	 */
	private SocketData<GuestListGetData> ApiConnectInit(int id,String ip) {
		//エージェントとの通信データ用値設定
		SocketData<GuestListGetData> socketData = new SocketData<GuestListGetData>();
		socketData.setOpid(id);
		socketData.setHostIp(ip);
		
		return socketData;
	}
	
	/**
	 * エージェントと通信するデータを設定する（指定した対象のゲストOSに対し命令を実行する場合）
	 */
	private SocketData<GuestOperationData> ApiConnectInit(int id,String ip,String GuestOsName) {
		//エージェントとの通信データ用値設定
		SocketData<GuestOperationData> socketData = new SocketData<GuestOperationData>();
		socketData.setOpid(id);
		socketData.setHostIp(ip);
		GuestOperationData data = new GuestOperationData(GuestOsName);
		socketData.setdata(data);
		
		return socketData;
	}
	
}
