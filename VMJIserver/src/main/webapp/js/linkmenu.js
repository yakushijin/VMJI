function logout(){
	const xsrf = $("#token").val();	
    var data = { '_csrf': xsrf};
    ServerInterface.FormPost('./logout', data);
}

function manual(){

var fullpath = HOST + "/manual.html";
	window.open(fullpath, "_blank","top=50,left=50,width=800,height=800,scrollbars=1,location=0,menubar=0,toolbar=0,status=1,directories=0,resizable=1");
}

function guestoslist(){
	ServerInterface.NoPramGet("./guestos");
}

function hostoslist(){
	ServerInterface.NoPramGet("./hostos");
}

function userlist(){
	ServerInterface.NoPramGet("./userlist");
}

function operationlog(){
	ServerInterface.NoPramGet("./operationlog");
}
