<%@ page language="Java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<html> 
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<link rel="stylesheet" href="<spring:theme code='styleSheet'/>" type="text/css"/>
	<script type="text/javascript" src="resources/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="resources/js/util/util.js"></script>
	<script type="text/javascript" src="resources/js/product/product.js"></script>
</head> 
<body>
	<input id="redThemeBtn" type="button" value="红色主题"/>
	<input id="baseThemeBtn" type="button" value="蓝色主题"/>
	<div>
	
		商品
	
	</div>
</body> 
</html> 