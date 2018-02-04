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
import play.model.User;
import play.model.Video;

public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		// First condition is to get all videos the user is following
		// Second to get all users the user is following
		// Third is to check on single.html if user is following video's author
		if (page.contentEquals("following")) {
			List<Video> videos = FollowDAO.getVideos(userId);
			
			Map<String, Object> data = new HashMap<>();
			data.put("videos", videos);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} else if (page.contentEquals("follows")) {
			List<User> users = FollowDAO.getUsers(userId);
			List count = new ArrayList();
			
			for(User user : users) {
				count.add(FollowDAO.count(user.getId()));
			}
			
			Map<String, Object> data = new HashMap<>();
			data.put("users", users);
			data.put("count", count);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} else {
			int followerId = Integer.parseInt(request.getParameter("follower_id"));
			
			boolean follow = FollowDAO.get(followerId, userId);
			
			Map<String, Object> data = new HashMap<>();
			data.put("follow", follow);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User auth = (User) session.getAttribute("auth");
		
		Map<String, Object> data = new HashMap<>();
		String status = "success";
		
		if(auth == null) {
			status = "failure";
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} else {
			int followerId = Integer.parseInt(request.getParameter("follower_id"));
			int userId = Integer.parseInt(request.getParameter("user_id"));
			
			if (followerId == userId) return;
			if (followerId != auth.getId()) return;
			
			if (FollowDAO.get(followerId, userId))
				FollowDAO.delete(followerId, userId);
			else
				FollowDAO.create(followerId, userId);
		}
		
	}

}
