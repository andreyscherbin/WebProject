<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/security_tags.tld" prefix="sec"%>
<%@ taglib uri="/WEB-INF/security_functions_tags.tld" prefix="f"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="home.welcome" var="welcome_message" />
<fmt:message key="home.lang" var="lang_message" />
<fmt:message key="home.role" var="role_message" />
<fmt:message key="home.new_section" var="new_section_message" />
<fmt:message key="home.sections" var="sections_message" />
<fmt:message key="home.view" var="view_message" />
<c:set var="username" value="${fn:escapeXml(sessionScope.username)}" />
<c:set var="lang" value="${sessionScope.lang}" />
<c:set var="role" value="${sessionScope.role}" />
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/home.css">
<%@ include file="fragments/head.jspf"%>
</head>
<body>

	<%@ include file="fragments/navbar.jspf"%>

	<div class="container-fluid mt-100 ">
		<h3>${welcome_message}${username}${lang_message}${lang}
			${role_message} ${role}</h3>
		<sec:authorize access="${f:hasRole('ADMIN',pageContext)}">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/controller?command=go_to_new_section_page">
				${new_section_message} </a>
		</sec:authorize>
		<%@ include file="fragments/messages.jspf"%>
		<!-- SECTIONS -->
		<c:if test="${sections.size() > 0}">

			<div class="album py-5 bg-light">
				<div class="container">
					<div class="row">
						<c:set var="count" value="1" scope="page" />
						<c:forEach var="section" items="${sections}">
							<div class="col-md-4">
								<div class="card mb-4 box-shadow">
									<img class="card-img-top"
										src="${pageContext.request.contextPath}/image/${count}.jpg"
										width="300" height="200" alt="Card image cap">
									<div class="card-body">
										<p class="card-text">${fn:escapeXml(section.header)}</p>
										<div class="d-flex justify-content-between align-items-center">
											<div class="btn-group">
												<a
													href="${pageContext.request.contextPath}/controller?command=view_section_by_id&section_id=${section.id}"
													class="btn btn-sm btn-outline-secondary">
													${view_message} </a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<c:set var="count" value="${count + 1}" scope="page" />
						</c:forEach>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</body>
</html>