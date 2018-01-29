function proceed() {}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	$("#submit").click(function(e) {
		e.preventDefault();
		
		var comm = 0;
		if($('#commentable').is(':checked')) {
			comm = 1;
		}
		
		var vote = 0;
		if($('#voteable').is(':checked')) {
			vote = 1;
		}
		
		var data = {
			url: $("#url").val(),
			name: $("#name").val(),
			description: $("#description").val(),
			visibility: $('input[name=visibility]:checked', '#upload-form').val(),
			commentable: comm,
			voteable: vote,
			userId: eventAuth.id
		}
		
		$.post('VideoServlet', data, function(data) {
			window.location.replace('single.html?id=' + data.id);
		});
	});
	
	
});