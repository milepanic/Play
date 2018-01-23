package play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.CommentDAO;
import play.model.Comment;
import play.model.Video;

public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int videoId = Integer.parseInt(request.getParameter("id"));
		List<Comment> comments = CommentDAO.getAll(videoId);
		
		Map<String, Object> data = new HashMap<>();
		data.put("comments", comments);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("text");
		int userId = Integer.parseInt(request.getParameter("user_id"));
		int videoId = Integer.parseInt(request.getParameter("video_id"));
		
		int commentId = CommentDAO.last() + 1;
		System.out.println(commentId);
		
		Comment comment = new Comment(commentId, text, new Date(), userId, videoId);
		
		CommentDAO.add(comment);
		
		Map<String, Object> data = new HashMap<>();
		data.put("comment", comment);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
