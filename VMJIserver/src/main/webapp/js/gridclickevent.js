function GridClickEventInstance(){
	//ServerInterface = new ServerInterface();
}

class GridClickEvent{
	UserCheck(Screen,UserId){
		const xsrf = $("#token").val();	
        var data = {'screen':Screen,'userid': UserId, '_csrf': xsrf};
        ServerInterface.FormPost('./user', data);	
	}
	GuestOsCheck(Screen,Id){
		const xsrf = $("#token").val();	
        var data = {'screen':Screen,'id': Id, '_csrf': xsrf};
        ServerInterface.FormPost('./guestos', data);	
	}
	HostOsCheck(Screen,Id){
		const xsrf = $("#token").val();	
        var data = {'screen':Screen,'id': Id, '_csrf': xsrf};
        ServerInterface.FormPost('./hostos', data);	
	}
	GuestOsOperation(UserId,GuestOsId,HostOsId,OperationType){
		const xsrf = $("#token").val();	
        var data = {'apiname': "GuestOsStart",'userid': UserId,'guestosid': GuestOsId,'hostosid': HostOsId, '_csrf': xsrf};
        ServerInterface.AjaxPost('./guestos' + OperationType, data);
	}
	stop(UserId,GuestOsId,HostOsId,OperationType){
		const xsrf = $("#token").val();	
        var data = {'apiname': "GuestOsStop",'userid': UserId,'guestosid': GuestOsId,'hostosid': HostOsId,'operationtype':OperationType, '_csrf': xsrf};
        ServerInterface.AjaxPost('./guestosoperation', data);
	}
	UserUpdate(Screen,datatype,list,apiname){
		console.log(list);
		const xsrf = $("#token").val();	
        var data = {'apiname': apiname
        			,'userid': list[0][0]
					,'username': list[0][1]
					,'loginid': list[0][2]
					,'password': list[0][3]
					,'adminflag': list[0][4]
        			,'grantusercreate': list[0][6]
			        ,'grantuserupdate': list[0][7]
			        ,'grantuserdelete': list[0][8]
			        ,'grantuserauthbrowse': list[0][9]
			        ,'grantguestosallget': list[0][10]
			        ,'grantguestosstart': list[0][11]
			        ,'grantguestosstop': list[0][12]
			        ,'grantguestosauthbrowse': list[0][13]
			        ,'grantguestosupdate': list[0][14]
			        ,'granthostoscreate': list[0][15]
			        ,'granthostosupdate': list[0][16]
			        ,'granthostosdelete': list[0][17]
					,'remarks': list[0][18]
					,'_csrf': xsrf};
        ServerInterface.AjaxPost('./user' + datatype, data);
	}
	GuestOsUpdate(Screen,list){
		const xsrf = $("#token").val();
		console.log(list);
        var data = {'guestosid': list[0][0]
					,'guestoshostname': list[0][3]
					,'os': list[0][4]
					,'ip': list[0][8]
					,'lanip': list[0][9]
					,'vlanid': list[0][10]
					,'loginuser': list[0][11]
					,'loginpassword': list[0][12]
					,'loginport': list[0][13]
					,'kanshi': list[0][14]
        			,'userid': list[0][17]
					,'remarks': list[0][19]
        			,'_csrf': xsrf};
        ServerInterface.AjaxPost('./guestosupdate', data);
	}
	HostOsUpdate(Screen,list){
		const xsrf = $("#token").val();	
        var data = {'hostosid': list[0][0]
					,'hostname': list[0][1]
					,'ip': list[0][2]
					,'cpu': list[0][3]
					,'mem': list[0][4]
					,'disk': list[0][5]
        			,'remarks': list[0][12]
					,'_csrf': xsrf};
        ServerInterface.AjaxPost('./hostosupdate', data);
	}
}
