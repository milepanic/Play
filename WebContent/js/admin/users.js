$(document).ready(function () {
	
	$.ajax({
		type: 'GET',
		url: '../AdminServlet',
		mimeType: 'json',
		success: function(data) {
			
			if(data.status === "unauthorized") {
				window.location.replace('../');
				return;
			}
			
			$("#dataTable").DataTable({
				data: data.users,
				columns: [
					{ 
						'data': 'username',
						'render': function(username, type, row) {
							return '<a href="../profile.html?id=' + row.id + '">' + username + '</a>';
						}
					},
					{ 'data': 'firstName' },
					{ 'data': 'lastName' },
					{ 'data': 'email' },
					{ 'data': 'role' },
					{ 
						'mData': 'action',
						'mRender': function(data, type, row) {
							if(row.banned) {
								return '<td>' +
											'<a class="text-success unban" title="Unban" href="#" ' +
												' data-name="' + row.username + '"data-id="' + row.id + '">' +
						                		'<i class="fa fa-check" aria-hidden="true"></i>' +
						                	'</a>' +
						                '</td>';
							} else {
								return '<td>' +
			                	'<a class="text-danger ban" title="Ban user" data-toggle="modal"' +
			                		'data-target="#myModal" data-name="' + row.username + '" data-id="' + row.id + '">' +
			                		'<i class="fa fa-ban" aria-hidden="true"></i>' +
			                	'</a>&nbsp;' +
			                	'<a class="text-muted" title="Delete" href="#">' +
			                		'<i class="fa fa-trash" aria-hidden="true"></i>' +
			                	'</a>&nbsp;' +
			                	'<a class="text-warning role" data-role="' + row.role + '" title="Change role" href="#">' +
			                		'<i class="fa fa-edit" aria-hidden="true"></i>' +
			                	'</a>' +
			               '</td>';
							}
							
						}
					}
				]
			});
		},
		error: function() {
			alert('Error');
		}
	});
	
	$('#dataTable').delegate('.ban', 'click', function (e) {
        var name = $(this).data('name');
        var id = $(this).data('id');
        
        var modal = $("#myModal");
        modal.find('.modal-content').attr('data-id', id);
        modal.find('.modal-title').text('Are you sure you want to ban ' + name);
    });
	
	$('.btn-ban').on('click', function() {
		
		id = $(this).parents(':eq(1)').data('id');
		
		var data = {
			action: "ban",
			id: id,
			banned: true
		}
		
		$.post('../AdminServlet', data, function() {
			location.reload();
		});
	});
	
	$('#dataTable').delegate('.unban', 'click', function() {
		id = $(this).data('id');
		name = $(this).data('name');
		
		if(confirm('Do you want to unban ' + name)) {
			var data = {
				action: "ban",
				id: id,
				banned: false
			}
			
			$.post('../AdminServlet', data, function() {
				location.reload();
			});
		}
	});
	
	$('#dataTable').delegate('.role', 'click', function() {
		id = $(this).parent().find('.ban').data('id');
		name = $(this).parent().find('.ban').data('name');
		role = $(this).data('role');
		
		if(confirm(name + ' is ' + role.toLowerCase() + '. Do you want to change the role?')) {
			var data = {
				action: "role",
				id: id,
				role: role
			}
			
			$.post('../AdminServlet', data, function() {
				location.reload();
			});
		}
	});
});