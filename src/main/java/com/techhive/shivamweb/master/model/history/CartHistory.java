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
import com.techhive.shivamweb.model.Cart;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_CART_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class CartHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "cartHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	private String cartStatus;

	public CartHistory() {
		super();
	}

	public CartHistory(Cart cart, EnumForAction action, UserHistory userHistory, PktMasterHistory pktMasterHistory) {
		super();
		this.action = action;
		this.idOfEntity = cart.getId();
		this.user = userHistory;
		this.pktMaster = pktMasterHistory;
		this.Total = cart.getDiscount();
		this.perCaratPrice = cart.getPerCaratPrice();
		this.discount = cart.getDiscount();
		this.cartStatus = cart.getCartStatus();
	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;

	private Double perCaratPrice;

	private Double Total;

	private Double discount;

	@OneToOne
	private UserHistory user;

	@OneToOne
	private PktMasterHistory pktMaster;

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

	public UserHistory getUser() {
		return user;
	}

	public void setUser(UserHistory user) {
		this.user = user;
	}

	public PktMasterHistory getPktMaster() {
		return pktMaster;
	}

	public void setPktMaster(PktMasterHistory pktMaster) {
		this.pktMaster = pktMaster;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getPerCaratPrice() {
		return perCaratPrice;
	}

	public void setPerCaratPrice(Double perCaratPrice) {
		this.perCaratPrice = perCaratPrice;
	}

	public Double getTotal() {
		return Total;
	}

	public void setTotal(Double total) {
		Total = total;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

}
