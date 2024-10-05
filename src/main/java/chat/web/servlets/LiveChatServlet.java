package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LiveChatServlet
 */
@WebServlet("/LiveChat")
public class LiveChatServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
       
   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	   if (!assertUserPresent(req, resp)) {
			return;
		}
	   req.getRequestDispatcher("/LiveChat.jsp").forward(req, resp);
	   
   }

}
