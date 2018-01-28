package play;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.FollowDAO;
import play.dao.VideoDAO;
import play.model.Video;

public class FollowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		
		if (page.contentEquals("following")) {
			List<Video> videos = FollowDAO.getVideos(1);
			
			Map<String, Object> data = new HashMap<>();
			data.put("videos", videos);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} else {
			int followerId = Integer.parseInt(request.getParameter("follower_id"));
			int userId = Integer.parseInt(request.getParameter("user_id"));
			
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
		int followerId = Integer.parseInt(request.getParameter("follower_id"));
		int userId = Integer.parseInt(request.getParameter("user_id"));
		
		if (followerId == userId)
			return;
		
		if (FollowDAO.get(followerId, userId))
			FollowDAO.delete(followerId, userId);
		else
			FollowDAO.create(followerId, userId);
			
	}

}
