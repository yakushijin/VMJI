package mainPackage;

/**
 * 各daoクラスにて発行するsqlを生成
 */
public class SqlQuerys extends SqlNames{
	
	//log
	private class OperationLogSearch{
		protected static final String CommonName = SqlNames.OperationLogSearch;	
		protected static final String CommonQuery = 
				"select "
				+ "new mainPackage.entity.OperationLogSearch"
				+ "(a.LogId,b.UserId,b.UserName,a.GuestOsName,to_char(a.OperationDate,'YYYY/MM/DD-HH:MI:SS'),c.OperationId,c.OperationContents) "
				+ "from OperationLogEntity a "
				+ "join UserEntity b on (a.UserId = b.UserId) "
				+ "join OperationContentsEntity c on (a.OperationId = c.OperationId) "
				;	
	}
	
	public class OperationLogSearchAdmin extends OperationLogSearch{
		public static final String Name = SqlNames.OperationLogSearch + "Admin";
		private static final String parm = "order by a.OperationDate desc";
public static final String Query = 	CommonQuery	+ parm;
	}
	
	public class OperationLogSearchUser extends OperationLogSearch{
		public static final String Name = SqlNames.OperationLogSearch + "User";
		private static final String parm = "where b.UserId = :where "
											+ "order by a.OperationDate desc";
		public static final String Query = 	CommonQuery	+ parm;
	}
	
	public class OperationLogSearchLogid extends OperationLogSearch{
		public static final String Name = SqlNames.OperationLogSearch + "Logid";
		private static final String parm = "where a.LogId = :where ";
		public static final String Query = 	CommonQuery	+ parm;
	}
	
	private class OperationLogSearchNewLogid{
		protected static final String CommonName = SqlNames.OperationLogSearchNewLogid;	
		protected static final String CommonQuery = 
				"select "
				+ "new mainPackage.entity.OperationLogSearchNewLogid"
				+ "(max(LogId)) "
				+ "from OperationLogEntity "
				;	
	}
	
	public class OperationLogSearchNewLogidUser extends OperationLogSearchNewLogid{
		public static final String Name = SqlNames.OperationLogSearchNewLogid + "User";
		private static final String parm = "where userid = :where ";
		public static final String Query = 	CommonQuery	+ parm;
	}
	
	//user
	private class UserSearch{
		protected static final String CommonName = SqlNames.UserSearch;	
		protected static final String CommonQuery = "from UserEntity ";
	}
	
	public class UserSearchId extends UserSearch{
		public static final String Name = SqlNames.UserSearch + "Id";
		private static final String parm = "where LoginId = ?1";
		public static final String Query = CommonQuery + parm;
	}
	
	public class UserSearchUserId extends UserSearch{
		public static final String Name = SqlNames.UserSearch + "UserId";
		private static final String parm = "where UserId = ?1";
		public static final String Query = CommonQuery + parm;
	}
	
	private class UserListSearch{
		protected static final String CommonName = SqlNames.UserListSearch;	
		protected static final String CommonQuery = 
				"select "
				+ "new  mainPackage.entity.UserListSearch"
				+ "(a.UserId,a.UserName,a.LoginId,a.Password,a.AdminFlag,to_char(max(b.OperationDate),'yyyy/mm/dd-hh') "
				+ ",a.GrantUserCreate,a.GrantUserUpdate,a.GrantUserDelete,a.GrantUserAuthBrowse "
				+ ",a.GrantGuestOsAllGet,a.GrantGuestOsStart,a.GrantGuestOsStop,a.GrantGuestOsAuthBrowse "
				+ ",a.GrantGuestOsUpdate,a.GrantHostOsCreate,a.GrantHostOsUpdate,a.GrantHostOsDelete,a.Remarks) "
				+ "from UserEntity a "
				+ "left join OperationLogEntity b on (a.UserId = b.UserId) "
				+ "group by a.UserId,a.UserName,a.LoginId,a.Password,a.AdminFlag,a.Remarks"
				+ ",a.GrantUserCreate,a.GrantUserUpdate,a.GrantUserDelete,a.GrantUserAuthBrowse "
				+ ",a.GrantGuestOsAllGet,a.GrantGuestOsStart,a.GrantGuestOsStop,a.GrantGuestOsAuthBrowse "
				+ ",a.GrantGuestOsUpdate,a.GrantHostOsCreate,a.GrantHostOsUpdate,a.GrantHostOsDelete "
				+ "order by a.UserId"
				;
	}
	
	public class UserListSearchAll extends UserListSearch{
		public static final String Name = SqlNames.UserSearch + "All";
		public static final String Query = CommonQuery;
	}
	
	private class UserListConditionsSearch{
		protected static final String CommonName = SqlNames.UserListConditionsSearch;	
		protected static final String CommonQuery = 
				"select "
				+ "new  mainPackage.entity.UserNameSearch"
				+ "(UserId,UserName) "
				+ "from UserEntity "
				; 
	}
	
