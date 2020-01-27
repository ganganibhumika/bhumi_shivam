package com.techhive.shivamweb.master.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.listners.ThirdPartyDiscountMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_THIRD_PARTY_DISCOUNT_MASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(ThirdPartyDiscountMasterListner.class)
public class ThirdPartyDiscountMaster extends Auditable<String> {

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
	
	@Transient
	private Boolean isDefaultUser;

	@ManyToOne
	@JsonIgnore	
	private PartyMaster partyMaster;

	@Transient
	private String idOfParty;

	@Transient
	private Map<String, Object> mapOfOtherDetails = new HashMap<>();
	
	@PreRemove
	protected void onRemove() {
		PartyMaster partyMaster = this.partyMaster;
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(partyMaster)) {
			partyMaster.getSetOfThirdPartyDiscount().remove(this);
		}
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public PartyMaster getPartyMaster() {
		return partyMaster;
	}

	public void setPartyMaster(PartyMaster partyMaster) {
		this.partyMaster = partyMaster;
	}

	public String getIdOfParty() {
		return ShivamWebMethodUtils.isObjectisNullOrEmpty(idOfParty) ? idOfParty : this.partyMaster.getId();
	}

	public void setIdOfParty(String idOfParty) {
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(idOfParty)) {
			this.setPartyMaster(new PartyMaster(idOfParty));
		}
		this.idOfParty = idOfParty;
	}

	public Map<String, Object> getMapOfOtherDetails() {
		this.mapOfOtherDetails = new HashMap<>();
		
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(this.partyMaster)) {
				mapOfOtherDetails.put("id", this.partyMaster.getId());
				mapOfOtherDetails.put("partyName", this.partyMaster.getPartyname());
			}
		return mapOfOtherDetails;
	}

	public void setMapOfOtherDetails(Map<String, Object> mapOfOtherDetails) {
		this.mapOfOtherDetails = mapOfOtherDetails;
	}

	public Boolean getIsDefaultUser() {
		return isDefaultUser;
	}

	public void setIsDefaultUser(Boolean isDefaultUser) {
		this.isDefaultUser = isDefaultUser;
	}

}
