package com.techhive.shivamweb.master.model.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.DiscountMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_DISCOUNT_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class DiscountMasterHistory  {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "discountHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	private Double discount;

	private Double toCarat;

	private Double fromCarat;

	private Integer toDays;

	private Integer fromDays;

	private Boolean isFancy;

	private Boolean isActive;
	
	private Boolean isDefaultUser;
	
	private String idOfEntity;
	
	private Integer discountMasterOrder;

	@Enumerated(EnumType.STRING)
	EnumForAction action;
	
	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@ManyToOne
	private UserHistory userHistory;

	@Transient
	private String idOfUser;

	public DiscountMasterHistory() {
		super();
	}

	public DiscountMasterHistory(DiscountMaster discount,UserHistory userfromHistory, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = discount.getId();
		this.discount = discount.getDiscount();
		this.toCarat = discount.getToCarat();
		this.fromCarat = discount.getFromCarat();
		this.toDays = discount.getToDays();
		this.fromDays = discount.getFromDays();
		this.isDefaultUser=discount.getIsActive();
		this.isFancy = discount.getIsFancy();
		this.isActive = discount.getIsActive();
		this.userHistory = userfromHistory;
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
		return idOfUser;
	}

	public void setIdOfUser(String idOfUser) {
		this.idOfUser = idOfUser;
	}

	public EnumForAction getAction() {
		return action;
	}

	public void setAction(EnumForAction action) {
		this.action = action;
	}


	public UserHistory getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(UserHistory userHistory) {
		this.userHistory = userHistory;
	}

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
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

	public Integer getDiscountMasterOrder() {
		return discountMasterOrder;
	}

	public void setDiscountMasterOrder(Integer discountMasterOrder) {
		this.discountMasterOrder = discountMasterOrder;
	}
	

}
