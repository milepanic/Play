var eventAuth = null;

$.get('SessionServlet', function(data) {
	eventAuth = data.auth;
	setData(data);
});

function setData(data) {
	var auth = data.auth;
	var status = data.status;
	
	// checks if user is logged in in order to setup header
	if (data.status == "logged in") {
		
		$(".auth-info").append(
			'<ul class="nav navbar-nav navbar-right">' +
				'<li class="dropdown">' +
					'<a href="#" class="dropdown-toggle" id="dropdown-name" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' +
						
					'</a>' +
					'<ul class="dropdown-menu">' +
						'<li><a class="profile-link">Profile</a></li>' +
						'<li><a class="profile-link-edit" href="#">Edit profile</a></li>' +
						'<li><a href="upload.html">Upload video</a></li>' +
						'<li role="separator" class="divider"></li>' +
						'<li><a href="#" id="logout">Logout</a></li>' +
					'</ul>' +
				'</li>' +
			'</ul>'
		);
		
		data.auth.role == 'ADMIN' ?
			$('.auth-info').find('.dropdown-menu').append(
				'<li role="separator" class="divider"></li>' +
				'<li><a href="admin.html">Admin panel</a></li>'
			) : '';
		
		$("#dropdown-name").html(data.auth.username + ' <span class="caret"></span>');
		$(".profile-link").attr("href", "profile.html?id=" + data.auth.id);
		$(".profile-link-edit").attr("href", "edit-profile.html?id=" + data.auth.id);
	} else {
		$(".auth-info").append(
			'<ul class="nav navbar-nav navbar-right not-logged-in">' +
				'<li><a href="register.html">Register</a></li>' +
				'<li><a href="login.html">Log in</a></li>' +
			'</ul>'
		);
	}
	
	// logs out
	$(".auth-info").delegate('#logout', 'click', function(e) {
		e.preventDefault();
		
		$.get('LogoutServlet', function() {
			console.log('logging out');
			
			window.location.replace('index.html');
		});
	});
	
	//calls other functions that need auth and include this file
	proceed(data);
}
	
// sidebar toggle
$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});
