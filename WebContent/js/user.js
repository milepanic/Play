function proceed(data) {}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	function getUser() {
		
		var profile = $('.profile-container');
		
		$.get('UserServlet', {'id': id}, function(data) {
			
			if(data.message === "banned-user") {
				$('.profile-container').remove();
				$('.message-div').append('<h2>This user is banned</h2>');
			}
			
			if(data.message === "deleted-user") {
				$('.profile-container').remove();
				$('.message-div').append('<h2>This user is deleted</h2>');
			}

			if(data.auth !== null) {
				if(data.auth.id == id) {
					$('.profile-action').append('<a class="btn btn-default follow-btn"' +
							' href="edit-profile.html?id=' + id + '">Edit Profile</a>');
				} else {
					follows(data.auth, data.user.id);
					
					if(data.auth.role === "ADMIN") {
						if(data.user.banned) {
							$('.profile-action').append('<button id="block-user-btn" data-blocked="false"' +
							'class="btn btn-warning follow-btn">Unban</button>');
						} else {
							$('.profile-action').append('<button id="block-user-btn" data-blocked="true"' +
							'class="btn btn-danger follow-btn">Ban</button>');
						}
						$('.profile-action').append('<a href="#" class="btn btn-danger follow-btn"' +
						' id="delete-user-btn"><i class="fa fa-trash"></i></a>');
						$('.profile-action').append('<button class="btn btn-success follow-btn"' +
								' id="change-role-btn" data-role="' + data.user.role + '">Change role</button>');
						$('.profile-action').append('<a class="btn btn-default follow-btn"' +
								' href="edit-profile.html?id=' + id + '">Edit Profile</a>');
					}
				}
			}
			
			if(data.user.banned)
				$('.message-div').append('<h2>You are banned</h2>');
			
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
				
				if(data.auth === null || data.auth.id !== data.user.id && data.auth.role !== "ADMIN") {
					window.location.replace('/Play');
					return;
				}
				
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
				
				var auth = data.auth;
				
				var data = {
					page: "follows",
					userId: id
				}
				
				$.get('FollowServlet', data, function(data) {
					console.log(data);
					for(i in data.users) {
						
						followsUsers(auth, data.users[i].id);
						
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
										'<div data-div="' + data.users[i].id + '"></div>' +
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
	
	function followsUsers(auth, id) {
		
		if(auth.id === id) return;
		
		var data = {
			page: "single",
			follower_id: auth.id,
			userId: id
		}
		
		$.get('FollowServlet', data, function(data) {
			if(data.follow) {
				$('[data-div="' + id + '"]').append('<button class="btn btn-default user-follow">Following</button>');
			} else {
				$('[data-div="' + id + '"]').append('<button class="btn btn-primary user-follow">+ Follow</button>');
			}
		});
	}
	
	function follows(auth, userId) {
		
		//follow/following btn or edit video
		if(auth.id == userId) return;
		
		var data = {
			page: "single",
			follower_id: auth.id,
			userId: userId
		}
		
		$.get('FollowServlet', data, function(data) {
			
			if(data.follow)
				$('.profile-action').prepend('<button class="btn btn-default follow-btn" id="follow">Following</button>');
			else
				$('.profile-action').prepend('<button class="btn btn-primary follow-btn" id="follow">+ Follow</button>');
		});
	}
	
	$('.profile-action').delegate('#follow', 'click', function (e) {
		e.preventDefault();
		
		var follow = $('#follow');
		
		followUser(eventAuth.id, id, follow);
	});
	
	$('.follows').delegate('.user-follow', 'click', function(e) {
		e.preventDefault();
		
		var id = $(this).parent().data('div');
		var follow = $(this);
		
		followUser(eventAuth.id, id, follow);
	});
	
	function followUser(followerId, userId, follow) {
		
		if (eventAuth == null) {
			if (confirm("You must be logged in to follow a user. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		if (eventAuth.id === id) return;
		
		if (follow.hasClass('btn-primary')) {
			follow.removeClass('btn-primary');
			follow.addClass('btn-default');
			follow.text('Following');
		} else {
			follow.removeClass('btn-default');
			follow.addClass('btn-primary');
			follow.text('+ Follow');
		}
		
		var data = {
				follower_id: eventAuth.id,
				user_id: userId
			}
			
			$.post('FollowServlet', data);
	}
	
	$(".profile-action").delegate('#block-user-btn', 'click', function(e) {
		e.preventDefault();
		
		if($(this).hasClass('btn-danger'))
			if(!confirm('Do you want to ban this user?')) return;
		if($(this).hasClass('btn-warning'))
			if(!confirm('Do you want to unban this user?')) return;
		
		var blocked = $(this).data('blocked');
		
		var data = {
			action: "ban",
			id: id,
			banned: blocked
		}
		
		$.post('AdminServlet', data, function(data) {
			location.reload();
		});
	});
	
	$(".profile-action").delegate('#change-role-btn', 'click', function(e) {
		e.preventDefault();
		
		if(!confirm('Do you want to change role of this user?')) return; 
		
		var role = $(this).data('role');
		
		var data = {
			action: "role",
			id: id,
			role: role
		}
		
		$.post('AdminServlet', data, function(data) {
			location.reload();
		});
	});
	
	$(".profile-action").delegate('#delete-user-btn', 'click', function(e) {
		e.preventDefault();
		
		if(!confirm('Do you want to delete this user?')) return; 
		
		var data = {
			action: "delete",
			id: id
		}
		
		$.post('AdminServlet', data, function(data) {
			location.reload();
		});
	});
	
	getUser();
});