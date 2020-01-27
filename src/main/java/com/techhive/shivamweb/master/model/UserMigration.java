package com.techhive.shivamweb.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.techhive.shivamweb.listners.UserMigrationListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_USERMIGRATION)
@EntityListeners(UserMigrationListner.class)
@JsonInclude(Include.NON_NULL)
public class UserMigration {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "userMigrationId", nullable = false, insertable = false, updatable = false)
	private String id;

	private String username;

	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

	private String email;

	private String firstName;

	private String lastName;

	private String companyAddress;

	private String companyName;

	private String phoneNo;

	private String mobileNo;

	private String gender;

	private String pinCode;

	private String city;

	private String state;

	private String country;

	private String mobileCCode;

	private String teleCCode;

	private String teleACode;

	private String prefix;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
//		if (username.equalsIgnoreCase("null")) {
//			return null;
//		}
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
//		if (password.equalsIgnoreCase("null")) {
//			return null;
//		}
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
//		if (email.equalsIgnoreCase("null")) {
//			return null;
//		}
		
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
//		if (firstName.equalsIgnoreCase("null")) {
//			return null;
//		}
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
//		if (lastName.equalsIgnoreCase("null")) {
//			return null;
//		}
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCompanyAddress() {
//		if (companyAddress.equalsIgnoreCase("null")) {
//			return null;
//		}
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
//		if (companyName.equalsIgnoreCase("null")) {
//			return null;
//		}
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhoneNo() {
//		if (phoneNo.equalsIgnoreCase("null")) {
//			return null;
//		}
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
//		if (mobileNo.equalsIgnoreCase("null")) {
//			return null;
//		}
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
//		if (city.equalsIgnoreCase("null")) {
//			return null;
//		}
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
//		if (state.equalsIgnoreCase("null")) {
//			return null;
//		}
		return state;
	}

	public void setState(String state) {
		
		this.state = state;
	}

	public String getCountry() {
//		if (country.equalsIgnoreCase("null")) {
//			return null;
//		}
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getMobileCCode() {
//		if (mobileCCode.equalsIgnoreCase("null")) {
//			return null;
//		}
		return mobileCCode;
	}

	public void setMobileCCode(String mobileCCode) {
		this.mobileCCode = mobileCCode;
	}

	public String getTeleCCode() {
		
//		if (teleCCode.equalsIgnoreCase("null")) {
//			return null;
//		}
		return teleCCode;
	}

	public void setTeleCCode(String teleCCode) {
		this.teleCCode = teleCCode;
	}

	public String getTeleACode() {
//		if (teleACode.equalsIgnoreCase("null")) {
//			return null;
//		}
		return teleACode;
	}

	public void setTeleACode(String teleACode) {
		this.teleACode = teleACode;
	}

	public String getPrefix() {
//		if (prefix.equalsIgnoreCase("null")) {
//			return null;
//		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
