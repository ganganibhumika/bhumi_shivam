package com.techhive.shivamweb.master.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.listners.UserListner;
import com.techhive.shivamweb.master.model.DTO.SalesPersonMasterDto;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_USER)
@EntityListeners(UserListner.class)
@JsonInclude(Include.NON_EMPTY)
public class User extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "userId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "firstName", columnDefinition = "varchar(30)")
	private String firstName;

	@Column(name = "lastName", columnDefinition = "varchar(30)")
	private String lastName;

	@Column(name = "username", columnDefinition = "varchar(30)")
	private String username;

	@Column(name = "prefix", columnDefinition = "varchar(8)")
	private String prefix;

	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", columnDefinition = "varchar(100)")
	private String password;

	@Column(name = "companyAddress", columnDefinition = "varchar(300)")
	private String companyAddress;

	@Column(name = "companyName", columnDefinition = "varchar(100)")
	private String companyName;

	@Column(name = "phoneNo", columnDefinition = "varchar(50)")
	private String phoneNo;

	@Column(name = "mobileNo", columnDefinition = "varchar(30)")
	private String mobileNo;

	@Column(name = "gender", columnDefinition = "varchar(10)")
	private String gender;

	@Column(name = "email", columnDefinition = "varchar(50)")
	private String email;

	@Column(name = "pinCode", columnDefinition = "varchar(20)")
	private String pinCode;

	@Column(name = "city", columnDefinition = "varchar(55)")
	private String city;

	@Column(name = "state", columnDefinition = "varchar(55)")
	private String state;

	@Column(name = "country", columnDefinition = "varchar(55)")
	private String country;

	@Column(name = "profile", columnDefinition = "varchar(50)")
	private String profile;

	@Column(name = "mobileCCode", columnDefinition = "varchar(30)")
	private String mobileCCode;

	@Column(name = "teleCCode", columnDefinition = "varchar(30)")
	private String teleCCode;

	@Column(name = "teleACode", columnDefinition = "varchar(30)")
	private String teleACode;

	@Column(name = "fcmToken", columnDefinition = "varchar(250)")
	private String fcmToken;

	private Boolean isApproved;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	protected Date approveDate;

	private String ipAddress;

	private Boolean isAdmin;

	private Boolean isEmailVerified;

	private Boolean isShow;

	/**
	 * @author bhumi
	 */
	private Date pwdRequestDateTime;

	// extra filed for angular

	@Transient
	private String token;

	@Transient
	private Boolean isAuthenticated;
	/**
	 * mapping with discount master
	 * 
	 * @author Heena
	 */
	@OneToMany(mappedBy = "user")
	@OrderBy("createdDate")
	private Set<DiscountMaster> setOfDiscountMaster;

	/**
	 * heena
	 * 
	 * @param id
	 */

	public User(String id) {
		this.id = id;
	}

	public User() {
		super();
	}

	public User(String id, String username) {
		super();
		this.id = id;
		this.username = username;
	}


	@ManyToOne
	@JsonIgnore
	private SalesPersonMaster salesPersonMaster;

	@OneToOne
	@JsonIgnore
	private SoftwarePartyMaster softwarePartyMaster;

	@Transient
	private String softwarePartyId;

	@Transient
	private String salesPersonId;
	private Boolean isDeleted;
	private Boolean isSuperAdmin;

	@PreRemove
	protected void onRemove() {
		Set<DiscountMaster> setOfDiscountMaster = this.setOfDiscountMaster;
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(setOfDiscountMaster)) {
			setOfDiscountMaster.forEach(discountMaster -> {
				discountMaster.setUser(null);
			});
		}
		SalesPersonMaster salesPersonMaster = this.salesPersonMaster;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(salesPersonMaster)) {
			salesPersonMaster.getSetOfUser().remove(this);
		}
		SoftwarePartyMaster softwarePartyMaster = this.softwarePartyMaster;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(softwarePartyMaster)) {
			softwarePartyMaster.setUser(null);
		}

	}

	@PrePersist
	protected void onCreate() {
		isDeleted = false;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
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

	public Set<DiscountMaster> getSetOfDiscountMaster() {
		return setOfDiscountMaster;
	}

	public void setSetOfDiscountMaster(Set<DiscountMaster> setOfDiscountMaster) {
		this.setOfDiscountMaster = setOfDiscountMaster;
	}

	public SalesPersonMaster getSalesPersonMaster() {
		return salesPersonMaster;
	}

	public void setSalesPersonMaster(SalesPersonMaster salesPersonMaster) {
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

	public String getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(String salesPersonId) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(salesPersonId)) {
			this.salesPersonMaster = new SalesPersonMaster(salesPersonId);
		}
		this.salesPersonId = salesPersonId;
	}

	public Date getPwdRequestDateTime() {
		return pwdRequestDateTime;
	}

	public void setPwdRequestDateTime(Date pwdRequestDateTime) {
		this.pwdRequestDateTime = pwdRequestDateTime;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getMobileCCode() {
		return mobileCCode;
	}

	public void setMobileCCode(String mobileCCode) {
		this.mobileCCode = mobileCCode;
	}

	public String getTeleCCode() {
		return teleCCode;
	}

	public void setTeleCCode(String teleCCode) {
		this.teleCCode = teleCCode;
	}

	public String getTeleACode() {
		return teleACode;
	}

	public void setTeleACode(String teleACode) {
		this.teleACode = teleACode;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public SoftwarePartyMaster getSoftwarePartyMaster() {
		return softwarePartyMaster;
	}

	public void setSoftwarePartyMaster(SoftwarePartyMaster softwarePartyMaster) {
		this.softwarePartyMaster = softwarePartyMaster;
	}

	public String getSoftwarePartyId() {
		return softwarePartyId;
	}

	public void setSoftwarePartyId(String softwarePartyId) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(softwarePartyId)) {
			this.softwarePartyMaster = new SoftwarePartyMaster(softwarePartyId);
		}
		this.softwarePartyId = softwarePartyId;
	}

	public Boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(Boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

}
