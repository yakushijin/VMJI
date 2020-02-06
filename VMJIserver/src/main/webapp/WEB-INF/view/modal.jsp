<!DOCTYPE html>
<%@page import="java.util.List" %>
<%@page import="mainPackage.Main" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<html>
	<head>
		<style type="text/css"> <%@ include file="../../css/modal.css" %> </style>
		<script type="text/javascript">	<%@ include file="../../js/modal.js" %>	</script>
	<meta charset="utf-8">
	<title>Welcome</title>
	</head> 
	<body>
		<div id="modal-background" class="modal-background" >
			<div class="row">
				<div class="col-md-12" >
					<div class="ModalTitel HeaderBackend"> 
				 		<div id="titelarea"></div>
					</div>
				</div>			
			</div>
			<div class="HeaderBackend text-info"> 
				<div id="subtitelarea"></div>
			</div>
			<div class="row text-white" style="text-align:right;padding-right:20px;padding-top:20px;">
				<div class="col-md-6" >
				<div id="leftarea"></div>
				</div>
				<div class="col-md-6" >
				<div id="rightarea"></div>
				</div>
			</div>
			<div class="row text-white" style="text-align:right;padding-right:20px;">
				<div class="col-md-12" >
					<div id="freearea"></div>
				</div>			
			</div>
			<div id="buttonarea">
				<button type="button" id="searchbutton" class="text-warning bg-info btn btn-pdefault btn-xs btn-sm" style="display: none;">検索</button>
				<button type="button" id="updatebutton" class="text-warning bg-info btn btn-pdefault btn-xs btn-sm" style="display: none;">更新</button>
				<button type="button" id="createbutton" class="text-warning bg-info btn btn-pdefault btn-xs btn-sm" style="display: none;">作成</button>
				<button type="button" id="closebutton" class="text-warning bg-info btn btn-pdefault btn-xs btn-sm" onclick=CloseModal()>閉じる</button>										
			</div>					
		</div>
	</body>
</html>
