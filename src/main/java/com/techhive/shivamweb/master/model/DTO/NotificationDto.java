package com.techhive.shivamweb.master.model.DTO;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Temporal;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;

@JsonInclude(Include.NON_EMPTY)
public class NotificationDto {
	private String id;

	private String description;

	private String category;

	private Boolean isAdmin;

	private String stoneOrUserId;

	private Boolean isRead;

	private Boolean isShow;

	@CreatedDate
	@Temporal(TIMESTAMP)
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	protected Date createdDate;

	/***
	 * @author neel getAllNotificationByUserId
	 * @param createdDate
	 */
	public NotificationDto(String id, String description, String category, Boolean isAdmin, String stoneOrUserId,
			Boolean isRead, Boolean isShow, Date createdDate) {
		super();
		this.id = id;
		this.description = description;
		this.category = category;
		this.isAdmin = isAdmin;
		this.stoneOrUserId = stoneOrUserId;
		this.isRead = isRead;
		this.isShow = isShow;
		this.createdDate = createdDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getStoneOrUserId() {
		return stoneOrUserId;
	}

	public void setStoneOrUserId(String stoneOrUserId) {
		this.stoneOrUserId = stoneOrUserId;
	}

	public Boolean getIsRead() {
		return isRead;
	}

	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

}
