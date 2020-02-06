package mainPackage.businessLogic;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Logic;
import mainPackage.SqlQuerys.HostOsSearchId;
import mainPackage.entity.HostOsEntity;

/**
 * ホストOS関連のビジネスロジック
 */
public class HostOsLogic extends Logic<HostOsEntity>{
	@Autowired
	private HostOsLogic hostOsLogic;
	
	public HostOsLogic() {
		init();
	}
		
	public void AddLogic(HostOsEntity entity) {};
	
	/**
	 * ホストOS情報を更新する為のデータ設定を実施する
	 */
	public void UpdateLogic(HostOsEntity entity) {
		//ホストOSIDから対象のホストOS情報を取得する
		List<HostOsEntity> hostosList = hostOsLogic.hostOsDao.HostOsSearchId(HostOsSearchId.Name,entity.getHostOsId());

		//更新可能な値を設定
		hostOsLogic.hostOsEntity.setHostOsId(hostosList.get(0).getHostOsId());
		hostOsLogic.hostOsEntity.setHostName(entity.getHostName());
		hostOsLogic.hostOsEntity.setIp(entity.getIp());
		hostOsLogic.hostOsEntity.setCpu(entity.getCpu());
		hostOsLogic.hostOsEntity.setMem(entity.getMem());
		hostOsLogic.hostOsEntity.setDisk(entity.getDisk());
		hostOsLogic.hostOsEntity.setRemarks(entity.getRemarks());
		hostOsLogic.hostOsEntity.setGuestOsEntity(hostosList.get(0).getGuestOsEntity());
		
		//DBを更新
		hostOsLogic.hostOsDao.addEntity(hostOsLogic.hostOsEntity);
	};
	
}
