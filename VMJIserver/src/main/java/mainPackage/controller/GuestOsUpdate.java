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
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.businessLogic.GuestOsLogic;
import mainPackage.businessLogic.OperationLogLogic;
import mainPackage.entity.GuestOsEntity;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.OperationLogSearchNewLogid;
import mainPackage.entity.UserEntity;
import mainPackage.SqlQuerys.GuestOsSearchGuestOsid;
import mainPackage.SqlQuerys.OperationLogSearchLogid;
import mainPackage.SqlQuerys.OperationLogSearchNewLogidUser;

/**
 * ゲストOS情報更新コントローラ
 *
 */
@WebServlet("/guestosupdate")
public class GuestOsUpdate extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private GuestOsUpdate guestOsUpdate;	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * 画面上で編集したゲストOS情報をDBに反映させる
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ゲストOS情報を画面の変更内容に従い更新する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request)) && guestOsUpdate.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSUPDATE.toString())) {
			//リクエストパラメータの内容を取得し各値に設定
			guestOsUpdate.guestOsEntity.setGuestOsId(Integer.parseInt(request.getParameter("guestosid")));
			guestOsUpdate.guestOsEntity.setGuestOsHostName(request.getParameter("guestoshostname"));
			guestOsUpdate.guestOsEntity.setOs(request.getParameter("os"));
			guestOsUpdate.guestOsEntity.setIp(request.getParameter("ip"));
			guestOsUpdate.guestOsEntity.setLanIp(request.getParameter("lanip"));
			guestOsUpdate.guestOsEntity.setVlanId(request.getParameter("vlanid"));
			guestOsUpdate.guestOsEntity.setLoginPort(request.getParameter("loginport"));
			guestOsUpdate.guestOsEntity.setKanshi(Boolean.valueOf(request.getParameter("kanshi")));
			guestOsUpdate.guestOsEntity.setRemarks(request.getParameter("remarks"));
			
			guestOsUpdate.userEntity.setUserId(Integer.parseInt(request.getParameter("userid")));
			guestOsUpdate.guestOsEntity.setUserId(guestOsUpdate.userEntity);			
		
			//ゲストOSのログインID、パスワード設定
			EncryptionCheck(guestOsUpdate.guestOsEntity,request);
			
			//変換したゲストOS情報をDBに登録
			guestOsUpdate.guestOsLogic.UpdateLogic(guestOsUpdate.guestOsEntity);
			
			//ログインIDからユーザ情報を取得する（ログ書き込み用）
			List<UserEntity> userlist = guestOsUpdate.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
			int userid = userlist.get(0).getUserId();
			
			//更新対象のゲストOS名を取得（ログ書き込み用）
			List<GuestOsEntity> guestOsList = guestOsUpdate.guestOsDao.GuestOsSearchGuestOsid(GuestOsSearchGuestOsid.Name,Integer.parseInt(request.getParameter("guestosid")));	
			String GuestOsName = guestOsList.get(0).getGuestOsName();
			
			//操作ログをDBに書き込む
			guestOsUpdate.operationLogLogic.OperationLogDbAdd(userid,GuestOsName,OPERATIONCONTENTSID.UPDATE.getContentsid());
			
			//画面返却用のログ取得（非同期の為、最新のログを取得する）
			List<OperationLogSearchNewLogid> logid = guestOsUpdate.operationLogDao.OperationLogSearchNewLogidUser(OperationLogSearchNewLogidUser.Name,userid);	    	
			List<OperationLogSearch> operationloglist = guestOsUpdate.operationLogDao.OperationLogSearch(OperationLogSearchLogid.Name,logid.get(0).getLogId());
	    			
			//画面に返す値を設定
			String json = GetListJson(operationloglist);
			
			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),"",GuestOsName + MESSAGE.UPDATE.getMessage() + MESSAGE.SUCCESS.getMessage(),json);
        }
		//管理者権限以外の場合実行
        else {
        	//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.FAILURE.getId(),"",GRANT.GUESTOSUPDATE.getGrantName() + MESSAGE.GRANTNOT.getMessage(),"[]");
        }		
	}
	
	/**
	 * ゲストOSのログインID、パスワードの設定
	 */
	private void EncryptionCheck(GuestOsEntity entity,HttpServletRequest request)
	{
		//ログインIDとパスワードの参照権限がある場合リクエストの内容を設定
		if (guestOsUpdate.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSUPDATE.toString())) {
			guestOsUpdate.guestOsEntity.setLoginUser(request.getParameter("loginpassword"));
			guestOsUpdate.guestOsEntity.setLoginPassword(request.getParameter("loginpassword"));
					
		}else {
			//ログインIDとパスワードの参照権限が無い場合対象のカラムが暗号化されているため更新対象としない
			List<GuestOsEntity> guestOsList = guestOsUpdate.guestOsDao.GuestOsSearchGuestOsid(GuestOsSearchGuestOsid.Name,entity.getGuestOsId());			
			guestOsUpdate.guestOsEntity.setLoginPassword(guestOsList.get(0).getLoginPassword());
			guestOsUpdate.guestOsEntity.setLoginUser(guestOsList.get(0).getLoginUser());	
		}
	}
	
}
