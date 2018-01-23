$(document).ready(function() {
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	function getVideo() {
		
		var video_box = $('.video-single-box');
		
		$.get('VideoServlet', {'id': id}, function(data) {
			$(document).attr('title', data.video.name);
			
			video_box.find('iframe').attr('src', data.video.url + '?autoplay=1');
			video_box.find('.video-single-title').text(data.video.name);
			video_box.find('.views').text(data.video.views);
			video_box.find('.date').text(data.video.createdAt);
			video_box.find('.video-single-description > p').text(data.video.description);
		});
	}
	
	function getComments() {
		
		$.get('CommentServlet', {'id': id}, function(data) {
			
			for(i in data.comments) {
				
				$('.comments').append(
					'<div class="comment">' +
						'<img class="profile-pic-small" src="" alt="profile pic">' +
						'<span class="comment-name">TODO: get user</span>' +
						'<div class="comment-div">' +
							'<p class="comment-text">' + data.comments[i].text + '</p>' +
						'</div>' +
					'</div>'
				);
				
			}
		});
	}
	
	$('#comment-btn').on('click', function(e) {
		e.preventDefault();
		
		var text = $('.write-comment').val();
		
		var params = {
			'text': text,
			'user_id': 1,
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
							'<img class="profile-pic-small" src="" alt="profile pic">' +
							'<span class="comment-name">TODO: get user</span>' +
							'<div class="comment-div">' +
								'<p class="comment-text">' + data.comment.text + '</p>' +
							'</div>' +
						'</div>'
					);
			},
			error: function() {
				alert('error');
			}
		});
		
	});
	
	getVideo();
	getComments();
});