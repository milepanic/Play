$(document).ready(function () {
	
	$("#btn-login").click(function (e) {
		e.preventDefault();
		
		var username = $("#username").val();
		var password = $("#password").val();
		
		var data = {
			'username': username,
			'password': password
		}
		
		$.ajax({
			url: 'LoginServlet',
			method: 'POST',
			data: data,
			success: function(data) {
				console.log(data);
				
				if (data.status == 'success') {
					window.location.replace('index.html');
				}
			},
			error: function() {
				
			}
		});
	});
	
});