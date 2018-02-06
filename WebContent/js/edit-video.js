function proceed() {}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var data = {
		id: id,
		page: "single"
	}
	
	$.get('VideoServlet', data, function(data) {

		if(data.auth === null || data.auth.id !== data.video.user.id && data.auth.role !== "ADMIN") {
			window.location.replace('/Play');
			return;
		}
		
		var name = data.video.name;
		
		$('#edit-h3').append('Edit Video "' + data.video.name + '"');
		$('#delete-h3').append('Delete Video "' + data.video.name + '"');
		
		$("#description").val(data.video.description);
		
		switch(data.video.visibility) {
			case "PUBLIC":
				$("#radio-public").prop('checked', true);
				break;
			case "UNLISTED":
				$("#radio-unlisted").prop('checked', true);
				break;
			case "PRIVATE":
				$("#radio-private").prop('checked', true);
				break;
		}
		
		if(data.video.commentable == true)
			$("#commentable").prop('checked', true);
		
		if(data.video.voteable == true)
			$("#voteable").prop('checked', true);
		
		if(data.auth.role === "ADMIN") {
			
			if(data.video.blocked) {
				$('#block-h3').append('Unblock this video');
				$('#block-form').find('button').append('Unblock');
				$('#block').data('blocked', false);
			} else {
				$('#block-h3').append('Block this video');
				$('#block-form').find('button').append('Block');
				$('#block').data('blocked', true);
			}
			$('#block-div').removeAttr('hidden');
		}
	});
	
	$("#submit").on('click', function(e) {
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
			id: id,
			description: $("#description").val(),
			visibility: $('input[name=visibility]:checked', '#edit-form').val(),
			commentable: comm,
			voteable: vote,
			type: "edit"
		}
		
		$.post('VideoServlet', data, function(data) {
			window.location.replace('single.html?id=' + id);
		});
	});
	
	$('#delete').on('click', function(e) {
		e.preventDefault();
		
		var data = {
			id: id,
			role: eventAuth.role,
			type: "delete"
		}
		
		$.post('VideoServlet', data, function(data) {
			window.location.replace('/Play');
		});
	});
	
	$('#block').on('click', function(e) {
		e.preventDefault();
		
		var blocked = $(this).data('blocked');
		
		var data = {
			id: id,
			blocked: blocked,
			type: "block"
		}
		
		$.post('VideoServlet', data, function(data) {
			window.location.replace('/Play');
		});
	});
	
});