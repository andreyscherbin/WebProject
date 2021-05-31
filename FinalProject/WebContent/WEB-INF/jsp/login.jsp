<%@ page language="java" session="true"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="login.title" var="title_message" />
<fmt:message key="login.welcome" var="welcome_message" />
<fmt:message key="login.username" var="username_message" />
<fmt:message key="login.password" var="password_message" />
<fmt:message key="login.sign_in" var="sign_in_message" />
<fmt:message key="validation.user.username"
	var="username_validation_message" />
<fmt:message key="validation.user.password"
	var="password_validation_message" />

<html>
<head>
<title>${title_message}</title>
<!-- Bootstrap core CSS -->
<%@ include file="fragments/head.jspf"%>
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/signin.css"
	rel="stylesheet">
<script src="${pageContext.request.contextPath}/js/login_validation.js"></script>
</head>

<body>
	<%@ include file="fragments/navbar.jspf"%>
	<div class="container-fluid mt-100 ">
		<form id="formSignIn" class="form-signin needs-validation" novalidate
			method="POST" action="${pageContext.request.contextPath}/controller">
			<input type="hidden" name="command" value="login">
			<h1 class="h3 mb-3 font-weight-normal">${welcome_message}</h1>

			<!-- username -->
			<label for="forUsername" class="form-label">${username_message}</label>
			<div class="input-group has-validation">
				<input type="text" id="forUsername" name="username"
					class="form-control" required autofocus
					aria-describedby="inputGroupPrepend" pattern="[A-Za-z0-9]{1,10}"
					maxlength="10" />
				<div class="invalid-feedback">${username_validation_message}</div>
			</div>

			<!-- password -->
			<label for="forPassword" class="form-label">${password_message}</label>
			<div class="input-group has-validation">
				<input type="password" id="forPassword" name="password"
					class="form-control" required autofocus
					aria-describedby="inputGroupPrepend"
					pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}" />
				<div class="invalid-feedback">${password_validation_message}</div>
			</div>

			<button id="btnSignIn" class="btn btn-lg btn-primary btn-block"
				type="submit">${sign_in_message}</button>
			<br>
			<%@ include file="fragments/messages.jspf"%>
		</form>
	</div>
</body>
</html>