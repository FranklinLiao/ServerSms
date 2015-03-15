package com.franklin.serversms;

import com.thinkland.sdk.android.SDKInitializer;

import android.app.Application;

public class ServerSms extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());  //³õÊ¼»¯¾ÛºÏSDK
	}
	
}
