package me.modmuss50.mattermost.websocket;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public class AuthChallange {
	public int seq = 1;

	public String action = "authentication_challenge";

	public Data data = new Data();

	public class Data {
		public String token;
	}

}
