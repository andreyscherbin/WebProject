<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log In</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
	<form name="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="login" />
		Username: <input type="text" name="username" required pattern="[A-Za-z0-9]+" maxlength="10"
		title="Must contain only digits and upperscase or lowercase letter, with max length 10" />
		<hr/>
		Password: <input type="password" name="password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" 
		title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" />			 
		        ${error_login}		
		        ${wrong_action}		 
		        ${null_page}	
		  		${empty_command}		 
		        ${wrong_command}		 
		        ${error_authentication}	
		<hr/>        		
		<input type="submit" value="Log in" />
		<hr/>		
	</form>		
</body>
</html>