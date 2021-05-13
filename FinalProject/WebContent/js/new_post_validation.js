$(function() {
	$("#btnCreatePost").on("click", function(e) {

		var form = $("#formCreatePost")[0];
		var content = document.getElementById('content');
		content.setCustomValidity('');
		var isValid = form.checkValidity();
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}
		if (!validateNewPostDescription()) {
			e.preventDefault();
			e.stopPropagation();
		}
		form.classList.add('was-validated');
		return true;
	});
});

function validateNewPostDescription() {
	var content = document.getElementById('content');
	var contentRGEX = /^[a-zA-Z0-9а-яА-я\s?,.!<->;:=+/()\\"'#]+$/i;

	if (trimfield(content.value) == '') {
		content.setCustomValidity('error');
		return false;
	}

	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		content.setCustomValidity('error');
		return false;
	}
	content.setCustomValidity('');
	return true;
}

function trimfield(str) {
	return str.replace(/^\s+|\s+$/g, '');
}

function validateEditForm(x) {

	var form = document.getElementById("formEditPost" + x);
	var content = document.getElementById('content' + x);
	content.setCustomValidity('');
	var isValid = form.checkValidity();
	if (!isValid) {
		form.classList.add('was-validated');
		return false;
	}
	if (!validateEditPostDescription(x)) {
		form.classList.add('was-validated');
		return false;
	}
	form.classList.add('was-validated');
	return true;
}

function validateEditPostDescription(x) {
	var content = document.getElementById('content' + x);
	var contentRGEX = /^[a-zA-Z0-9а-яА-я\s?,.!<->;:=+/()\\"'#]+$/i;

	if (trimfield(content.value) == '') {
		content.setCustomValidity('error');
		return false;
	}

	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		content.setCustomValidity('error');
		return false;
	}
	content.setCustomValidity('');
	return true;
}

function trimfield(str) {
	return str.replace(/^\s+|\s+$/g, '');
}

function showEdit(x) {
	document.getElementById("editForm" + x).style.display = "block";
	document.getElementById("editLink" + x).style.display = "none";
	document.getElementById("content" + x).focus;
	var edit = document.getElementById("edit" + x).innerHTML.trim();
	document.getElementById("content" + x).innerHTML = edit;
}

function closeEdit(x) {
	document.getElementById("editForm" + x).style.display = "none";
	document.getElementById("editLink" + x).style.display = "block";
}

