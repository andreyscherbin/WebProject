<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="error_500.title" var="title_message" />
<fmt:message key="error_500.error_details" var="error_details_message" />
<fmt:message key="error_500.home" var="home_message" />
<fmt:message key="error.is_failed" var="is_failed_message" />
<fmt:message key="error.servlet_name_or_type"
	var="servlet_name_or_type_message" />
<fmt:message key="error.status_code" var="status_code_message" />
<fmt:message key="error.exception_message" var="exception_message" />

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
					<h1>Oops!</h1>
					<h2>
						${fn:escapeXml(pageContext.errorData.requestURI)}
						${is_failed_message} <br /> ${servlet_name_or_type_message}:
						${fn:escapeXml(pageContext.errorData.servletName)} <br />
						${status_code_message}: ${pageContext.errorData.statusCode} <br />
						${exception_message}:
						${fn:escapeXml(pageContext.errorData.throwable.message)}
					</h2>
					<div class="error-details">${error_details_message}</div>
					<div class="error-actions">
						<a href="${pageContext.request.contextPath}"
							class="btn btn-primary btn-lg bi bi-house"> ${home_message}</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>