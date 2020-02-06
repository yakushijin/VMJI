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
		<style type="text/css"> <%@ include file="../../css/CustomStyle.css" %> </style>
		
		<script type="text/javascript">	<%@ include file="../../js/globalconst.js" %>	</script>
		<script type="text/javascript">	<%@ include file="../../js/top.js" %>	</script>
		<script type="text/javascript">	<%@ include file="../../js/linkmenu.js" %>	</script>
		<script type="text/javascript">	<%@ include file="../../js/serverinterface.js" %>	</script>
		<script type="text/javascript">	<%@ include file="../../js/httpresult.js" %>	</script>
		
		<script type="text/javascript"> ServerInterfaceInstance() </script>
		<script type="text/javascript"> TopInstance() </script> 
		  
		<meta charset="utf-8">
		<title>ログイン画面</title>
	</head> 
	<body>

<div id="canvas-wrap">
<canvas id="canvas-container"></canvas>
<div id="canvas-txt">
		<div class="top-30">
			<div class="mt-3">
			  	<div class="row">
					<div class="col-md-4" ></div>
					<div class="col-md-4 background text-white text-center" >
					<div id="logo"></div>
					β版
					</div>
			 	</div>
			</div>
			<div class="mt-3">
			 	 <div class="row">
					<div class="col-md-4" ></div>
					<div class="col-md-4 background text-white">
						<div class="container">
						        <div class="form-group">
						            <label>ログインID</label>
						            <input type="text" id="loginid" class="form-control">
						        </div>
						        <div class="form-group">
						            <label>パスワード</label>
						            <input type="password" id="password" class="form-control">
						        </div>
						        <div class="checkbox">
						            <label>
						                <input type="checkbox">remember
						            </label>
						        </div>
						        <input id="token" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						        <button type="button" class="text-white bg-info btn btn-pdefault btn-xs" onclick=login()>ログイン</button>

						</div>
					</div>
				</div>
			</div>
		</div>
</div>
</div>


	</body>
</html>
