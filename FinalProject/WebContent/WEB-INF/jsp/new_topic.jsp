<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="new_topic.create_topic" var="create_topic_message" />
<fmt:message key="new_topic.title" var="title_message" />
<fmt:message key="new_topic.description" var="description_message" />
<fmt:message key="new_topic.create_button" var="create_button_message" />
<fmt:message key="new_topic.cancel_button" var="cancel_button_message" />
<fmt:message key="validation.latin_cyrillic_digit_100"
	var="validation_title_message" />
<fmt:message key="validation.empty" var="validation_empty_message" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create topic page</title>
<%@ include file="fragments/head.jspf"%>
<script
	src="${pageContext.request.contextPath}/js/new_topic_validation.js"></script>
</head>
<body>
	<%@ include file="fragments/navbar.jspf"%>
	<div class="container">
		<div class="row">

			<div class="col-md-8 col-md-offset-2">

				<h1>${create_topic_message}</h1>

				<form id="formCreateTopic" class="form-signin needs-validation"
					novalidate
					action="${pageContext.request.contextPath}/controller?command=create_topic&section_id=${param.section_id}"
					method="POST">
					<div class="form-group">
						<label class="form-label" for="title">${title_message} </label> <input
							id="inputTitle" type="text" class="form-control" name="header"
							required autofocus aria-describedby="inputGroupPrepend"
							pattern="[А-Яа-яA-Za-z0-9]+" maxlength="100" />
						<div class="invalid-feedback">${validation_title_message}</div>
					</div>

					<div class="form-group">
						<label class="form-label" for="description">${description_message}</label>
						<textarea id="inputDescription" rows="5" class="form-control"
							name="content"></textarea>
						<div class="invalid-feedback">${validation_empty_message}</div>
					</div>

					<div class="form-group">
						<button id="btnCreateTopic" type="submit" class="btn btn-primary">${create_button_message}</button>
						<button class="btn btn-light">${cancel_button_message}</button>
					</div>

				</form>
			</div>
		</div>
	</div>
</body>
</html>