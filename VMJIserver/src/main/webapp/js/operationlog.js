function OperationLogInstance(){
	OperationLog = new OperationLog();
	HttpResult = new HttpResult();
	ServerInterface = new ServerInterface();
}


function LogListGet(){
	const xsrf = $("#token").val();
    var data = {'apiname': "LogGet",'_csrf': xsrf};
    ServerInterface.AjaxPost('./operationlog', data);
}

class OperationLog{
	LogListDisp(url,data){
		console.log(url);
		$(logdisp).empty();
		var dataArray = [];
		for(var go in data){
			var color = "text-white"
			switch(data[go].OperationId) {
				case 101:
					color = "text-info"
					break;
			    case 202:
			    	color = "text-danger"
			        break;
			    case 301:
			    	color = "text-warning"
			        break;
			    case 401:
			    	color = "text-warning"
			        break;
			    case 402:
			    	color = "text-danger"
			        break;
			}
			dataArray.push(data[go].LogId);
				$(logdisp).append(
					'<div  style="float:left;" style="font-size: 18px;">'+
					data[go].OperationDate + " " +
					data[go].UserName  + '-></div>' +
					'<div class="'+ color +'" style="float:left;" style="font-size: 18px;">' +
					data[go].GuestOsName + " " +
					data[go].OperationContents +
					"</div><br/>");
		}

		var screenstatus = $("#screenstatus").val();
		if( screenstatus != "new"){
			var latestLogId = Math.max.apply(null, dataArray);
			console.log(data);
			var latestGuestOsName = "";
			var latestOperationContents = "";

			for(var go in data){
				if(data[go].LogId == latestLogId){
					latestGuestOsName = data[go].GuestOsName
					latestOperationContents = data[go].OperationContents
					break;
				}
			}



			$(result).empty();
			$(result).prepend('<div id="resultmessege" class="Notice ">' + latestGuestOsName + latestOperationContents +'</div>');

			$(this).delay(3000).queue(function(){
				$("#resultmessege").remove();
		        $(this).dequeue();
		    });

			if(url == "./guestosoperation"){
				location.reload();
			}



		}


	}
	NewLogGet(){
		const xsrf = $("#token").val();
	    var data = {'apiname': "LogGet",'_csrf': xsrf};
	    ServerInterface.AjaxPost('./operationlog', data);	
	}
	
}
