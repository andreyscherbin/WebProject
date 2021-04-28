$(function() {
	$("#btnSignIn").on("click", function(e) {
		var form = $("#formSignIn")[0];
		var isValid = form.checkValidity();
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}
		form.classList.add('was-validated');
		return true;
	});
});