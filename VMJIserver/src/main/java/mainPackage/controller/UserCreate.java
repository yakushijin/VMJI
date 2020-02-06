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
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

/**
 * ユーザ情報更新コントローラ
 */
@WebServlet("/usercreate")
public class UserCreate extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserCreate userCreate;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 画面上で編集したユーザ情報をDBに反映させる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの内容を取得し各値に設定
		String apiname = request.getParameter("apiname");
		
		//ユーザ情報を画面の変更内容に従い更新する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request)) && userCreate.permission.GrantCheck(GetSessionLoginId(request),GRANT.USERCREATE.toString())) {
			//リクエストパラメータの内容を取得し各値に設定			
			userCreate.userEntity.setUserId(Integer.parseInt(request.getParameter("userid")));
			userCreate.userEntity.setUserName(request.getParameter("username"));
			userCreate.userEntity.setLoginId(request.getParameter("loginid"));
			userCreate.userEntity.setPassword(request.getParameter("password"));
			userCreate.userEntity.setAdminFlag(Boolean.valueOf(request.getParameter("adminflag")));
			userCreate.userEntity.setRemarks(request.getParameter("remarks"));
			
			userCreate.userEntity.setGrantUserCreate(Boolean.valueOf(request.getParameter("grantusercreate")));
			userCreate.userEntity.setGrantUserUpdate(Boolean.valueOf(request.getParameter("grantuserupdate")));
			userCreate.userEntity.setGrantUserDelete(Boolean.valueOf(request.getParameter("grantuserdelete")));
			userCreate.userEntity.setGrantUserAuthBrowse(Boolean.valueOf(request.getParameter("grantuserauthbrowse")));
			userCreate.userEntity.setGrantGuestOsAllGet(Boolean.valueOf(request.getParameter("grantguestosallget")));
			userCreate.userEntity.setGrantGuestOsStart(Boolean.valueOf(request.getParameter("grantguestosstart")));
			userCreate.userEntity.setGrantGuestOsStop(Boolean.valueOf(request.getParameter("grantguestosstop")));
			userCreate.userEntity.setGrantGuestOsAuthBrowse(Boolean.valueOf(request.getParameter("grantguestosauthbrowse")));
			userCreate.userEntity.setGrantGuestOsUpdate(Boolean.valueOf(request.getParameter("grantguestosupdate")));
			userCreate.userEntity.setGrantHostOsCreate(Boolean.valueOf(request.getParameter("granthostoscreate")));
			userCreate.userEntity.setGrantHostOsUpdate(Boolean.valueOf(request.getParameter("granthostosupdate")));
			userCreate.userEntity.setGrantHostOsDelete(Boolean.valueOf(request.getParameter("granthostosdelete")));
			
			//変換したゲストOS情報をDBに登録
			userCreate.userLogic.AddLogic(userCreate.userEntity);

			//ログインIDからユーザ情報を取得する（ログ書き込み用）
			List<UserEntity> userlist = userCreate.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
			int userid = userlist.get(0).getUserId();
			
			//更新対象のユーザ名を取得（ログ書き込み用）
			String UserName = request.getParameter("username");
			
			userCreate.operationLogLogic.OperationLogDbAdd(userid,UserName,OPERATIONCONTENTSID.CREATE.getContentsid());
			
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = userCreate.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userid);	    	
			List<OperationLogSearch> operationloglist = userCreate.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname,UserName + MESSAGE.CREATE.getMessage() + MESSAGE.SUCCESS.getMessage(),json);
        }
		//管理者権限以外の場合実行
        else {
        	//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),apiname,GRANT.USERCREATE.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
        }
	}
	
}
