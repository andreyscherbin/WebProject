<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="service_error.title" var="title_message" />
<fmt:message key="service_error.sorry" var="sorry_message" />
<fmt:message key="service_error.error_details"
	var="error_details_message" />
<fmt:message key="service_error.home" var="home_message" />
<fmt:message key="error.is_failed" var="is_failed_message" />
<fmt:message key="error.servlet_name_or_type"
	var="servlet_name_or_type_message" />
<fmt:message key="error.status_code" var="status_code_message" />
<fmt:message key="error.exception_message" var="exception_message" />
<fmt:message key="error.uri" var="uri_message" />
<fmt:message key="error.cause" var="cause_message" />

<html>
<head>
<title>${title_message}</title>
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/error_page.css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div class="error-template">
					<h1>${sorry_message}</h1>
					<h2>
						${exception_message}: ${fn:escapeXml(requestScope.errorMessage)} <br>
						${cause_message}: ${fn:escapeXml(requestScope.errorCause)} <br>
						${uri_message}: ${fn:escapeXml(requestScope.errorLocation)} <br>
						${status_code_message}: ${requestScope.errorCode} <br>
					</h2>
					<div class="error-details">${error_details_message}</div>
					<div class="error-actions">
						<a href="${pageContext.request.contextPath}"
							class="btn btn-primary btn-lg bi bi-house"> ${home_message} </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>