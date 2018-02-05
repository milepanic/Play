package play;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.VideoDAO;
import play.dao.VoteDAO;
import play.model.Video;
import play.model.Vote;

public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");		
		int voteableId = Integer.parseInt(request.getParameter("voteableId"));
		String voteableType = request.getParameter("voteableType");
		
		// prvi uslov je kada se ucita stranica da provjeri da li je korisnik glasao
		// drugi je da ispise broj up/down vote-a videa ili komentara
		if(action.contains("check-vote")) {
			int userId = Integer.parseInt(request.getParameter("userId"));
			
			Vote vote = new Vote();
			vote.setUserId(userId);
			vote.setVoteableId(voteableId);
			vote.setVoteableType(voteableType);
			
			Vote voted = VoteDAO.get(vote);
			
			Map<String, Object> data = new HashMap<>();
			data.put("vote", voted);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		} else {			
			int upvotes = VoteDAO.getVotes(voteableId, voteableType, true);
			System.out.println("Upvotes: " + upvotes);
			
			int downvotes = VoteDAO.getVotes(voteableId, voteableType, false);
			System.out.println("Downvotes: " + downvotes);
			
			Map<String, Object> data = new HashMap<>();
			data.put("upvotes", upvotes);
			data.put("downvotes", downvotes);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println(jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("userId"));
		int voteableId = Integer.parseInt(request.getParameter("voteableId"));
		String voteableType = request.getParameter("voteableType");
		boolean isVote = Boolean.parseBoolean(request.getParameter("vote"));
		
		if(voteableType.contentEquals("video")) {
			Video video = VideoDAO.get(voteableId);
			if(!video.isVoteable()) return;
		}
		
		Vote vote = new Vote();
		vote.setUserId(userId);
		vote.setVoteableId(voteableId);
		vote.setVoteableType(voteableType);
		vote.setVote(isVote);
		
		Vote compare = VoteDAO.get(vote);
		if(compare == null)
			VoteDAO.create(vote);
		else if(compare.isVote() != isVote)
			VoteDAO.update(vote);
		else if(compare.isVote() == isVote)
			VoteDAO.delete(vote);
		
		//success - fail
	}

}
