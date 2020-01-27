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
import com.techhive.shivamweb.listners.LabMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;


@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_LABMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(LabMasterListner.class)

public class LabMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "labMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "labMasterName", columnDefinition = "varchar(30)")
	private String labMasterName;

	@Column(name = "labMasterOrder")
	private Integer labMasterOrder;

	public LabMaster(String labMasterName, Integer labOrd) {
		this.labMasterName=	labMasterName;
		this.labMasterOrder=labOrd;
	}

	public LabMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabMasterName() {
		return labMasterName;
	}

	public void setLabMasterName(String labMasterName) {
		this.labMasterName = labMasterName.toUpperCase();
	}

	public Integer getLabMasterOrder() {
		return labMasterOrder;
	}

	public void setLabMasterOrder(Integer labMasterOrder) {
		this.labMasterOrder = labMasterOrder;
	}
	
	
}
