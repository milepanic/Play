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
		String userId = request.getParameter("user_id");
		String videoId = request.getParameter("video_id");
		
		Comment comment = new Comment(1, text, new Date());
		
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
