package com.xinxin.everyxday.base.loopj.requestclient;


public class RequestClientFactory {

	private static AsyncHttpClient client;

	public static AsyncHttpClient getInstance() {

		if (client == null) {
			client = new AsyncHttpClient();
		}

		return client;
	}
	
}
