package me.modmuss50.mattermost;

import me.modmuss50.mattermost.models.ModelUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by modmuss50 on 24/05/2017.
 */
public class HttpUtil {

	public static String serverURL;

	public static HttpResponse getResponse(Object jsonBody, String api) throws IOException {
		return getResponse(jsonBody, api, serverURL);
	}

	public static HttpResponse getResponse(Object jsonBody, String api, String serverURL) throws IOException {
		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(serverURL + api);
		if (Mattermost.SESSION_TOKEN != null && !Mattermost.SESSION_TOKEN.isEmpty()) {
			httppost.addHeader("Authorization", "Bearer " + Mattermost.SESSION_TOKEN);
		}
		httppost.setEntity(new ByteArrayEntity(ModelUtil.GSON.toJson(jsonBody).getBytes()));

		HttpResponse response = httpclient.execute(httppost);
		return response;
	}

	public static String getContent(HttpResponse response) throws IOException {
		InputStream inputStream = response.getEntity().getContent();
		StringWriter writer = new StringWriter();
		IOUtils.copy(inputStream, writer);
		String theString = writer.toString();
		inputStream.close();
		return theString;
	}

	public static Header[] getHeaders(HttpResponse response) {
		return response.getAllHeaders();
	}

	public static Header getHeader(HttpResponse response, String headerName) {
		for (Header header : getHeaders(response)) {
			if (header.getName().equalsIgnoreCase(headerName)) {
				return header;
			}
		}
		return null;
	}

}
