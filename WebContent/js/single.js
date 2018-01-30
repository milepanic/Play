function proceed(data) {
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	follows(data.auth, id);
	voted(data.auth, id);
}

//Follow btn, follow user
function follows(auth, id) {
	
	//follow/following btn or edit video
	if(auth.id == id) {
		$('.follow-edit').append('<a href="edit-video.html?id=' + id + '"' +
				'class="btn btn-default edit-btn">Edit Video</a>');
	} else {
		var data = {
			page: "single",
			follower_id: auth.id,
			user_id: id
		}
		
		$.get('FollowServlet', data, function(data) {
			
			if(data.follow)
				$('.follow-edit').append('<button class="btn btn-default follow-btn">Following</button>');
			else
				$('.follow-edit').append('<button class="btn btn-primary follow-btn">+ Follow</button>');
		});
		
		
	}
		
	// Follow - Unfollow
	$('.follow-edit').delegate('.follow-btn', 'click', function (e) {
		e.preventDefault();
		
		if (auth == null) {
			if (confirm("You must be logged in to follow a user. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		if (auth.id == id)
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
			follower_id: auth.id,
			user_id: id
		}
		
		$.post('FollowServlet', data);
	});
}

function voted(auth, id) {
	
	var data = {
		userId: auth.id,
		videoId: id
	}
	
	$.get('VoteServlet', data, function(data) {
		if(data.vote != null)
			if(data.vote.like)
				$("#upvote").addClass('voted');
			else
				$("#downvote").addClass('voted');
	});
}

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
			
			if(data.video.voteable) {
				$('.votes').removeClass('hidden');
			} else
				$('.votes').remove();
			
			$('.video-single-comments').removeClass('hidden');
			if(data.video.commentable) {
				
			} else {
				$('.video-single-comments').find('h5').text('Comments are disabled');
				$('.comments-hide').remove();
			}
		});
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
						'<span class="comment-date">' + data.comments[i].createdAt + '</span>' +
						'<div class="comment-div">' +
							'<p class="comment-text">' + data.comments[i].text + '</p>' +
						'</div>' +
						'<div class="comment-votes">' +
							'<a href="#">' +
	        					'<i class="fa fa-thumbs-up"></i> 105' +
	        				'</a>' +
	        				'<a href="#">' +
	        					'<i class="fa fa-thumbs-down"></i> 13' +
	        				'</a>' +
						'</div>' +
					'</div>'
				);
				
			}
		});
	}
	
	//Like/dislike video
	$('.vote').on('click', function(e) {
		e.preventDefault();
		
		//ako korisnik nije ulogovan
		if (eventAuth == null) {
			if (confirm("You must be logged in to vote. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		// gleda da li je upvote ili downvote
		var type = false;
		
		if($(this).data('type') == 'upvote')
			type = true;
		
		// postavlja odgovarajuci izgled
		var span = $(this).find('span');
		
		if(type) {
			if($('#downvote').hasClass('voted'))
				$('#downvote').removeClass('voted');
		} else {
			if($('#upvote').hasClass('voted'))
				$('#upvote').removeClass('voted');
		}
		
		span.toggleClass('voted');
		
		// salje serveru podatke
		var data = {
			userId: eventAuth.id,
			videoId: id,
			like: type
		}
		
		$.post('VoteServlet', data);
		
	});
	
	//Commenting
	$('#comment-btn').on('click', function(e) {
		e.preventDefault();
		
		if (eventAuth == null) {
			if (confirm("You must be logged in to leave a comment. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		var text = $('.write-comment').val();
		var id = window.location.search.slice(1).split('&')[0].split('=')[1];
		var params = {
			'text': text,
			'user_id': eventAuth.id,
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
							'<a href="profile.html?username=' + eventAuth.username + '">' +
								'<img class="profile-pic-small" src="img/dude.jpg" alt="profile pic">' +
								'<span class="comment-name">' + eventAuth.username + '</span>' +
							'</a>' +
							'<span class="comment-date">' + data.comment.createdAt + '</span>' +
							'<div class="comment-div">' +
								'<p class="comment-text">' + data.comment.text + '</p>' +
							'</div>' +
							'<div class="comment-votes">' +
								'<a href="#">' +
                					'<i class="fa fa-thumbs-up"></i> 105' +
                				'</a>' +
                				'<a href="#">' +
                					'<i class="fa fa-thumbs-down"></i> 13' +
                				'</a>' +
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