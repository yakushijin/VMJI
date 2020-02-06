package mainPackage.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mainPackage.Servlet;

/**
 * ログイン画面コントローラ
 */
@WebServlet("/top")
public class TopController extends Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * ログイン画面をフロントに返す
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageResponse(request,response,"top.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
