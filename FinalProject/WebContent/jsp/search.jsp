<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search</title>
<%-- <jsp:include page="fragments/head.jspf"></jsp:include> --%>
<!--what's the difference?-->
<%@ include file="fragments/head.jspf"%>
</head>
<body>

	<%-- <jsp:include page="fragments/navbar.jspf"></jsp:include> --%>
	<!--   what is the difference? -->
	<%@ include file="fragments/navbar.jspf"%>

	<div class="container">

		<!-- <div th:replace="fragments/messages :: messages"></div> -->

		<div class="row">
			<div class="col s12">
				<div class="divider"></div>
				<c:forEach var="topic" items="${topics}">
					<div class="row">
						<div class="col s12">
							<div class="section">
								<a href="topic/${topic.id}">${topic.user.username} ${topic.header}</a>
								<%-- <span class="right" th:text="${topic.creationDate} ? ${#calendars.format(topic.creationDate, 'dd MMMM yyyy HH:mm')}"></span> <span class="truncate"
                                th:text="${topic.content}"></span> --%>
							</div>
							<div class="divider"></div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>

	<!-- <footer th:replace="fragments/footer :: footer"></footer> -->
</body>
</html>