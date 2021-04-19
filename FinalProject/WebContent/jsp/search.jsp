<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
<%-- <jsp:include page="fragments/head.jspf"></jsp:include> --%>
<!--what's the difference?-->
<%@ include file="fragments/head.jspf"%>
</head>
<body>
	
	<%@ include file="fragments/navbar.jspf"%>

	<div class="container">

		<%@ include file="fragments/messages.jspf"%>

		<div class="row">
			<div class="col s12">
				<div class="divider"></div>
				<c:forEach var="topic" items="${topics}">
					<div class="row">
						<div class="col s12">
							<div class="section">
								<a href="topic/${topic.id}">${fn:escapeXml(topic.user.userName)}
									${fn:escapeXml(topic.header)}</a>
								<%-- <span class="right" th:text="${topic.creationDate} ? ${#calendars.format(topic.creationDate, 'dd MMMM yyyy HH:mm')}"></span> <span class="truncate"
                                th:text="${topic.content}"></span> --%>
							</div>
							<div class="divider"></div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- <footer th:replace="fragments/footer :: footer"></footer> -->
</body>
</html>