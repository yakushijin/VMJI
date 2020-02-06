<!DOCTYPE html>
<%@page import="java.util.List" %>
<%@page import="mainPackage.Main" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
	<head>
		<style type="text/css"> <%@ include file="../../css/message.css" %> </style>
		<script type="text/javascript">	<%@ include file="../../js/message.js" %>	</script>
	<meta charset="utf-8">
	<title>message</title>
	</head> 
	<body>
		<div id="message-background" class="message-background" >
			<div id="messagearea">tset</div>
		
			<div id="buttonarea">
				<button type="button" id="messageclosebutton" class="text-warning bg-info btn btn-pdefault btn-xs btn-sm" onclick=CloseMessage()>閉じる</button>				
			</div>	
			</div>	
	</body>
</html>
