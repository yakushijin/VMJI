package mainPackage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.Servlet;
import mainPackage.SqlQuerys.OperationLogSearchAdmin;
import mainPackage.SqlQuerys.OperationLogSearchUser;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.OperationLogSearch;
import mainPackage.entity.UserEntity;

/**
 * 操作ログ情報取得コントローラ
 */
@WebServlet("/operationlog")
public class OperationLogController extends Servlet<OperationLogSearch>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private OperationLogController operationLogController;
	
	/**
	 * ログページをを画面に表示（管理者向け機能、ログの取得は別処理にて非同期にて実施）
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ユーザIDからゲストOS情報を取得する。管理者ユーザのリクエスト以外はエラーとする。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//ログ一覧画面をフロントに表示
			request.getRequestDispatcher("/WEB-INF/view/operationlog.jsp").forward(request,response);
        }
		//管理者権限以外の場合実行
        else {
        	//エラー画面をフロントに表示
        	request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request,response);
        }			
	}

	/**
	 * ログの情報を取得し、結果を画面に返す。一般ユーザはそのユーザに紐づくログを、管理者はすべてのログを取得
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<OperationLogSearch> operationloglist = new ArrayList<OperationLogSearch>();
		
		//権限チェックをし、管理者ユーザと一般ユーザにてそれぞれ処理を分岐させる。
		if (CheckAuthority(GetSessionAuthority(request))) {
			//データベースからすべてのログ情報を取得
			operationloglist = operationLogController.operationLogDao.OperationLogSearch(OperationLogSearchAdmin.Name);

        }
        else {
    		//ログインIDからユーザ情報を取得する
    		List<UserEntity> userlist = operationLogController.userDao.UserSearchId(UserSearchId.Name,GetSessionLoginId(request));
        	
        	//データベースからユーザに紐づくログ情報を取得
        	operationloglist = operationLogController.operationLogDao.OperationLogSearch(OperationLogSearchUser.Name,userlist.get(0).getUserId());
        }
		
		//取得したログデータを画面に返す
		AjaxResponse(request,response,operationloglist);
	}
		
	/**
	 * 本クラス共通の処理。画面へユーザ情報を返す
	 */
	private void AjaxResponse(HttpServletRequest request, HttpServletResponse response,List<OperationLogSearch> operationloglist) throws ServletException, IOException {
		//リクエストパラメータの内容を取得し各値に設定
		String apiname = request.getParameter("apiname");
		
		//画面に返す値を設定
		String json = GetListJson(operationloglist);
		StringResponse(request,"loglist",json);

		//結果を画面へ返す
		AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname,MESSAGE.SUCCESS.getMessage(),json);
	}
}
