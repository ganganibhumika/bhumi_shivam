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
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_NEWARRIVALSETTINGS_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class NewArrivalSettingsHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "newArrivalSettingsHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	private Integer noOfDays;

	public NewArrivalSettingsHistory() {
		super();
	}

	public NewArrivalSettingsHistory(NewArrivalSettings newArrivalSettings, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = newArrivalSettings.getId();
		this.noOfDays = newArrivalSettings.getNoOfDays();
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

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
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

}
