package play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.FollowDAO;
import play.dao.UserDAO;
import play.dao.VideoDAO;
import play.model.User;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		User user = UserDAO.get(id);
		
		int followersCount = FollowDAO.count(user.getId());
		int viewsCount = VideoDAO.countViews(id);
		int videosCount = VideoDAO.countVideos(id);
		
		List count = new ArrayList();
		count.add(viewsCount);
		count.add(followersCount);
		count.add(videosCount);
		
		Map<String, Object> data = new HashMap<>();
		data.put("user", user);
		data.put("count", count);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User auth = (User) session.getAttribute("auth");
		
		Map<String, Object> data = new HashMap<>();
		String status = "success";
		
		String username = request.getParameter("username");
		User user = UserDAO.get(username);
		
		if(auth == null || auth.getId() != user.getId()) {
			status = "failure";
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			return;
		}
		
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String description = request.getParameter("description");
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setDescription(description);
		
		UserDAO.update(user);
	}
	
}