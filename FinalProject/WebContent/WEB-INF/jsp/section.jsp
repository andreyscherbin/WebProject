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
<fmt:message key="section.delete_section" var="delete_section_message" />
<fmt:message key="section.topics" var="topics_message" />
<fmt:message key="section.new_topic" var="new_topic_message" />
<fmt:message key="section.delete_topic" var="delete_topic_message" />
<fmt:message key="section.pin_topic" var="pin_topic_message" />
<fmt:message key="section.unpin_topic" var="unpin_topic_message" />
<fmt:message key="section.close_topic" var="close_topic_message" />
<fmt:message key="section.unclose_topic" var="unclose_topic_message" />
<fmt:message key="message.topic.closed" var="closed_topic_message" />

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


		<h3>${topics_message}${fn:escapeXml(section.header)}</h3>
		<p>${fn:escapeXml(section.description)}</p>

		<sec:authorize access="${f:hasRole('ADMIN',pageContext)}">
			<a class="btn btn-danger" href="${pageContext.request.contextPath}/controller?command=delete_section&section_id=${section.id}">
				${delete_section_message} </a>
		</sec:authorize>
		<sec:authorize access="${f:isAuthenticated(pageContext)}">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/controller?command=go_to_new_topic_page&section_id=${section.id}">
				${new_topic_message} </a>
		</sec:authorize>

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
										<c:if test="${topic.closed}">
										${closed_topic_message}
										</c:if>
									</p>
									<div class="text-muted small">
										<fmt:parseDate value="${ topic.creationDate }"
											pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
										<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
											value="${ parsedDateTime }" />
										Topic Id : ${topic.id}
										<sec:authorize
											access="${f:hasAnyRole(pageContext,'ADMIN','MODER')}">
											<!-- delete topic button -->
											<a class="btn btn-danger bi bi-trash"
												href="${pageContext.request.contextPath}/controller?command=delete_topic&topic_id=${topic.id}&section_id=${section.id}">
												${delete_topic_message} </a>
											<!--  pin topic button -->
											<c:if test="${!topic.pinned}">
												<a class="btn btn-light bi bi-pin"
													href="${pageContext.request.contextPath}/controller?command=pin_topic&topic_id=${topic.id}&section_id=${section.id}">
													${pin_topic_message} </a>
											</c:if>
											<!--  unpin topic button -->
											<c:if test="${topic.pinned}">
												<a class="btn btn-light bi bi bi-x"
													href="${pageContext.request.contextPath}/controller?command=pin_topic&topic_id=${topic.id}&section_id=${section.id}">
													${unpin_topic_message} </a>
											</c:if>
											<!-- close topic button -->
											<c:if test="${!topic.closed}">
												<a class="btn btn-light bi bi-lock-fill"
													href="${pageContext.request.contextPath}/controller?command=close_topic&topic_id=${topic.id}&section_id=${section.id}">
													${close_topic_message} </a>
											</c:if>
											<!-- unclose topic button -->
											<c:if test="${topic.closed}">
												<a class="btn btn-light bi bi-unlock-fill"
													href="${pageContext.request.contextPath}/controller?command=close_topic&topic_id=${topic.id}&section_id=${section.id}">
													${unclose_topic_message} </a>
											</c:if>
										</sec:authorize>
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