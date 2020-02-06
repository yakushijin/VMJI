package mainPackage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import mainPackage.GlobalParameter.GRANT;
import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.Servlet;
import mainPackage.SqlQuerys.UserListSearchAll;
import mainPackage.entity.UserListSearch;

/**
 * ユーザ一覧画面コントローラ
 */
@WebServlet("/userlist")
public class UserList extends Servlet<UserListSearch> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserList userList;

	/**
	 * すべてのユーザの情報を取得し、結果を画面に返す（管理者向け機能）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ユーザIDからゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			
			//DBからすべてのユーザ情報を取得する
			List<UserListSearch> userListRecode = userList.userDao.UserListSearchAll(UserListSearchAll.Name);
			
			//ログインIDとパスワードの参照権限が無い場合対象のカラムを暗号化する
			if (!userList.permission.GrantCheck(GetSessionLoginId(request),GRANT.USERAUTHBROWSE.toString())) {
				userList.userLogic.UserEncryption(userListRecode);
			}
			
			//ログインしたことが無いユーザに対する制御
			userList.userLogic.UserLoginHistoryUpdate(userListRecode);
			
			//画面に返す値を設定
			StringResponse(request,"userlist",GetListJson(userListRecode));					

			//結果を画面へ返す
			PageResponse(request,response,"userlist.jsp");
        }
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }			
	}
	
	/**
	 * ユーザリストボックス用の検索。検索条件にそったユーザ情報を画面に返す（管理者向け機能）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//ユーザIDからゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//リクエストパラメータの内容を取得し各値に設定
			String apiname = request.getParameter("apiname");
			String username = request.getParameter("username");
			String loginid = request.getParameter("loginid");
			String adminflag = request.getParameter("adminflag");

			//DBから条件に沿ったユーザ情報を取得する
			List<UserListSearch> userListRecode = userList.userDao.UserListConditionsSearchAnd(username,loginid,adminflag);
			
			//画面に返す値を設定
			String json = GetListJson(userListRecode);
			StringResponse(request,"conditionssearchuserlist",json);

			//結果を画面へ返す
			AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname, MESSAGE.SUCCESS.getMessage(),json);
        }
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }				
	}	
	
}
