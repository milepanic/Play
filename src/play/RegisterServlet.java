package play;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import play.dao.UserDAO;
import play.model.User;

public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User auth = (User) session.getAttribute("auth");
		
		if (auth != null) return;
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");		
		String description = request.getParameter("description");
		
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTime = sdf.format(dt);
		
		if(UserDAO.validateEmail(email) || UserDAO.validateUsername(username)) {
			System.out.println("Failure");
			response.sendRedirect("register.html");
			return;
		}
		
		User user = new User();
		
		user.setId(UserDAO.last() + 1);
		user.setUsername(username);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setDescription(description);
		user.setRegisteredAt(currentTime);
		
		UserDAO.create(user);
		session.setAttribute("auth", user);

		response.sendRedirect("index.html");
	}

}
