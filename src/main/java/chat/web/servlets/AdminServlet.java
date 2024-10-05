package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import chat.utils.ChatProperties;
import chat.utils.Constants;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/Admin")
public class AdminServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		if (!assertUserPresent(req, resp)) {
			return;
		}
		String user=(String)getAttrib(req, Constants.UNAME_FIELD);
		if (ChatProperties.isAdminAllowed()&&ChatProperties.isAdminChangeAllowed()) {
			if (isAdmin(user)) {
				unsetAdmin(user);
			}
			else {
				setAdmin(user);
			}
		}
		resp.sendRedirect(getServletContext().getContextPath()+"/Chat");
		
		return;
	}
}
