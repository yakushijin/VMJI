function ServerInterfaceInstance(){
	//HttpResult = new HttpResult();
}

class ServerInterface{
	NoPramGet(url) {
	    var $form = $('<form/>', {'action': url, 'method': 'get'});
	    $form.appendTo(document.body);
	    $form.submit();
	}       
	TherePramGet(url, data) {

	}
	FormPost(url, data) {
        var $form = $('<form/>', {'action': url, 'method': 'post'});
        for(var key in data) {
                $form.append($('<input/>', {'type': 'hidden', 'name': key, 'value': data[key]}));
        }
        $form.appendTo(document.body);
        $form.submit();
	}	
	AjaxPost(url, data){
		const xsrf = $("#token").val();
        $.ajax({
            url: url,
            type:'POST',
            data: data
        })
        // 成功
        .done( (data) => {
  	
        	HttpResult.AjaxPostResult(url,data);
        	console.log(url);
        })
        // 失敗
        .fail( (data) => {
            
            console.log(data);
        })
        // 成功・失敗どちらでも発動
        .always( (data) => {
        });
	}
}


