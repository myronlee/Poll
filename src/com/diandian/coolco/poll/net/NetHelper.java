package com.diandian.coolco.poll.net;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetHelper {
	
	private final static String TAG = NetHelper.class.getName();

	public static String request(HttpClient client, String url, String reqJsonStr) {
		try {
			HttpPost post = new HttpPost(new URI(url));
			post.setEntity(new StringEntity(reqJsonStr));
			HttpResponse response = client.execute(post);
			int status = response.getStatusLine().getStatusCode();
			if (200 == status) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				} else {
					Log.e(TAG, "response.getEntity() return null");
					return null;
				}
			} else {
				Log.e(TAG, "response.getStatusLine().getStatusCode() = " + status);
				return null;
			}
		} catch (Exception e) {
			Log.e(TAG, "exception " + e.toString());
			return null;
		}
	}
	
	public static boolean IsNetConnected(Context context) {
		try {
			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkinfo = connManager.getActiveNetworkInfo();
			if (networkinfo == null || !networkinfo.isAvailable()) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
