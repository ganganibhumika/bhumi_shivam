package com.techhive.shivamweb.master.model.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SOFTWAREPARTY_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class SoftwarePartyMasterHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "softwarePartyHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	public SoftwarePartyMasterHistory() {
		super();
	}

	public SoftwarePartyMasterHistory(String id) {
		super();
		this.id = id;
	}

	public SoftwarePartyMasterHistory(SoftwarePartyMaster softwarePartyMaster,  EnumForAction action) {
		super();
		this.action = action;

		this.idOfEntity = softwarePartyMaster.getId();

		this.mobileNo = softwarePartyMaster.getMobileNo();
		
		this. partyCode= softwarePartyMaster.getPartyCode();

		this.groupCode= softwarePartyMaster.getGroupCode();

		this.partyName= softwarePartyMaster.getPartyName();

		this.partyCompanyName= softwarePartyMaster.getPartyCompanyName();

		this. address= softwarePartyMaster.getAddress();

		this. telNo= softwarePartyMaster.getTelNo();

		this. emailId= softwarePartyMaster.getEmailId();

		this.country= softwarePartyMaster.getCountry();

		this. city= softwarePartyMaster.getCity();

		this.brokerName= softwarePartyMaster.getBrokerName();

		this.remark= softwarePartyMaster.getRemark();

		this. gstin= softwarePartyMaster.getGstin();

	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;


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
	private UserHistory user;

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

	public UserHistory getUser() {
		return user;
	}

	public void setUser(UserHistory user) {
		this.user = user;
	}



}
