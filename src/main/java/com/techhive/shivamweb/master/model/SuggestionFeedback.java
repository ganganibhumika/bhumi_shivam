package com.techhive.shivamweb.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.listners.SuggestionFeedbackListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name=ShivamWebVariableUtils.TABLE_NAME_FOR_SUGGESTIONFEEDBACK)
@EntityListeners(SuggestionFeedbackListner.class)
@JsonInclude(Include.NON_EMPTY)
public class SuggestionFeedback extends Auditable<String>{
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "suggestionId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "contactPerson", columnDefinition = "varchar(30)")
	private String contactPerson;

	@Column(name = "email", columnDefinition = "varchar(50)")
	private String email;

	@Column(name = "username", columnDefinition = "varchar(30)")
	private String username;

	@Column(name = "userId")
	private String userId;

	@Column(name = "feedback", columnDefinition = "varchar(500)")
	private String feedback;
	
	@Column(name = "companyName", columnDefinition = "varchar(50)")
	private String companyName;

	@Column(name = "telephone", columnDefinition = "varchar(30)")
	private String telephone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
