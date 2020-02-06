package mainPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import mainPackage.GlobalParameter.AUTHORITY;
import mainPackage.GlobalParameter.OPERATIONCONTENTSID;
import mainPackage.GlobalParameter.OPERATIONTARGET;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.entity.UserEntity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 認証管理クラス
 */
public class AuthSuccessHandler extends Servlet implements AuthenticationSuccessHandler {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private AuthSuccessHandler authSuccessHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException {
        //リクエスト内容の管理者権限情報とログインID情報を取得する
        List<GrantedAuthority> newAuthorities = new ArrayList<>(authentication.getAuthorities());
        UserDetails principal = (UserDetails)authentication.getPrincipal();
        HttpSession session = request.getSession(true); 
        String Authority = newAuthorities.get(0).getAuthority();
        String LoginId = principal.getUsername();
        
        //セッションへ各値をセット
        session.setAttribute("Authority", Authority);
        session.setAttribute("loginid", LoginId);
        
        //ログインIDからユーザIDを取得
        List<UserEntity> userList = authSuccessHandler.userDao.UserSearchId(UserSearchId.Name,LoginId);

		//操作ログをDBに書き込む
		authSuccessHandler.operationLogLogic.OperationLogDbAdd(userList.get(0).getUserId(),OPERATIONTARGET.SYSTEM.getTargetName(),OPERATIONCONTENTSID.LOGIN.getContentsid());
        
		//リダイレクト先を管理者ユーザと一般ユーザに振り分ける
        if (Authority.equals(AUTHORITY.ROLE_ADMIN.toString())) {
        	//管理者ユーザの場合ゲストOS一覧画面へ
        	authSuccessHandler.defaultRedirectStrategy.sendRedirect(request, response, "/guestos");
        }
        else if (Authority.equals(AUTHORITY.ROLE_USER.toString())) {
        	//一般ユーザの場合ユーザ用画面へ
        	authSuccessHandler.defaultRedirectStrategy.sendRedirect(request, response, "/user");
        }
        else {
        	authSuccessHandler.defaultRedirectStrategy.sendRedirect(request, response, "/top");
        }
    }
    
}