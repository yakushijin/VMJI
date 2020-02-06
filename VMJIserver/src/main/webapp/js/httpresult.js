function HttpResultInstance(dispid){
	if(dispid == "user"){
		//OperationLog = new OperationLog();
	}
	
}

class HttpResult{
	AjaxPostResult(url,data){
		var apiname = data["apiname"];

		if(data["SuccessOrFailure"] == "0"){
			$("#messagearea").empty();
			$("#messagearea").append('<div class="message-text" style="color:#007ae0;">' + data["message"] + '</div>' );
			$("#message-background").css("display", "block");
		}else if(data["SuccessOrFailure"] == "1"){
			$("#messagearea").empty();
			$("#messagearea").append('<div class="message-text" style="color:#ff0000;">' + data["message"] + '</div>' );
			$("#message-background").css("display", "block");
			Modal.LoadOff();
		}
		
		if(data["SuccessOrFailure"] != "1"){
			if(apiname == 'InfoGet'){
				Information.InfoListDisp(data["data"]);
			}
			else if(apiname == 'UserNameSearch'){
				Modal.UserListBoxAdd(data["data"]);
			}
			else if(apiname == 'LogGet'){
				OperationLog.LogListDisp(url,data["data"]);
			}
			else if(apiname == 'GuestOsAllGet'){
				OperationLog.NewLogGet();
				Modal.LoadOff();
				$(this).delay(2000).queue(function(){
					location.reload();
			    });				
			}
			else if(apiname == 'UserCreate'){
				OperationLog.NewLogGet();
				Modal.LoadOff();
				$(this).delay(2000).queue(function(){
					location.reload();
			    });
				
			}
			else if(apiname == 'GuestOsStart'){
				OperationLog.NewLogGet();
				Modal.LoadOff();
				$(this).delay(2000).queue(function(){
					location.reload();
			    });
				
			}
			else if(apiname == 'GuestOsStop'){
				OperationLog.NewLogGet();
				Modal.LoadOff();
				$(this).delay(2000).queue(function(){
					location.reload();
			    });				
			}
			else
			{
				OperationLog.NewLogGet();
			}			
		}	
	}
	
}