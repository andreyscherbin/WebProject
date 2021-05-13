<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/security_tags.tld" prefix="sec"%>
<%@ taglib uri="/WEB-INF/security_functions_tags.tld" prefix="f"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<fmt:message key="home.welcome" var="welcome_message" />
<fmt:message key="home.lang" var="lang_message" />
<fmt:message key="home.role" var="role_message" />
<fmt:message key="home.new_section" var="new_section_message" />
<fmt:message key="home.sections" var="sections_message" />
<c:set var="username" value="${fn:escapeXml(sessionScope.username)}" />
<c:set var="lang" value="${sessionScope.lang}" />
<c:set var="role" value="${sessionScope.role}" />
<html>
<head>
<%@ include file="/WEB-INF/jsp/fragments/head.jspf"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/admin_home.css">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" />
</head>
<body>
	<%@ include file="/WEB-INF/jsp/fragments/navbar.jspf"%>
	<div class="container-fluid mt-100">
		<%@ include file="/WEB-INF/jsp/fragments/messages.jspf"%>
		<!-- USERS -->

		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title text-uppercase mb-0">Manage Users</h5>
						</div>
						<div class="table-responsive">
							<table class="table no-wrap user-table mb-0">
								<thead>
									<tr>
										<th scope="col"
											class="border-0 text-uppercase font-medium pl-4">#</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Name</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Email</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Register
											Date</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Last
											Login Date</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Is
											Email Verifed</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Is
											Active</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Role</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Change
											Role</th>
										<th scope="col" class="border-0 text-uppercase font-medium">Manage</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="user" items="${users}">
										<tr>
											<td class="pl-4">${user.id}</td>
											<td>
												<h5 class="font-medium mb-0">${fn:escapeXml(user.userName)}</h5>
											</td>
											<td><span class="text-muted">${fn:escapeXml(user.email)}</span></td>
											<td><span class="text-muted"><strong><fmt:parseDate
															value="${user.registerDate}" pattern="yyyy-MM-dd'T'HH:mm"
															var="parsedDateTime" type="both" /> <fmt:formatDate
															pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
												</strong></span></td>
											<td><span class="text-muted"><strong><fmt:parseDate
															value="${user.lastLoginDate}"
															pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
															type="both" /> <fmt:formatDate
															pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }" />
												</strong></span></td>
											<td><span class="text-muted">${user.emailVerifed}</span></td>
											<td><span class="text-muted">${user.active}</span></td>
											<td><span class="text-muted">${user.role}</span></td>
											<td>
												<form method="POST"
													action="${pageContext.request.contextPath}/controller?command=change_role&user_id=${user.id}">
													<select class="form-control category-select" id="roles"
														name="role">
														<option value="ADMIN">Admin</option>
														<option value="MODER">Moder</option>
														<option value="USER">User</option>
													</select>
													<button id="btnSignIn" class="btn btn-dark btn-sm"
														type="submit">Change Role</button>
												</form>
											</td>
											<td>
												<form method="POST"
													action="${pageContext.request.contextPath}/controller?command=ban_user&user_id=${user.id}">
													<c:if test="${user.active}">
													<button type="submit"
														class="btn btn-outline-info btn-circle btn-lg btn-circle">
														<i class="fa fa-lock"></i>
													</button>
													</c:if>
													<c:if test="${!user.active}">
													<button type="submit"
														class="btn btn-outline-info btn-circle btn-lg btn-circle">
														<i class="fa fa-unlock"></i>
													</button>
													</c:if>
												</form>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>