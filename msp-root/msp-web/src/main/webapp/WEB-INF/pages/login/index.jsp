<%@ page language="Java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<script type="text/javascript">
		$(document).ready(function(){
			$('#codeImg').on('click',function(){
				console.log('click');
				$('#codeImg').attr('src','<%=path%>/login/code?'+Math.random());
			});	
		})
	</script>
</head> 
<body>
	<div>
		<h1>登录信息</h1>
		<span>测试用户:user-user,manager-manager</span>
		<form action="<%=path%>/j_spring_security_check" method="POST" class="form-horizontal" id="loginForm">
			用户名:<input name="username" type="text" value="user"/><br>
			密&nbsp;&nbsp;码：<input name="password" type="password" value="user"/><br>
			验证码：<input name="code" type="text" /><br>
			<a style="cursor: pointer;"><img id="codeImg" style="width:100px;" src="<%=path%>/login/code"/></a>
			<input style="width:100px;height:30px;" type="submit" value="登录"/>
		</form>
	</div>
</body> 
</html> 