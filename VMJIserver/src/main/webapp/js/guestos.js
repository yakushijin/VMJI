function GuestOsInstance(){
	Utility = new Utility();
	OperationLog = new OperationLog();
	HttpResult = new HttpResult();
	ServerInterface = new ServerInterface();
	GridClickEvent = new GridClickEvent();
	Information = new Information;
	Modal = new Modal;
}

function GridCreate(){
	var json = $("#json").val();
	var jsonlist = $.parseJSON(json)
    var grid = document.getElementById('grid');
    var gridlist = new Handsontable(grid, {
		data: jsonlist
		,rowHeaders: true
		,columnSorting: {
	        column: 0,
	        sortColumn: false
	    }
    	,currentRowClassName: 'currentRow'
    	,multiSelect: false
    	,stretchH: 'last'
		,colWidths: [0.1,180,110,0.1,160,45,45,45,100,0.1,0.1,0.1,0.1,50,0.1,0.1,100,0.1,120,200]
		,colHeaders: ['GuestOsId','GuestName','Status','GuestOsHostName','Os','Cpu','Mem','Disk','Ip','LanIp','VlanId','LoginUser','LoginPassword','Port','Kanshi','HostOsId','HostName','UserId','UserName','Remarks']
    	,contextMenu: {
            items: [
                {
                    key: 'UserCheck',
                    name: 'UserCheck',
                    callback: function() {
                    	var sel = this.getSelected();
                    	GridClickEvent.UserCheck("GuestOsScreen",gridlist.getDataAtCell(sel[0][0],17));
                    	Utility.ScreenStatus(1);
                    }
                },
                {
                    key: 'HostOsCheck',
                    name: 'HostOsCheck',
                    callback: function() {
                    	var sel = this.getSelected();
                    	GridClickEvent.HostOsCheck("GuestOsScreen",gridlist.getDataAtCell(sel[0][0],15));
                    	Utility.ScreenStatus(1);
                    }
                },
                {
                    key: 'Update',
                    name: 'Update',
                    callback: function() {
                    	var sel = this.getSelected();
                    	var result = Utility.CommonConfirm("GuestOsScreen",gridlist.getDataAtCell(sel[0][0],1),"update");
                    	if (result){
                    		GridClickEvent.GuestOsUpdate("GuestOsScreen",gridlist.getData(sel[0][0],0,sel[0][0],19));
                    	}
                    	Utility.ScreenStatus(1);
                    }
                },
                {
                    key: 'UserChange',
                    name: 'UserChange',
                    callback: function() {
                    	var sel = this.getSelected();
                    	var sel = this.getSelected();
                    	$("#leftarea").empty();
                    	$("#rightarea").empty();
                    	$("#titelarea").empty();
                    	$("#subtitelarea").empty();
                    	$("#freearea").empty();
                    	
                    	$("#modal-background").css("display", "block");
                    	
                    	$("#titelarea").append('<div style="font-size: 40px;">'+ gridlist.getDataAtCell(sel[0][0],1) + '</div>')
                    	$("#titelarea").append('<input type="hidden" id="GuestOsId" value="'+ gridlist.getDataAtCell(sel[0][0],0) +'">')

                    	
                    	$("#titelarea").append('<input type="hidden" id="GuestOsHostName" value="'+ gridlist.getDataAtCell(sel[0][0],3) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Os" value="'+ gridlist.getDataAtCell(sel[0][0],4) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Cpu" value="'+ gridlist.getDataAtCell(sel[0][0],5) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Mem" value="'+ gridlist.getDataAtCell(sel[0][0],6) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Disk" value="'+ gridlist.getDataAtCell(sel[0][0],7) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Ip" value="'+ gridlist.getDataAtCell(sel[0][0],8) +'">')
                    	
                    	$("#titelarea").append('<input type="hidden" id="LanIp" value="'+ gridlist.getDataAtCell(sel[0][0],9) +'">')
                    	$("#titelarea").append('<input type="hidden" id="VlanId" value="'+ gridlist.getDataAtCell(sel[0][0],10) +'">')
                    	$("#titelarea").append('<input type="hidden" id="LoginUser" value="'+ gridlist.getDataAtCell(sel[0][0],11) +'">')
                    	$("#titelarea").append('<input type="hidden" id="LoginPassword" value="'+ gridlist.getDataAtCell(sel[0][0],12) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Port" value="'+ gridlist.getDataAtCell(sel[0][0],13) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Kanshi" value="'+ gridlist.getDataAtCell(sel[0][0],14) +'">')
                    	$("#titelarea").append('<input type="hidden" id="Remarks" value="'+ gridlist.getDataAtCell(sel[0][0],19) +'">')

                    	
                    	$("#titelarea").append('<input type="hidden" id="conditionssearchuserlist" value="">')
                    	$("#subtitelarea").append('<span>OwnUser:'+ gridlist.getDataAtCell(sel[0][0],18) + '</span>')
                    	
                    	$("#leftarea").append('<div style="font-size: 40px;">NewUser</div>')   
                    	$("#leftarea").append('<select  id="userlistbox" class="listbox"> </select>')
                    	
                    	$("#rightarea").append('<div style="font-size: 40px;">UserSearch</div>')                   	
                    	$("#rightarea").append('<p>UserName: <input type="text" id="SearchUserName" class="textbox" value=""></p>')
                    	$("#rightarea").append('<p>AdminFlg: <input type="text" id="SearchAdminFlg" class="textbox" value=""></p>')
                    	$("#rightarea").append('<p>LoginLd: <input type="text" id="SearchLoginLd" class="textbox" value=""></p>')

                    	Modal.ButtonClear();
                    	
                		$("#searchbutton").show();
                    	$("#searchbutton").on('click',function(){
                    		UserListSearch();
                    	});
                		$("#updatebutton").show();
                    	$("#updatebutton").on('click',function(){
                    		GuestOsUserUpdate();
                    	});

                		const xsrf = $("#token").val();	
                        var data = {'apiname': "UserNameSearch"
                        			,'username': $("#SearchUserName").val()
                					,'adminflag': $("#SearchAdminFlg").val()
                					,'loginid': $("#SearchLoginLd").val()
                					,'_csrf': xsrf};
                        ServerInterface.AjaxPost('./userlist', data);
                    	
                    }
                },
                {
                    key: 'Detail',
                    name: 'Detail',
                    callback: function() {
                    	var sel = this.getSelected();
                    	$("#leftarea").empty();
                    	$("#rightarea").empty();
                    	$("#titelarea").empty();
                    	$("#subtitelarea").empty();
                    	$("#freearea").empty();
                    	
                    	$("#modal-background").css("display", "block");
                    	
                    	$("#titelarea").append('<div style="font-size: 40px;">'+ gridlist.getDataAtCell(sel[0][0],1) + '</div>')
                    	$("#titelarea").append('<input type="hidden" id="GuestOsId" value="'+ gridlist.getDataAtCell(sel[0][0],0) +'">')
                    	$("#titelarea").append('<input type="hidden" id="UserId" value="'+ gridlist.getDataAtCell(sel[0][0],17) +'">')
                    	$("#subtitelarea").append('<span>CurrentStatus:'+ gridlist.getDataAtCell(sel[0][0],2) + '</span>')
                    	$("#subtitelarea").append('<span>/RideHostOs:'+ gridlist.getDataAtCell(sel[0][0],16) + '</span>')
                    	$("#subtitelarea").append('<span>/OwnUser:'+ gridlist.getDataAtCell(sel[0][0],18) + '</span>')
                    	
                    	$("#leftarea").append('<p>GuestOsHostName: <input type="text" id="GuestOsHostName" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],3) +'"></p>')
                    	$("#leftarea").append('<p>Os: <input type="text" id="Os" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],4) +'"</p>')
                    	$("#leftarea").append('<p>Cpu: <input type="text" id="Cpu" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],5) +'"</p>')
                    	$("#leftarea").append('<p>Mem: <input type="text" id="Mem" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],6) +'"</p>')
                    	$("#leftarea").append('<p>Disk: <input type="text" id="Disk" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],7) +'"></p>')
                    	$("#leftarea").append('<p>Ip: <input type="text" id="Ip" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],8) +'"></p>')
                    	
                    	$("#rightarea").append('<p>LanIp: <input type="text" id="LanIp" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],9) +'"></p>')
                    	$("#rightarea").append('<p>VlanId: <input type="text" id="VlanId" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],10) +'"></p>')
                    	$("#rightarea").append('<p>LoginUser: <input type="text" id="LoginUser" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],11) +'"></p>')
                    	$("#rightarea").append('<p>LoginPassword: <input type="text" id="LoginPassword" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],12) +'"></p>')
                    	$("#rightarea").append('<p>Port: <input type="text" id="Port" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],13) +'"></p>')
                    	$("#rightarea").append('<p>Kanshi: <input type="text" id="Kanshi" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],14) +'"></p>')

                    	$("#freearea").append('<p>Remarks: <input type="text" id="Remarks" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],19) +'"></p>')

                    	Modal.ButtonClear();
                    	
				        $("#updatebutton").show();
                    	$("#updatebutton").on('click',function(){
                    		GuestOsDetailUpdate();
                    	});		
                    }
                } 
            ]
        }
	});
    	
};

function GuestOsMakeNew(){
	var result = Utility.CommonConfirm("GuestOsScreen","AllGuestOs","Get");
	if (result){
		Modal.LoadOn();
		
		Utility.ScreenStatus(1);
		const xsrf = $("#token").val();	
	    var data = { 'apiname': "GuestOsAllGet",'_csrf': xsrf};
	    ServerInterface.AjaxPost('./guestosgetinfo', data);
	}

}
