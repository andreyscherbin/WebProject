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

	<div class="container-fluid mt-100">

		<%@ include file="fragments/messages.jspf"%>


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
		<a href="topic/new/"> <fmt:message key="page.section.new.topic" />
		</a>

		<c:forEach var="topic" items="${topics}">
			<div class="row">
				<div class="col-md-12">
					<div class="card mb-4">
						<div class="card-header">
							<div class="media flex-wrap w-100 align-items-center">
								<div class="media-body ml-3">
									<p>${fn:escapeXml(topic.user.userName)}
										<a
											href="${pageContext.request.contextPath}/controller?command=view_topic_by_id&topic_id=${topic.id}">${fn:escapeXml(topic.header)}
										</a>
									</p>
									<div class="text-muted small">
										<fmt:parseDate value="${ topic.creationDate }"
											pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
										<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
											value="${ parsedDateTime }" />
										Topic Id : ${topic.id}
									</div>
								</div>
								<div class="text-muted small ml-3">
									<div>
										last login date <strong><fmt:parseDate
												value="${ topic.user.lastLoginDate }"
												pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
												type="both" /> <fmt:formatDate pattern="dd.MM.yyyy HH:mm"
												value="${ parsedDateTime }" /> </strong>
									</div>
								</div>
							</div>
						</div>
						<div class="card-body">
							<p>${fn:escapeXml(topic.content)}</p>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>