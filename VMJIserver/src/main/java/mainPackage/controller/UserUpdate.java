package mainPackage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Servlet;
import mainPackage.GlobalParameter.AUTHORITY;
import mainPackage.GlobalParameter.GRANT;
import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.OPERATIONCONTENTSID;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.SqlQuerys.GuestOsSearchGuestOsid;
import mainPackage.SqlQuerys.OperationLogSearchLogid;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.SqlQuerys.UserSearchUserId;
import mainPackage.entity.GuestOsEntity;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;

/**
 * ユーザ情報更新コントローラ
 */
@WebServlet("/userupdate")
public class UserUpdate extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UserUpdate userUpdate;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 画面上で編集したユーザ情報をDBに反映させる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ユーザ情報を画面の変更内容に従い更新する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request)) && userUpdate.permission.GrantCheck(GetSessionLoginId(request),GRANT.USERUPDATE.toString())) {
			//リクエストパラメータの内容を取得し各値に設定
			userUpdate.userEntity.setUserId(Integer.parseInt(request.getParameter("userid")));
			userUpdate.userEntity.setUserName(request.getParameter("username"));
			userUpdate.userEntity.setAdminFlag(Boolean.valueOf(request.getParameter("adminflag")));
			userUpdate.userEntity.setRemarks(request.getParameter("remarks"));
			
			userUpdate.userEntity.setGrantUserCreate(Boolean.valueOf(request.getParameter("grantusercreate")));
			userUpdate.userEntity.setGrantUserUpdate(Boolean.valueOf(request.getParameter("grantuserupdate")));
			userUpdate.userEntity.setGrantUserDelete(Boolean.valueOf(request.getParameter("grantuserdelete")));
			userUpdate.userEntity.setGrantUserAuthBrowse(Boolean.valueOf(request.getParameter("grantuserauthbrowse")));
			userUpdate.userEntity.setGrantGuestOsAllGet(Boolean.valueOf(request.getParameter("grantguestosallget")));
			userUpdate.userEntity.setGrantGuestOsStart(Boolean.valueOf(request.getParameter("grantguestosstart")));
			userUpdate.userEntity.setGrantGuestOsStop(Boolean.valueOf(request.getParameter("grantguestosstop")));
			userUpdate.userEntity.setGrantGuestOsAuthBrowse(Boolean.valueOf(request.getParameter("grantguestosauthbrowse")));
			userUpdate.userEntity.setGrantGuestOsUpdate(Boolean.valueOf(request.getParameter("grantguestosupdate")));
			userUpdate.userEntity.setGrantHostOsCreate(Boolean.valueOf(request.getParameter("granthostoscreate")));
			userUpdate.userEntity.setGrantHostOsUpdate(Boolean.valueOf(request.getParameter("granthostosupdate")));
			userUpdate.userEntity.setGrantHostOsDelete(Boolean.valueOf(request.getParameter("granthostosdelete")));
			
			//ユーザのログインID、パスワード設定
			EncryptionCheck(userUpdate.userEntity,request);
			
			//変換したゲストOS情報をDBに登録
			userUpdate.userLogic.UpdateLogic(userUpdate.userEntity);
			
			//ログインIDからユーザ情報を取得する（ログ書き込み用）
			List<UserEntity> userlist = userUpdate.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
			int userid = userlist.get(0).getUserId();
			
			//更新対象のユーザ名を取得（ログ書き込み用）
			String UserName = request.getParameter("username");
			
			userUpdate.operationLogLogic.OperationLogDbAdd(userid,UserName,OPERATIONCONTENTSID.UPDATE.getContentsid());			
			
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = userUpdate.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userid);	    	
			List<OperationLogSearch> operationloglist = userUpdate.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),"",UserName + MESSAGE.UPDATE.getMessage()+ MESSAGE.SUCCESS.getMessage(),json);
        }
		//管理者権限以外の場合実行
        else {
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),"",GRANT.USERUPDATE.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
        }
	}
		
	/**
	 * ユーザのログインID、パスワードの設定
	 */
	private void EncryptionCheck(UserEntity entity,HttpServletRequest request)
	{
		//ログインIDとパスワードの参照権限がある場合リクエストの内容を設定
		if (userUpdate.permission.GrantCheck(GetSessionLoginId(request),GRANT.USERAUTHBROWSE.toString())) {
			userUpdate.userEntity.setLoginId(request.getParameter("loginid"));
			userUpdate.userEntity.setPassword(request.getParameter("password"));					
		}else {
			//ログインIDとパスワードの参照権限が無い場合対象のカラムが暗号化されているため更新対象としない
			List<UserEntity> userlist = userUpdate.userDao.UserSearchUserId(UserSearchUserId.Name,entity.getUserId());
			userUpdate.userEntity.setLoginId(userlist.get(0).getLoginId());
			userUpdate.userEntity.setPassword(userlist.get(0).getPassword());
		}
	}
	
}
