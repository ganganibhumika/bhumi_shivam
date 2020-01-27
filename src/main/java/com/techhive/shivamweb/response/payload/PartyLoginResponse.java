package com.techhive.shivamweb.response.payload;

import com.techhive.shivamweb.master.model.PartyMaster;

public class PartyLoginResponse {

	private String id;
	private String partyname;
	private String email;
	private String token;
	private Boolean isAuthenticated;

	public PartyLoginResponse(PartyMaster party, String token) {
		super();
		this.id = party.getId();
		this.partyname = party.getPartyname();
		this.token = token;
		this.email = party.getEmail();
		this.isAuthenticated = true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyname() {
		return partyname;
	}

	public void setPartyname(String partyname) {
		this.partyname = partyname;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

}
