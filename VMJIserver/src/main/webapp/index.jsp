<!DOCTYPE html>
<%@page import="java.util.List" %>
<%@page import="mainPackage.Main" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<h1>JPA Sample</h1>
		<form method="post" action="test">
		<table>
			<tr><td>Name:</td><td><input type="text" name="name"></td></tr>
			<tr><td>mail:</td><td><input type="text" name="mail"></td></tr>
			<tr><td>age:</td><td><input type="text" name="age"></td></tr>
			<tr><td></td><td><input type="submit" value="追加"></td></tr>
		</table>
		</form>
	<hr>
	<ol>
	<% for(Object entity : (List)request.getAttribute("entities")){ %>
		<li><%=entity %></li>
	<% } %>
	</ol>
	</body>
</html>
