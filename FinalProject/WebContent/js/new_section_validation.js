$(function() {
	$("#inputDescription").bind('keyup', function(e) {
						
		validateDescription()				
		return true;
	});
});


$(function() {
	$("#btnCreateSection").on("click", function(e) {

		var form = $("#formCreateSection")[0];
		validateDescription()
		var isValid = form.checkValidity();				
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}		
		form.classList.add('was-validated');
		return true;
	});
});

function validateDescription() {
	var content = document.getElementById('inputDescription');
	var contentRGEX = /^(?!\s*$)[a-zA-Z0-9а-яА-я\s?,.!-<>;:=+/()\\"'#%&«»]{1,100}$/i;	

	var contentResult = contentRGEX.test(content.value);
	if (contentResult == false) {
		content.setCustomValidity('error');
		return false;
	}
	content.setCustomValidity('');
	return true;
}