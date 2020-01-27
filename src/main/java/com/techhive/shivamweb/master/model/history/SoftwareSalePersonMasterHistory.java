package com.techhive.shivamweb.master.model.history;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;


@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SOFTWARE_SALES_PERSON_MASTER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class SoftwareSalePersonMasterHistory extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "softwaresalesPersonHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "name", columnDefinition = "varchar(50)")
	private String name;

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	private String idOfEntity;

	public SoftwareSalePersonMasterHistory(SoftwareSalePersonMaster target, EnumForAction action) {
		this.action = action;
		this.idOfEntity = target.getId();
		this.name = target.getName();
	}

	public SoftwareSalePersonMasterHistory(String id) {
		super();
		this.id = id;
	}
	public SoftwareSalePersonMasterHistory() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
