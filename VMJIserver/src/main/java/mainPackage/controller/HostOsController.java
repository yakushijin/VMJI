package mainPackage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import mainPackage.Servlet;
import mainPackage.SqlQuerys.HostOsSearchCalcAll;
import mainPackage.SqlQuerys.HostOsSearchCalcHostOsId;
import mainPackage.entity.HostSearch;

/**
 * ホストOS一覧画面コントローラ
 */
@WebServlet("/hostos")
public class HostOsController extends Servlet<HostSearch> {
	private static final long serialVersionUID = 1L;

	@Autowired
	private HostOsController hostOsController;
	
	/**
	 * すべてのホストOSの情報を取得し、結果を画面に返す（管理者向け機能）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//全てのホストOSの情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//データベースからホストOS情報を取得
			List<HostSearch> hostoslist = hostOsController.hostOsDao.HostOsSearchAll(HostOsSearchCalcAll.Name);
			
			//取得したホストOS情報を画面に返す
			doResponse(request,response,hostoslist);
        }
		//管理者権限以外の場合実行
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }
	}

	/**
	 * ゲストOS一覧画面から、ゲストOSに紐づくホストOSを取得し、結果を画面に返す（管理者向け機能）
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストされたIDに紐づくゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//データベースからホストOS情報を取得
			List<HostSearch> hostoslist = hostOsController.hostOsDao.HostOsSearchCalcHostOsId(HostOsSearchCalcHostOsId.Name,GetRequestId(request));
			
			//取得したホストOS情報を画面に返す
			doResponse(request,response,hostoslist);
        }
		//管理者権限以外の場合実行
        else {
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }
	}
	
	/**
	 * 本クラス共通の処理。画面へユーザ情報を返す
	 */
	private void doResponse(HttpServletRequest request, HttpServletResponse response,List<HostSearch> hostoslist) throws ServletException, IOException {
		//画面に返す値を設定
		StringResponse(request,"hostoslist",GetListJson(hostoslist));
	
		//結果を画面へ返す
		PageResponse(request,response,"hostos.jsp");
	}
}
