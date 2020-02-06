package mainPackage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Servlet;
import mainPackage.GlobalParameter.GRANT;
import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.OPERATIONCONTENTSID;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.SqlQuerys.OperationLogSearchLogid;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.HostOsEntity;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

/**
 * ホストOS情報更新コントローラ
 */
@WebServlet("/hostosupdate")
public class HostOsUpdate extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private HostOsUpdate hostOsUpdate;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 画面上で編集したホストOS情報をDBに反映させる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ホストOS情報を画面の変更内容に従い更新する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request)) && hostOsUpdate.permission.GrantCheck(GetSessionLoginId(request),GRANT.HOSTOSUPDATE.toString())) {
			//リクエストパラメータの内容を取得し各値に設定			
			hostOsUpdate.hostOsEntity.setHostOsId(Integer.parseInt(request.getParameter("hostosid")));
			hostOsUpdate.hostOsEntity.setHostName(request.getParameter("hostname"));
			hostOsUpdate.hostOsEntity.setIp(request.getParameter("ip"));
			hostOsUpdate.hostOsEntity.setCpu(Integer.parseInt(request.getParameter("cpu")));
			hostOsUpdate.hostOsEntity.setMem(Integer.parseInt(request.getParameter("mem")));
			hostOsUpdate.hostOsEntity.setDisk(Integer.parseInt(request.getParameter("disk")));
			hostOsUpdate.hostOsEntity.setRemarks(request.getParameter("remarks"));
			
			//変換したゲストOS情報をDBに登録
			hostOsUpdate.hostOsLogic.UpdateLogic(hostOsUpdate.hostOsEntity);
			
			//ログインIDからユーザ情報を取得する（ログ書き込み用）
			List<UserEntity> userlist = hostOsUpdate.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
			int userid = userlist.get(0).getUserId();
			
			//変更対象ホスト名取得（ログ書き込み用）
			String HostOsName = hostOsUpdate.hostOsEntity.getHostName();

			//操作ログをDBに書き込む
			hostOsUpdate.operationLogLogic.OperationLogDbAdd(userid,HostOsName,OPERATIONCONTENTSID.UPDATE.getContentsid());
			
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = hostOsUpdate.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userid);	    	
			List<OperationLogSearch> operationloglist = hostOsUpdate.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),"",HostOsName + MESSAGE.UPDATE.getMessage() + MESSAGE.SUCCESS.getMessage(),json);
        }
		//管理者権限以外の場合実行
        else {
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),"",GRANT.HOSTOSUPDATE.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
       }
	}
	
}
