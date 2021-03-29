<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Log In</title>
    <style>
      input:invalid {
        border: 2px dashed red;
      }      
      input:valid {
        border: 2px solid black;
      }
    </style>
</head>
<body>
	<form name="loginForm" method="POST" action="controller">
		<input type="hidden" name="command" value="login" />
		Username:<br />
		<input type="text" name="username" required pattern="[A-Za-z0-9]+" maxlength="10"
		title="Must contain only digits and upperscase or lowercase letter, with max length 10" />
		Password:<br />
		<input type="password" name="password" required pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" 
		title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" />
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
		  <br /> 
		        ${error_authentication}
		 <br />		
		<input type="submit" value="Log in" />
	</form>	
	<hr />
</body>
</html>