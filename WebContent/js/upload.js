function proceed(data) {
	
	if(data.status === "unauthenticated") {
		window.location.replace('/Play');
		return;
	}
}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	$.validator.setDefaults({
		errorClass: 'text-danger',
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		}
	});
	
	$.validator.addMethod('nameLength', function(value, element) {
		return value.length <= 50;
	}, 'Username can\'t be longer than 50 characters');
	
	$('#upload-form').validate({
		rules: {
			url: {
				required: true,
				url2: true
			},
			name: {
				required: true,
				nameLength: true
			}
		},
		messages: {
			url: {
				required: 'Please enter Youtube URL'
			},
			name: {
				required: 'Name can\'t be empty',
			}
		}
	});
	
	$("#submit").click(function(e) {
		if(!$('#upload-form').valid()) return;
		
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