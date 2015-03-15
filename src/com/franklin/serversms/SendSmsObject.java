package com.franklin.serversms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

public class SendSmsObject {
	private String longitude = null;
	private String latitude = null;
	private String date = null;
	private String ownNumber = null;
	private String body = null;;
	
	public SendSmsObject(GetSmsObject smsObject) {
		this.latitude = smsObject.getBodyInfos().get(0); //���ǵ����ͻصĶ���γ����ǰ�������ں�
		this.longitude = smsObject.getBodyInfos().get(1);
		//�õ���ǰʱ����Ϊ��ȡ��λ��Ϣ��ʱ��
		this.date = new GetTime().getSendTime();
		this.ownNumber = smsObject.getNumber();
		if(ownNumber.startsWith("+86")) {  //	ȥ��ǰ׺+86
			this.ownNumber = ownNumber.substring(3);
		}
		getSendSms();
	}
	private void  getSendSms() {
		this.body = "J:"+longitude+"W:"+latitude+"T:"+date+":"+ownNumber;
	}
	
	public String getInfo() {
		return this.body;
	}
	
}
