package br.com.a2dm.spdm.notification;

import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class PushMessageBuilder {

	public PushMessageBuilder() {
	}
	
	public String build(String profile, String deviceID, String message, Map<String,String> payloadData){
		
		JSONObject data = new JSONObject();
		data.put("tokens", buildTokens(deviceID));
		data.put("profile", profile);
		data.put("notification", buildNotification(message, payloadData));
		
		return data.toJSONString();
	}
	
	private JSONArray buildTokens(String deviceID){
		JSONArray tokens = new JSONArray();
		tokens.add(deviceID);
		return tokens;
	}
	
	private JSONObject buildNotification(String message, Map<String,String> payloadData){
		JSONObject notification = new JSONObject();
		notification.put("message", message);
		notification.put("title", "");
		if(payloadData != null && !payloadData.isEmpty()){
			JSONObject payload = buildPayload(payloadData);
			notification.put("android", buildAndroid(payload));
		}
		return notification;
	}
	
	private JSONObject buildAndroid(JSONObject payload){
		JSONObject android = new JSONObject();
		android.put("payload", payload);
		return android;
	}
	
	private JSONObject buildPayload(Map<String,String> payloadData){
		JSONObject payload = new JSONObject();
		for(Map.Entry<String, String> entry : payloadData.entrySet()){
			payload.put(entry.getKey(), entry.getValue());
		}
		return payload;
	}

}
