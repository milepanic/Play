package play;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
		String page = request.getParameter("page");
		
		Map<String, Object> data = new HashMap<>();
		
		if (page.contentEquals("single")) {
			Video video = VideoDAO.get(id);
			int views = video.getViews();
			views++;
			video.setViews(views);
			VideoDAO.update(video);
			data.put("video", video);
		} else if (page.contentEquals("user")) {
			String order = request.getParameter("order");
			String param = "created_at";
			String rank = "ASC";
			
			switch(order.toString()) {
			case "Latest":
				rank = "DESC";
				break;
			case "Most popular":
				param = "views";
				rank = "DESC";
			case "Least popular":
				param = "views";
			}
			
			List<Video> videos = VideoDAO.getWhereUserWithParams(id, param, rank);
			data.put("videos", videos);
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		
		String description = request.getParameter("description");
		Visibility visibility = Visibility.valueOf(request.getParameter("visibility"));
		boolean commentable = Boolean.valueOf(request.getParameter("commentable"));
		System.out.println("Commentable is " + commentable);
		boolean voteable = Boolean.valueOf(request.getParameter("voteable"));
		
		if(type.contentEquals("edit")) {
			int id = Integer.parseInt(request.getParameter("id"));
			
			Video video = VideoDAO.get(id);
			video.setDescription(description);
			video.setVisibility(visibility);
			video.setCommentable(commentable);
			video.setVoteable(voteable);
			
			VideoDAO.update(video);
			return;
		} else if(type.contentEquals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			// napraviti delete u DAO
		} else {
			String YoutubeUrl = request.getParameter("url");
			String name = request.getParameter("name");
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

}
