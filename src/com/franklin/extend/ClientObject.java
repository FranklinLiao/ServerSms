package com.franklin.extend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.franklin.serversms.GetSmsObject;
import com.franklin.serversms.GetSmsTool;
import com.franklin.serversms.SendSmsObject;

import android.app.Activity;

public class ClientObject {
	private String clientName;
	private List<String> clientSendInfo = new ArrayList<String>();
	
	public ClientObject(Activity activity,String clientName) {
		this.clientName = clientName;
		List<GetSmsObject> clientSendObject = new GetSmsTool(activity).getOneClientSms(clientName);
		
		Iterator<GetSmsObject> iterator  = clientSendObject.iterator();
		while(iterator.hasNext()) {
			GetSmsObject smsObject = iterator.next();
			SendSmsObject sendSmsObject = new SendSmsObject(smsObject);
			if(sendSmsObject.getInfo()!=null) {
				clientSendInfo.add(sendSmsObject.getInfo());
			}
		}
	}
	
	public List<String> getClientSendInfo() {
		return this.clientSendInfo;
	}
} 
