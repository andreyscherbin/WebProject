<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
<%-- <jsp:include page="fragments/head.jspf"></jsp:include> --%>
<!--what's the difference?-->
<%@ include file="fragments/head.jspf"%>
</head>
<body>

	<%-- <jsp:include page="fragments/navbar.jspf"></jsp:include> --%>
	<!--   what is the difference? -->
	<%@ include file="fragments/navbar.jspf"%>

	<div class="container">
		<!-- SECTIONS -->

		<div class="row">
			<c:if test="${sections.size() > 0}">
				<div class="col s12">

					<div class="row" id="sections">
						<div class="col s12">
							<div class="row">
								<div class="col s6">
									<h4>
										<fmt:message key="sections" />
									</h4>
								</div>
								<div class="col s6">
									<%-- <a sec:authorize="hasAnyAuthority('ADMIN', 'HEAD_ADMIN')"
									th:href="@{/section/new}" th:text="#{new.section}"
									class="right waves-effect waves-light btn"></a> --%>
									<c:if test="${sessionScope.role == 'ADMIN'}">
									 <a href="section/new"> <fmt:message key="new.section" /> </a>
									</c:if>
								</div>
							</div>
						</div>
					</div>

					<div class="row">
						<div class="col s12">
							<ul>
								<c:forEach var="section" items="${sections}">
									<li>
										<div>
											<a
												href="section/${section.id}">${section.header}
											</a>
										</div>
										<div>
											<p>${section.description}</p>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>