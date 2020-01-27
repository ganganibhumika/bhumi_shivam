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
import com.techhive.shivamweb.listners.NewArrivalSettingsListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name=ShivamWebVariableUtils.TABLE_NAME_FOR_NEWARRIVALSETTINGS)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(NewArrivalSettingsListner.class)
public class NewArrivalSettings extends Auditable<String>{

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "newArrivalId", nullable = false, insertable = false, updatable = false)
	private String id;
	
	private Integer noOfDays;
	
	public NewArrivalSettings() {
		super();
	}

	public NewArrivalSettings(String id, Integer noOfDays) {
		super();
		this.id = id;
		this.noOfDays = noOfDays;
	}

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
	
	
}
