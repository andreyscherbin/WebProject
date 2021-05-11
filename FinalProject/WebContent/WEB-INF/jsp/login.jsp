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
	src="${pageContext.request.contextPath}/js/login_validation.js"></script>
</head>

<body class="text-center">
	<form id="formSignIn" class="form-signin needs-validation" novalidate
		method="POST" action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="login">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>

		<!-- username -->
		<label for="forUsername" class="form-label">Username</label>
		<div class="input-group has-validation">
			<input type="text" id="forUsername" name="username"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend" pattern="[A-Za-z0-9]{1,10}"
				maxlength="10" />
			<div class="invalid-feedback">Must contain only digits and
				upperscase or lowercase letter, with max length 10</div>
		</div>

		<!-- password -->
		<label for="forPassword" class="form-label">Password</label>
		<div class="input-group has-validation">
			<input type="password" id="forPassword" name="password"
				class="form-control" required autofocus
				aria-describedby="inputGroupPrepend"
				pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,60}" />
			<div class="invalid-feedback">Must contain at least one number
				and one uppercase and lowercase letter, and at least 8 or more
				characters</div>
		</div>

		<button id="btnSignIn" class="btn btn-lg btn-primary btn-block"
			type="submit">Sign in</button>
		<br>
		<%@ include file="fragments/messages.jspf"%>
	</form>
</body>
</html>