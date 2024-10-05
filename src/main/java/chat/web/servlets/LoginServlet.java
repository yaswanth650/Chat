package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (isBanned(req.getRemoteAddr())) {
			sendBanMsg(req, resp);
			return;
		}
		String user=getUser(req);
		if(user==null||user.equals("")) {
			sendErrMsg(req, resp, "missing user");
			return;
		}
		
		if(!hasUser(user)) {
			sendErrMsg(req, resp, "Login failed");
			return;
		}
		if (!login(req)) {
			sendErrMsg(req, resp, "Login failed");
			return;
		}
		
		resp.sendRedirect(getServletContext().getContextPath()+"/Chat");
		return;
	}
}
