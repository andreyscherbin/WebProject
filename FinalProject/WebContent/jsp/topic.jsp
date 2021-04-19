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

	<script src="${pageContext.request.contextPath}/js/post_validation.js"></script>

	<%@ include file="fragments/navbar.jspf"%>

	<div class="container">

		<%@ include file="fragments/messages.jspf"%>
		<div class="row z-depth-1" style="margin-bottom: 40px; padding: 10px;">
			<div class="col s12">
				<h5>
					<a
						href="${pageContext.request.contextPath}/controller?command=view_topic_by_section&section=${topic.section.id}">${fn:escapeXml(topic.section.header)}
					</a> : : <span"${fn:escapeXml(topic.header)}"></span>
				</h5>
			</div>
		</div>

		<!-- TOPIC  -->

		<div class="row z-depth-1">
			<div class="row">
				<!-- L -->
				<div class="col s2  center">
					<div class="row">
						<div class="col s11 right">
							<a
								href="${pageContext.request.contextPath}/controller?command=view_user_by_username&username=${fn:escapeXml(topic.user.userName)}">${fn:escapeXml(topic.user.userName)}
							</a>
						</div>
					</div>
					<div class="col s12"></div>
				</div>
				<!-- R -->
				<div class="col s10">
					<div class="row">
						<div class="col s11">
							<p>
								<fmt:parseDate value="${ topic.creationDate }"
									pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
								<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
									value="${ parsedDateTime }" />
							</p>
							<p>
								<%-- th:utext="${#strings.replace(topic.content,T(java.lang.System).getProperty('line.separator'),'&lt;br /&gt;')}"> --%>
								${fn:escapeXml(topic.content)}
							</p>
							<div class="divider"></div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<c:set var="username" scope="request"
					value="${fn:escapeXml(topic.user.userName)}" />
				<c:if test="${sessionScope.username == username }">
					<div class="col s10 right">
						<a
							href="${pageContext.request.contextPath}/controller?command=delete_topic_by_id&topic_id=${topic.id}">Delete
						</a> <a
							href="${pageContext.request.contextPath}/controller?command=edit_topic_by_id&topic_id=${topic.id}">Edit
						</a>
					</div>
				</c:if>
			</div>
		</div>



		<!-- POSTS -->

		<c:forEach var="post" items="${posts}">
			<c:set var="id" scope="request" value="${post.id}" />
			<div class="row z-depth-1">
				<!-- L -->
				<div class="col s2  center">
					<a
						href="${pageContext.request.contextPath}/controller?command=view_user_by_username&username=${fn:escapeXml(post.user.userName)}">${fn:escapeXml(post.user.userName)}
					</a>
					<div class="col s12"></div>
				</div>

				<!-- R -->
				<div class="col s10">
					<div class="row">
						<div class="col s11">
							<%-- <p
								th:text="${post.creationDate} ? ${#calendars.format(post.creationDate, 'HH:mm:ss')} + ' ' + #{page.topic.on.day} + ' ' + ${#calendars.format(post.creationDate, 'dd MMMM yyyy')} + ' ID: ' + ${post.id} ">
							</p> --%>
							<p>
								<fmt:parseDate value="${ post.creationDate }"
									pattern="HH:mm::ss" var="parsedDateTime" type="both" />
								<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
									value="${ parsedDateTime }" var="creationDate" />
								ID: ${post.id}

							</p>
							<p>
								<%-- th:utext="${#strings.replace(post.content,T(java.lang.System).getProperty('line.separator'),'&lt;br /&gt;')}" --%>
								${fn:escapeXml(post.content)}
							</p>

						</div>
					</div>
				</div>

				<div class="row">
					<c:set var="username" scope="request"
						value="${fn:escapeXml(post.user.userName)}" />
					<c:if test="${sessionScope.username == username }">
						<div class="col s10 right">
							<a
								href="${pageContext.request.contextPath}/controller?command=delete_post_by_id&post_id=${post.id}">Delete
							</a> <a
								href="${pageContext.request.contextPath}/controller?command=edit_post_by_id&post_id=${post.id}">Edit
							</a>
						</div>
					</c:if>
				</div>
			</div>
		</c:forEach>

		<!-- SECTION REPLY -->

		<c:if test="${sessionScope.role != null}">
			<div class="row">
				<div class="col">
					<form onsubmit="validate()"
						action="${pageContext.request.contextPath}/controller?command=create_post&topic_id=${topic.id}"
						method="POST">
						<div class="row">
							<div class="input-group">
								<div class="input-group-prepend">
									<span class="input-group-text">Your reply</span>
								</div>
								<textarea class="form-control" id="content" name="content"> </textarea>
								<%-- <p th:if="${#fields.hasErrors('content')}"  НУЖНА ВАЛИДАЦИЯ
									th:errors="*{content}" class="red-text">Invalid content!
								</p> --%>
							</div>
						</div>

						<div class="row">
							<div>
								<button class="btn btn-outline-dark" type="submit">
									Send reply</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</c:if>
		<c:if test="${sessionScope.role == null}">
			<div class="row">
				<div class="col ">
					<h5>
						<fmt:message key="login.to.reply" />
					</h5>
				</div>
			</div>
		</c:if>
	</div>