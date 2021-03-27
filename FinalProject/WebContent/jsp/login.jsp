<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Login</title>
</head>
<body>
	<form name="loginForm" method="POST" action="controller">
		<input type="hidden" name="command" value="login" />
		Login:<br />
		<input type="text" name="login" value="" />
		<br />Password:<br />
		<input type="password" name="password" value="" />
		 <br />		 
		        ${error_login}
		 <br /> 
		        ${wrong_action}
		 <br /> 
		        ${null_page}
		 <br />
		 <br /> 
		        ${empty_command}
		 <br />
		 <br /> 
		        ${wrong_command}
		 <br />
		<input type="submit" value="Log in" />
	</form>	
	<hr />
</body>
</html>