<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<title>Service Error</title>
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
						Exception Message: ${fn:escapeXml(requestScope.errorMessage)} <br> Cause:
						${fn:escapeXml(requestScope.errorCause)} <br> URI:
						${fn:escapeXml(requestScope.errorLocation)} <br> Status code:
						${requestScope.errorCode} <br>
					</h2>
					<div class="error-details">Sorry, an error has occured on the server side</div>
					<div class="error-actions">
						<a href="${pageContext.request.contextPath}"
							class="btn btn-primary btn-lg"> <svg
								xmlns="http://www.w3.org/2000/svg" width="16" height="16"
								fill="currentColor" class="bi bi-house" viewBox="0 0 16 16">
                               <path fill-rule="evenodd"
									d="M2 13.5V7h1v6.5a.5.5 0 0 0 .5.5h9a.5.5 0 0 0 .5-.5V7h1v6.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5zm11-11V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z" />
                               <path fill-rule="evenodd"
									d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z" />
                             </svg> Take Me Home
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>