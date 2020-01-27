package com.techhive.shivamweb.master.model.DTO;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class SalesPersonMasterDto {
	
	private String id;

	private String name;

	private String mobileNo;

	private String email;
	
	private String skype;

	private String softwareUserId;

	private String qQaddress;

	private Boolean isActive;

	private Boolean isPrimary;

	private Set<UserDto> setOfUser;
	
	/**
	 * @author neel 
	 * findAllByIsEmailVerified
	 */
	public SalesPersonMasterDto(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSkype() {
		return skype;
	}

	public void setSkype(String skype) {
		this.skype = skype;
	}

	public String getSoftwareUserId() {
		return softwareUserId;
	}

	public void setSoftwareUserId(String softwareUserId) {
		this.softwareUserId = softwareUserId;
	}

	public String getqQaddress() {
		return qQaddress;
	}

	public void setqQaddress(String qQaddress) {
		this.qQaddress = qQaddress;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Set<UserDto> getSetOfUser() {
		return setOfUser;
	}

	public void setSetOfUser(Set<UserDto> setOfUser) {
		this.setOfUser = setOfUser;
	}
	

}
