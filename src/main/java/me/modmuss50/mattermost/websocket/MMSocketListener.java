package me.modmuss50.mattermost.websocket;

import com.neovisionaries.ws.client.*;

import java.util.List;
import java.util.Map;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public class MMSocketListener implements WebSocketListener {
	@Override
	public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {

	}

	@Override
	public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {

	}

	@Override
	public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {

	}

	@Override
	public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {

	}

	@Override
	public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onTextMessage(WebSocket websocket, String text) throws Exception {

	}

	@Override
	public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

	}

	@Override
	public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

	}

	@Override
	public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

	}

	@Override
	public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

	}

	@Override
	public void onError(WebSocket websocket, WebSocketException cause) throws Exception {

	}

	@Override
	public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {

	}

	@Override
	public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

	}

	@Override
	public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {

	}

	@Override
	public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

	}

	@Override
	public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

	}

	@Override
	public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {

	}

	@Override
	public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {

	}
}
