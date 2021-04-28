<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>404 Error Page</title>
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/error_page.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>Oops!</h1>
					<h2>
						${fn:escapeXml(pageContext.errorData.requestURI)} is failed <br />
						Servlet name or type: ${fn:escapeXml(pageContext.errorData.servletName)} <br />
						Status code: ${pageContext.errorData.statusCode} <br />
					</h2>
					<div class="error-details">Sorry, an error has occured,
						Requested page not found!</div>
					<div class="error-actions">
						<a href="${pageContext.request.contextPath}" 
							class="btn btn-primary btn-lg" > Take Me Home 
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>