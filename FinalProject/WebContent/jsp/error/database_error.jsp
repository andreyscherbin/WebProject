<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Error Page</title>
</head>
<body>
	DATABASE ERROR <br/>
	Request from ${pageContext.errorData.requestURI} is failed
	<br/>
	 Servlet name or type: ${pageContext.errorData.servletName}
	<br/>
	 Status code: ${pageContext.errorData.statusCode}
	<br/>
	 Exception: ${pageContext.errorData.throwable.cause}
</body>
</html>