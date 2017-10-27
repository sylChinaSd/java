<%@ page language="Java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; 
%> 
<!DOCTYPE html>
<html> 
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    
    <link href="resources/images/logo.png" rel="Shortcut Icon">
	<link href="resources/css/style.css" rel="stylesheet">
    <link href="resources/css/__main.css" rel="stylesheet">
    <link href="resources/css/bootstrapValidator.min.css" rel="stylesheet">
    <link href="resources/skins/icheck/flat/red.css" rel="stylesheet">
    <link href="resources/css/cropper.min.css" rel="stylesheet">

	<script src="resources/js/dateformat.js"></script>
    <script src="resources/js/jquery-1.11.3.min.js"></script>
    <script src="resources/js/jquery.form.js"></script>
	<script src="resources/js/jquery.validate.js"></script>
	<script src="resources/js/additional-methods.js"></script>
	<script src="resources/js/cropper.min.js"></script>
	<script>
		jQuery.validator.addMethod("step2", function(value, element,params) {
			var tmp = value*Math.pow(10,params);
		  return (tmp-parseInt(tmp))==0;
		}, "小数输入框源码验证出错!");
	</script>
    <script src="resources/js/bootstrap.min.js"></script>
    <script src="resources/js/bootstrapValidator.min.js"></script>
    <script src="resources/js/icheck.min.js"></script>
    <script src="resources/js/common.js"></script>
    <!--<script src="resources/js/login.js"></script>-->
    <title>MSP平台</title>
</head>
<body class="login-body">
	<div class="container">
		<div id="app">
			<route-view></route-view>	
		</div>	
	</div>
	
	<script src="resources/js/build.js"></script>	
</body>
</html>