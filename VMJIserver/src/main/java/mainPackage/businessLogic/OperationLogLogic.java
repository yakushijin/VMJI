package mainPackage.businessLogic;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import mainPackage.Logic;
import mainPackage.entity.OperationContentsEntity;
import mainPackage.entity.OperationLogEntity;
import mainPackage.entity.UserEntity;

/**
 * 操作ログ関連のビジネスロジック
 */
public class OperationLogLogic extends Logic{
	@Autowired
	private OperationLogLogic operationLogLogic;

	public OperationLogLogic() {
		init();
	}
	
	public void OperationLogDbAdd(int userid,String target,int Contentsid) {
		//ユーザ内容格納
		operationLogLogic.userEntity.setUserId(userid);
		
		//実行内容格納
		operationLogLogic.operationContentsEntity.setOperationId(Contentsid);
		
		//ログ情報を格納
		operationLogLogic.operationLogEntity.setUserId(operationLogLogic.userEntity);
		operationLogLogic.operationLogEntity.setGuestOsName(target);
		operationLogLogic.operationLogEntity.setOperationId(operationLogLogic.operationContentsEntity);
		operationLogLogic.operationLogEntity.setOperationDate(new Date());
		
		//DBを更新
		operationLogLogic.operationLogDao.addEntity(operationLogLogic.operationLogEntity);
	}

	protected void AddLogic(Object data) {
	}

	protected void UpdateLogic(Object data) {
	}
	
}
