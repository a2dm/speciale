package br.com.a2dm.spdm.notification;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class PushNotification {
	
	private static final String URL_PUSH = "https://api.ionic.io/push/notifications";
	private static final String TOKEN_PUSH = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiIwNzVkYzk3OS00MzA1LTRlYmUtOWI3My02OGY3YjM1YTg5ZDYifQ.HiQeSqOV6L5JpW9RAEgu7Q0GBVPDJpw0SRhHhZg4H4M";
	private static final String PROFILE_PUSH = "producao";
	
	private PushNotification() {
	}
	
	public static PushNotificationResult send(String deviceID, String message, Map<String,String> payloadData) throws IOException {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			
			StringEntity myEntity = new StringEntity(new PushMessageBuilder().build(PROFILE_PUSH, deviceID, message, payloadData), ContentType.create("application/json", "UTF-8"));
			
			HttpPost httpPost = new HttpPost(URL_PUSH);
			httpPost.setEntity(myEntity);
			httpPost.addHeader("Content-Type", "application/json");
			httpPost.setHeader("Authorization", "Bearer " + TOKEN_PUSH);

			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				return new PushNotificationResult(response.getStatusLine().toString(), response.getStatusLine().toString());
			} finally {
				response.close();
			}
		}catch(Exception e){
			throw new IOException(e);
		}
	}
}