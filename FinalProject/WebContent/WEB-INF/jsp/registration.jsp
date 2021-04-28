<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
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
		<h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>


		<!-- email address -->
		<label for="inputEmail" class="form-label">Email address</label>
		<div class="input-group has-validation">
			<input type="text" id="inputEmail" name="email" class="form-control"
				aria-describedby="inputGroupPrepend" required autofocus
				pattern="^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$">
			<div class="invalid-feedback">Please enter valid email</div>
		</div>

		<!-- username -->
		<label for="inputUsername" class="form-label">Username</label>
		<div class="input-group has-validation">
			<input type="text" id="inputUsername" name="username"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend" pattern="[A-Za-z0-9]+"
				maxlength="10" />
			<div class="invalid-feedback">Must contain only digits and
				upperscase or lowercase letter, with max length 10</div>
		</div>

		<!-- password -->
		<label for="inputPassword" class="form-label">Password</label>
		<div class="input-group has-validation">
			<input type="password" id="inputPassword" name="password"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" />
			<div class="invalid-feedback">Must contain at least one number
				and one uppercase and lowercase letter, and at least 8 or more
				characters</div>
		</div>

		<!-- repeat password -->
		<label for="repeatPassword" class="form-label">RepeatPassword</label>
		<div class="input-group has-validation">
			<input type="password" id="repeatPassword" name="repeatPassword"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" />
			<div class="invalid-feedback">Passwords Don't Match</div>
		</div>

		<button id="btnSignUp" class="btn btn-lg btn-primary btn-block"
			type="submit">Sign Up</button>
		<br>
		<script
			src="${pageContext.request.contextPath}/js/password_validation.js"></script>
		<%@ include file="fragments/messages.jspf"%>
	</form>
</body>
</html>