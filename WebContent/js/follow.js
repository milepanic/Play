function proceed(data) {
	var auth = data.auth;
	
	function getVideos() {

		var data = {
			page: "following",
			userId: auth.id
		}
		
		$.get('FollowServlet', data, function(data) {
			
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
}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	
});