package mainPackage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import mainPackage.GlobalParameter.GRANT;
import mainPackage.SqlQuerys.UserSearchId;
import mainPackage.dao.UserDao;
import mainPackage.entity.UserEntity;

public class Permission {
	@Autowired
	private Permission permission;
	
	protected UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public boolean GrantCheck(String lginid,String grantname) {
		List<UserEntity> userlist = permission.userDao.UserSearchId(UserSearchId.Name,lginid);
		
		boolean grantPermission = false;
		
		if(grantname.equals(GRANT.USERCREATE.toString())) {
			grantPermission = userlist.get(0).getGrantUserCreate();			
		}else if(grantname.equals(GRANT.USERUPDATE.toString())) {
			grantPermission = userlist.get(0).getGrantUserUpdate();
		}else if(grantname.equals(GRANT.USERDELETE.toString())) {
			grantPermission = userlist.get(0).getGrantUserDelete();
		}else if(grantname.equals(GRANT.USERAUTHBROWSE.toString())) {
			grantPermission = userlist.get(0).getGrantUserAuthBrowse();
		}else if(grantname.equals(GRANT.GUESTOSALLGET.toString())) {
			grantPermission = userlist.get(0).getGrantGuestOsAllGet();
		}else if(grantname.equals(GRANT.GUESTOSSTART.toString())) {
			grantPermission = userlist.get(0).getGrantGuestOsStart();
		}else if(grantname.equals(GRANT.GUESTOSSTOP.toString())) {
			grantPermission = userlist.get(0).getGrantGuestOsStop();
		}else if(grantname.equals(GRANT.GUESTOSAUTHBROWSE.toString())) {
			grantPermission = userlist.get(0).getGrantGuestOsAuthBrowse();
		}else if(grantname.equals(GRANT.GUESTOSUPDATE.toString())) {
			grantPermission = userlist.get(0).getGrantGuestOsUpdate();
		}else if(grantname.equals(GRANT.HOSTOSCREATE.toString())) {
			grantPermission = userlist.get(0).getGrantHostOsCreate();
		}else if(grantname.equals(GRANT.HOSTOSUPDATE.toString())) {
			grantPermission = userlist.get(0).getGrantHostOsUpdate();
		}else if(grantname.equals(GRANT.HOSTOSDELETE.toString())) {
			grantPermission = userlist.get(0).getGrantHostOsDelete();
		}else {
			grantPermission = false;
		}
		
		return grantPermission;
		
	}
	
}
