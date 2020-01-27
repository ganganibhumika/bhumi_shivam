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
import javax.persistence.OneToMany;
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
import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_PARTYMASTER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class PartyMasterHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "partyMastHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "partyname", columnDefinition = "varchar(30)")
	private String partyname;

	@Column(name = "password", columnDefinition = "varchar(100)")
	private String password;

	private Boolean isActive;
	
	private String email;

	private Boolean isParty;
	
	@OneToMany(mappedBy="partyMasterHistory")
	Set<ThirdPartyDiscountMasterHistory> setOfThirdPartyDiscountMasterHistory;
	public PartyMasterHistory() {
		super();
	}

	public PartyMasterHistory(String id) {
		super();
		this.id = id;
	}

	public PartyMasterHistory(PartyMaster partyMaster, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = partyMaster.getId();
		this.partyname = partyMaster.getPartyname();
		this.password = partyMaster.getPassword();
		this.isActive = partyMaster.getIsActive();
		this.email=partyMaster.getEmail();
		this.isParty = partyMaster.getIsParty();
	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;


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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsParty() {
		return isParty;
	}

	public void setIsParty(Boolean isParty) {
		this.isParty = isParty;
	}

	public Set<ThirdPartyDiscountMasterHistory> getSetOfThirdPartyDiscountMasterHistory() {
		return setOfThirdPartyDiscountMasterHistory;
	}

	public void setSetOfThirdPartyDiscountMasterHistory(
			Set<ThirdPartyDiscountMasterHistory> setOfThirdPartyDiscountMasterHistory) {
		this.setOfThirdPartyDiscountMasterHistory = setOfThirdPartyDiscountMasterHistory;
	}
	
}
