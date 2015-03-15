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
        //提取当天接收到的短信
        String selCondition = "address like ?";
        String selValue[] = {"%"+clientNumber};  //此处特意将%放在后面  考虑到查询值和%都需要用引号   不能将%放到条件中
        //String selCondition = "date like ?"; //不知道如何在数据库中将long型的时间转换为string型  所以数据库检索时没有加入时间限制
        //String selValue[] = {new GetTime().getDate()+"%"};  //此处特意将%放在后面  考虑到查询值和%都需要用引号   不能将%放到条件中
        @SuppressWarnings("deprecation")
        Cursor cursor = activity.managedQuery(uri, projection,selCondition,selValue // WHERE clause.   , selectionArgs,
                ,"date desc");
        if (cursor.moveToFirst() != false) {  //cursor中不为空
            int addressColumn = cursor.getColumnIndex("address");
            int bodyColumn = cursor.getColumnIndex("body");
            int dateColumn = cursor.getColumnIndex("date");
            int cnt = cursor.getCount();
            for(cursor.moveToFirst();!cursor.isLast();cursor.moveToNext()) {
            	 String address = cursor.getString(addressColumn);
            	 //得到日期时间
            	 String dateInfo = cursor.getString(dateColumn); //返回一个long型的时间
                 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                 Date d = new Date(Long.parseLong(dateInfo));
                 String date = dateFormat.format(d);
                if(date.indexOf(new GetTime().getDate())!=-1) { //是当天收到的短信
                    String bodyInfo = cursor.getString(bodyColumn);
                    GetSmsObject smsObeject = new GetSmsObject(address, bodyInfo,date);
                    if(smsObeject.getFlag()) { //如果短信中的内容的确有效，那么放入到list中
                    	oneClientSms.add(smsObeject);
                    }
                } else { //没有当日的短信
                	break;
                }
            }
            cursor.close();
            cursor = null;
        }
        return oneClientSms;
	}


}
