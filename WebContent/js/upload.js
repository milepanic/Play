$(document).ready(function() {
	
	$("#submit").click(function (e) {
		e.preventDefault();
		
		var url = $("#url").val();		
		var id = url.split("=")[1];
		
		var description = $("#description").val();
		
		var radio = $('input[name=visibility]:checked', '#upload-form').val();
		
		var commentable = 0;
		
		if ($("#commentable").is(":checked")) {
			commentable = 1;
		}
		
		var voteable = 0;
		
		if ($("#voteable").is(":checked")) {
			voteable = 1;
		}
		
		var data = {
			'url': "https://www.youtube.com/embed/" + id,
			'thumbnail': "https://img.youtube.com/vi/" + id + "/0.jpg",
			'description': description,
			'visibility': radio,
			'commentable': commentable,
			'voteable': voteable,
			'user_id': 1
		}
	});
});