$(document).ready(function () {
	
	var auth = null;

	// Checks if user is logged in
	$.get('SessionServlet', function (data) {
		if (data.status == "logged in") {
			auth = data.auth;
			
			$(".auth-info").append(
				'<ul class="nav navbar-nav navbar-right">' +
					'<li class="dropdown">' +
						'<a href="#" class="dropdown-toggle" id="dropdown-name" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">' +
							
						'</a>' +
						'<ul class="dropdown-menu">' +
							'<li><a class="profile-link">Profile</a></li>' +
							'<li><a href="#">Edit profile</a></li>' +
							'<li><a href="upload.html">Upload video</a></li>' +
							'<li role="separator" class="divider"></li>' +
							'<li><a href="#" id="logout">Logout</a></li>' +
						'</ul>' +
					'</li>' +
				'</ul>'
			);
			
			$("#dropdown-name").html(data.auth.username + ' <span class="caret"></span>');
			$(".profile-link").attr("href", "profile.html?username=" + data.auth.username);
		} else {
			$(".auth-info").append(
				'<ul class="nav navbar-nav navbar-right not-logged-in">' +
					'<li><a href="register.html">Register</a></li>' +
					'<li><a href="login.html">Log in</a></li>' +
				'</ul>'
			);
		}
	});
	
	// sidebar toggle
	$("#menu-toggle").click(function(e) {
	    e.preventDefault();
	    $("#wrapper").toggleClass("toggled");
	});
	
	// logs out
	$(".auth-info").delegate('#logout', 'click', function(e) {
		e.preventDefault();
		
		$.get('LogoutServlet', function() {
			console.log('logging out');
			
			window.location.replace('index.html');
		});
	});
	
	// Posting comments
	$('#comment-btn').on('click', function(e) {
		e.preventDefault();
		
		if (auth == null) {
			if (confirm("You must be logged in to leave a comment. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		var text = $('.write-comment').val();
		var id = window.location.search.slice(1).split('&')[0].split('=')[1];
		var params = {
			'text': text,
			'user_id': auth.id,
			'video_id': id
		}
		
		$.ajax({
			method: 'POST',
			url: 'CommentServlet',
			data: params,
			success: function(data) {
				$('.write-comment').val('');
				
				$('.comments').append(
						'<div class="comment">' +
							'<a href="profile.html?username=' + auth.username + '">' +
								'<img class="profile-pic-small" src="img/dude.jpg" alt="profile pic">' +
								'<span class="comment-name">' + auth.username + '</span>' +
							'</a>' +
							'<div class="comment-div">' +
								'<p class="comment-text">' + data.comment.text + '</p>' +
							'</div>' +
						'</div>'
					);
			},
			error: function() {
				alert('Error! Try Again');
			}
		});
	});
	
	
});

