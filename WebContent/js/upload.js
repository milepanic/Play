$(document).ready(function() {
	
	$.get('SessionServlet', function (data) {
		$(".user-hidden").val(data.auth.id);
	})
});