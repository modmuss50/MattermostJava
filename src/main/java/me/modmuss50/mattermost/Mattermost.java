package me.modmuss50.mattermost;

import com.neovisionaries.ws.client.*;
import me.modmuss50.mattermost.models.ModelUtil;
import me.modmuss50.mattermost.models.auth.Login;
import me.modmuss50.mattermost.models.post.Post;
import me.modmuss50.mattermost.websocket.AuthChallange;
import me.modmuss50.mattermost.websocket.MMSocketListener;
import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public class Mattermost {

	public static String SESSION_TOKEN;
	private static Login login = null;
	private static String baseURL;
	private static String botUsername;

	public void login(String url, String email, String password) throws IOException {
		baseURL = url;
		HttpUtil.serverURL = url + "/api/v4";

		login = new Login();
		login.login_id = email;
		login.password = password;

		HttpResponse loginRespose = HttpUtil.getResponse(login, "/users/login");
		JSONParser jsonParser = new JSONParser();
		try {
			JSONObject jsonObject = (JSONObject) jsonParser.parse(HttpUtil.getContent(loginRespose));
			botUsername = (String) jsonObject.get("username");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SESSION_TOKEN = HttpUtil.getHeader(loginRespose, "Token").getValue();
	}

	public void sendMessage(String message, String channelID) throws IOException {
		Post post = new Post();
		post.channel_id = channelID;
		post.message = message;
		HttpResponse postResponse = HttpUtil.getResponse(post, "/posts");
	}

	public void createMessageHanlder(final IMessageHandler messageHandler, boolean skipSSL) throws URISyntaxException, IOException, InterruptedException {

		HttpResponse loginRespose = HttpUtil.getResponse(login, "/users/login", baseURL + "/api/v3");
		String socketToken  = HttpUtil.getHeader(loginRespose, "Token").getValue();

		SocketConnector.SKIP_SSL_CHECK = skipSSL;
		System.out.println(baseURL.replace("https", "wss") + "/api/v3/websocket");
		WebSocket webSocket = new WebSocketFactory().createSocket(baseURL.replace("https", "wss") + "/api/v3/users/websocket");
		webSocket.addListener(new MMSocketListener() {
			@Override
			public void onTextMessage(WebSocket websocket, String text) throws Exception {
				super.onTextMessage(websocket, text);
				String webMessage = new String(text);
				websocket.flush();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(webMessage);
				if(jsonObject.containsKey("event")){
					if(jsonObject.get("event").equals("posted")){
						JSONObject data = (JSONObject) jsonObject.get("data");
						if(data.containsKey("post")){
							Post post = ModelUtil.GSON.fromJson(data.get("post").toString(), Post.class);
							post.sender_name = data.get("sender_name").toString();
							if(!post.sender_name.equals(botUsername)){
								messageHandler.handleMessage(post);
							}
						}
					}
				}
			}

			@Override
			public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
				System.out.println("onPingFrame");
			}

			@Override
			public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
				System.out.println("onPongFrame");
			}

			@Override
			public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
				System.out.println("onError" + cause.getMessage());
			}

			@Override
			public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
				switch (newState) {
					case OPEN: {
						System.out.println("OPEN");
						break;
					}
					case CLOSING:
						websocket.sendClose();
				}
			}

			@Override
			public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
				System.out.println("onDisconnected");
			}
		});

		try {
			webSocket.connect();
		} catch (WebSocketException e) {
			e.printStackTrace();
		}

		AuthChallange authChallange = new AuthChallange();
		authChallange.data.token = socketToken;
		webSocket.sendBinary(ModelUtil.GSON.toJson(authChallange).getBytes());

	}

}
