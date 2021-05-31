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
<c:set var="username" value="${fn:escapeXml(sessionScope.username)}" />
<c:set var="lang" value="${sessionScope.lang}" />
<c:set var="role" value="${sessionScope.role}" />
<html>
<head>
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

		<div class="row">
			<c:if test="${sections.size() > 0}">
				<div class="col s12">

					<div class="row" id="sections">
						<div class="col s12">
							<div class="row">
								<div class="col s6">
									<h4>${sections_message}</h4>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col s12">
							<ul>
								<c:forEach var="section" items="${sections}">
									<li>
										<div>
											<a
												href="${pageContext.request.contextPath}/controller?command=view_section_by_id&section_id=${section.id}">${fn:escapeXml(section.header)}
											</a>
										</div>
										<div>
											<p>${fn:escapeXml(section.description)}</p>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>