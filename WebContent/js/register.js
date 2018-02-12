$(document).ready(function () {
	
	$.validator.setDefaults({
		errorClass: 'text-danger',
		highlight: function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},
		unhighlight: function(element) {
			$(element).closest('.form-group').removeClass('has-error');
		}
	});
	
	$.validator.addMethod('usernameLength', function(value, element) {
		return value.length <= 10;
	}, 'Username can\'t be longer than 10 characters');
	
	$('#register-form').validate({
		rules: {
			username: {
				required: true,
				usernameLength: true,
				remote: 'ValidationServlet',
			},
			password: "required",
			email: {
				required: true,
				email: true,
				remote: 'ValidationServlet',
			}
		},
		messages: {
			username: {
				remote: $.validator.format('{0} is already taken')
			},
			email: {
				required: 'Please enter email adress',
				email: 'Please enter valid email adress',
				remote: $.validator.format('{0} already exists. <a href="login.html">Log in</a> if it is your email')
			}
		}
	});
	
});
