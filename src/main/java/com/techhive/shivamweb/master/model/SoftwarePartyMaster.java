package com.techhive.shivamweb.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.listners.SoftwarePartyMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SOFTWARE_PATY_MASTER)
@EntityListeners(SoftwarePartyMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class SoftwarePartyMaster {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "softwarePartyMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "partyCode", columnDefinition = "varchar(30)")
	private String partyCode;

	@Column(name = "groupCode", columnDefinition = "varchar(30)")
	private String groupCode;

	@Column(name = "partyName", columnDefinition = "varchar(50)")
	private String partyName;

	@Column(name = "partyCompanyName", columnDefinition = "varchar(50)")
	private String partyCompanyName;

	@Column(name = "address", columnDefinition = "varchar(300)")
	private String address;

	@Column(name = "mobileNo", columnDefinition = "varchar(30)")
	private String mobileNo;

	@Column(name = "telNo", columnDefinition = "varchar(30)")
	private String telNo;

	@Column(name = "emailId", columnDefinition = "varchar(50)")
	private String emailId;

	@Column(name = "country", columnDefinition = "varchar(55)")
	private String country;

	@Column(name = "city", columnDefinition = "varchar(55)")
	private String city;

	@Column(name = "brokerName", columnDefinition = "varchar(30)")
	private String brokerName;

	@Column(name = "remark", columnDefinition = "varchar(100)")
	private String remark;

	@Column(name = "gstin", columnDefinition = "varchar(15)")
	private String gstin;

	@OneToOne(mappedBy = "softwarePartyMaster")
	private User user;

	@PreRemove
	protected void onRemove() {
		User user = this.user;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(user)) {
			user.setSoftwarePartyMaster(null);
		}
	}

	public SoftwarePartyMaster() {
		super();
	}

	public SoftwarePartyMaster(String softwarePartyId) {
		this.id = softwarePartyId;
	}

	public SoftwarePartyMaster(String partyId, String partyName) {
		this.id = partyId;
		this.partyName = partyName;
	}

	public SoftwarePartyMaster(String partyCode, String groupCode, String partyName, String partyCompanyName,
			String address, String mobileNo, String telNo, String emailId, String country, String city,
			String brokerName, String remark, String gstin) {
		super();
		this.partyCode = partyCode;
		this.groupCode = groupCode;
		this.partyName = partyName;
		this.partyCompanyName = partyCompanyName;
		this.address = address;
		this.mobileNo = mobileNo;
		this.telNo = telNo;
		this.emailId = emailId;
		this.country = country;
		this.city = city;
		this.brokerName = brokerName;
		this.remark = remark;
		this.gstin = gstin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyCompanyName() {
		return partyCompanyName;
	}

	public void setPartyCompanyName(String partyCompanyName) {
		this.partyCompanyName = partyCompanyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
