package com.franklin.serversms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.franklin.juhesdk.JsonParser;

import android.R.integer;


public class GetSmsObject {
	private String number = null;
	private String body = null;
	private List<String> bodyInfos = new ArrayList<String>();
	private String date = null;
	private boolean valid = false;
	
	public GetSmsObject(String number,String body,String date) {
		// TODO Auto-generated constructor stub
		this.number = number;
		this.body = body;
		this.date = date;
		Pattern patternGps = Pattern.compile("^GPS.*|^��վ��λ.*");  //ƥ����GPS��ͷ���ַ���
		Matcher matcher = patternGps.matcher(body);
		boolean flag  = matcher.matches();
		if(flag) {
			List<String>  infos = new ArrayList<String>();
			if(body.startsWith("GPS")) {  //��GPS��Ϣ
				infos = getGpsInfos(); //ֱ��ȡ��GPS��Ϣ
			} else { //�ǻ�վ��Ϣ
				List<String> bsInfos = getBsInfos(); //�ȵõ���վ��Ϣ
				if(2==bsInfos.size()) {
					infos = new JsonParser().getLonLat(bsInfos); //γ����ǰ�������ں�
				}
			}
			if(2==infos.size()) {
				valid = true;
				this.bodyInfos = infos;
			}
		}
	} 
	
	private List<String> getGpsInfos() { //ȡ�������еľ�γ��
		List<String> bodyInfo = new ArrayList<String>();
		String patternStr  = "\\d{2,3}\\.\\d{6}";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(body);
		while(matcher.find()) {
		    String info = matcher.group();
			if(info!=null) {
				bodyInfo.add(info);
			}
			if(bodyInfo.size()==2) {
				break;
			}
		}
		return bodyInfo;
	}
	
	private List<String> getBsInfos() {  //ȡ�������еĻ�վ��Ϣ
		List<String> bsInfos = new ArrayList<String>();
		int lacStartIndex = body.indexOf("����1:")+4;
		int lacEndIndex = body.indexOf(',');
		String lac = body.substring(lacStartIndex, lacEndIndex);
		int cellidStartIndex = body.indexOf("����2:")+4;
		int cellidEndIndex = body.indexOf(".");
		String cellid = body.substring(cellidStartIndex,cellidEndIndex);
		bsInfos.add(lac);
		bsInfos.add(cellid);
 		return bsInfos;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public boolean getFlag() {
		return valid;
	}

	public List<String> getBodyInfos() {
		return bodyInfos;
	}
}
