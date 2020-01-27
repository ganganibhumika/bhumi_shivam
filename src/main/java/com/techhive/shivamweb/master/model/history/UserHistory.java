package com.techhive.shivamweb.master.model.history;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_USER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class UserHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "userHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	public UserHistory() {
	}

	public UserHistory(String id) {
		super();
		this.id = id;
	}
	

	public UserHistory(User user, SalesPersonMasterHistory salesPersonMasterHistory,SoftwarePartyMasterHistory softwarePartyMasterHistory, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = user.getId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.prefix = user.getPrefix();
		this.password = user.getPassword();
		this.companyAddress = user.getCompanyAddress();
		this.companyName = user.getCompanyName();
		this.mobileNo = user.getMobileNo();
		this.gender = user.getGender();
		this.email = user.getEmail();
		this.pinCode = user.getPinCode();
		this.city = user.getCity();
		this.country = user.getCountry();
		this.isApproved = user.getIsApproved();
		this.isAdmin = user.getIsAdmin();
		this.isEmailVerified = user.getIsEmailVerified();
		this.isDeleted = user.getIsDeleted();
		this.mobileCCode = user.getMobileCCode();
		this.teleCCode = user.getTeleCCode();
		this.teleACode = user.getTeleACode();
		this.profile = user.getProfile();
		this.isShow=user.getIsShow();
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(salesPersonMasterHistory.getId()))
			this.salesPersonMaster = salesPersonMasterHistory;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(softwarePartyMasterHistory.getId()))
			this.softwarePartyMaster = softwarePartyMasterHistory;
		
	}

	/**
	 * mapping with discount master
	 * 
	 * @author Heena
	 */
	@OneToMany
	private Set<DiscountMasterHistory> setOfDiscountMaster;
	
	@ManyToOne
	private SalesPersonMasterHistory salesPersonMaster;
	
	@OneToOne
	SoftwarePartyMasterHistory softwarePartyMaster;

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;

	@Column(name = "firstName", columnDefinition = "varchar(30)")
	private String firstName;

	@Column(name = "lastName", columnDefinition = "varchar(30)")
	private String lastName;

	@Column(name = "username", columnDefinition = "varchar(30)")
	private String username;

	@Column(name = "prefix", columnDefinition = "varchar(8)")
	private String prefix;

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

	private Boolean isApproved;

	private Boolean isAdmin;

	private Boolean isEmailVerified;

	private Boolean isDeleted;
	
	private Boolean isShow;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumForAction getAction() {
		return action;
	}

	public void setAction(EnumForAction action) {
		this.action = action;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
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

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public SalesPersonMasterHistory getSalesPersonMaster() {
		return salesPersonMaster;
	}

	public void setSalesPersonMaster(SalesPersonMasterHistory salesPersonMaster) {
		this.salesPersonMaster = salesPersonMaster;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public SoftwarePartyMasterHistory getSoftwarePartyMaster() {
		return softwarePartyMaster;
	}

	public void setSoftwarePartyMaster(SoftwarePartyMasterHistory softwarePartyMaster) {
		this.softwarePartyMaster = softwarePartyMaster;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	

}
