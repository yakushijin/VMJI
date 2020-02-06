function GuestOsDetailUpdate(){
	const xsrf = $("#token").val();	
    var data = { '_csrf': xsrf};
	var result = Utility.CommonConfirm("GuestOsScreen",$("#GuestOsHostName").val(),"update");
	if (result){
		const xsrf = $("#token").val();	
        var data = {'guestosid': $("#GuestOsId").val()
					,'guestoshostname': $("#GuestOsHostName").val()
					,'os': $("#Os").val()
					,'ip': $("#Ip").val()
					,'lanip': $("#LanIp").val()
					,'vlanid': $("#VlanId").val()
					,'loginuser': $("#LoginUser").val()
					,'loginpassword': $("#LoginPassword").val()
					,'loginport': $("#Port").val()
					,'kanshi': $("#Kanshi").val()
					,'userid': $("#UserId").val()
					,'remarks': $("#Remarks").val()
        			,'_csrf': xsrf};
        Utility.ScreenStatus(1);
        ServerInterface.AjaxPost('./guestosupdate', data);
	}

}

function UserListSearch(){
	const xsrf = $("#token").val();	
    var data = {'apiname': "UserNameSearch"
    			,'username': $("#SearchUserName").val()
				,'adminflag': $("#SearchAdminFlg").val()
				,'loginid': $("#SearchLoginLd").val()
				,'_csrf': xsrf};
    console.log(data);
    ServerInterface.AjaxPost('./userlist', data);

}

function UserDetailUpdate(){
	const xsrf = $("#token").val();	
    var data = { '_csrf': xsrf};
	var result = Utility.CommonConfirm("UserListScreen","","update");
	if (result){
		var adduserlist = [[$("#UserId").val()
			,$("#UserName").val()
			,$("#LoginLd").val()
			,$("#Passwrod").val()
			,$("#AdminFlg").prop("checked")
			,""
			,$("#GrantUserCreate").prop("checked")
			,$("#GrantUserUpdate").prop("checked")
			,$("#GrantUserDelete").prop("checked")
			,$("#GrantUserAuthBrowse").prop("checked")
			,$("#GrantGuestOsAllGet").prop("checked")
			,$("#GrantGuestOsStart").prop("checked")
			,$("#GrantGuestOsStop").prop("checked")
			,$("#GrantGuestOsAuthBrowse").prop("checked")
			,$("#GrantGuestOsUpdate").prop("checked")
			,$("#GrantHostOsCreate").prop("checked")
			,$("#GrantHostOsUpdate").prop("checked")
			,$("#GrantHostOsDelete").prop("checked")
			,$("#Remarks").val()
			]];
		Utility.ScreenStatus(1);
		GridClickEvent.UserUpdate("UserListScreen","update",adduserlist,"UserUpdate");
	}

}

function CloseModal(){
	$("#leftarea").empty();
	$("#rightarea").empty();
	$("#titelarea").empty();
	$("#subtitelarea").empty();
	$("#freearea").empty();
	$("#modal-background").css("display", "none");
}


function GuestOsUserUpdate(){
	var result = Utility.CommonConfirm("GuestOsScreen",$("#GuestOsHostName").val(),"UserChange");
	if (result){
		
		const xsrf = $("#token").val();	
        var data = {'guestosid': $("#GuestOsId").val()
					,'guestoshostname': $("#GuestOsHostName").val()
					,'os': $("#Os").val()
					,'ip': $("#Ip").val()
					,'lanip': $("#LanIp").val()
					,'vlanid': $("#VlanId").val()
					,'loginuser': $("#LoginUser").val()
					,'loginpassword': $("#LoginPassword").val()
					,'loginport': $("#Port").val()
					,'kanshi': $("#Kanshi").val()
					,'userid': $('option:selected').val()
					,'remarks': $("#Remarks").val()
        			,'_csrf': xsrf};
        console.log(data);
        Utility.ScreenStatus(1);
        ServerInterface.AjaxPost('./guestosupdate', data);
	}
    
}

class Modal{
	UserListBoxAdd(data){
		$("#userlistbox").empty();
		for(var go in data){
			$("#userlistbox").append('<option value='+ data[go].UserId +'>'+ data[go].UserName +'</option>')
		}		
	}
	ButtonClear(){
		 $("#searchbutton").css("display", "none");
		 $("#updatebutton").css("display", "none");
		 $("#createbutton").css("display", "none");
		 $("#searchbutton").off();
		 $("#updatebutton").off();
		 $("#createbutton").off();
	}
	LoadOn(){
		$('body').append('<div div id="loaderid" class="loader"></div>' );
		$('body').prepend('<div id="lockid" class="lock">' );				
		$('body').append('</div>');
	}
	LoadOff(){
		$("#loaderid").remove();
		$("#lockid").remove();
	}
	
}