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
	
	<form name="ViewForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view" />		
		<input type="submit" value="View" />		
	</form>	
	
	<form name="ViewByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_by_id" />
		<input type="text" name="id" value="" />  		
		<input type="submit" value="ViewById" />${wrongInput}	
	</form>	
	
	<form name="SortByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="sort_by_id" />		
		<input type="submit" value="SortById" />
	</form>		
</body>
</html>