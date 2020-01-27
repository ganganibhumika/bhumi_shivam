package com.techhive.shivamweb.master.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.listners.UpcomingFairListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_UPCOMINGFAIR)
@EntityListeners(UpcomingFairListner.class)
@JsonInclude(Include.NON_EMPTY)
public class UpcomingFairMaster extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "upcomingFairId", nullable = false, insertable = false, updatable = false)
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

}
