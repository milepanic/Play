$(document).ready(function () {
	
	$.get('../AdminServlet', function (data) {
		
		var dataTable = $("#dataTable").DataTable();
		
		/*for(i in data.users) {
			$('#tbody').append(
				'<tr>' +
	                '<td><a href="profile.html?username=' + data.users[i].username + '">' + data.users[i].username + '</a></td>' +
	                '<td>' + data.users[i].firstName + '</td>' +
	                '<td>' + data.users[i].lastName + '</td>' +
	                '<td>' + data.users[i].email + '</td>' +
	                '<td>' + data.users[i].role + '</td>' +
	                '<td>' +
	                	'<a class="text-danger ban" title="Ban user" data-toggle="modal"' +
	                		'data-target="#myModal" data-name="' + data.users[i].username + '" data-id="' + data.users[i].id + '">' +
	                		'<i class="fa fa-ban" aria-hidden="true"></i>' +
	                	'</a>&nbsp;' +
	                	'<a class="text-success" title="Unban" href="#">' +
	                		'<i class="fa fa-check" aria-hidden="true"></i>' +
	                	'</a>' +
	               '</td>' +
	            '</tr>'
			);
		}*/
		
	});
});