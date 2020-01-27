package com.techhive.shivamweb.master.model.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.ThirdPartyDiscountMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_THIRD_PARTY_DISCOUNT_MASTER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class ThirdPartyDiscountMasterHistory {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "discountMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	private double discount;

	private double fromCarat;

	private double toCarat;

	private Integer fromDays;

	private Integer toDays;

	private Boolean isFancy;

	private Boolean isActive;

	private String idOfEntity;

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	public ThirdPartyDiscountMasterHistory() {
		super();
	}
	
	@ManyToOne
	@JsonIgnore
	PartyMasterHistory partyMasterHistory;
	
	@Transient
	private String idOfParty;


	public ThirdPartyDiscountMasterHistory(ThirdPartyDiscountMaster thirdPartyDiscount,
			PartyMasterHistory partyMasterHistory, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity=thirdPartyDiscount.getId();
		this.discount=thirdPartyDiscount.getToCarat();
		this.fromCarat=thirdPartyDiscount.getFromCarat();
		this.toCarat=thirdPartyDiscount.getToCarat();
		this.fromDays=thirdPartyDiscount.getFromDays();
		this.toDays=thirdPartyDiscount.getToDays();
		this.isFancy=thirdPartyDiscount.getIsFancy();
		this.isActive=thirdPartyDiscount.getIsActive();
		this.partyMasterHistory=partyMasterHistory;
	}

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
	}

	public EnumForAction getAction() {
		return action;
	}

	public void setAction(EnumForAction action) {
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getFromCarat() {
		return fromCarat;
	}

	public void setFromCarat(double fromCarat) {
		this.fromCarat = fromCarat;
	}

	public double getToCarat() {
		return toCarat;
	}

	public void setToCarat(double toCarat) {
		this.toCarat = toCarat;
	}

	public Integer getFromDays() {
		return fromDays;
	}

	public void setFromDays(Integer fromDays) {
		this.fromDays = fromDays;
	}

	public Integer getToDays() {
		return toDays;
	}

	public void setToDays(Integer toDays) {
		this.toDays = toDays;
	}

	public Boolean getIsFancy() {
		return isFancy;
	}

	public void setIsFancy(Boolean isFancy) {
		this.isFancy = isFancy;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public PartyMasterHistory getPartyMasterHistory() {
		return partyMasterHistory;
	}

	public void setPartyMasterHistory(PartyMasterHistory partyMasterHistory) {
		this.partyMasterHistory = partyMasterHistory;
	}

}
