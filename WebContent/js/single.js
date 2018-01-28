$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	var userId = 0;
	
	function getVideo() {
		
		var video_box = $('.video-single-box');
		
		$.get('VideoServlet', {'id': id}, function(data) {
			userId = data.video.user.id;
			
			$(document).attr('title', data.video.name);
			
			video_box.find('iframe').attr('src', data.video.url + '?autoplay=1');
			video_box.find('.video-single-title').text(data.video.name);
			video_box.find('.views').text(data.video.views);
			video_box.find('.date').text(data.video.createdAt);
			video_box.find('.profile-url').attr('href', 'profile.html?username=' + data.video.user.username);
			video_box.find('.profile-name').text(data.video.user.username);
			video_box.find('.video-single-description > p').text(data.video.description);
		});
	}
	
	// checks if user follows the author
	function follows() {
		
	}
	
	function getComments() {
		
		$.get('CommentServlet', {'id': id}, function(data) {
			
			for(i in data.comments) {
				
				$('.comments').append(
					'<div class="comment">' +
						'<a href="profile.html?username=' + data.comments[i].user.username + '">' +
							'<img class="profile-pic-small" src="img/dude.jpg" alt="profile pic">' +
							'<span class="comment-name">' + data.comments[i].user.username + '</span>' +
						'</a>' +
						'<div class="comment-div">' +
							'<p class="comment-text">' + data.comments[i].text + '</p>' +
						'</div>' +
					'</div>'
				);
				
			}
		});
	}
	
	$('.follow-edit').append(
		'<button class="btn btn-primary follow-btn">+ Follow</button>'
	);
	
	// Follow - Unfollow
	$('.follow-edit').delegate('.follow-btn', 'click', function (e) {
		e.preventDefault();
		
		if (auth == null) {
			if (confirm("You must be logged in to follow a user. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		if (auth.id == userId)
			return;
		
		if ($('.follow-btn').hasClass('btn-primary')) {
			$('.follow-btn').removeClass('btn-primary');
			$('.follow-btn').addClass('btn-default');
			$('.follow-btn').text('Following');
		} else {
			$('.follow-btn').removeClass('btn-default');
			$('.follow-btn').addClass('btn-primary');
			$('.follow-btn').text('+ Follow');
		}
		
		var data = {
			page: "single",
			follower_id: auth.id,
			user_id: userId
		}
		
		$.post('FollowServlet', data, function () {
			
		})
	});
	
	//Commenting
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
	
	getVideo();
	getComments();
});