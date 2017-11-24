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
</head> 
<body>
	<div>
		权限不足<br>
		<a href="<%=path%>/logout">其他用户登录</a>
	</div>
</body> 
</html> 