package mainPackage.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import mainPackage.GlobalParameter.MESSAGE;
import mainPackage.GlobalParameter.SUCCESSORFAILURE;
import mainPackage.Servlet;
import mainPackage.entity.InformationEntity;

/**
 * インフォメーション情報取得コントローラ
 */
@WebServlet("/Information")
public class InformationController extends Servlet<InformationEntity>{
	private static final long serialVersionUID = 1L;

	@Autowired
	private InformationController informationController;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * インフォメーション情報を取得し、結果を画面に返す。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//管理者権限の場合実行
		if (CheckAuthority(GetSessionAuthority(request))) {
			//データベースからすべてのインフォメーション情報を取得
			List<InformationEntity> informationlist = informationController.informationDao.SelectEntity();
			//取得したログデータを画面に返す
			AjaxResponse(request,response,informationlist);
        }
		//管理者権限以外の場合実行
        else {
        	//データベースからすべてのインフォメーション情報を取得
			List<InformationEntity> informationlist = informationController.informationDao.SelectEntity();
			//取得したログデータを画面に返す
			AjaxResponse(request,response,informationlist);
        }
	}
	
	/**
	 * 本クラス共通の処理。画面へユーザ情報を返す
	 */
	private void AjaxResponse(HttpServletRequest request, HttpServletResponse response,List<InformationEntity> informationlist) throws ServletException, IOException {
		//リクエストパラメータの内容を取得し各値に設定
		String apiname = request.getParameter("apiname");
		
		//画面に返す値を設定
		String json = GetListJson(informationlist);
		StringResponse(request,"Infolist",json);

		//結果を画面へ返す
		AjaxResponse(request,response,SUCCESSORFAILURE.DONTJUDGE.getId(),apiname,"" + MESSAGE.SUCCESS.getMessage(),json);
	}
}
