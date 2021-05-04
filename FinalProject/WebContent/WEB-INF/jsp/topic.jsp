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
<fmt:message key="topic.login_to_reply" var="login_to_reply_message" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Section</title>
<%@ include file="fragments/head.jspf"%>

</head>
<body>

	<script src="${pageContext.request.contextPath}/js/post_validation.js"></script>
	<script src="${pageContext.request.contextPath}/js/edit_post.js"></script>

	<%@ include file="fragments/navbar.jspf"%>

	<div class="container-fluid mt-100">

		<%@ include file="fragments/messages.jspf"%>
		<div class="row z-depth-1" style="margin-bottom: 40px; padding: 10px;">
			<div class="col s12">
				<h5>
					<a
						href="${pageContext.request.contextPath}/controller?command=view_topic_by_section&section=${topic.section.id}">${fn:escapeXml(topic.section.header)}
					</a> <span"${fn:escapeXml(topic.header)}"></span>
				</h5>
			</div>
		</div>

		<!-- TOPIC  -->

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
											pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
										<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
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



		<!-- POSTS -->

		<c:forEach var="post" items="${posts}">
			<div class="row">
				<div class="col-md-12">
					<div class="card mb-4">
						<div class="card-header">
							<div class="media flex-wrap w-100 align-items-center">
								<div class="media-body ml-3">
									<p>${fn:escapeXml(post.user.userName)}</p>
									<c:set var="username" scope="request"
										value="${fn:escapeXml(post.user.userName)}" />
									<c:if test="${sessionScope.username == username }">
										<div>
											<a
												href="${pageContext.request.contextPath}/controller?command=delete_post_by_id&post_id=${post.id}&topic_id=${topic.id}"
												class="btn btn-light bi bi-trash">delete </a> <a
												class="btn btn-light bi bi-pencil" href="javascript:void();"
												id="editLink${post.id}" onclick="showEdit(${post.id});">edit
											</a>
										</div>
									</c:if>
									<div class="text-muted small">
										<fmt:parseDate value="${ post.creationDate }"
											pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
										<fmt:formatDate pattern="dd.MM.yyyy HH:mm"
											value="${ parsedDateTime }" />
										Post Id : ${post.id}
									</div>
								</div>
								<div class="text-muted small ml-3">
									<div>
										last login date <strong><fmt:parseDate
												value="${ post.user.lastLoginDate }"
												pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
												type="both" /> <fmt:formatDate pattern="dd.MM.yyyy HH:mm"
												value="${ parsedDateTime }" /> </strong>
									</div>
								</div>
							</div>
						</div>

						<!-- R -->
						<div class="card-body">
							<!-- POST CONTENT -->
							<p id="edit${post.id}">${(post.content)}</p>
							<div id="editForm${post.id}" style="display: none">
								<form name="formEdit"
									onsubmit="return validateEditForm(${post.id});"
									action="${pageContext.request.contextPath}/controller?command=edit_post_by_id&post_id=${post.id}&topic_id=${topic.id}"
									method="POST">
									<div class="form-group">
										<label for="content${post.id}">Your Edit</label>
										<textarea class="form-control" id="content${post.id}"
											name="content"> </textarea>
									</div>
									<button class="btn btn-outline-dark" id="submit${post.id}"
										type="submit">Send Edit</button>
									<button class="btn btn-outline-dark" id="cancel${post.id}"
										onclick="closeEdit(${post.id})" type="button">Cancel</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</c:forEach>

		<div>
			<!-- SECTION REPLY -->
			<sec:authorize access="${f:isAuthenticated(pageContext)}">
				<form name="formReply" onsubmit="return validateReplyForm();"
					action="${pageContext.request.contextPath}/controller?command=create_post&topic_id=${topic.id}"
					method="POST">
					<div class="form-group">
						<label for="comment">Your Reply</label>
						<textarea class="form-control" id="content" name="content"> </textarea>
					</div>
					<button class="btn btn-outline-dark bi bi-reply" id="submit"
						type="submit">Send reply</button>
				</form>

			</sec:authorize>
			<sec:authorize access="${!f:isAuthenticated(pageContext)}">
				<div class="row">
					<div class="col ">
						<h5>${login_to_reply_message}</h5>
					</div>
				</div>
			</sec:authorize>
		</div>
	</div>
</body>
</html>