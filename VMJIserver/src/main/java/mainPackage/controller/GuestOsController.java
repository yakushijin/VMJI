package mainPackage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Servlet;
import mainPackage.GlobalParameter.GRANT;
import mainPackage.SqlQuerys.GuestOsSearchUserHostOsAll;
import mainPackage.SqlQuerys.GuestOsSearchUserHostOsHostOsId;
import mainPackage.SqlQuerys.GuestOsSearchUserHostOsUserId;
import mainPackage.entity.GuestSearch;

/**
 * ゲストOS一覧画面コントローラ
 *
 */
@WebServlet("/guestos")
public class GuestOsController extends Servlet<GuestSearch> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private GuestOsController guestOsController;

	/**
	 * すべてのゲストOSの情報を取得し、結果を画面に返す（管理者向け機能）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//全てのゲストOSの情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//データベースからゲストOS情報を取得
			List<GuestSearch> guestoslist = guestOsController.guestOsDao.GuestOsSearchAll(GuestOsSearchUserHostOsAll.Name);
			
			//取得したゲストOS情報を画面に返す
			doResponse(request,response,guestoslist);
        }
		//管理者権限以外の場合実行
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }		
	}

	/**
	 * 以下どちらかを実行する。（管理者向け機能）
	 * ①ホストOS一覧画面から、ホストOSに紐づくゲストOSを取得し、結果を画面に返す
	 * ②ユーザ一覧画面から、ユーザに紐づくゲストOSを取得し、結果を画面に返す
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストされたIDに紐づくゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {					
			List<GuestSearch> guestoslist = new ArrayList<GuestSearch>();
			//受取元画面による処理の分岐。（実行するSQLを変更する）
			switch(GetScreen(request)) {					
				case "UserListScreen" :
					//ユーザIDをキーにデータベースからゲストOS情報を取得
					guestoslist = guestOsController.guestOsDao.GuestOsSearchUserId(GuestOsSearchUserHostOsUserId.Name,GetRequestId(request));
					break;
				case "HostOsScreen" :
					//ホストOSIDをキーにデータベースからゲストOS情報を取得
					guestoslist = guestOsController.guestOsDao.GuestOsSearchHostOsId(GuestOsSearchUserHostOsHostOsId.Name,GetRequestId(request));
					break;
			}
			
			//取得したゲストOS情報を画面に返す
			doResponse(request,response,guestoslist);
        }
		//管理者権限以外の場合実行
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }
	}
		
	/**
	 * 本クラス共通の処理。画面へユーザ情報を返す
	 */
	private void doResponse(HttpServletRequest request, HttpServletResponse response,List<GuestSearch> guestoslist) throws ServletException, IOException {		
		//ログインIDとパスワードの参照権限が無い場合対象のカラムを暗号化する
		if (!guestOsController.permission.GrantCheck(GetSessionLoginId(request),GRANT.GUESTOSAUTHBROWSE.toString())) {
			guestOsController.guestOsLogic.GuestOsEncryption(guestoslist);
		}
		
		//画面に返す値を設定
		StringResponse(request,"guestoslist",GetListJson(guestoslist));

		//結果を画面へ返す
		PageResponse(request,response,"guestos.jsp");
	}

}
