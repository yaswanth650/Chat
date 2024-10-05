package chat.web.servlets;

import java.io.IOException;
import  jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;

import chat.utils.Constants;
import chat.web.websockets.ReloadSocket;

/**
 * Servlet implementation class DelUserServlet
 */
@WebServlet("/delUser")
public class DelUserServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
       
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!assertUserPresent(req, resp)) {
			return;
		}
		
		String user=(String) getAttrib(req, Constants.UNAME_FIELD);
		if (!isAdmin(user)) {
			resp.sendRedirect(getServletContext().getContextPath()+"/Chat");
			return;
		}
		String unameDel=(String)getAttrib(req, Constants.UNAME_DEL_FIELD);
		if (unameDel==null) {
			resp.sendRedirect(getServletContext().getContextPath()+"/Chat");
			return;
		}
		unregister(unameDel);
		
		resp.sendRedirect(getServletContext().getContextPath()+"/Chat");
		ReloadSocket.reloadAll();
		return;
	}

}
