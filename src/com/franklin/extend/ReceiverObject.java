package com.franklin.extend;

import java.util.ArrayList;
import java.util.List;

public class ReceiverObject {
	private String receiverName;
	private List<String> clientName = new ArrayList<String>();
	//private List<String> 
	public ReceiverObject(String receiverName,List<String> clientNames) {
		this.receiverName = receiverName;
		this.clientName.addAll(clientNames);
		
	}
	
	public String getReceiverName() {
		return this.receiverName;
	}
	
	public List<String> getClientName() {
		return this.clientName;
	}
}	
