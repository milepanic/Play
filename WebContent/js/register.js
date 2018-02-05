$(document).ready(function () {
	
	$("#btn-register").click(function (e) {
		e.preventDefault();
		
		var username = $("#username").val();
		var password = $("#password").val();
		var firstName = $("#firstname").val();
		var lastName = $("#lastname").val();
		var email = $("#email").val();
		var description = $("#description").val();
		
		var data = {
			'username': username,
			'password': password,
			'firstname': firstName,
			'lastname': lastName,
			'email': email,
			'descrtiption': description
		}
		
		$.post('RegisterServlet', data, function(data) {
			console.log(data);
			if(data.status === "username-fail") {
				$("#username").css('border-color', 'red');
				$("#username-message").append(data.message);
				return;
			}
			
			if(data.status === "email-fail") {
				$("#email").css('border-color', 'red');
				$("#email-message").append(data.message);
				return;
			}
 			
			//window.location.replace('/Play');
		});
	});
	
});