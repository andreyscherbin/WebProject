<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>
<html>
<head>
<title>Index</title>
</head>
<body>
	<c:set var="lang" value="en_US" scope="session" />
	<c:redirect url="/jsp/home.jsp"/>
</body>
</html>