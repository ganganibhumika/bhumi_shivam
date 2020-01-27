package com.techhive.shivamweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.enums.EnumForOfferDiscountStatus;
import com.techhive.shivamweb.listners.OfferDiscountRequestListner;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_OFFERDISCOUNTREQUEST)
@JsonInclude(Include.NON_EMPTY)
 @EntityListeners(OfferDiscountRequestListner.class)
public class OfferDiscountRequest extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "offerDiscountId", insertable = false, nullable = false, updatable = false)
	private String id;

	@ManyToOne
	private User user;

	@Transient
	private String userId;

	@ManyToOne
	private PktMaster pktMaster;

	@Transient
	private String pktMasterId;

	private Double userDiscount;

	private String approveStatus;
	
	@Transient
	private Double perCaratePriceNew;
	
	@Transient
	private Double totalPriceNew;

	@Transient
	private Integer totalNoOfRecords;
	
	@PrePersist
	public void onCreate() {
		this.approveStatus = EnumForOfferDiscountStatus.PENDING.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			this.user = new User(userId);
		}
		this.userId = userId;
	}

	public PktMaster getPktMaster() {
		return pktMaster;
	}

	public void setPktMaster(PktMaster pktMaster) {
		this.pktMaster = pktMaster;
	}

	public String getPktMasterId() {
		return pktMasterId;
	}

	public void setPktMasterId(String pktMasterId) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMasterId)) {
			this.pktMaster = new PktMaster(pktMasterId);
		}
		this.pktMasterId = pktMasterId;
	}

	public Double getUserDiscount() {
		return userDiscount;
	}

	public void setUserDiscount(Double userDiscount) {
		this.userDiscount = userDiscount;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public Double getPerCaratePriceNew() {
		return perCaratePriceNew;
	}

	public void setPerCaratePriceNew(Double perCaratePriceNew) {
		this.perCaratePriceNew = perCaratePriceNew;
	}

	public Double getTotalPriceNew() {
		return totalPriceNew;
	}

	public void setTotalPriceNew(Double totalPriceNew) {
		this.totalPriceNew = totalPriceNew;
	}

	public Integer getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(Integer totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	
}
