package com.techhive.shivamweb.master.model.DTO;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;

@JsonInclude(Include.NON_EMPTY)
public class UserDto {
	private String id;

	private String firstName;

	private String lastName;

	private String username;

	private String prefix;
	private String password;

	private String companyAddress;

	private String companyName;

	private String phoneNo;

	private String mobileNo;

	private String gender;

	private String email;

	private String pinCode;

	private String city;

	private String state;

	private String country;

	private Boolean isApproved;

	private Boolean isEmailVerified;

	private String token;

	private Boolean isAuthenticated;

	private Boolean isAdmin;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	protected Date createdDate;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	protected Date approveDate;

	private String ipAddress;

	// private Set<DiscountMasterDto> setOfDiscountMaster;

	private SalesPersonMasterDto salesPersonMaster;

	private SoftwarePartyMaster softwarePartyMaster;

	/**
	 * @author bhumi getAllUserFromShow getAllUserFromShow
	 */
	public UserDto(String id, String firstName, String lastName, String username, String email, String salesPersonId,
			String salesPersonName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.salesPersonMaster = new SalesPersonMasterDto(salesPersonId, salesPersonName);
	}

	/**
	 * @author neel findAllClient
	 */
	public UserDto(String id, String firstName, String lastName, String username, String prefix, String companyAddress,
			String companyName, String phoneNo, String mobileNo, String gender, String email, String pinCode,
			String city, String state, String country) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.prefix = prefix;
		this.companyAddress = companyAddress;
		this.companyName = companyName;
		this.phoneNo = phoneNo;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.email = email;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	/**
	 * @author neel getAllUser
	 */
	public UserDto(String id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	/**
	 * @author neel findAllByIsEmailVerified
	 * @param isAdmin
	 */
	public UserDto(String id, String firstName, String lastName, String username, String prefix, String companyAddress,
			String companyName, String phoneNo, String mobileNo, String gender, String email, String pinCode,
			String city, String state, String country, Date createdDate, Date approveDate, String ipAddress,
			Boolean isApproved, String salesPersonId, String salesPersonName, String partyId, String partyName,
			Boolean isAdmin) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.prefix = prefix;
		this.companyAddress = companyAddress;
		this.companyName = companyName;
		this.phoneNo = phoneNo;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.email = email;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.country = country;
		this.createdDate = createdDate;
		this.approveDate = approveDate;
		this.ipAddress = ipAddress;
		this.isApproved = isApproved;
		this.salesPersonMaster = new SalesPersonMasterDto(salesPersonId, salesPersonName);

		this.softwarePartyMaster = new SoftwarePartyMaster(partyId, partyName);
		this.isAdmin = isAdmin;
	}

	/**
	 * @author neel findAllByIsEmailVerifiedSearch
	 * @param isAdmin
	 */
	public UserDto(String id, String firstName, String lastName, String username, String prefix, String companyAddress,
			String companyName, String phoneNo, String mobileNo, String gender, String email, String pinCode,
			String city, String state, String country, Date createdDate, Date approveDate, String ipAddress,
			Boolean isApproved, Boolean isAdmin) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.prefix = prefix;
		this.companyAddress = companyAddress;
		this.companyName = companyName;
		this.phoneNo = phoneNo;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.email = email;
		this.pinCode = pinCode;
		this.city = city;
		this.state = state;
		this.country = country;
		this.createdDate = createdDate;
		this.approveDate = approveDate;
		this.ipAddress = ipAddress;
		this.isApproved = isApproved;
		this.isAdmin = isAdmin;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Boolean getIsAuthenticated() {
		return isAuthenticated;
	}

	public void setIsAuthenticated(Boolean isAuthenticated) {
		this.isAuthenticated = isAuthenticated;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public SalesPersonMasterDto getSalesPersonMaster() {
		return salesPersonMaster;
	}

	public void setSalesPersonMaster(SalesPersonMasterDto salesPersonMaster) {
		this.salesPersonMaster = salesPersonMaster;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public SoftwarePartyMaster getSoftwarePartyMaster() {
		return softwarePartyMaster;
	}

	public void setSoftwarePartyMaster(SoftwarePartyMaster softwarePartyMaster) {
		this.softwarePartyMaster = softwarePartyMaster;
	}

	// private Set<DiscountMasterDto> setOfDiscountMaster;

}
