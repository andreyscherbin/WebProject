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
<fmt:message key="admin_home.title" var="title_message" />
<fmt:message key="admin_home.manage_users" var="manage_users_message" />
<fmt:message key="admin_home.name" var="name_message" />
<fmt:message key="admin_home.email" var="email_message" />
<fmt:message key="admin_home.register_date" var="register_date_message" />
<fmt:message key="admin_home.last_login_date" var="last_login_date_message" />
<fmt:message key="admin_home.is_email_verifed" var="is_email_verifed_message" />
<fmt:message key="admin_home.is_active" var="is_active_message" />
<fmt:message key="admin_home.role" var="role_message" />
<fmt:message key="admin_home.change_role" var="change_role_message" />
<fmt:message key="admin_home.manage" var="manage_message" />

<c:set var="username" value="${fn:escapeXml(sessionScope.username)}" />
<c:set var="lang" value="${sessionScope.lang}" />
<c:set var="role" value="${sessionScope.role}" />
<html>
<head>
<title>${title_message}</title>
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
										<th scope="col" class="border-0 text-uppercase font-medium">${name_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${email_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${register_date_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${last_login_date_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${is_email_verifed_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${is_active_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${role_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${change_role_message}</th>
										<th scope="col" class="border-0 text-uppercase font-medium">${manage_message}</th>
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
														type="submit">${change_role_message}</button>
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