package me.modmuss50.mattermost;

import me.modmuss50.mattermost.models.post.Post;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public interface IMessageHandler {

	void handleMessage(Post message);
}
