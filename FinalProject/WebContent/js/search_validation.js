$(function() {
	$("#btnSearch").on("click", function(e) {
		var form = $("#formSearch")[0];
		var isValid = form.checkValidity();
		if (!isValid) {
			e.preventDefault();
			e.stopPropagation();
		}
		form.classList.add('was-validated');
		return true;
	});
});