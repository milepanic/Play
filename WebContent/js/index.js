function proceed() {}

$(document).ready(function() {
	
	$("head").append('<script type="text/javascript" src="js/session.js"></script>');
	
	function getVideos() {

		$.get('IndexServlet', function(data) {
			console.log(data);
			for(i in data.users) {
				$('.most-popular').append(
					'<div class="popular-user-box">' +
						'<div class="popular-user-left">' +
							'<a href="profile.html?id=' + data.users[i].id + '"><img class="profile-pic-medium"' +
							'src="https://www.poeticous.com/system/poets/photos/000/026/357/large/jm-flower-crown.jpg?1481219945"></a>' +
						'</div>' +
						'<div class="popular-user-right col-md-7">' +
							'<a class="popular-user-username" href="profile.html?id=' + data.users[i].id + '">' + data.users[i].username + '</a><br>' +
							'<small class="popular-user-followers"><span data-div-id="' + i + '" class="popular-user-number"></span> Followers</small>' +
						'</div>' +
            		'</div>'
				);
			}
			
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
							'<a href="profile.html?id=' + data.videos[i].user.id +'" class="channel-name">' + data.videos[i].user.username + '</a><br>' +
							'<span class="views"><i class="fa fa-eye"></i> ' + data.videos[i].views + ' Views </span>&nbsp;' +
							'<span class="date"><i class="fa fa-clock-o"></i> ' + data.videos[i].createdAt + '</span>' +
						'</div>' +
					'</div>'
				);
			}
			
			for(i in data.count) {
				$('[data-div-id="' + i + '"]').append(data.count[i]);
			}
		});
	}
	
	getVideos();
});