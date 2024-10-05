package chat.web.websockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import chat.ChatData;

@ServerEndpoint("/liveChat")
public class LiveChatSocket {

	private static List<Session> sessions = new ArrayList<>();

	private void sendMsg(String msg) {
		for (Session session : sessions) {

			try {

				if (session.isOpen()) {
					session.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// public static void reloadAll(String user) {
	// for (Session session : sessions) {
	// try {
	// if( session.isOpen()) {
	// session.getBasicRemote().sendText("Reload");
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }

	@OnMessage
	public void onMessage(String message) {
		if (message == null) {
			return;
		}
		String[] splitted = message.split("] ");
		if (splitted.length != 2 || splitted[1].equals("")) {
			return;
		}
		
		if (ChatData.endsWithSpaces(splitted[1])) {
			return;
		}
		// if((!splitted[1].matches(Constants.ALLOWED_IN))||message.length()>ChatProperties.getMaxChatCharactarsAllowedIn()){
		// return;
		// }
		// if (!getChatData().renew(user)) {
		//
		// return;
		// }
		sendMsg(message);
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		// System.out.println("Open");
		if (!sessions.contains(session)) {
			sessions.add(session);
		}

	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// System.out.println("Error");
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		// System.out.println("End "+closeReason.getReasonPhrase());
		if (sessions.contains(session)) {
			sessions.remove(session);
		}
	}

}
