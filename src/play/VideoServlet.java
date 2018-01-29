package play;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.UserDAO;
import play.dao.VideoDAO;
import play.model.User;
import play.model.Video;
import play.model.Video.Visibility;

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		System.out.println("ID videa je: " + id);
		Video video = VideoDAO.get(id);
		
		Map<String, Object> data = new HashMap<>();
		data.put("video", video);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String YoutubeUrl = request.getParameter("url");
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Visibility visibility = Visibility.valueOf(request.getParameter("visibility"));
		boolean commentable = Boolean.parseBoolean(request.getParameter("commentable"));
		boolean voteable = Boolean.parseBoolean(request.getParameter("voteable"));
		int userId = Integer.parseInt(request.getParameter("userId"));
		
		String[] parts = YoutubeUrl.split("=");
		String id = parts[1];
		
		int videoId = VideoDAO.last() + 1;
		String url = "https://www.youtube.com/embed/" + id;
		String thumbnail = "https://img.youtube.com/vi/" + id + "/0.jpg";

		boolean blocked = false;
		int views = 0;
		
		User user = UserDAO.get(userId);
		
		Video video = new Video(videoId, name, url, thumbnail, description, 
				visibility, commentable, voteable, blocked, views, new Date(), user);
		
		VideoDAO.create(video);
		
		Map<String, Object> data = new HashMap<>();
		data.put("id", videoId);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
