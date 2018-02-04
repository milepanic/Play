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
			$('.profile-follows').attr('href', "follows.html?id=" + data.user.id);
			
			profile.find('.profile-header-name').text(data.user.username);
			profile.find('.profile-description').text(data.user.description);

			$('.profile-info').append(
				'<p>' + data.count[0] + '<br> <span>Views</span></p>' +
				'<p>' + data.count[1] + '<br> <span>Followers</span></p>' +
				'<p>' + data.count[2] + '<br> <span>Videos</span></p>'
			);
			
			if (window.location.pathname == '/Play/profile.html') {
				
				function getVideos() {
					
					$('.videos-box').empty();
					
					var order = $('#comments-order').find(":selected").text();
					
					var data2 = {
						id: id,
						page: "user",
						order: order
					}
					
					$.get('VideoServlet', data2, function(data) {
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
					});
				}
				
				getVideos();
				
				$('.order-val').on('click', function() {
					getVideos();
				});
				
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
			} else if (window.location.pathname == '/Play/follows.html') {
				
				var data = {
					page: "follows",
					userId: id
				}
				
				$.get('FollowServlet', data, function(data) {
					console.log(data);
					for(i in data.users) {
						$('.follows').append(
							'<div class="col-md-4">' +
								'<div class="user-box">' +
									'<div class="user-left">' +
										'<a href="profile.html?id=' + data.users[i].id + '"><img class="profile-pic-medium"' +
										'src="https://www.poeticous.com/system/poets/photos/000/026/357/large/jm-flower-crown.jpg?1481219945"></a>' +
									'</div>' +
									'<div class="user-right col-md-7">' +
										'<a class="user-username" href="profile.html?id=' + data.users[i].id + '">' + data.users[i].username + '</a>' +
										'<p class="user-followers"><span data-id="' + [i] + '" class="user-number"></span> Followers</p>' +
										'<button class="btn btn-primary user-follow">+ Follow</button>' +
									'</div>' +
									'<div class="clearfix"></div>' +
								'</div>' +
							'</div>'
						);
					}
					
					for(j in data.count) {
						$('.user-followers').find('[data-id="' + j + '"]').append(data.count[j]);
					}
				});
			}
		});
	}
	
	getUser();
});