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
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.UserEntity;

/*
 * ユーザ向け画面コントローラ
 */
@WebServlet("/user")
public class UserController extends Servlet<UserEntity> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserController userController;

	/**
	 * 一般ユーザ向けの取得処理。ログインしているユーザのユーザIDに紐づくゲストOS情報を取得する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		//ログインIDからユーザ情報を取得する
		List<UserEntity> userlist = userController.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
		
		//ユーザIDからゲストOS情報を取得し、結果を画面に返す。ユーザ情報が無い、または複数ヒットした場合エラーとする。
		if (userlist.size() == 1) {
			//画面に返すユーザ情報を取得
			List<UserEntity> userList = GetRecord(request,response,GetSessionAuthority(request),userlist.get(0).getUserId());
			
			//取得したユーザ情報を画面に返す
			doResponse(request,response,userList);
		}
		else {
			request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
		}	
	}
	
	
	/**
	 * 管理者向けの取得処理。ユーザ一覧画面より、指定したユーザのユーザIDに紐づくゲストOS情報を取得する
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//ユーザIDからゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//画面に返すユーザ情報を取得
			List<UserEntity> userList = GetRecord(request,response,GetSessionAuthority(request),Integer.parseInt(request.getParameter("userid")));
			//取得したユーザ情報を画面に返す
			doResponse(request,response,userList);
        }
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }		
	}
	
	/**
	 * 本クラス共通の処理。ユーザIDに紐づくゲストOS情報を取得する。
	 */
	private List<UserEntity> GetRecord(HttpServletRequest request, HttpServletResponse response,String Authority,int userid){
		//ユーザIDに紐づくゲストOS情報を、ホストOSに接続し取得する
		List<UserEntity> userList = userController.guestOsLogic.UserGuestOsGetRecord(userid);
		
		//ログインIDとパスワードの参照権限が無い場合対象のカラムを暗号化する
		if (!userController.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSAUTHBROWSE.toString())) {
			userController.guestOsLogic.UserGestOsEncryption(userList);
		}
		//userList.get(0).getGuestOsEntity().get(0).getLoginPassword();
		
		return userList;
	}
	
	/**
	 * 本クラス共通の処理。画面へユーザ情報を返す
	 */
	private void doResponse(HttpServletRequest request, HttpServletResponse response,List<UserEntity> userList) throws ServletException, IOException {
		//画面に返す値を設定
		StringResponse(request,"UserName",userList.get(0).getUserName());
		StringResponse(request,"userlist",GetListJson(userList));

		//結果を画面へ返す
		PageResponse(request,response,"user.jsp");
	}
	
}
