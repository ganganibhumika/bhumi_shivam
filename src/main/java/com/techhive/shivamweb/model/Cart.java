package com.techhive.shivamweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.enums.EnumForCartStatus;
import com.techhive.shivamweb.enums.EnumForOrderStatus;
import com.techhive.shivamweb.listners.CartListner;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_CART)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(CartListner.class)
public class Cart extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "cartId", insertable = false, nullable = false, updatable = false)
	private String id;

	@ManyToOne
	private User user;

	@Transient
	private String userId;

	@ManyToOne
	// @JsonIgnore
	private PktMaster pktMaster;

	@Transient
	private String pktMasterId;

	private Double discount;

	private Double perCaratPrice;

	private Double Total;

	private String cartStatus;

	@Transient
	private Integer totalNoOfRecords;

	@PrePersist
	public void onCreate() {
		this.cartStatus = EnumForCartStatus.PENDING.toString();
	}

	public Cart() {
		super();
	}

	@PreRemove
	public void onRemove() {
		// PktMaster pktMaster=this.pktMaster;
		// if(!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMaster)) {
		// pktMaster.setCart(null);
		// }
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

	public PktMaster getPktMaster() {
		return pktMaster;
	}

	public void setPktMaster(PktMaster pktMaster) {
		this.pktMaster = pktMaster;
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

	public String getPktMasterId() {
		return pktMasterId;
	}

	public void setPktMasterId(String pktMasterId) {
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMasterId)) {
			this.pktMaster = new PktMaster(pktMasterId);
		}
		this.pktMasterId = pktMasterId;
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

	public Integer getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(Integer totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public String getCartStatus() {
		return cartStatus;
	}

	public void setCartStatus(String cartStatus) {
		this.cartStatus = cartStatus;
	}

}
