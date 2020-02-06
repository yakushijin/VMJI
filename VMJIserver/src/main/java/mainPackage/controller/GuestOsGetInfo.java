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
import mainPackage.GlobalParameter.OPERATIONTARGET;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.SqlQuerys.OperationLogSearchLogid;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

/**
 * 最新のゲストOS情報取得コントローラ
 *
 */
@WebServlet("/guestosgetinfo")
public class GuestOsGetInfo extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private GuestOsGetInfo guestOsGetInfo;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 全てのホストOSに接続し、全てのゲストOSを取得し、結果を返す（管理者向け機能）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//全てのゲストOSの情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request)) && guestOsGetInfo.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSALLGET.toString())) {
			//リクエストパラメータの内容を取得し各値に設定
			String apiname = request.getParameter("apiname");
			
			//ログインIDからユーザ情報を取得する
			List<UserEntity> userlist = guestOsGetInfo.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));

			//ホストOSのエージェントと通信し、最新のゲストOS情報を取得し、DBを更新する
			guestOsGetInfo.guestOsLogic.GuestOsGetRecord();
			
			//操作ログをDBに書き込む
			guestOsGetInfo.operationLogLogic.OperationLogDbAdd(userlist.get(0).getUserId(),OPERATIONTARGET.ALLGUESTOS.getTargetName(),OPERATIONCONTENTSID.GETALL.getContentsid());
			
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = guestOsGetInfo.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userlist.get(0).getUserId());	    	
			List<OperationLogSearch> operationloglist = guestOsGetInfo.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname,OPERATIONTARGET.ALLGUESTOS.getTargetName() + MESSAGE.SUCCESS.getMessage(),json);
        }
		//管理者権限以外の場合実行
        else {
        	//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),"",GRANT.GUESTOSALLGET.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
        }	
	}

}
