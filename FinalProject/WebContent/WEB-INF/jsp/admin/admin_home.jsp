<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/security_tags.tld" prefix="sec"%>
<%@ taglib uri="/WEB-INF/security_functions_tags.tld" prefix="f"%>
<%@ page session="true"%>

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
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
</head>
<body>
	<%@ include file="/WEB-INF/jsp/fragments/navbar.jspf"%>
	<div class="container-fluid mt-100">
		<%@ include file="/WEB-INF/jsp/fragments/messages.jspf"%>
		<!-- USERS -->
		<table>
			<c:forEach var="user" items="${users}" varStatus="status">
				<tr>
					<td><c:out value="${ user }" /></td>
					<td><c:out value="${ user.id }" /></td>
					<td><c:out value="${ user.userName }" /></td>
					<td><c:out value="${ user.password }" /></td>
					<td><c:out value="${ user.email }" /></td>
					<td><c:out value="${ status.count }" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>