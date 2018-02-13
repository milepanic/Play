function proceed(data) {}

var search = window.location.search.slice(1).split('&')[0].split('=')[1];
$('#search').val(search);

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	var search = window.location.search.slice(1).split('&')[0].split('=')[1];
	
	var data = {
		search: search,
		videoname: true,
		username: true,
		comment: true
	}
	
	$.get('SearchServlet', data, function(data) {
		
		for(i in data.videos) {
			
			$('.s-videos-box').append(
				'<div class="s-video-box col-md-6">' +
					'<div class="s-thumbnail">' +
						'<a href="single.html?id=' + data.videos[i].id + '">' +
							'<img class="s-video-img" src="' + data.videos[i].thumbnail + '">' +
						'</a>' +
					'</div>' +
					'<div class="s-info-box">' +
						'<div class="channel-video">' +
							'<a href="single.html?id=' + data.videos[i].id + '" class="s-video-name">' + data.videos[i].name + '</a><br>' +
							'<a href="profile.html?id=' + data.videos[i].user.id + '" class="s-channel-name">' + data.videos[i].user.username + '</a>' +
						'</div>' +
						'<div>' +
							'<div class="s-likes-dislikes" data-id="' + data.videos[i].id + '">' +
								'<i class="fa fa-thumbs-up" aria-hidden="true"></i> <span class="s-upvotes">0</span> Upvotes ' +
								'<i class="fa fa-thumbs-down" aria-hidden="true"></i> <span class="s-downvotes">0</span> Downvotes' +
							'</div>' +
							'<div class="views-date">' +
								'<span class="s-views"><i class="fa fa-eye"></i> ' + data.videos[i].views + ' Views  </span>' +
								'<span class="s-date"><i class="fa fa-clock-o"></i>  ' + data.videos[i].createdAt + '</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
				'</div>'
			);
			
			getVotes(data.videos[i].id);
		}
		
	});
	
	function getVotes(id) {
		
		var data = {
			action: "get-video-votes",
			voteableType: "video",
			voteableId: id
		}
		
		$.get('VoteServlet', data, function(data) {
			console.log(data);
			var votes = $('[data-id=' + id + ']');
			
			votes.find('.s-downvotes').empty().append(data.downvotes);
			votes.find('.s-upvotes').empty().append(data.upvotes);
		});
	}
	
});