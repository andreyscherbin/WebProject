<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Forbidden Page</title>
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/forbidden_page.css">
</head>
<body class="bg-dark text-white py-5">
	<div class="container py-5">
		<div class="row">
			<div class="col-md-2 text-center">
				<p>
					<i class="bi bi-exclamation-triangle"></i><br />Status Code: 403
				</p>
			</div>
			<div class="col-md-10">
				<h3>OPPSSS!!!! Sorry...</h3>
				<p>
					Sorry, your access is refused due to security reasons of our server
					and also our sensitive data.<br />Please go back to the previous
					page to continue browsing.
				</p>
				<a class="btn btn-danger" href="javascript:history.back()">Go
					Back</a>
			</div>
		</div>
	</div>
</body>
</html>