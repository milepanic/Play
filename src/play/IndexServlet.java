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

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.FollowDAO;
import play.dao.UserDAO;
import play.dao.VideoDAO;
import play.model.User;
import play.model.Video;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Video> videos = VideoDAO.getAll();
		List<User> users = UserDAO.mostPopular();
		System.out.println(users);
		
		List<Integer> count = new ArrayList<>();
		for(User user : users) {
			int followersCount = FollowDAO.count(user.getId());
			count.add(followersCount);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		data.put("users", users);
		data.put("count", count);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
