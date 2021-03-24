<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>View Messages</title>
</head>
<body>
	<table>
		<c:forEach var="elem" items="${lst}" varStatus="status">
			<tr>
				<td><c:out value="${ elem }" /></td>
				<td><c:out value="${ elem.id }" /></td>
				<td><c:out value="${ elem.text }" /></td>
				<td><c:out value="${ status.count }" /></td>
			</tr>
		</c:forEach>
	</table>
	
	<br />		 
		        ${emptyList}
	<br />
</body>
</html>