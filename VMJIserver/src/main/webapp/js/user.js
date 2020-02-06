function UserInstance(){
	Utility = new Utility();
	OperationLog = new OperationLog();
	HttpResult = new HttpResult();
	ServerInterface = new ServerInterface();
	GridClickEvent = new GridClickEvent();
	Information = new Information;
	Modal = new Modal;
}


function vmlist(){
    var json = $("#json").val();
	var jsonlist = $.parseJSON(json)
	var userid = jsonlist[0].UserId;    
	
	var box = '<div class="row box">';
	var boxitem = '<div class="boxitem">';
	var boxtext = '<div class="boxtext">';
	var row = '<div class="row">';
	var divstart = '<div>';
	var spanstart = '<span>';
	var divend = '</div>';
	var spanend = '</span>';
	var colmd1 = '<div class="col-md-1" >';
	var colmd2 = '<div class="col-md-2" >';
	var colmd3 = '<div class="col-md-3" >';
	var colmd4 = '<div class="col-md-4" >';
	var colmd6 = '<div class="col-md-6" >';
	var colmd8 = '<div class="col-md-8" >';
	var colmd10 = '<div class="col-md-10" >';
	var colmd12 = '<div class="col-md-12" >';
		
	var Cpu;
	var Disk;
	var GuestOsHostName;
	var GuestOsId;
	var GuestOsName;
	var HostOsId;
	var Ip;
	var LanIp;
	var LoginPort;
	var Mem;
	var Os;
	var Remarks;
	var Status;
	
	var gazou = "";
	var count = 0;
	
    for(var go in jsonlist[0].GuestOsEntity){
    	
    	Cpu = jsonlist[0].GuestOsEntity[go]["Cpu"];
		Disk = jsonlist[0].GuestOsEntity[go]["Disk"];
		GuestOsHostName = jsonlist[0].GuestOsEntity[go]["GuestOsHostName"];
		GuestOsId = jsonlist[0].GuestOsEntity[go]["GuestOsId"];
		GuestOsName = jsonlist[0].GuestOsEntity[go]["GuestOsName"];
		HostOsId = jsonlist[0].GuestOsEntity[go]["HostOsId"]["HostOsId"];
		Ip = jsonlist[0].GuestOsEntity[go]["Ip"];
		LanIp = jsonlist[0].GuestOsEntity[go]["LanIp"];
		LoginPort = jsonlist[0].GuestOsEntity[go]["LoginPort"];
		Mem = jsonlist[0].GuestOsEntity[go]["Mem"];
		Os = jsonlist[0].GuestOsEntity[go]["Os"];
		Remarks = jsonlist[0].GuestOsEntity[go]["Remarks"];
		Status = jsonlist[0].GuestOsEntity[go]["Status"];
		LoginUser = jsonlist[0].GuestOsEntity[go]["LoginUser"];
		LoginPassword = jsonlist[0].GuestOsEntity[go]["LoginPassword"];
		
		//VMの起動状況判定
		if(Status == $("#vmup").val()){
			vmup = "";
			vmdown = "disabled";
			Status = "vmup";
		}else{
			vmup = "disabled";
			vmdown = "";
			Status = "vmdown";
		}
		
		//VMの起動状況による画像差し替え判定
		if(Status == "vmup"){
			gazou = "/vmup.jpg";
		}else if (Status == "vmdown"){
			gazou = "/vmdown.jpg";
		}else{
			gazou = "/unknown.jpg";
		}
		var fullpath = HOST + gazou;
		
		//html生成
    	var createhtml = 
    		box
    			+ colmd1
    				+ '<img src="' + fullpath + '"class="boximage" alt="' + Status + '" title="' + Status + '">'
    			+ divend
    			+ colmd10
    				+ row
    					+ colmd2
    						+ boxitem
    							+ "GuestOsName "
    						+ divend
    					+ divend
    					+ colmd3
							+ boxtext
								+ GuestOsName
							+ divend
						+ divend
						+ colmd2
							+ boxitem
								+ "LoginUser "
							+ divend
						+ divend
						+ colmd2
							+ boxtext
								+ LoginUser
							+ divend
						+ divend
						+ colmd1
							+ boxitem
								+ "Ip  "
							+ divend
						+ divend
						+ colmd2
							+ boxtext
								+ Ip
							+ divend
						+ divend
						
					+ divend
					
        			+ row
						+ colmd2
							+ boxitem
								+ "Os "
							+ divend
						+ divend
						+ colmd3
							+ boxtext
								+ Os
							+ divend
						+ divend						
						+ colmd2
							+ boxitem
								+ "Password "
							+ divend
						+ divend
						+ colmd2
							+ boxtext
								+ LoginPassword
							+ divend
						+ divend						
						+ colmd1
							+ boxitem
								+ "port "
							+ divend
						+ divend
						+ colmd2
							+ boxtext
								+ LoginPort
							+ divend
						+ divend
					+ divend
					
    				+ row
						+ colmd2
							+ boxitem
								+ "Remarks "
							+ divend
						+ divend
						+ colmd10
							+ boxtext
								+ Remarks
							+ divend
						+ divend
					+ divend
				+ divend
					
				+ colmd1
					+ '<span><button ' + vmdown + ' type="button" id="start_'+ count +'"class="text-warning bg-info btn btn-pdefault boxbuttom" onclick=GuestOsStart('+ count +')>start</button></span>'
					+ '<span><button ' + vmup + ' type="button" id="stop'+ count +'"class="text-warning bg-info btn btn-pdefault boxbuttom" onclick=GuestOsStop('+ count +')>stop</button></span>'
					+ '<span><button type="button" id="detail_'+ count +'"class="text-warning bg-info btn btn-pdefault boxbuttom" onclick=GuestOsDetail('+ count +')>detail</button></span>'
				+ divend
				
			+ divend;
    	
    	$("#vmlist").append(createhtml)
    	
    	$("#vmlist").append('<input type="hidden" id="Cpu_'+ count +'" value="'+ Cpu +'">');
    	$("#vmlist").append('<input type="hidden" id="Disk_'+ count +'" value="'+ Disk +'">');
    	$("#vmlist").append('<input type="hidden" id="GuestOsHostName_'+ count +'" value="'+ GuestOsHostName +'">');
    	$("#vmlist").append('<input type="hidden" id="GuestOsId_'+ count +'" value="'+ GuestOsId +'">');
    	$("#vmlist").append('<input type="hidden" id="HostOsId_'+ count +'" value="'+ HostOsId +'">');
    	$("#vmlist").append('<input type="hidden" id="LanIp_'+ count +'" value="'+ LanIp +'">');
    	$("#vmlist").append('<input type="hidden" id="Mem_'+ count +'" value="'+ Mem +'">');
    	$("#vmlist").append('<input type="hidden" id="UserId_'+ count +'" value="'+ userid +'">');
    	$("#vmlist").append('<input type="hidden" id="GuestOsName_'+ count +'" value="'+ GuestOsName +'">');
    	$("#vmlist").append('<input type="hidden" id="Ip_'+ count +'" value="'+ Ip +'">');
    	$("#vmlist").append('<input type="hidden" id="LoginPort_'+ count +'" value="'+ LoginPort +'">');
    	$("#vmlist").append('<input type="hidden" id="Os_'+ count +'" value="'+ Os +'">');
    	$("#vmlist").append('<input type="hidden" id="Remarks_'+ count +'" value="'+ Remarks +'">');
    	$("#vmlist").append('<input type="hidden" id="Status_'+ count +'" value="'+ Status +'">');
    	$("#vmlist").append('<input type="hidden" id="LoginUser_'+ count +'" value="'+ LoginUser +'">');
    	$("#vmlist").append('<input type="hidden" id="LoginPassword_'+ count +'" value="'+ LoginPassword +'">');
    	
    	count += 1;
    }
    
}


