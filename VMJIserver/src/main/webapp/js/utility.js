class Utility{
	CommonConfirm(Screen,Target,Messege){
		var result = window.confirm( Messege + ' the ' + Target + '. Is it OK ?');
		return result;
	}
	
	ScreenStatus(number){
		if(number == 0){
			$('#screenstatus').val('new');
		}
		else{
			$('#screenstatus').val('old');
		}
	}
	
}

function initScreenStatus(){
	$('#screenstatus').val('new');
}