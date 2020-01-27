package com.techhive.shivamweb.master.model.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_NOTIFICATION_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class NotificationHistory extends Auditable<String> {
	public NotificationHistory() {

	}

	public NotificationHistory(Notification target, UserHistory user, EnumForAction action) {
		this.action = action;
		this.idOfEntity = target.getId();
		this.userHistory = user;
		this.category = target.getCategory();
		this.description = target.getDescription();
		this.isAdmin = target.getIsAdmin();
		this.stoneOrUserId = target.getStoneOrUserId();
		this.isRead = target.getIsRead();
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "notificationHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	private String idOfEntity;

	@Column(name = "description", columnDefinition = "varchar(100)")
	private String description;

	@Column(name = "category", columnDefinition = "varchar(100)")
	private String category;
	private Boolean isAdmin;
	private String stoneOrUserId;
	private Boolean isRead;

	@ManyToOne
	private UserHistory userHistory;

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

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
	}

	public UserHistory getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(UserHistory userHistory) {
		this.userHistory = userHistory;
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

}
