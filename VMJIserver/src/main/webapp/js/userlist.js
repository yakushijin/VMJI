function UserListInstance(){
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
	console.log(jsonlist);
    var grid = document.getElementById('grid');
    var gridlist = new Handsontable(grid, {
		data: jsonlist
		,rowHeaders: true
		,    columnSorting: {
	        column: 0,
	        sortColumn: false
	    }
    	,currentRowClassName: 'currentRow'
    ,multiSelect: false
    ,stretchH: 'last'
		,colWidths: [0.1,150,300,150,0.1,120,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,0.1,400]
		,colHeaders: ['UserId','UserName','LoginId','Password','AdminFlag','lastlogin','GrantUserCreate','GrantUserUpdate','GrantUserDelete','GrantUserAuthBrowse','GrantGuestOsAllGet','GrantGuestOsStart','GrantGuestOsStop','GrantGuestOsAuthBrowse','GrantGuestOsUpdate','GrantHostOsCreate','GrantHostOsUpdate','GrantHostOsDelete','Remarks']
    	,contextMenu: {
            items: [
                {
                    key: 'UserCheck',
                    name: 'UserCheck',
                    callback: function() {
                    	var sel = this.getSelected();
                    	GridClickEvent.UserCheck("UserListScreen",gridlist.getDataAtCell(sel[0][0],0));
                    }
                },
                {
                    key: 'GuestOsCheck',
                    name: 'GuestOsCheck',
                    callback: function() {
                    	var sel = this.getSelected();
                    	GridClickEvent.GuestOsCheck("UserListScreen",gridlist.getDataAtCell(sel[0][0],0));
                    }
                },
                {
                    key: 'Update',
                    name: 'Update',
                    callback: function() {
                    	var sel = this.getSelected();
                    	var result = Utility.CommonConfirm("UserListScreen",gridlist.getDataAtCell(sel[0][0],1),"update");
                    	if (result){
                    		GridClickEvent.UserUpdate("UserListScreen","update",gridlist.getData(sel[0][0],0,sel[0][0],18),"UserUpdate");
                    	}
                    	Utility.ScreenStatus(1);
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

                        	$("#leftarea").append('<p>UserName: <input type="text" id="UserName" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],1) +'"></p>')
                        	$("#leftarea").append('<div ><label for="AdminFlg" style="float:right;margin-left:10px;"> AdminFlg: <input type="checkbox" id="AdminFlg" class="checkbox"></label></div>')                        	
                        	
                        	$("#rightarea").append('<p>LoginLd: <input type="text" id="LoginLd" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],2) +'"></p>')
                        	$("#rightarea").append('<p>Passwrod: <input type="text" id="Passwrod" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],3) +'"></p>')
                        	
                        	$("#freearea").append('<p>Remarks: <input type="text" id="Remarks" class="textbox" value="'+ gridlist.getDataAtCell(sel[0][0],18) +'"></p>')
                        	
                        	$("#titelarea").append('<input type="hidden" id="UserId" value="'+ gridlist.getDataAtCell(sel[0][0],0) +'">')
                        	$("#titelarea").append('<div style="font-size: 40px;">'+ gridlist.getDataAtCell(sel[0][0],1) + '</div>')
                       	
                        	$("#freearea").append('<div ><label for="GrantUserUpdate" style="float:right;"> UserUpdate: <input type="checkbox" id="GrantUserUpdate" class="checkbox"></label></div>')
                        	$("#freearea").append('<span><label for="GrantUserDelete"> UserDelete: <input type="checkbox" id="GrantUserDelete" class="checkbox"></label></span>')
                        	$("#freearea").append('<span><label for="GrantUserCreate"> UserCreate: <input type="checkbox" id="GrantUserCreate" class="checkbox"></label></span>')							
                        	$("#freearea").append('<span><label for="GrantUserAuthBrowse"> UserAuthBrowse: <input type="checkbox" id="GrantUserAuthBrowse" class="checkbox"></label></span>')
                        	
                        	$("#freearea").append('<div><label for="GrantGuestOsStart" style="float:right;"> GuestOsStart: <input type="checkbox" id="GrantGuestOsStart" class="checkbox"></label></div>')
                        	$("#freearea").append('<span><label for="GrantGuestOsAllGet"> GuestOsAllGet: <input type="checkbox" id="GrantGuestOsAllGet" class="checkbox"></label></span>')                       	
							$("#freearea").append('<span ><label for="GrantGuestOsStop"> GuestOsStop: <input type="checkbox" id="GrantGuestOsStop" class="checkbox"></label></span>')
                        	$("#freearea").append('<span><label for="GrantGuestOsAuthBrowse"> GuestOsAuthBrowse: <input type="checkbox" id="GrantGuestOsAuthBrowse" class="checkbox"></label></span>')
                        	$("#freearea").append('<span><label for="GrantGuestOsUpdate"> GuestOsUpdate: <input type="checkbox" id="GrantGuestOsUpdate" class="checkbox"></label></span>')
                        	
                        	$("#freearea").append('<div><label for="GrantHostOsUpdate" style="float:right;"> HostOsUpdate: <input type="checkbox" id="GrantHostOsUpdate" class="checkbox"></label></div>')
							$("#freearea").append('<span><label for="GrantHostOsCreate"> HostOsCreate: <input type="checkbox" id="GrantHostOsCreate" class="checkbox"></label></span>')
                        	$("#freearea").append('<span><label for="GrantHostOsDelete"> HostOsDelete: <input type="checkbox" id="GrantHostOsDelete" class="checkbox"></label></span>')

                        	$("#AdminFlg").prop('checked', gridlist.getDataAtCell(sel[0][0],4));
                        	$("#GrantUserCreate").prop('checked', gridlist.getDataAtCell(sel[0][0],6));
                        	$("#GrantUserUpdate").prop('checked', gridlist.getDataAtCell(sel[0][0],7));
                        	$("#GrantUserDelete").prop('checked', gridlist.getDataAtCell(sel[0][0],8));
                        	$("#GrantUserAuthBrowse").prop('checked', gridlist.getDataAtCell(sel[0][0],9));
                        	$("#GrantGuestOsAllGet").prop('checked', gridlist.getDataAtCell(sel[0][0],10));
                        	$("#GrantGuestOsStart").prop('checked', gridlist.getDataAtCell(sel[0][0],11));
                        	$("#GrantGuestOsStop").prop('checked', gridlist.getDataAtCell(sel[0][0],12));
                        	$("#GrantGuestOsAuthBrowse").prop('checked', gridlist.getDataAtCell(sel[0][0],13));
                        	$("#GrantGuestOsUpdate").prop('checked', gridlist.getDataAtCell(sel[0][0],14));
                        	$("#GrantHostOsCreate").prop('checked', gridlist.getDataAtCell(sel[0][0],15));
                        	$("#GrantHostOsUpdate").prop('checked', gridlist.getDataAtCell(sel[0][0],16));
                        	$("#GrantHostOsDelete").prop('checked', gridlist.getDataAtCell(sel[0][0],17));
                        	
                        	Modal.ButtonClear();
                        	
                            $("#updatebutton").show();
                        	$("#updatebutton").on('click',function(){
                        		UserDetailUpdate();
                        	});	

                    }
                }
            ]
        }
	});
    	
};


function UserAddModal(){

    	$("#leftarea").empty();
    	$("#rightarea").empty();
    	$("#titelarea").empty();
    	$("#subtitelarea").empty();
    	$("#freearea").empty();
    	
    	$("#modal-background").css("display", "block");
    	
    	$("#titelarea").append('<div style="font-size: 40px;">User Add</div>')
    	$("#subtitelarea").append('<span>User Add</span>')
    	
    	$("#leftarea").append('<p>UserName: <input type="text" id="UserName" class="textbox" value=""></p>')
    	$("#leftarea").append('<div ><label for="AdminFlg" > AdminFlg: <input type="checkbox" id="AdminFlg" class="checkbox"></label></div>')                        	                        	
    	    	
    	$("#rightarea").append('<p>LoginLd: <input type="text" id="LoginLd" class="textbox" value=""></p>')
    	$("#rightarea").append('<p>Passwrod: <input type="text" id="Passwrod" class="textbox" value=""></p>')

       	$("#freearea").append('<p>Remarks: <input type="text" id="Remarks" class="textbox" value=""></p>')
   	
    	$("#freearea").append('<div ><label for="GrantUserUpdate" style="float:right;"> UserUpdate: <input type="checkbox" id="GrantUserUpdate" class="checkbox"></label></div>')
    	$("#freearea").append('<span><label for="GrantUserDelete"> UserDelete: <input type="checkbox" id="GrantUserDelete" class="checkbox"></label></span>')
    	$("#freearea").append('<span><label for="GrantUserCreate"> UserCreate: <input type="checkbox" id="GrantUserCreate" class="checkbox"></label></span>')							
    	$("#freearea").append('<span><label for="GrantUserAuthBrowse"> UserAuthBrowse: <input type="checkbox" id="GrantUserAuthBrowse" class="checkbox"></label></span>')
    	
    	$("#freearea").append('<div><label for="GrantGuestOsStart" style="float:right;"> GuestOsStart: <input type="checkbox" id="GrantGuestOsStart" class="checkbox"></label></div>')
    	$("#freearea").append('<span><label for="GrantGuestOsAllGet"> GuestOsAllGet: <input type="checkbox" id="GrantGuestOsAllGet" class="checkbox"></label></span>')                       	
		$("#freearea").append('<span ><label for="GrantGuestOsStop"> GuestOsStop: <input type="checkbox" id="GrantGuestOsStop" class="checkbox"></label></span>')
    	$("#freearea").append('<span><label for="GrantGuestOsAuthBrowse"> GuestOsAuthBrowse: <input type="checkbox" id="GrantGuestOsAuthBrowse" class="checkbox"></label></span>')
    	$("#freearea").append('<span><label for="GrantGuestOsUpdate"> GuestOsUpdate: <input type="checkbox" id="GrantGuestOsUpdate" class="checkbox"></label></span>')
    	
    	$("#freearea").append('<div><label for="GrantHostOsUpdate" style="float:right;"> HostOsUpdate: <input type="checkbox" id="GrantHostOsUpdate" class="checkbox"></label></div>')
		$("#freearea").append('<span><label for="GrantHostOsCreate"> HostOsCreate: <input type="checkbox" id="GrantHostOsCreate" class="checkbox"></label></span>')
    	$("#freearea").append('<span><label for="GrantHostOsDelete"> HostOsDelete: <input type="checkbox" id="GrantHostOsDelete" class="checkbox"></label></span>')

       	Modal.ButtonClear();
       	
        $("#createbutton").show();
    	$("#createbutton").on('click',function(){
    		UserAddExecution();
    	});


}

function UserAddExecution(){
	var result = Utility.CommonConfirm("UserListScreen",$("#UserName").val(),"add");
if (result){
	Modal.LoadOn();
	var adduserlist = [["0"
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
	GridClickEvent.UserUpdate("UserListScreen","create",adduserlist,"UserCreate");
}
Utility.ScreenStatus(1);
}
