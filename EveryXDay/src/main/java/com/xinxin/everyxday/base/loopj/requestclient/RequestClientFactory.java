package com.xinxin.everyxday.base.loopj.requestclient;


import com.loopj.android.http.AsyncHttpClient;

public class RequestClientFactory {

	private static AsyncHttpClient client;

	public static AsyncHttpClient getInstance() {

		if (client == null) {
			client = new AsyncHttpClient();
		}

		return client;
	}
	
}
