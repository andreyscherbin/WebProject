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
		<input type="hidden" name="command" value="login">
		<h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
		<label for="forUsername" class="sr-only">Username</label> <input
			type="text" id="forUsername" name="username" class="form-control"
			placeholder="Username" required autofocus pattern="[A-Za-z0-9]+"
			maxlength="10"
			title="Must contain only digits and upperscase or lowercase letter, with max length 10" />
		<label for="forPassword" class="sr-only">Password</label> <input
			type="password" name="password" id="forPassword" class="form-control"
			placeholder="Password" required
			pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
			title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" />
		<!-- <div class="checkbox mb-3">
			<label> <input type="checkbox" value="remember-me">
				Remember me
			</label>
		</div> -->
		<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
			in</button>
		<br>
		<%@ include file="fragments/messages.jspf"%>
	</form>
</body>
</html>