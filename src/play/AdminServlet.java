package play;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.UserDAO;
import play.model.User;
import play.model.User.Role;

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User auth = (User) session.getAttribute("auth");
		
		Map<String, Object> data = new HashMap<>();
		String status = "success";
		
		if(auth == null || auth.getRole() != Role.ADMIN) {
			status = "unauthorized";
		} else {
			List<User> users = UserDAO.getAll();
			data.put("users", users);
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		int id = Integer.parseInt(request.getParameter("id"));
		User user = UserDAO.get(id);
		
		if(action.contentEquals("ban")) {
			boolean ban = Boolean.parseBoolean(request.getParameter("banned"));
			
			user.setBanned(ban);
			UserDAO.update(user);
		} else if(action.contentEquals("role")) {
			Role role = Role.valueOf(request.getParameter("role"));
			
			if(role.equals(Role.ADMIN)) role = Role.USER;
			else role = Role.ADMIN;
			
			user.setRole(role);
			UserDAO.update(user);
		}
	}

}
