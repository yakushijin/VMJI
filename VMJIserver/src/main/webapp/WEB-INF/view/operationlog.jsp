<!DOCTYPE html>
<%@page import="java.util.List" %>
<%@page import="mainPackage.Main" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
	<head>
	
	<script type="text/javascript">	<%@ include file="../../js/jquery-3.3.1.min.js" %> </script>
	<style type="text/css"> <%@ include file="../../css/bootstrap.min.css" %> </style>
	<script type="text/javascript">	<%@ include file="../../js/bootstrap.min.js" %> </script>
	
	<style type="text/css"> <%@ include file="../../css/handsontable.full.min.css" %> </style>
	<script type="text/javascript">	<%@ include file="../../js/handsontable.full.min.js" %> </script>
	
	<style type="text/css"> <%@ include file="../../css/Header.css" %> </style>
	<style type="text/css"> <%@ include file="../../css/Notice.css" %> </style>
	
	<input type="hidden" id ="token" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	<script type="text/javascript">	<%@ include file="../../js/header.js" %>	</script>
	<script type="text/javascript">	<%@ include file="../../js/operationlog.js" %>	</script>
	<script type="text/javascript">	<%@ include file="../../js/linkmenu.js" %>	</script>
	<script type="text/javascript">	<%@ include file="../../js/serverinterface.js" %>	</script>
	<script type="text/javascript">	<%@ include file="../../js/httpresult.js" %>	</script>
	<script type="text/javascript"> OperationLogInstance() </script>

		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<div class="mr-3 ml-3">
		  	<div class="row">
		  		<div class="col-md-2 HeaderBackend HeaderLogoLine" >
					<div id="logo">
						<script type="text/javascript"> HeaderLogo() </script> 
					</div>
				</div>
				<div class="col-md-4 HeaderBackend ScreenName" >
					ログ一覧
				</div>
					<div class="col-md-6 HeaderBackend LinkName" >
						<div>
							<button type="button" class="btn HeaderButtom" onclick=guestoslist()>ゲストOS一覧</button>
							<button type="button" class="btn HeaderButtom" onclick=hostoslist()>ホストOS一覧</button>
							<button type="button" class="btn HeaderButtom" onclick=userlist()>ユーザ一覧</button>
							<button type="button" class="btn HeaderButtom" onclick=operationlog()>ログ一覧</button>
							<button type="button" class="btn HeaderButtom" onclick=manual()>マニュアル</button>
							<button type="button" class="btn HeaderButtom" onclick=logout()>ログアウト</button>
						</div>
					<div id="result" ></div>
				</div>
			</div>
		<div class="mt-1">
			<div id="grid">
				<script type="text/javascript"> LogListGet() </script>
			</div>
			<div class="row">
				<div class="col-md-6" >
						<div class="Menu">最近のログ</div>
				</div>
			</div>

			<div class="card">
			  <div class="card-body bg-light">
			    <div id="logdisp"> </div> 
			  </div>
			</div>

	</div>

			 	</div>



	</body>
</html>
