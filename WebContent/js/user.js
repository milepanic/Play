$(document).ready(function() {
	
	function getUser() {
		
		var id = window.location.search.slice(1).split('&')[0].split('=')[1];
		var profile = $('.profile-container');
		
		$.get('UserServlet', {'id': id}, function(data) {
			profile.find('.profile-header-name').text(data.user.username);
			profile.find('.profile-description').text(data.user.description);
		});
	}
	
	getUser();
});