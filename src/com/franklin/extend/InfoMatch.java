package com.franklin.extend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class InfoMatch {
	private List<ReceiverObject> receiverObjects = new ArrayList<ReceiverObject>();
	private String file = "/com/franklin/extend/ClientReceiver.properties";
	//private String file = "/com/franklin/extend/ClientReceiverTemp.properties";
	public InfoMatch() {
		try {
			InputStream inputStream = getClass().getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			String receiverName=null;
			List<String> clientName = new ArrayList<String>();
			while((line=br.readLine())!=null) {
				if(line.contains(":")) {
					if(receiverName!=null&&clientName.size()>0) {
						addInfoMatch(receiverName,clientName);
						clientName.clear();
					}
					receiverName = line.substring(0, line.indexOf(":")).trim();
				} else {
					clientName.add(line.trim());
				}
			}
			if(receiverName!=null&&clientName.size()>0) {
				addInfoMatch(receiverName,clientName);
				clientName.clear();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("cannot find the file!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void addInfoMatch(String recieverName,List<String> clientNames) {
		ReceiverObject receiverObject = new ReceiverObject(recieverName,clientNames);
		this.receiverObjects.add(receiverObject);
	}
	
	public List<ReceiverObject> getInfoMatchResult() {
		return this.receiverObjects;
	}
}
