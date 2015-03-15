package com.franklin.serversms;

import java.awt.font.NumericShaper;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;

@SuppressWarnings("deprecation")
public class SendSms {
	private String number = null;
	private String body = null;
	private Context context = null;
	SendSms(Context context,String number,String body) {
		this.context = context;
		this.number = number;
		this.body = body;
	}
	
	public void sendMessage() {
		SmsManager smsManager = SmsManager.getDefault();
		//PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
		if(body.length() > 70) {  //短信大于70字那么就要分成多条短信发送
			 List<String> bodys = smsManager.divideMessage(body);
			 for(String partBody:bodys) {
				 smsManager.sendTextMessage(number, null, partBody, null, null);
			 }
		} else {
			 smsManager.sendTextMessage(number, null, body, null, null);
		}
	}
}
