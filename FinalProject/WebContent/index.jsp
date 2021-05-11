<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<html>
<head>
<title>Index</title>
</head>
<body>
	<c:set var="lang" value="en_US" scope="session" />
	<c:if test="${empty sessionScope.role}">
		<c:set var="role" value="GUEST" scope="session" />
	</c:if>
	<jsp:forward page="/WEB-INF/jsp/home.jsp" />
</body>
</html>