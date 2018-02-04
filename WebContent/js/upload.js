function proceed(data) {
	
	if(data.status === "unauthenticated") {
		window.location.replace('/Play');
		return;
	}
}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	$("#submit").click(function(e) {
		e.preventDefault();
		
		var comm = false;
		if($('#commentable').is(':checked')) {
			comm = true;
		}
		
		var vote = false;
		if($('#voteable').is(':checked')) {
			vote = true;
		}
		
		var data = {
			url: $("#url").val(),
			name: $("#name").val(),
			description: $("#description").val(),
			visibility: $('input[name=visibility]:checked', '#upload-form').val(),
			commentable: comm,
			voteable: vote,
			userId: eventAuth.id,
			type: "upload"
		}
		
		$.post('VideoServlet', data, function(data) {
			window.location.replace('single.html?id=' + data.id);
		});
	});
	
	
});