<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Section</title>
<%@ include file="fragments/head.jspf"%>
</head>
<body>

	<%@ include file="fragments/navbar.jspf"%>

	<div class="container">

		<%@ include file="fragments/messages.jspf"%>

		<div class="row">
			<div class="col s12">
				<div class="row">
					<div class="col s12">
						<h3>
							<fmt:message key="page.section.topics.in.section" />
							${fn:escapeXml(section.header)}
						</h3>
						<p>${fn:escapeXml(section.description)}</p>
						<c:if test="${sessionScope.role == 'ADMIN'}">
							<a href="section/delete/${sections.id}"> <fmt:message
									key="page.section.delete.section" />
							</a>
						</c:if>
						<a href="topic/new/"> <fmt:message
								key="page.section.new.topic" />
						</a>
					</div>
				</div>
				<div class="divider"></div>
				<c:forEach var="topic" items="${topics}">
					<div class="col s12">
						<div class="section">
							<a
								href="${pageContext.request.contextPath}/controller?command=view_topic_by_id&topic_id=${topic.id}">${fn:escapeXml(topic.user.userName)}
								: ${fn:escapeXml(topic.header)} </a> <span> <fmt:parseDate
									value="${ topic.creationDate }" pattern="yyyy-MM-dd'T'HH:mm"
									var="parsedDateTime" type="both" /> <fmt:formatDate
									pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
							</span> <span>${fn:escapeXml(topic.content)} </span>
						</div>
						<div>
							<p>${fn:escapeXml(section.description)}</p>
						</div>
						<div class="divider"></div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>