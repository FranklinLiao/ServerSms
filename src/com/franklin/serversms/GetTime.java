package com.franklin.serversms;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetTime {
	private SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public String getDate() {
		return simpleDateFormat.format(new Date()).substring(0, 8);
	}
	
	public String getTime() {
		return simpleDateFormat.format(new Date());
	}
	
	public String getSendTime() {
		Date date = new Date();
		date.setHours(date.getHours()-8);
		return simpleDateFormat.format(date);
	}
}
