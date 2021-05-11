$(function() {
	$("#btnCreateTopic").on("click", function(e) {

		var form = $("#formCreateTopic")[0];
		var isValid = form.checkValidity();
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}
		if (!validateDescription()) {
			e.preventDefault();
			e.stopPropagation();
		}
		form.classList.add('was-validated');
		return true;
	});
});

function validateDescription() {
	var content = document.getElementById('inputDescription');
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