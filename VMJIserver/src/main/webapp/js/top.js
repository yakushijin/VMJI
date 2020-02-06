function TopInstance(){
	ServerInterface = new ServerInterface();
}

function login(){
	const loginid = $("#loginid").val();
	const password = $("#password").val();
	const xsrf = $("#token").val();	
    var data = {'username': loginid,'password': password, '_csrf': xsrf};
    ServerInterface.FormPost('./auth', data);
}

	window.onload = function() {
		//背景画像設定
		var date = new Date();
		var time = date.getHours();
		var gazou = "";
		
		if(time >= 18 || time <= 6){
			gazou = "/sky_5.jpg";
		}else if (time >= 16 && time <= 18){
			gazou = "/sky_4.jpg";
		}else{
			var val = Math.ceil( Math.random()*3 );
			gazou = "/sky_" + val +".jpg";
		}
		
		var fullpath = HOST + gazou;		
		$('html,body').css('background-image', 'url(' + fullpath + ')');
		
		//ロゴ表示
		var vmkanrisystem = "/vmkanrisystem.png";
		var vmkanrisystemLogo = HOST + vmkanrisystem;
		
		$("#logo").append('<div><img src="' + vmkanrisystemLogo + '" style="width="80" height="60" alt="vmkanrisystemLogo"></div>');		

	}