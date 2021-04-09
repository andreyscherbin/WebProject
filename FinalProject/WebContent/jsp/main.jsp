<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page session="true"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="pagecontent" />
<html>
<head>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/styles.css" />
<title><fmt:message key="label.title" /></title>

</head>
<body>
	<h3>
		<fmt:message key="label.welcome" />
	</h3>
	<hr />
	${user}, hello!
	<hr />
	<a href="controller?command=logout">Logout</a>

	<form name="ViewUserByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user_by_id" /> <input
			type="text" name="id" value="" /> <input type="submit"
			value="ViewUserById" />${wrongInput}
	</form>

	<form name="SortUserByIdForm" method="POST" action="controller">
		<input type="hidden" name="command" value="sort_user_by_id" /> <input
			type="submit" value="SortUserById" />
	</form>

	<form name="ViewUserForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user" /> <input
			type="submit" value="ViewUser" />
	</form>

	<form name="ViewUserByUserNameForm" method="POST" action="controller">
		<input type="hidden" name="command" value="view_user_by_username" />
		<input type="text" name="user_name" value="" /> <input type="submit"
			value="ViewUserByUserName" />
	</form>

	<img src="${pageContext.request.contextPath}/image/anon.jpg"
		style="width: 100%; height: auto;">

	<div class="container">
		<h1>Characters Search</h1>
		<div id="searchWrapper">
			<input type="text" name="searchBar" id="searchBar"
				placeholder="search for a character" />
		</div>
		<ul id="charactersList"></ul>
	</div>



	<script type="text/javascript">
    const charactersList = document.getElementById('charactersList');
    const searchBar = document.getElementById('searchBar');
    let hpCharacters = [];

    searchBar.addEventListener('keyup', (e) => {
        const searchString = e.target.value.toLowerCase();

        const filteredCharacters = hpCharacters.filter((character) => {
            return (
              //  character.name.toLowerCase().includes(searchString) ||
              //  character.house.toLowerCase().includes(searchString)
                    character.toLowerCase().includes(searchString)
            );
        });
        displayCharacters(filteredCharacters);
    });

    const loadCharacters = async () => {
        try {
             const res = await fetch("controller?command=view_topic");
         // const res = await fetch('https://hp-api.herokuapp.com/api/characters');
            hpCharacters = await res.json();           
        } catch (err) {
            console.error(err);
        }
    };

    const displayCharacters = (characters) => {
        const htmlString = characters
            .map((character) => {
                return '<p>' + character + '</p>';
            })
            .join('');
        charactersList.innerHTML = htmlString;
    };

    loadCharacters();
</script>
</body>
</html>