function LogListGet(){
	var json = $("#json").val();
	var UserId = $.parseJSON(json)[0].UserId;
	const xsrf = $("#token").val();	
    var data = {'userid': UserId, '_csrf': xsrf};
    ServerInterface.AjaxPost('./operationlog', data);
}


function GuestOsStart(count){
	var result = Utility.CommonConfirm("UserScreen",$("#GuestOsName_" + count).val(),"start");
	if (result){
		Modal.LoadOn();
		Utility.ScreenStatus(1);
    	GridClickEvent.GuestOsOperation(
    			$("#UserId_" + count).val()
    			,$("#GuestOsId_" + count).val()
    			,$("#HostOsId_" + count).val()
    			,"start"
    	);
	}
	
}

function GuestOsStop(count){
	var result = Utility.CommonConfirm("UserScreen",$("#GuestOsName_" + count).val(),"stop");
	if (result){
		Modal.LoadOn();
		Utility.ScreenStatus(1);
    	GridClickEvent.GuestOsOperation(
    			$("#UserId_" + count).val()
    			,$("#GuestOsId_" + count).val()
    			,$("#HostOsId_" + count).val()
    			,"stop"
    	);
	}
	
}

function GuestOsDetail(count){
	$("#leftarea").empty();
	$("#rightarea").empty();
	$("#titelarea").empty();
	$("#subtitelarea").empty();
	$("#freearea").empty();
	
	$("#modal-background").css("display", "block");
	
	$("#titelarea").append('<div style="font-size: 40px;">'+ $("#GuestOsName_" + count).val() + '</div>')
	$("#titelarea").append('<input type="hidden" id="GuestOsId" value="'+ $("#GuestOsId_" + count).val() +'">')
	$("#subtitelarea").append('<span>CurrentStatus:'+ $("#Status_" + count).val() + '</span>')
	
	$("#leftarea").append('<p>GuestOsHostName: <input type="text" id="GuestOsHostName" class="textbox" value="'+ $("#GuestOsHostName_" + count).val() +'"></p>')
	$("#leftarea").append('<p>Ip: <input type="text" id="Ip" class="textbox" value="'+ $("#Ip_" + count).val() +'"></p>')	                    	
	$("#leftarea").append('<p>LanIp: <input type="text" id="LanIp" class="textbox" value="'+ $("#LanIp_" + count).val() +'"></p>')
	$("#leftarea").append('<p>Port: <input type="text" id="Port" class="textbox" value="'+ $("#LoginPort_" + count).val() +'"></p>')
	$("#leftarea").append('<p>LoginUser: <input type="text" id="LoginUser" class="textbox" value="'+ $("#LoginUser_" + count).val() +'"></p>')
	
	$("#rightarea").append('<p>Os: <input type="text" id="Os" class="textbox" value="'+ $("#Os_" + count).val() +'"></p>')
	$("#rightarea").append('<p>Cpu: <input type="text" id="Cpu" class="textbox" value="'+ $("#Cpu_" + count).val() +'"></p>')
	$("#rightarea").append('<p>Mem: <input type="text" id="Mem" class="textbox" value="'+ $("#Mem_" + count).val() +'"></p>')
	$("#rightarea").append('<p>Disk: <input type="text" id="Disk" class="textbox" value="'+ $("#Disk_" + count).val() +'"></p>')
	$("#rightarea").append('<p>LoginPassword: <input type="text" id="LoginPassword" class="textbox" value="'+ $("#LoginPassword_" + count).val() +'"></p>')
	
	$("#freearea").append('<p>Remarks: <input type="text" id="Remarks" class="textbox" value="'+ $("#Remarks_" + count).val() +'"></p>')

	$("#updatebutton").remove();
	$("#usersearch").remove();
	$("#addbutton").remove();
	$("#guestosuserupdatebutton").remove();	
	
}

