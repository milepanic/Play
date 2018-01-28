$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	$.get('SessionServlet', function (data) {
		$(".user-hidden").val(data.auth.id);
	})
});