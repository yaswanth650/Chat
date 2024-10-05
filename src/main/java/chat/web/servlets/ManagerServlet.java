package chat.web.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import chat.utils.Constants;

/**
 * Servlet implementation class ManegerServlet
 */
@WebServlet("/Manager")
public class ManagerServlet extends AbstractChatServlet {
	private static final long serialVersionUID = 1L;
       
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	if (!assertUserPresent(req, resp)) {
			return;
		}
    	if (!isAdmin(getUser(req))) {
    		resp.sendRedirect(req.getServletContext().getContextPath()+"/Chat");
    		return;
		}
    	boolean isManager=isManager(req);
    	if (isManager) {
    		getSession(req).setAttribute(Constants.MANAGER_FIELD, false);
		}
    	else {
    		getSession(req).setAttribute(Constants.MANAGER_FIELD, true);
    	}
    	String chatName=(String) getAttrib(req, "chat");
    	String qStr="";
    	if (!(chatName==null||chatName.equals(""))) {
			qStr="?chat="+chatName;
		}
    	resp.sendRedirect(req.getServletContext().getContextPath()+"/Chat"+qStr);
    }
    
    

}
