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
			if(data.status === "failure") {
				$("#username").css('border-color', 'red');
				$("#message").append(data.message);
				return;
			}
			
			window.location.replace('/Play');
		});
	});
	
});