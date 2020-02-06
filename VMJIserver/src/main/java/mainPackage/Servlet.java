package mainPackage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mainPackage.GlobalParameter.AUTHORITY;
import mainPackage.GlobalParameter.GRANT;
import mainPackage.GlobalParameter.OPERATIONTARGET;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.businessLogic.*;
import mainPackage.dao.*;
import mainPackage.entity.*;

public class Servlet<T> extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected DefaultRedirectStrategy defaultRedirectStrategy;
	
	public void setDefaultRedirectStrategy(DefaultRedirectStrategy defaultRedirectStrategy) {
		this.defaultRedirectStrategy = defaultRedirectStrategy;
	}
	
	protected UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	protected GuestOsDao guestOsDao;
	
	public void setGuestOsDao(GuestOsDao guestOsDao) {
		this.guestOsDao = guestOsDao;
	}

	protected HostOsDao hostOsDao;
	
	public void setHostOsDao(HostOsDao hostOsDao) {
		this.hostOsDao = hostOsDao;
	}
	
	protected OperationLogDao operationLogDao;
	
	public void setOperationLogDao(OperationLogDao operationLogDao) {
		this.operationLogDao = operationLogDao;
	}

	protected InformationDao informationDao;
	
	public void setInformationDao(InformationDao informationDao) {
		this.informationDao = informationDao;
	}

	protected UserEntity userEntity;
	
	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	protected GuestOsEntity guestOsEntity;
	
	public void setGuestOsEntity(GuestOsEntity guestOsEntity) {
		this.guestOsEntity = guestOsEntity;
	}
	
	protected HostOsEntity hostOsEntity;
	
	public void setHostOsEntity(HostOsEntity hostOsEntity) {
		this.hostOsEntity = hostOsEntity;
	}
	
	protected OperationLogEntity operationLogEntity;
	
	public void setOperationLogEntity(OperationLogEntity operationLogEntity) {
		this.operationLogEntity = operationLogEntity;
	}
	
	protected InformationEntity informationEntity;
	
	public void setInformationEntity(InformationEntity informationEntity) {
		this.informationEntity = informationEntity;
	}	
	
	protected UserLogic userLogic;
	
	public void setUserLogic(UserLogic userLogic) {
		this.userLogic = userLogic;
	}
	
	protected GuestOsLogic guestOsLogic;
	
	public void setGuestOsLogic(GuestOsLogic guestOsLogic) {
		this.guestOsLogic = guestOsLogic;
	}
	
	protected HostOsLogic hostOsLogic;
	
	public void setHostOsLogic(HostOsLogic hostOsLogic) {
		this.hostOsLogic = hostOsLogic;
	}
	
	protected OperationLogLogic operationLogLogic;
	
	public void setOperationLogLogic(OperationLogLogic operationLogLogic) {
		this.operationLogLogic = operationLogLogic;
	}
	
	protected Permission permission;
	
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	@Override
	public void init() throws ServletException {
		super.init();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);;
	}
	
	/**
	 * セッション情報からユーザ権限情報を取得する
	 */	
	protected String GetSessionAuthority(HttpServletRequest request) {
		HttpSession session = request.getSession(true); 
		String Authority = (String)session.getAttribute("Authority");
		return Authority;
	}

	/**
	 * セッション情報からユーザログインIDを取得する
	 */	
	protected String GetSessionLoginId(HttpServletRequest request) {
		HttpSession session = request.getSession(true); 
		String loginid = (String)session.getAttribute("loginid");
		return loginid;
	}
	
	/**
	 * ユーザ権限情報を使用し、管理者ユーザか一般ユーザか判定する
	 */	
	protected boolean CheckAuthority(String Authority) {
		if (Authority.equals(AUTHORITY.ROLE_ADMIN.toString())) {
			return true;
        }
        else {
        	return false;
        }		
	}
	
	/**
	 * リクエスト情報からユーザIDを取得する
	 */	
	protected int GetRequestUserid(HttpServletRequest request) {
		int userid = Integer.parseInt(request.getParameter("userid"));
		return userid;
	}
	
	/**
	 * リクエスト情報から各種IDを取得する
	 */	
	protected int GetRequestId(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		return id;
	}
	
	/**
	 * リクエスト情報から画面名を取得する
	 */	
	protected String GetScreen(HttpServletRequest request) {
		String screen = request.getParameter("screen");
		return screen;
	}
	
	/**
	 * 各種リストをJsonへ変換
	 */	
	public String GetListJson(List<T> list){
		//取得したリストをJsonに変換
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String json = gson.toJson(list);		
		return json;
	}
	
	/**
	 * 各レスポンスパラメータへ値を設定する
	 */	
	public void StringResponse(HttpServletRequest request,String jspstring,String javastring){
		request.setAttribute(jspstring,javastring);
	}
	
	/**
	 * 各画面へレスポンスを返す
	 */	
	public void PageResponse(HttpServletRequest request,HttpServletResponse response,String page) throws ServletException, IOException{
		String dir = "/WEB-INF/view/";
		String url = dir + page;
		request.getRequestDispatcher(url).forward(request,response);
	}
	
	/**
	 * 各画面へレスポンスを返す
	 */	
	public void AjaxResponse(HttpServletRequest request,HttpServletResponse response,int SuccessOrFailure,String apiname,String message,String data) throws ServletException, IOException{

		
		
		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{\"SuccessOrFailure\" :[\"" + SuccessOrFailure + "\"],\"apiname\": [\"" + apiname + "\"],\"message\": [\"" + message + "\"],\"data\" :" + data + "}");
	}
	
}
