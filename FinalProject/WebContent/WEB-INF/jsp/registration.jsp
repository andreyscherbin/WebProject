<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="registration.title" var="title_message" />
<fmt:message key="registration.welcome" var="welcome_message" />
<fmt:message key="registration.email" var="email_message" />
<fmt:message key="registration.username" var="username_message" />
<fmt:message key="registration.password" var="password_message" />
<fmt:message key="registration.repeat_password"
	var="repeat_password_message" />
<fmt:message key="registration.sign_up" var="sign_up_message" />
<fmt:message key="validation.user.username"
	var="username_validation_message" />
<fmt:message key="validation.user.password"
	var="password_validation_message" />
<fmt:message key="validation.user.email" var="email_validation_message" />
<fmt:message key="validation.user.repeat_password"
	var="repeat_password_validation_message" />

<html>
<head>
<title>${title_message}</title>
<!-- Bootstrap core CSS -->
<%@ include file="fragments/head.jspf"%>
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/signin.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/js/registration_validation.js"></script>
</head>

<body class="text-center">
	<form id="formSignUp" class="form-signin needs-validation" novalidate
		method="POST" action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="registration">
		<h1 class="h3 mb-3 font-weight-normal">${welcome_message}</h1>


		<!-- email address -->
		<label for="inputEmail" class="form-label">${email_message}</label>
		<div class="input-group has-validation">
			<input type="email" id="inputEmail" name="email" class="form-control"
				aria-describedby="inputGroupPrepend" required autofocus>
			<div class="invalid-feedback">${email_validation_message}</div>
		</div>

		<!-- username -->
		<label for="inputUsername" class="form-label">${username_message}</label>
		<div class="input-group has-validation">
			<input type="text" id="inputUsername" name="username"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend" pattern="[A-Za-z0-9]{1,10}"
				maxlength="10" />
			<div class="invalid-feedback">${username_validation_message}</div>
		</div>

		<!-- password -->
		<label for="inputPassword" class="form-label">${password_message}</label>
		<div class="input-group has-validation">
			<input type="password" id="inputPassword" name="password"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}" />
			<div class="invalid-feedback">${password_validation_message}</div>
		</div>

		<!-- repeat password -->
		<label for="repeatPassword" class="form-label">${repeat_password_message}</label>
		<div class="input-group has-validation">
			<input type="password" id="repeatPassword" name="repeatPassword"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}" />
			<div class="invalid-feedback">${repeat_password_validation_message}</div>
		</div>

		<button id="btnSignUp" class="btn btn-lg btn-primary btn-block"
			type="submit">${sign_up_message}</button>
		<br>
		<script
			src="${pageContext.request.contextPath}/js/password_validation.js"></script>
		<%@ include file="fragments/messages.jspf"%>
	</form>
</body>
</html>