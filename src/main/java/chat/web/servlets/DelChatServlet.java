package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import  jakarta.servlet.annotation.WebServlet;
import  jakarta.servlet.http.HttpServletRequest;
import  jakarta.servlet.http.HttpServletResponse;

import chat.utils.Constants;
import chat.web.websockets.ReloadSocket;

/**
 * Servlet implementation class DelChatServlet
 */
@WebServlet("/DelChat")
public class DelChatServlet extends AbstractChatServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!assertUserPresent(req, resp)) {
			return;
		}
    	if (!isAdmin(getUser(req))) {
    		resp.sendRedirect(req.getServletContext().getContextPath()+"/Chat");
    		return;
		}
    	if (!isManager(req)) {
			return;
		}
    	Object oChatId= getAttrib(req, Constants.CHAT_ID);
    	
    	if (oChatId==null) {
    		resp.sendRedirect(req.getServletContext().getContextPath()+"/Chat");
			return;
		}
    	try {
    		getChatData().deleteChat((String) oChatId);
		} catch (Exception e) {
			System.err.println("Error[deleting message]"+e.getMessage());
		}
    	resp.sendRedirect(req.getServletContext().getContextPath()+"/Chat");
    	ReloadSocket.reloadAll();
	}

}
