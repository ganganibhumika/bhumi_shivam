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
import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SALESPERSON_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class SalesPersonMasterHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "salesPersonMasHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	public SalesPersonMasterHistory() {
		super();
	}

	public SalesPersonMasterHistory(String id) {
		super();
		this.id = id;
	}

	public SalesPersonMasterHistory(SalesPersonMaster salesPersonMaster, SoftwareSalePersonMasterHistory softwareSalePersonMasterHistory, EnumForAction action) {
		super();
		this.action = action;

		this.idOfEntity = salesPersonMaster.getId();
		this.name = salesPersonMaster.getName();

		this.mobileNo = salesPersonMaster.getMobileNo();

		this.email = salesPersonMaster.getEmail();
		this.skype = salesPersonMaster.getSkype();

		this.SoftwareSalePersonMasterHistory = softwareSalePersonMasterHistory;

		this.qQaddress = salesPersonMaster.getqQaddress();

		this.isActive = salesPersonMaster.getIsActive();

		this.isPrimary = salesPersonMaster.getIsPrimary();
	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;

	private String name;

	private String mobileNo;

	private String email;

	private String skype;

	@OneToOne
	private SoftwareSalePersonMasterHistory  SoftwareSalePersonMasterHistory;
	

	@Column(name = "qQaddress", columnDefinition = "varchar(300)")
	private String qQaddress;

	private Boolean isActive;

	private Boolean isPrimary;

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



	public SoftwareSalePersonMasterHistory getSoftwareSalePersonMasterHistory() {
		return SoftwareSalePersonMasterHistory;
	}

	public void setSoftwareSalePersonMasterHistory(SoftwareSalePersonMasterHistory softwareSalePersonMasterHistory) {
		SoftwareSalePersonMasterHistory = softwareSalePersonMasterHistory;
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

}
