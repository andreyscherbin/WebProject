<%@ page contentType="text/html; charset=UTF-8" session="true"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Index</title>
</head>
<body>
	<c:if test="${empty sessionScope.lang}">
		<c:set var="lang" value="en_US" scope="session" />
	</c:if>
	<c:if test="${empty sessionScope.role}">
		<c:set var="role" value="GUEST" scope="session" />
	</c:if>
	<jsp:forward page="/WEB-INF/jsp/home.jsp" />
</body>
</html>