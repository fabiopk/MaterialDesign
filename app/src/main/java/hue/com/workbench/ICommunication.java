package hue.com.workbench;

import org.json.JSONObject;

import com.android.volley.Response;

public interface ICommunication  extends
	Response.Listener<JSONObject>, Response.ErrorListener {
	
	public String getUrl();
	public JSONObject getData();
	
}
