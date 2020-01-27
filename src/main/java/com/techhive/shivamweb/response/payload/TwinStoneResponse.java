package com.techhive.shivamweb.response.payload;

import java.util.List;

import com.techhive.shivamweb.master.model.PktMaster;

public class TwinStoneResponse {

	String pair;
	
	List<PktMaster> listOfPkt;
	
	

	public TwinStoneResponse(String pair, List<PktMaster> listOfPkt) {
		super();
		this.pair = pair;
		this.listOfPkt = listOfPkt;
	}

	public String getPair() {
		return pair;
	}

	public void setPair(String pair) {
		this.pair = pair;
	}

	public List<PktMaster> getListOfPkt() {
		return listOfPkt;
	}

	public void setListOfPkt(List<PktMaster> listOfPkt) {
		this.listOfPkt = listOfPkt;
	}
	
	
}
