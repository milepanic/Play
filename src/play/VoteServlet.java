package play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.VoteDAO;
import play.model.Vote;

public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		
		Vote vote = new Vote();
		vote.setUserId(userId);
		vote.setVideoId(videoId);
		
		Vote voted = VoteDAO.get(vote);
		
		Map<String, Object> data = new HashMap<>();
		data.put("vote", voted);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		boolean like = Boolean.parseBoolean(request.getParameter("like"));
		
		// ako je videu zabranjeno lajkovanje return
		
		Vote vote = new Vote();
		vote.setUserId(userId);
		vote.setVideoId(videoId);
		vote.setLike(like);
		
		Vote compare = VoteDAO.get(vote);
		if(compare == null)
			VoteDAO.create(vote);
		else if(compare.isLike() != like)
			VoteDAO.update(vote);
		else if(compare.isLike() == like)
			VoteDAO.delete(vote);
		
		//success - fail
	}

}
