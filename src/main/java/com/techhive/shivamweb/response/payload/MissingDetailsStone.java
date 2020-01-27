package com.techhive.shivamweb.response.payload;

import java.util.Set;

public class MissingDetailsStone {
	
	private Set<String> totalStoneId;
	
	private Set<String> dataAvailableStoneId;
	
	private Set<String> dataUnAvailableStoneId;
	
	private Set<String> soldStoneId;

	public Set<String> getTotalStoneId() {
		return totalStoneId;
	}

	public void setTotalStoneId(Set<String> totalStoneId) {
		this.totalStoneId = totalStoneId;
	}

	public Set<String> getDataAvailableStoneId() {
		return dataAvailableStoneId;
	}

	public void setDataAvailableStoneId(Set<String> dataAvailableStoneId) {
		this.dataAvailableStoneId = dataAvailableStoneId;
	}

	public Set<String> getDataUnAvailableStoneId() {
		return dataUnAvailableStoneId;
	}

	public void setDataUnAvailableStoneId(Set<String> dataUnAvailableStoneId) {
		this.dataUnAvailableStoneId = dataUnAvailableStoneId;
	}

	public Set<String> getSoldStoneId() {
		return soldStoneId;
	}

	public void setSoldStoneId(Set<String> soldStoneId) {
		this.soldStoneId = soldStoneId;
	}


	
	
}
