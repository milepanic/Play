function proceed(data) {
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	if(data.auth && data.auth.id == id)
		$('.profile-action').append('<a class="btn btn-default follow-btn"' +
				' href="edit-profile.html?id=' + id + '">Edit Profile</a>');
	else
		$('.profile-action').append('<button class="btn btn-primary follow-btn">+ Follow</button>');
}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	function getUser() {
		
		var id = window.location.search.slice(1).split('&')[0].split('=')[1];
		var profile = $('.profile-container');
		
		$.get('UserServlet', {'id': id}, function(data) {
			
			$(document).attr("title", data.user.username + " - Play");
			
			$('.profile-videos').attr('href', "profile.html?id=" + data.user.id);
			$('.profile-about').attr('href', "about.html?id=" + data.user.id);
			
			profile.find('.profile-header-name').text(data.user.username);
			profile.find('.profile-description').text(data.user.description);
			
			if (window.location.pathname == '/Play/profile.html') {
				for(i in data.videos) {
					
					$('.videos-box').append(
						'<div class="video-box">' +
							'<div class="thumbnail">' +
								'<a href="single.html?id=' + data.videos[i].id + '">' +
									'<img class="video-img" src="' + data.videos[i].thumbnail + '">' +
								'</a>' +
							'</div>' +
							'<div class="info-box">' +
								'<a href="single.html?id=' + data.videos[i].id + '" class="video-name">' +
									data.videos[i].name +
								'</a><br>' +
								'<a href="profile.html?username=' + data.videos[i].user.username +'" class="channel-name">' + data.videos[i].user.username + '</a><br>' +
								'<span class="views"><i class="fa fa-eye"></i> ' + data.videos[i].views + ' Views </span>&nbsp;' +
								'<span class="date"><i class="fa fa-clock-o"></i> ' + data.videos[i].createdAt + '</span>' +
							'</div>' +
						'</div>'
					);
				}
			} else if (window.location.pathname == '/Play/about.html') {
				
				$('.upload-h3').text('About ' + data.user.username);
				
				$('.about-first-name').text(data.user.firstName);
				$('.about-last-name').text(data.user.lastName);
				$('.about-email').text(data.user.email);
				$('.about-date').text(data.user.registeredAt);
				$('.about-type').text(data.user.role);
				
			} else if (window.location.pathname == '/Play/edit-profile.html') {
				
				$('#username').val(data.user.username);
				$('#firstname').val(data.user.firstName);
				$('#lastname').val(data.user.lastName);
				$('#email').val(data.user.email);
				$('#description').val(data.user.description);
				
				
				
				$("#submit").on('click', function (e) {
					e.preventDefault();
					
					var data = {
						username: $('#username').val(),
						firstname: $('#firstname').val(),
						lastname: $('#lastname').val(),
						email: $('#email').val(),
						description: $('#description').val()
					}
					
					$.post('UserServlet', data, function() {
						window.location.replace('about.html?id=' + id);
					});
				})
			}
		});
	}
	
	getUser();
});