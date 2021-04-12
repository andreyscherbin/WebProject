<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Section</title>
<%-- <jsp:include page="fragments/head.jspf"></jsp:include> --%>
<!--what's the difference?-->
<%@ include file="fragments/head.jspf"%>
</head>
<body>

    <%-- <jsp:include page="fragments/navbar.jspf"></jsp:include> --%>
	<!--   what is the difference? -->
	<%@ include file="fragments/navbar.jspf"%>

    <div class="container">

        <div th:replace="fragments/messages :: messages"></div>

        <div class="row">
            <div class="col s12">
                <div class="row">
                    <div class="col s12">
                        <h3 th:text="#{page.section.topics.in.section} + ' ' + ${section.name}">Topics in section</h3>
                        <p th:text="${section.description}"></p>
                        <a sec:authorize="hasAnyAuthority('ADMIN', 'HEAD_ADMIN')" th:href="@{/section/delete/} + ${section.id}"
                            th:text="#{page.section.delete.section}" class="waves-effect waves-light btn"></a>
                        <a th:href="@{/topic/new}" th:text="#{page.section.new.topic}" class="right waves-effect waves-light btn"></a>
                    </div>
                </div>
                <div class="divider"></div>
                <div class="row" th:each="topic : ${topics}">
                    <div class="col s12">
                        <div class="section">
                            <a th:href="@{/topic/} + ${topic.id}" th:text="${topic.user.username} + ': ' + ${topic.title}"></a>
                            <span class="right" th:text="${topic.creationDate} ? ${#calendars.format(topic.creationDate, 'dd MMMM yyyy HH:mm')}"></span> <span class="truncate"
                                th:text="${topic.content}"></span>
                        </div>
                        <div class="divider"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>

    <footer th:replace="fragments/footer :: footer"></footer>
</body>
</html>