package mainPackage.entity;

import com.google.gson.annotations.Expose;

public class UserNameSearch {

	@Expose
	private int UserId;
	
	@Expose
	private String UserName;
	
	public UserNameSearch(int UserId,String UserName) {
		this.UserId = UserId;
		this.UserName = UserName;
	}
}


