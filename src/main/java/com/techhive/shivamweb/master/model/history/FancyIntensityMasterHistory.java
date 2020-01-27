package com.techhive.shivamweb.master.model.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
import com.techhive.shivamweb.master.model.FancyIntensityMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_FANCYINTENCITYMASTER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class FancyIntensityMasterHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "fancyintMasHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	public FancyIntensityMasterHistory() {
		super();
	}

	public FancyIntensityMasterHistory(FancyIntensityMaster fancyIntensityMaster, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = fancyIntensityMaster.getId();

		this.fancyIntensityName = fancyIntensityMaster.getFancyIntensityName();

		this.fancyIntensityOrder = fancyIntensityMaster.getFancyIntensityOrder();

	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;

	private String fancyIntensityName;

	private Integer fancyIntensityOrder;

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

	public String getFancyIntensityName() {
		return fancyIntensityName;
	}

	public void setFancyIntensityName(String fancyIntensityName) {
		this.fancyIntensityName = fancyIntensityName;
	}

	public Integer getFancyIntensityOrder() {
		return fancyIntensityOrder;
	}

	public void setFancyIntensityOrder(Integer fancyIntensityOrder) {
		this.fancyIntensityOrder = fancyIntensityOrder;
	}
	
	
}
