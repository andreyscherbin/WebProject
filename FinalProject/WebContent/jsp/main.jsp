<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Welcome</title>
</head>
<body>
	<h3>Welcome</h3>
	<hr />
	${user}, hello!
	<hr />
	<a href="controller?command=logout">Logout</a>	
	
	<form name="ViewUserByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user_by_id" />
		<input type="text" name="id" value="" />  		
		<input type="submit" value="ViewUserById" />${wrongInput}	
	</form>	
	
	<form name="SortUserByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="sort_user_by_id" />		
		<input type="submit" value="SortUserById" />
	</form>	
	
	<form name="ViewUserForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user" />		
		<input type="submit" value="ViewUser" />
	</form>	
	
	<form name="ViewUserByUserNameForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user_by_username" />	
		<input type="text" name="user_name" value="" /> 	
		<input type="submit" value="ViewUserByUserName" />
	</form>	
		
</body>
</html>