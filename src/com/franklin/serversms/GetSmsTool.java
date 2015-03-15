package com.franklin.serversms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;

public class GetSmsTool {
	//private List<String> clientNumbers = null;
	private Activity activity = null;
	private static String SMS_URI_INBOX = "content://sms/inbox";
	/*
	public GetSmsTool(Activity activity,List<String> clientNumbers) {
		// TODO Auto-generated constructor stub
		this.clientNumbers = clientNumbers;
		this.activity = activity;
	}
	*/
	public GetSmsTool(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}
	
	/*
	public List<List<GetSmsObject>> getAllClientSms() {
		Iterator<String> iterator  = clientNumbers.iterator();
		List<List<GetSmsObject>> clientsSms = new ArrayList<List<GetSmsObject>>();
		while(iterator.hasNext()) {
			String clientNumber = iterator.next();
			List<GetSmsObject> oneClientSms = getOneClientSms(clientNumber);
			if(oneClientSms.size()>0) {
				clientsSms.add(oneClientSms);
			}
		}
		return clientsSms;
	}
	*/
	public List<GetSmsObject> getOneClientSms(String clientNumber) {
		List<GetSmsObject> oneClientSms =  new ArrayList<GetSmsObject>();;
        Uri uri = Uri.parse(SMS_URI_INBOX);
        String[] projection = new String[] {"address","body","date"};
        //��ȡ������յ��Ķ���
        String selCondition = "address like ?";
        String selValue[] = {"%"+clientNumber};  //�˴����⽫%���ں���  ���ǵ���ѯֵ��%����Ҫ������   ���ܽ�%�ŵ�������
        //String selCondition = "date like ?"; //��֪����������ݿ��н�long�͵�ʱ��ת��Ϊstring��  �������ݿ����ʱû�м���ʱ������
        //String selValue[] = {new GetTime().getDate()+"%"};  //�˴����⽫%���ں���  ���ǵ���ѯֵ��%����Ҫ������   ���ܽ�%�ŵ�������
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection,selCondition,selValue // WHERE clause.   , selectionArgs,
                ,"date desc");
        if (cursor.moveToFirst() != false) {  //cursor�в�Ϊ��
            int addressColumn = cursor.getColumnIndex("address");
            int bodyColumn = cursor.getColumnIndex("body");
            int dateColumn = cursor.getColumnIndex("date");
            int cnt = cursor.getCount();
            for(cursor.moveToFirst();!cursor.isLast();cursor.moveToNext()) {
            	 String address = cursor.getString(addressColumn);
            	 //�õ�����ʱ��
            	 String dateInfo = cursor.getString(dateColumn); //����һ��long�͵�ʱ��
                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                 Date d = new Date(Long.parseLong(dateInfo));
                 String date = dateFormat.format(d);
                if(date.indexOf(new GetTime().getDate())!=-1) { //�ǵ����յ��Ķ���
                    String bodyInfo = cursor.getString(bodyColumn);
                    GetSmsObject smsObeject = new GetSmsObject(address, bodyInfo,date);
                    if(smsObeject.getFlag()) { //��������е����ݵ�ȷ��Ч����ô���뵽list��
                    	oneClientSms.add(smsObeject);
                    }
                } else { //û�е��յĶ���
                	break;
                }
            }
            cursor.close();
            cursor = null;
        }
        return oneClientSms;
	}


}
