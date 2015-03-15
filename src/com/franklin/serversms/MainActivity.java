package com.franklin.serversms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.franklin.extend.ClientObject;
import com.franklin.extend.InfoMatch;
import com.franklin.extend.ReceiverObject;
import com.franklin.juhesdk.JsonParser;

public class MainActivity extends Activity {
	final List<String> clientNumbers= Arrays.asList("13552693750","13581753951","18810907486","13552972563","13681292641","13501374909","13681292154");
	final List<String> receiverNumbers = Arrays.asList("15600564512","13948002832","15248310019","13948026225");
	final List<ReceiverObject> receiverObejcts = new InfoMatch().getInfoMatchResult();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//加入这个后，就可以不采用异步操作，在主线程中直接访问网络
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		//new SendSms(MainActivity.this,"15600564512", "haha").sendMessage();
		Button button = (Button) findViewById(R.id.startButton);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//主要的工作
				Iterator<ReceiverObject> iterOut = receiverObejcts.iterator();
				while(iterOut.hasNext()) { //遍历开发板的每个人 {
					ReceiverObject receiverObject = iterOut.next();
					String receiver = receiverObject.getReceiverName();
					List<String> clientNames = receiverObject.getClientName();
					Iterator<String> iterIn = clientNames.iterator();
					while(iterIn.hasNext()) {
						String clientName = iterIn.next();
						ClientObject clientObject = new ClientObject(MainActivity.this,clientName);
						List<String> sendInfos = clientObject.getClientSendInfo();
						Iterator<String> iter = sendInfos.iterator();
						while(iter.hasNext()) {
							String info = iter.next();
							new SendSms(MainActivity.this,receiver, info).sendMessage();
						}
					}
					
					
				}
				/*
				List<List<GetSmsObject>> clientSmss = new GetSmsTool(MainActivity.this,clientNumbers).getAllClientSms();
				Iterator<List<GetSmsObject>> iterOut = clientSmss.iterator();
				while(iterOut.hasNext()) { //遍历开发板的每个人
					Iterator<GetSmsObject> iterIn = iterOut.next().iterator();
					while(iterIn.hasNext()) {//遍历开发板中的每个短信
						GetSmsObject smsObject = iterIn.next();
						Iterator<String> iterRece = receiverNumbers.iterator();
						while(iterRece.hasNext()) {//遍历所有的收件人
							String receiver = iterRece.next();
							String info = new SendSmsObject(smsObject).getSendSms();
							new SendSms(MainActivity.this,receiver, info).sendMessage();
						}
					}
				}
				*/			
				Toast.makeText(getApplicationContext(), "Job Done!", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	
}
