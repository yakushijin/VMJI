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
import mainPackage.GlobalParameter.GUESTOSAUTHORITY;
import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.OPERATIONCONTENTSID;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.SqlQuerys.OperationLogSearchLogid;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

/**
 * ゲストOS操作コントローラ
 *
 */
@WebServlet("/guestosstart")
public class GuestOsStart extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private GuestOsStart suestOsStart;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * ホストOSのエージェントに接続し、指定したゲストに指定した命令を実行する
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの内容を取得し各値に設定
		String apiname = request.getParameter("apiname");
		
		if (suestOsStart.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSSTART.toString())) {
			//ログインIDからユーザ情報を取得する
			List<UserEntity> userlist = suestOsStart.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
			int userid = userlist.get(0).getUserId();
			
			//ゲストOS情報設定
			int guestosid = Integer.parseInt(request.getParameter("guestosid"));
			
			//ホストOS情報設定
			int hostosid = Integer.parseInt(request.getParameter("hostosid"));
				
			//ホストOSのエージェントに接続し、対象のゲストOSに対し指定した処理を実行する
			String GuestOsName = suestOsStart.guestOsLogic.GuestOsOperation(guestosid,hostosid,GUESTOSAUTHORITY.START.getContentsid());
	
			//操作ログをDBに書き込む
			suestOsStart.operationLogLogic.OperationLogDbAdd(userid,GuestOsName,OPERATIONCONTENTSID.START.getContentsid());
	
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = suestOsStart.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userid);	    	
			List<OperationLogSearch> operationloglist = suestOsStart.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname,GuestOsName + MESSAGE.SUCCESS.getMessage(),json);
		}else {
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),apiname,GRANT.GUESTOSSTART.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
        }	
	}
}
