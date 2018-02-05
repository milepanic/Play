$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	function getVideos() {

		$.get('IndexServlet', function(data) {
			
			for(i in data.videos) {				
				var date = new Date(parseInt(data.videos[i].createdAt.toString().substr(6)));
				var month = date.getMonth() + 1;
				var fullDate = date.getDate() + "." + month + "." + date.getFullYear();
				console.log(fullDate);
				
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
							'<a href="profile.html?id=' + data.videos[i].user.id +'" class="channel-name">' + data.videos[i].user.username + '</a><br>' +
							'<span class="views"><i class="fa fa-eye"></i> ' + data.videos[i].views + ' Views </span>&nbsp;' +
							'<span class="date"><i class="fa fa-clock-o"></i> ' + fullDate + '</span>' +
						'</div>' +
					'</div>'
				);
			}
		});
	}
	
	getVideos();
});