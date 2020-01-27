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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.master.model.UpcomingFairMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_UPCOMINGFAIR_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class UpcomingFairHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "upcomingFairHistId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "imageTitle", columnDefinition = "varchar(30)")
	private String imageTitle;

	@Column(name = "image", columnDefinition = "varchar(50)")
	private String image;
	
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	@Column(name = "startDate", columnDefinition = "date")
	private Date startDate;
	
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	@Column(name = "endDate", columnDefinition = "date")
	private Date endDate;

	public Boolean isFairActive;

	public UpcomingFairHistory() {
		super();
	}

	public UpcomingFairHistory(String id) {
		super();
		this.id = id;
	}

	public UpcomingFairHistory(UpcomingFairMaster upcomingFairMaster, EnumForAction action) {
		super();
		this.action = action;
		this.idOfEntity = upcomingFairMaster.getId();
		this.imageTitle=upcomingFairMaster.getImageTitle();
		this.image=upcomingFairMaster.getImage();
		this. startDate=upcomingFairMaster.getStartDate();
		this.endDate=upcomingFairMaster.getEndDate();
		this. isFairActive=upcomingFairMaster.getIsFairActive();
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

	public String getImageTitle() {
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		this.imageTitle = imageTitle;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Boolean getIsFairActive() {
		return isFairActive;
	}

	public void setIsFairActive(Boolean isFairActive) {
		this.isFairActive = isFairActive;
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
