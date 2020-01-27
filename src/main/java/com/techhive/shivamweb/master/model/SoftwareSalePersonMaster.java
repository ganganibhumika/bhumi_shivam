package com.techhive.shivamweb.master.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.listners.SoftwareSalesPersonMasterListner;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SOFTWARE_SALES_PERSON_MASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(SoftwareSalesPersonMasterListner.class)
public class SoftwareSalePersonMaster extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "softwaresalesPersonId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "name", columnDefinition = "varchar(50)")
	private String name;

	@OneToOne(mappedBy = "softwareSalePersonMaster")
	@JsonIgnore
	private SalesPersonMaster salesPersonMaster;

	@PreRemove
	protected void onRemove() {
		SalesPersonMaster salesPersonMaster = this.salesPersonMaster;
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(salesPersonMaster)) {
			salesPersonMaster.setSoftwareSalePersonMaster(null);
		}
	}

	public SoftwareSalePersonMaster() {
		super();
	}

	public SoftwareSalePersonMaster(String softwareUserIdNew) {
		this.id = softwareUserIdNew;
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

	public SalesPersonMaster getSalesPersonMaster() {
		return salesPersonMaster;
	}

	public void setSalesPersonMaster(SalesPersonMaster salesPersonMaster) {
		this.salesPersonMaster = salesPersonMaster;
	}

}
