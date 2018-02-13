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

import play.dao.VideoDAO;
import play.model.Video;

public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filter = request.getParameter("search");
		boolean videoname = Boolean.parseBoolean(request.getParameter("videoname"));
		boolean username = Boolean.parseBoolean(request.getParameter("username"));
		boolean comment = Boolean.parseBoolean(request.getParameter("comment"));
		
		List<Video> videos = VideoDAO.search(filter, videoname, username, comment);
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		
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
