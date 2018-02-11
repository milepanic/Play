package play;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import play.model.User.Role;
import play.model.Video;
import play.model.Video.Visibility;

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String page = request.getParameter("page");
		
		Map<String, Object> data = new HashMap<>();
		String status = "sucess";
		String message = "success";
		
		if (page.contentEquals("single")) {
			HttpSession session = request.getSession();
			User auth = (User) session.getAttribute("auth");

			Video video = VideoDAO.get(id);
			
			if(video.isBlocked() && auth == null
					|| video.isBlocked() && auth != null 
						&& auth.getRole() == Role.USER && auth.getId() != video.getUser().getId()) {
				status = "fail";
				message = "This video is blocked";
			} else if(video.getVisibility() == Visibility.PRIVATE && auth == null
				|| video.getVisibility() == Visibility.PRIVATE
					&& auth.getRole() == Role.USER && auth.getId() != video.getUser().getId()) {
				status = "fail";
				message = "This video is private";
			} else if(video.getUser().isBanned() && auth == null 
				|| video.getUser().isBanned() && auth.getRole() == Role.USER && auth.getId() != video.getUser().getId()) { 
				status = "fail";
				message = "The user of this video is banned";
			} else if(video.getUser().isDeleted() && auth == null || video.getUser().isDeleted() && auth.getRole() == Role.USER) {
				status = "fail";
				message = "The user of this video is deleted";
			} else {
				int views = video.getViews();
				views++;
				video.setViews(views);
				VideoDAO.update(video);
				
				int count = FollowDAO.count(video.getUser().getId());
				
				data.put("video", video);
				data.put("count", count);
				data.put("auth", auth);
			}			
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
		} else if (page.contentEquals("sidebar")) {
			List <Video> videos = VideoDAO.random(4);
			data.put("videos", videos);
		}
		data.put("status", status);
		data.put("message", message);
		
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
		
		if(auth == null || auth.isBanned() || auth.isDeleted()) {
			status = "failure";
			
			data.put("status", status);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
			return;
		}
		String type = request.getParameter("type");
		
		if(type.contentEquals("delete")) {
			int id = Integer.parseInt(request.getParameter("id"));
			Role role = Role.valueOf(request.getParameter("role"));
			
			VideoDAO.delete(id, role);
			return;
		} else if(type.contentEquals("block")) {
			int id = Integer.parseInt(request.getParameter("id"));
			boolean blocked = Boolean.parseBoolean(request.getParameter("blocked"));
			
			if(!auth.getRole().equals(Role.ADMIN)) return;
			
			Video video = VideoDAO.get(id);
			video.setBlocked(blocked);
			VideoDAO.update(video);
			return;
		}
		
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
			
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			User user = UserDAO.get(userId);
			
			Video video = new Video(videoId, name, url, thumbnail, description, 
					visibility, commentable, voteable, blocked, views, currentTime, user);
			
			VideoDAO.create(video);
			
			data.put("id", videoId);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
	}

}
