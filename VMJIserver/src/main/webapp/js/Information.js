function InfoListGet(){
	const xsrf = $("#token").val();	
    var data = {'apiname': "InfoGet",'_csrf': xsrf};
    ServerInterface.AjaxPost('./Information', data);
}

class Information{
	InfoListDisp(data){
		$(Infodisp).empty();
		console.log(data);
		for(var go in data){
				$(Infodisp).prepend('<div style="color: #0000ff;">' + data[go].InfoDate + "</div>" + 
						'<div style="margin-left : 2px;">>' + data[go].InfoContents + "</div>");
		}	

	}
	
}