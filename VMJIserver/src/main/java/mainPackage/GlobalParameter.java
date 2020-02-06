package mainPackage;

public final class GlobalParameter{
	
	public static int SystemUserId = 999999;

	public enum AUTHORITY {
		ROLE_ADMIN,
		ROLE_USER
	}
	
	public enum GUESTOSAUTHORITY {
		START(1),
		STOP(4),
		GETUNIT(5);
		
	    private int Contentsid;
	    
	    private GUESTOSAUTHORITY(int Contentsid) {
	        this.Contentsid = Contentsid;
	    }
	    
        public int getContentsid() {
            return this.Contentsid;
        }
	}

	public enum SUCCESSORFAILURE {
		SUCCESS(0),
		FAILURE(1),
		DONTJUDGE(9);
		
	    private int id;
	    
	    private SUCCESSORFAILURE(int id) {
	        this.id = id;
	    }
	    
        public int getId() {
            return this.id;
        }
	}
	
	public enum GRANT {
		USERCREATE("GrantUserCreate"),
		USERUPDATE("GrantUserUpdate"),
		USERDELETE("GrantUserDelete"),
		USERAUTHBROWSE("GrantUserAuthBrowse"),
		GUESTOSALLGET("GrantGuestOsAllGet"),
		GUESTOSSTART("GrantGuestOsStart"),
		GUESTOSSTOP("GrantGuestOsStop"),
		GUESTOSAUTHBROWSE("GrantGuestOsAuthBrowse"),
		GUESTOSUPDATE("GrantGuestOsUpdate"),
		HOSTOSCREATE("GrantHostOsUpdate"),
		HOSTOSUPDATE("GrantHostOsDelete"),
		HOSTOSDELETE("AllGuestOs");
		
	    private String GrantName;
	    
	    private GRANT(String GrantName) {
	        this.GrantName = GrantName;
	    }
	    
        public String getGrantName() {
            return this.GrantName;
        }
	}	
	
	public enum MESSAGE {
		GRANTNOT("の権限がありません"),
		CREATE("の作成"),
		UPDATE("の更新"),
		DELETE("の削除"),
		SUCCESS("に成功しました");
		
	    private String message;
	    
	    private MESSAGE(String message) {
	        this.message = message;
	    }
	    
        public String getMessage() {
            return this.message;
        }
	}
	
	public enum OPERATIONTARGET {
		SYSTEM("System"),
		ALLGUESTOS("AllGuestOs");
		
	    private String TargetName;
	    
	    private OPERATIONTARGET(String TargetName) {
	        this.TargetName = TargetName;
	    }
	    
        public String getTargetName() {
            return this.TargetName;
        }
	}
	
	public enum OPERATIONCONTENTSID {
		LOGIN(101),
		LOGOUT(102),
		CREATE(201),
		UPDATE(202),
		DELETE(203),
		GETALL(301),
		GETUNIT(302),
		START(401),
		STOP(402);
		
	    private int Contentsid;
	    
	    private OPERATIONCONTENTSID(int Contentsid) {
	        this.Contentsid = Contentsid;
	    }
	    
        public int getContentsid() {
            return this.Contentsid;
        }
	}
	
	public enum SERVERAPINAME {
		GUESTOSALLGET("GuestOsAllGet"),
		GUESTOSUPDATE("GuestOsUpdate"),
		GUESTOSSTART("GuestOsStart"),
		GUESTOSSTOP("GuestOsStop"),

		USERCREATE("UserCreate"),
		USERUPDATE("UserUpdate"),
		USERNAMESEARCH("UserNameSearch"),
		
		USERGUESTOSGET("UserGuestOsGet"),
		
		LOGGET("logGet"),
		INFOGET("InfoGet"),
		
		
		
		HOSTOSCREATE("HostosCreate"),
		HOSTOSUPDATE("HostosUpdate");
		
		

		
	    private String ApiName;
	    
	    private SERVERAPINAME(String ApiName) {
	        this.ApiName = ApiName;
	    }
	    
        public String getApiName() {
            return this.ApiName;
        }
	}
	
	
}