function validateEditForm(x) {
	var content = document.getElementById('content' + x);
	var contentRGEX = /^[a-z0-9а-я\s?,.!<->;:=+/()\\"'#]+$/i;

	if (trimfield(content.value) == '') {
		alert("Empty field, pls provide more information!");
		content.focus();
		return false;
	}

	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		alert('Please enter a valid post content');
		return false;
	}
	return true;
}

function validateReplyForm() {
	var content = document.getElementById('content');
	var contentRGEX = /^[a-z0-9а-я\s?,.!<->;:=+/()\\"'#]+$/i;

	if (trimfield(content.value) == '') {
		alert("Empty field, pls provide more information!");
		content.focus();
		return false;
	}

	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		alert('Please enter a valid post content');
		return false;
	}
	return true;
}

function trimfield(str) {
	return str.replace(/^\s+|\s+$/g, '');
}

