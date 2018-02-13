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
							'<span class="views"><i class="fa fa-eye"></i> <span class="view-number">' + data.videos[i].views + '</span> Views </span>&nbsp;' +
							'<span class="date"><i class="fa fa-clock-o"></i> <span class="date-span">' + data.videos[i].createdAt + '</span></span>' +
						'</div>' +
					'</div>'
				);
			}
			
			for(i in data.count) {
				$('[data-div-id="' + i + '"]').append(data.count[i]);
			}
		});
	}
	
	$('#toggle-search').click(function(e) {
		var action = $(this).data('action');
		$('#search-form').prop("hidden", false);
		
		if(action === "hide") {
			$('#search-form').slideUp("medium");
			$(this).find('i').removeClass('fa-caret-square-o-up').addClass('fa-caret-square-o-down');
			$(this).data('action', "show");
		} else if(action === "show") {
			$('#search-form').slideDown("medium");
			$(this).find('i').removeClass('fa-caret-square-o-down').addClass('fa-caret-square-o-up');
			$(this).data('action', "hide");
		}
	});
	
	$('.search-caret').click(function(e) {
		var point = $(this).data('point');
		if(point === "up") {
			$(this).find('i').removeClass('fa-caret-up').addClass('fa-caret-down');
			$(this).data('point', 'down');
		} else {
			$(this).find('i').removeClass('fa-caret-down').addClass('fa-caret-up');
			$(this).data('point', 'up');
		}
	});
	
	$('#search-form input').on('keyup', function() {
		var nameVal = $('#name').val().toLowerCase();
		var authorVal = $('#author').val().toLowerCase();
		var viewsVal = $('#views').val().toLowerCase();
		var dateVal = $('#date').val().toLowerCase();
		
		$('.video-box').each(function() {
			var name = $(this).find('.video-name').text().toLowerCase();
			var author = $(this).find('.channel-name').text().toLowerCase();
			var views = $(this).find('.view-number').text().toLowerCase();
			var date = $(this).find('.date-span').text().toLowerCase();
			
			if(name.indexOf(nameVal) < 0 || author.indexOf(authorVal) < 0
					|| views.indexOf(viewsVal) < 0 || date.indexOf(dateVal) < 0) $(this).hide();
			else $(this).show();
		});
	});
	
	getVideos();
});