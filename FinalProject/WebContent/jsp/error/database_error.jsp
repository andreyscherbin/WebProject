<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<title>Error Page</title>
</head>
<body>
	DATABASE ERROR
	<br /> Request from ${fn:escapeXml(pageContext.errorData.requestURI)}
	is failed
	<br /> Servlet name or type: ${pageContext.errorData.servletName}
	<br /> Status code: ${pageContext.errorData.statusCode}
	<br /> Exception: ${pageContext.errorData.throwable.cause}

</body>
</html>