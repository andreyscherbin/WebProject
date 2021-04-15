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
</head>

<body class="text-center">
	<form class="form-signin" method="POST"
		action="${pageContext.request.contextPath}/controller">
		<input type="hidden" name="command" value="registration">
		<h1 class="h3 mb-3 font-weight-normal">Please sign up</h1>
		<!-- email address -->
		<label for="inputEmail" class="sr-only">Email address</label> <input
			type="email" id="inputEmail" name="email" class="form-control"
			placeholder="Enter Email" required autofocus>
		<!-- username -->
		<label for="inputUsername" class="sr-only">Username</label> <input
			type="text" id="inputUsername" name="username" class="form-control"
			placeholder="Enter Username" required autofocus
			pattern="[A-Za-z0-9]+" maxlength="10"
			title="Must contain only digits and upperscase or lowercase letter, with max length 10" />
		<!-- password -->
		<label for="inputPassword" class="sr-only">Password</label> <input
			type="password" name="password" id="inputPassword"
			class="form-control" placeholder="Enter Password" required
			pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
			title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" />
		<!-- repeat password -->
		<label for="repeatPassword" class="sr-only">RepeatPassword</label> <input
			type="password" name="repeatPassword" id="repeatPassword"
			class="form-control" placeholder="Repeat Password" required
			pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
			title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" />
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
			Up</button>
		<br>
		<script
			src="${pageContext.request.contextPath}/js/password_validation.js"></script>
		<%@ include file="fragments/messages.jspf"%>
	</form>
</body>
</html>