	public class UserListConditionsSearchAnd extends UserListConditionsSearch{
		public String CreateSql(String username,String loginid,String adminflag) {
			String sql = CommonQuery;
			sql += "where 1 = 1 ";
			if(username != "") {
				sql += " and UserName = '" + username + "'";
			}
			if(loginid != "") {
				sql += " and LoginId = '" + loginid + "'";
			}
			if(adminflag != "") {
				sql += " and AdminFlag = '" + adminflag + "'";
			}	
			return sql;
		}

	}
	
	//GuestOs
	private class GuestOsSearch{
		protected static final String CommonName = SqlNames.GuestOsSearch;	
		protected static final String CommonQuery = "from GuestOsEntity ";
	}
	
	public class GuestOsSearchUuid extends GuestOsSearch{
		public static final String Name = SqlNames.GuestOsSearch + "Uuid";
		private static final String parm = "where Uuid = ?1";
		public static final String Query = CommonQuery + parm;
	}
	
	public class GuestOsSearchGuestOsid extends GuestOsSearch{
		public static final String Name = SqlNames.GuestOsSearch + "GuestOsid";
		private static final String parm = "where GuestOsid = ?1";
		public static final String Query = CommonQuery + parm;
	}
	
	private class GuestOsSearchUserHostOs{
		protected static final String CommonName = SqlNames.GuestOsSearchUserHostOs;	
		protected static final String CommonQuery = 
				"select "
				+ "new  mainPackage.entity.GuestSearch"
				+ "(a.GuestOsId,a.GuestOsName,a.Status,a.GuestOsHostName,a.Os,a.Cpu,a.Mem,a.Disk,"
				+ "a.Ip,a.LanIp,a.VlanId,a.LoginUser,a.LoginPassword,a.LoginPort,a.Kanshi,c.HostOsId,c.HostName,b.UserId,b.UserName,a.Remarks) "
				+ "from GuestOsEntity a "
				+ "join UserEntity b on (a.UserId = b.UserId) "
				+ "join HostOsEntity c on (a.HostOsId = c.HostOsId) "
				;
		protected static final String Ordrby =  "order by a.GuestOsId ";
	}
	
	public class GuestOsSearchUserHostOsAll extends GuestOsSearchUserHostOs{
		public static final String Name = SqlNames.GuestOsSearchUserHostOs + "All";
		public static final String Query = CommonQuery + Ordrby;
	}
	
	public class GuestOsSearchUserHostOsUserId extends GuestOsSearchUserHostOs{
		public static final String Name = SqlNames.GuestOsSearchUserHostOs + "UserId";
		private static final String parm = "where b.UserId = ?1 ";
		public static final String Query = CommonQuery + parm + Ordrby;
	}
	
	public class GuestOsSearchUserHostOsHostOsId extends GuestOsSearchUserHostOs{
		public static final String Name = SqlNames.GuestOsSearchUserHostOs + "HostOsId";
		private static final String parm = "where c.HostOsId = ?1 ";
		public static final String Query = CommonQuery + parm + Ordrby;
	}
	
	//HostOS
	public class HostOsSearch{
		protected static final String CommonName = SqlNames.HostOsSearch;	
		protected static final String CommonQuery = "from HostOsEntity ";
	}
	
	public class HostOsSearchId extends HostOsSearch{
		public static final String Name = SqlNames.HostOsSearch + "Id";	
		private static final String parm = "where HostOsId = ?1";
		public static final String Query = CommonQuery + parm;
	}	
	
	private class HostOsSearchCalc{
		protected static final String CommonName = SqlNames.HostOsSearchCalc;	
		protected static final String CommonQuery = 
				"select "
				+ "new  mainPackage.entity.HostSearch"
				+ "(a.HostOsId,a.HostName,a.Ip,a.Cpu,a.Mem,a.Disk,sum(b.Cpu),sum(b.Mem),sum(b.Disk),"
				+ 	"sum(b.Cpu) * 100 / a.Cpu,sum(b.Mem) * 100 / a.Mem,sum(b.Disk) * 100 / a.Disk,a.Remarks) "
				+ "from HostOsEntity a "
				+ "join GuestOsEntity b on (a.HostOsId = b.HostOsId) "
				;
	}
	
	public class HostOsSearchCalcAll extends HostOsSearchCalc{
		public static final String Name = SqlNames.HostOsSearchCalc + "All";
		private static final String parm = "group by a.HostOsId,a.HostName,a.Ip,a.Cpu,a.Mem,a.Disk";
		public static final String Query = CommonQuery + parm;
	}
	
	public class HostOsSearchCalcHostOsId extends HostOsSearchCalc{
		public static final String Name = SqlNames.HostOsSearchCalc + "HostOsId";
		private static final String parm =  "where a.HostOsId = ?1 "
				+ "group by a.HostOsId,a.HostName,a.Ip,a.Cpu,a.Mem,a.Disk ";
		public static final String Query = CommonQuery + parm;
	}
		
}