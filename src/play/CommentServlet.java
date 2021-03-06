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

import play.dao.CommentDAO;
import play.dao.UserDAO;
import play.model.Comment;
import play.model.User;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("id"));
		String order = request.getParameter("order");
		
		List<Comment> comments = null;
		
		if(order.contains("Likes")) {
			comments = CommentDAO.popular(videoId, true);
			System.out.println("most popular---");
		} else if(order.contains("Dislikes")) {
			comments = CommentDAO.popular(videoId, false);
			System.out.println("least popular---");
		} else {
			System.out.println("else ---");
			String param = "";
			String rank = "ASC";
			System.out.println(order.toString());
			switch(order.toString()) {
				case "Latest":
					param = "created_at";
					rank = "DESC";
					break;
				case "Oldest":
					param ="created_at";
					break;
			}
			
			comments = CommentDAO.getAll(videoId, param, rank);
		}
		
		int count = CommentDAO.count(videoId);
		
		Map<String, Object> data = new HashMap<>();
		data.put("comments", comments);
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
		
		if(auth == null || auth.isBanned() || auth.isDeleted()) {
			status = "failure";
		} else {
			String text = request.getParameter("text");
			
			if(text == "") return;
			
			int userId = Integer.parseInt(request.getParameter("user_id"));
			int videoId = Integer.parseInt(request.getParameter("video_id"));
			
			Date dt = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String currentTime = sdf.format(dt);
			
			int commentId = CommentDAO.last() + 1;
			System.out.println(commentId);
			
			User user = UserDAO.get(userId);
			Comment comment = new Comment(commentId, text, currentTime, user, videoId);
			
			CommentDAO.add(comment);
			data.put("comment", comment);
		}
		
		data.put("status", status);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
