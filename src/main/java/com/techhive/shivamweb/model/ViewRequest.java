package com.techhive.shivamweb.model;

import java.util.Date;

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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.listners.viewRequestListner;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name=ShivamWebVariableUtils.TABLE_NAME_FOR_VIEWREQUEST)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(viewRequestListner.class)
public class ViewRequest extends Auditable<String>{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "viewRequestId", insertable = false, nullable = false, updatable = false)
	private String id;
	
	@ManyToOne
	private User user;
	
	@Transient
	private String userId;
	
	@ManyToOne
	private PktMaster pktMaster;
	
	@Transient
	private String pktMasterId;
	
	private String ipAddress; 
	
	private Boolean inProgress; 
	
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	protected Date endDate;
	
	@PreRemove
	public void onRemove() {
		 PktMaster pktMaster=this.pktMaster;
		 if(!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMaster)) {
			 pktMaster.setConfirmOrder(null);
		 }
	}
	@PrePersist
	public void onCreate() {
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
		if(!ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			this.user=new User(userId);
		}
		this.userId = userId;
	}

	public String getPktMasterId() {
		return pktMasterId;
	}

	public void setPktMasterId(String pktMasterId) {
		if(!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMasterId)) {
			this.pktMaster=new PktMaster(pktMasterId);
		}
		this.pktMasterId = pktMasterId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Boolean getInProgress() {
		return inProgress;
	}
	public void setInProgress(Boolean inProgress) {
		this.inProgress = inProgress;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	
}
