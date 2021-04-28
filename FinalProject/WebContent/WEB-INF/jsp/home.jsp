<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
<%@ include file="fragments/head.jspf"%>
<script src="${pageContext.request.contextPath}/js/search_ajax.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/search.css">
</head>
<body>

	<%@ include file="fragments/navbar.jspf"%>

	<div class="container-fluid mt-100"<%-- style="background-image: url('${pageContext.request.contextPath}/image/anon.jpg'); height: 100vh;" --%>>
		<%@ include file="fragments/messages.jspf"%>
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
									<c:if test="${sessionScope.role == 'ADMIN'}">
										<a href="section/new"> <fmt:message key="new.section" />
										</a>
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
												href="${pageContext.request.contextPath}/controller?command=view_topic_by_section&section=${section.id}">${fn:escapeXml(section.header)}
											</a>
										</div>
										<div>
											<p>${fn:escapeXml(section.description)}</p>
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