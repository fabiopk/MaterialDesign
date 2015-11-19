package hue.com.workbench;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import network.VolleySingleton;

public class DataBaseCommunication {
	
	public static final String BASE_URL = "http://192.168.25.6:5984/test/";
	
	public static void sendGet(ICommunication com){
		send(com, Request.Method.GET);
	}
	
	public static void sendPost(ICommunication com ){
		send(com, Request.Method.POST);
	}
	
	private static void send( ICommunication com, int method ){
		VolleySingleton v = VolleySingleton.getInstance();
		RequestQueue rq = v.getRequestQueue();
		
		Log.i("SITAC - URL", com.getUrl() );
		if( com.getData() != null ){
			Log.i("SITAC - GET - DATA", com.getData().toString());
		}	
		
		JsonObjectRequest request = new JsonObjectRequest(
					method,
					com.getUrl(),
					com.getData(), com, com);
		
		rq.add(request);
	}
	
}
