package play;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import play.dao.UserDAO;

public class ValidationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		
		boolean bool = true;
		
		if(username != null && UserDAO.validateUsername(username)) {
			bool = false;
		} 
		
		if(email != null && UserDAO.validateEmail(email)) {
			bool = false;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(bool);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}
