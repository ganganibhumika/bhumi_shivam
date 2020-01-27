/**
 * @author Heena
 */
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
import com.techhive.shivamweb.listners.DiscountMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_DISCOUNTMASTER)
@EntityListeners(DiscountMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class DiscountMaster extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "discountMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	private Double discount;

	private Double toCarat;

	private Double fromCarat;

	private Integer toDays;

	private Integer fromDays;

	private Boolean isFancy;

	private Boolean isActive;

	private Boolean isDefaultUser;

	private Integer discountMasterOrder;

	@ManyToOne
	@JsonIgnore
	private User user;

	@Transient
	private String idOfUser;

	@Transient
	private Map<String, Object> mapOfOtherDetails = new HashMap<>();

	@PreRemove
	protected void onRemove() {
		User user = this.user;
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(user)) {
			user.getSetOfDiscountMaster().remove(this);
		}

	}

	public Boolean getIsDefaultUser() {
		return isDefaultUser;
	}

	public void setIsDefaultUser(Boolean isDefaultUser) {
		this.isDefaultUser = isDefaultUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getToCarat() {
		return toCarat;
	}

	public void setToCarat(Double toCarat) {
		this.toCarat = toCarat;
	}

	public Double getFromCarat() {
		return fromCarat;
	}

	public void setFromCarat(Double fromCarat) {
		this.fromCarat = fromCarat;
	}

	public Integer getToDays() {
		return toDays;
	}

	public void setToDays(Integer toDays) {
		this.toDays = toDays;
	}

	public Integer getFromDays() {
		return fromDays;
	}

	public void setFromDays(Integer fromDays) {
		this.fromDays = fromDays;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getIdOfUser() {
		return ShivamWebMethodUtils.isObjectNullOrEmpty(idOfUser) ? idOfUser : this.user.getId();
	}

	public void setIdOfUser(String idOfUser) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(idOfUser)) {
			this.setUser(new User(idOfUser));
		}
		this.idOfUser = idOfUser;
	}

	public Map<String, Object> getMapOfOtherDetails() {
		this.mapOfOtherDetails = new HashMap<>();
		if (this.isDefaultUser!= null && this.isDefaultUser==true) {
			mapOfOtherDetails.put("id",ShivamWebVariableUtils.DEFAULT_USER_NAME);
			mapOfOtherDetails.put("username", ShivamWebVariableUtils.DEFAULT_USER_NAME);
		} else {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(this.user)) {
				mapOfOtherDetails.put("id", this.user.getId());
				mapOfOtherDetails.put("username", this.user.getUsername());
			}
		}
		return mapOfOtherDetails;
	}

	public void setMapOfOtherDetails(Map<String, Object> mapOfOtherDetails) {
		this.mapOfOtherDetails = mapOfOtherDetails;
	}

	public Integer getDiscountMasterOrder() {
		return discountMasterOrder;
	}

	public void setDiscountMasterOrder(Integer discountMasterOrder) {
		this.discountMasterOrder = discountMasterOrder;
	}

}
