function proceed(data) {
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	follows(data.auth, id);
	voted(data.auth, id);
}

//Follow btn, follow user
function follows(auth, videoId) {
	
	// OVO NE RADI, NE STIGNE DA UZME ID
	var userId = $('.video-single-profile').data('id');
	
	//follow/following btn or edit video
	if(auth.id == 2) {
		$('.follow-edit').append('<a href="edit-video.html?id=' + videoId + '"' +
				'class="btn btn-default edit-btn">Edit Video</a>');
	} else {
		var data = {
			page: "single",
			follower_id: auth.id,
			userId: 2
		}
		
		$.get('FollowServlet', data, function(data) {
			
			if(data.follow)
				$('.follow-edit').append('<button class="btn btn-default follow-btn">Following</button>');
			else
				$('.follow-edit').append('<button class="btn btn-primary follow-btn">+ Follow</button>');
		});
		
	}
	
	
}

function voted(auth, id) {
	
	var data = {
		action: "check-vote",
		userId: auth.id,
		voteableId: id,
		voteableType: "video"
	}
	
	$.get('VoteServlet', data, function(data) {
		//console.log(data);
		if(data.vote != null)
			if(data.vote.vote)
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
		
		var data = {
			id: id,
			page: "single"
		}
		
		$.get('VideoServlet', data, function(data) {
			userId = data.video.user.id;
			
			$(document).attr('title', data.video.name);
			
			$('.profile-followers').append(data.count + ' Followers');
			
			video_box.find('iframe').attr('src', data.video.url + '?autoplay=1');
			video_box.find('.video-single-title').text(data.video.name);
			video_box.find('.views').text(data.video.views);
			video_box.find('.date').text(data.video.createdAt);
			video_box.find('.video-single-profile').attr('data-id', data.video.user.id);
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
		
		$('.comments').empty();
		
		var order = $('#comments-order').find(":selected").text();
		
		var data = {
			id: id,
			order: order
		}
		
		$.get('CommentServlet', data, function(data) {
			//console.log(data);
			$('#comment-number').append(data.count);
			
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
						'<div class="comment-votes" data-type="comment" data-id="' + data.comments[i].id + '">' +
							'<a href="#" class="vote" data-vote="downvote">' +
		    					'<span id="down">' +
									'<i class="fa fa-thumbs-down"></i> <span class="comment-number">0</span>' +
								'</span>' +
		    				'</a>' +
							'<a href="#" class="vote" data-vote="upvote">' +
	        					'<span id="up">' +
									'<i class="fa fa-thumbs-up"></i> <span class="comment-number">0</span>' +
								'</span>' +
	        				'</a>' +
						'</div>' +
					'</div>'
				);
				
			}
		});
	}
	
	$('.order-val').on('click', function() {
		getComments();
	});
	
	// Display number of video upvotes/downvotes
	function getVotes() {
		
		var data = {
			action: "get-votes",
			voteableType: "video",
			voteableId: id
		}
		
		$.get('VoteServlet', data, function(data) {
			$("#upvote").find('.number').empty().append(data.upvotes);
			$("#downvote").find('.number').empty().append(data.downvotes);
		});
	}
	/*
	function follows() {
		
		var userId = $('.video-single-profile').data('id');
		alert(userId);
		//follow/following btn or edit video
		if(1 == userId) {
			$('.follow-edit').append('<a href="edit-video.html?id=' + id + '"' +
					'class="btn btn-default edit-btn">Edit Video</a>');
		} else {
			var data = {
				page: "single",
				follower_id: 1,
				user_id: userId
			}
			
			$.get('FollowServlet', data, function(data) {
				
				if(data.follow)
					$('.follow-edit').append('<button class="btn btn-default follow-btn">Following</button>');
				else
					$('.follow-edit').append('<button class="btn btn-primary follow-btn">+ Follow</button>');
			});
			
		}
	}
	
	// Follow - Unfollow
	$('.follow-edit').delegate('.follow-btn', 'click', function (e) {
		e.preventDefault();
		
		if (eventAuth == null) {
			if (confirm("You must be logged in to follow a user. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		var userId = $('.video-single-profile').data('id');
		
		if (eventAuth.id == userId)
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
			follower_id: eventAuth.id,
			user_id: userId
		}
		
		$.post('FollowServlet', data);
	});
	*/
	// Like/dislike komentara
	$('.comments').delegate('.vote', 'click', function(e) {
		
		e.preventDefault();
		
		//ako korisnik nije ulogovan
		if (eventAuth == null) {
			if (confirm("You must be logged in to vote. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		// data-id komentara
		var id = $(this).parent().data('id');
		
		// gleda da li je upvote ili downvote
		var vote = false;
		
		if($(this).data('vote') == 'upvote')
			vote = true;
		
		// postavlja izgled
		var span = $(this).find('span');
		var number = parseInt(span.find('.comment-number').text());
		// && $('#down').parent().parent().data('id') == id
		if(vote) {
			if($('#down').hasClass('voted')) {
				$('#down').removeClass('voted');
				var nmb = $('#down').find('.comment-number').text();
				var otherNumber = parseInt(nmb);
				otherNumber--;
				$('#down').find('.comment-number').empty().append(otherNumber);
			}
			
			if($('#up').hasClass('voted'))
				number--;
			else
				number++;
			
			span.find('.comment-number').empty().append(number);
		// downvote
		} else {
			if($('#up').hasClass('voted')) {
				$('#up').removeClass('voted');
				var nmb = $('#up').find('.comment-number').text();
				var otherNumber = parseInt(nmb);
				otherNumber--;
				$('#up').find('.comment-number').empty().append(otherNumber);
			}
			
			if($('#down').hasClass('voted'))
				number--;
			else
				number++;
			
			span.find('.comment-number').empty().append(number);
		}
		
		span.toggleClass('voted');
		
		// da li je komentar ili video
		var voteableType = $(this).parent().data('type');
		
		// salje serveru podatke
		var data = {
			userId: eventAuth.id,
			voteableId: id,
			voteableType: voteableType,
			vote: vote
		}
		
		$.post('VoteServlet', data);
	});
	
	// Like/dislike videa
	$('.vote').on('click', function(e) {
		e.preventDefault();
		
		//ako korisnik nije ulogovan
		if (eventAuth == null) {
			if (confirm("You must be logged in to leave a rating. \nDo you want to log in now?"))
				window.location.replace('login.html');
			else
				return;
		}
		
		// da li je komentar ili video
		var voteableType = $(this).parent().data('type');
		
		// gleda da li je upvote ili downvote
		var vote = false;
		
		if($(this).data('vote') == 'upvote')
			vote = true;
		
		// postavlja odgovarajuci izgled
		var span = $(this).find('span');
		var number = parseInt(span.find('.number').text());
		
		// upvote
		if(vote) {
			if($('#downvote').hasClass('voted')) {
				$('#downvote').removeClass('voted');
				var nmb = $('#downvote').find('.number').text();
				var otherNumber = parseInt(nmb);
				otherNumber--;
				$('#downvote').find('.number').empty().append(otherNumber);
			}
			
			if($('#upvote').hasClass('voted'))
				number--;
			else
				number++;
			
			span.find('.number').empty().append(number);
		// downvote
		} else {
			if($('#upvote').hasClass('voted')) {
				$('#upvote').removeClass('voted');
				var nmb = $('#upvote').find('.number').text();
				var otherNumber = parseInt(nmb);
				otherNumber--;
				$('#upvote').find('.number').empty().append(otherNumber);
			}
			
			if($('#downvote').hasClass('voted'))
				number--;
			else
				number++;
			
			span.find('.number').empty().append(number);
		}
		
		span.toggleClass('voted');
		
		// salje serveru podatke
		var data = {
			userId: eventAuth.id,
			voteableId: id,
			voteableType: voteableType,
			vote: vote
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
				
				if(data.status === "failure") {
					alert('You are not logged in');
					window.location.replace('/Play');
					return;
				}
				
				$('.write-comment').val('');
				
				var count = parseInt($('#comment-number').text());
				count++;
				$('#comment-number').empty().append(count);
				
				$('.comments').prepend(
					'<div class="comment">' +
						'<a href="profile.html?username=' + eventAuth.username + '">' +
							'<img class="profile-pic-small" src="img/dude.jpg" alt="profile pic">' +
							'<span class="comment-name">' + eventAuth.username + '</span>' +
						'</a>' +
						'<span class="comment-date">' + data.comment.createdAt + '</span>' +
						'<div class="comment-div">' +
							'<p class="comment-text">' + data.comment.text + '</p>' +
						'</div>' +
						'<div class="comment-votes" data-type="comment" data-id="' + data.comment.id + '">' +
							'<a href="#" class="vote" data-vote="downvote">' +
		    					'<span id="down">' +
									'<i class="fa fa-thumbs-down"></i> <span class="comment-number">0</span>' +
								'</span>' +
		    				'</a>' +
							'<a href="#" class="vote" data-vote="upvote">' +
	        					'<span id="up">' +
									'<i class="fa fa-thumbs-up"></i> <span class="comment-number">0</span>' +
								'</span>' +
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
	getVotes();
	//follows();
});