$(function() {
	$("#content").bind('keyup', function(e) {					
		validateNewPostDescription()				
		return true;
	});
});

$(function() {
	$("#btnCreatePost").on("click", function(e) {

		var form = $("#formCreatePost")[0];
		validateNewPostDescription()
		var isValid = form.checkValidity();
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}		
		form.classList.add('was-validated');
		return true;
	});
});

function validateEditForm(x) {

	var form = document.getElementById("formEditPost" + x);
	validateEditPostDescription(x)
	var isValid = form.checkValidity();
	if (!isValid) {
		form.classList.add('was-validated');
		return false;
	}	
	form.classList.add('was-validated');
	return true;
}

function validateNewPostDescription() {
	var content = document.getElementById('content');
	var contentRGEX = /^(?!\s*$)[a-zA-Z0-9а-яА-я\s?,.!<->;:=+/()\\"'#]{1,65535}$/i;
	
	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		content.setCustomValidity('error');
		return false;
	}
	content.setCustomValidity('');
	return true;
}

function validateEditPostDescription(x) {
	var content = document.getElementById('content' + x);	
	var contentRGEX = /^(?!\s*$)[a-zA-Z0-9а-яА-я\s?,.!<->;:=+/()\\"'#]{1,65535}$/i;
	
	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		content.setCustomValidity('error');
		return false;
	}
	content.setCustomValidity('');
	return true;
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

