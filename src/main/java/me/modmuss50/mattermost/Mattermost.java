package me.modmuss50.mattermost;

import com.neovisionaries.ws.client.*;
import me.modmuss50.mattermost.models.ModelUtil;
import me.modmuss50.mattermost.models.auth.Login;
import me.modmuss50.mattermost.models.post.Post;
import me.modmuss50.mattermost.models.post.PostList;
import me.modmuss50.mattermost.websocket.AuthChallange;
import me.modmuss50.mattermost.websocket.MMSocketListener;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public class Mattermost {

	public static String SESSION_TOKEN;

	public void login(String url, String email, String password) throws IOException {
		HttpUtil.serverURL = url + "/api/v3";

		Login login = new Login();
		login.login_id = email;
		login.password = password;
		HttpResponse loginRespose = HttpUtil.getResponse(login, "/users/login");
		SESSION_TOKEN = HttpUtil.getHeader(loginRespose, "Token").getValue();
		System.out.println(SESSION_TOKEN);
	}


	public void sendMessage(String message, String channelID) throws IOException {
		Post post = new Post();
		post.channel_id = channelID;
		post.message = message;
		HttpResponse postResponse = HttpUtil.getResponse(post, "/posts");
	}

	public PostList getPosts(String channelID) throws IOException {
		class PostListRequest {
			String channel_id;
		}
		PostListRequest postListRequest = new PostListRequest();
		postListRequest.channel_id = channelID;
		HttpResponse postResponse = HttpUtil.getResponse(postListRequest,  "/channels/posts");
		String jsonRespose = HttpUtil.getContent(postResponse);
		System.out.println(jsonRespose);
		return ModelUtil.GSON.fromJson(jsonRespose, PostList.class);
	}

	public void createMessageHanlder(IMessageHandler messageHandler, boolean skipSSL) throws URISyntaxException, IOException, InterruptedException {
		SocketConnector.SKIP_SSL_CHECK = skipSSL;
		WebSocket webSocket = new WebSocketFactory().createSocket(HttpUtil.serverURL.replace("https", "wss") + "/users/websocket");
		webSocket.addListener(new MMSocketListener() {
			@Override
			public void onTextMessage(WebSocket websocket, String text) throws Exception {
				super.onTextMessage(websocket, text);
				String webMessage = new String(text);
				websocket.flush();
				messageHandler.handleMessage(webMessage);
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
		authChallange.data.token = Mattermost.SESSION_TOKEN;
		webSocket.sendBinary(ModelUtil.GSON.toJson(authChallange).getBytes());

	}





}
