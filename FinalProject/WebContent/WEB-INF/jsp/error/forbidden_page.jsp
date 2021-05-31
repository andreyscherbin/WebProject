<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="forbidden_page.title" var="title_message" />
<fmt:message key="forbidden_page.status_code" var="status_code_message" />
<fmt:message key="forbidden_page.sorry" var="sorry_message" />
<fmt:message key="forbidden_page.error_details"
	var="error_details_message" />
<fmt:message key="forbidden_page.back" var="back_message" />

<html>
<head>
<title>${title_message}</title>
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/forbidden_page.css">
</head>
<body class="bg-dark text-white py-5">
	<div class="container py-5">
		<div class="row">
			<div class="col-md-2 text-center">
				<p>
					<i class="bi bi-exclamation-triangle"></i><br />${status_code_message}
				</p>
			</div>
			<div class="col-md-10">
				<h3>${sorry_message}</h3>
				<p>${error_details_message}</p>
				<a class="btn btn-danger" href="javascript:history.back()">${back_message}</a>
			</div>
		</div>
	</div>
</body>
</html>