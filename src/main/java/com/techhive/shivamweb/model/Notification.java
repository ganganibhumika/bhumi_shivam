package com.techhive.shivamweb.model;

import java.util.Set;

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
import com.techhive.shivamweb.listners.NotificationListner;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_NOTIFICATION)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(NotificationListner.class)
public class Notification extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "notificationId", insertable = false, nullable = false, updatable = false)
	private String id;

	@Column(name = "description", columnDefinition = "varchar(100)")
	private String description;

	@Column(name = "category", columnDefinition = "varchar(100)")
	private String category;

	private Boolean isAdmin;

	private String stoneOrUserId;

	private Boolean isRead;

	private Boolean isShow;

	@Transient
	private Set<User> setOfUserObject;

	@ManyToOne
	private User user;

	@PrePersist
	public void prePresist() {
		this.isRead = false;
	}

	public Notification() {
		super();
	}

	public Notification(String id) {
		super();
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<User> getSetOfUserObject() {
		return setOfUserObject;
	}

	public void setSetOfUserObject(Set<User> setOfUserObject) {
		this.setOfUserObject = setOfUserObject;
	}

}
