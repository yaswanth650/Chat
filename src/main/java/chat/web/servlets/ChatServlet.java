package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class ChatServlet
 */
@WebServlet("/Chat")
public class ChatServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!assertUserPresent(req, resp)) {
			return;
		}
		String name=(String)getAttrib(req, "chat");
		if (name==null) {
			name=(String)getAttrib(req, "name");
		}
		if (name==null) {
			req.getRequestDispatcher("/Chat.jsp")
			.forward(req, resp);
			return;
		}
		if(!getChatData().hasChat(name)) {
			req.getRequestDispatcher("/Chat.jsp").forward(req, resp);
			return;
		}
		
		req.setAttribute("chat", name);
		req.getRequestDispatcher("/Chat.jsp").forward(req, resp);
		
		return;
	}
}
