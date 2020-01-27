package com.techhive.shivamweb.response.payload;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.UserMigration;

public class LoginResponse {

	private String id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String token;
	private Boolean isAuthenticated;
	private Boolean isAdmin;
	private UserMigration migration;
	private Boolean isMigration;

	// use for appplication
	private String androidVersion;
	private String iosVersion;
	
	public LoginResponse(User user, UserMigration migration) {
		super();
		this.id = user.getId();
		this.userName = user.getUsername();
		this.email = user.getEmail();
		this.token = user.getToken();
		this.isAdmin = user.getIsAdmin();
		this.firstName = user.getFirstName();
		this.isAuthenticated = user.getIsAuthenticated();
		this.lastName=user.getLastName();
		this.migration = migration;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = "Bearer " + token;
	}

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public UserMigration getMigration() {
		return migration;
	}

	public void setMigration(UserMigration migration) {
		this.migration = migration;
	}

	public Boolean getIsMigration() {
		return isMigration;
	}

	public void setIsMigration(Boolean isMigration) {
		this.isMigration = isMigration;
	}

	public String getAndroidVersion() {
		return androidVersion;
	}

	public void setAndroidVersion(String androidVersion) {
		this.androidVersion = androidVersion;
	}

	public String getIosVersion() {
		return iosVersion;
	}

	public void setIosVersion(String iosVersion) {
		this.iosVersion = iosVersion;
	}
	

}
