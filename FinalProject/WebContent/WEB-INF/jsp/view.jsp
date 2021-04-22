<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Messages</title>
</head>
<body>
	<table>
		<c:forEach var="user" items="${users}" varStatus="status">
			<tr>
				<td><c:out value="${ user }" /></td>
				<td><c:out value="${ user.id }" /></td>
				<td><c:out value="${ user.userName }" /></td>
				<td><c:out value="${ user.password }" /></td>
				<td><c:out value="${ user.email }" /></td>
				<td><c:out value="${ status.count }" /></td>			   
			</tr>
		</c:forEach>
	</table>
	
	<br />		 
		        ${empty_users}
	<br />
	<br />		 
		        ${empty_user}
	<br />
</body>
</html>