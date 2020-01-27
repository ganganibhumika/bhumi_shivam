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
import com.techhive.shivamweb.listners.ClarityMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_CLARITYMASTER)
@EntityListeners(ClarityMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class ClarityMaster extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "clarityMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "clarityMasterName", columnDefinition = "varchar(30)")
	private String clarityMasterName;

	@Column(name = "clarityMasterOrder")
	private Integer clarityMasterOrder;

	public ClarityMaster() {

	}

	public ClarityMaster(String clarityMasterName, int clarityMasterOrder) {
		this.clarityMasterName = clarityMasterName;
		this.clarityMasterOrder = clarityMasterOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClarityMasterName() {
		return clarityMasterName;
	}

	public void setClarityMasterName(String clarityMasterName) {
		this.clarityMasterName = clarityMasterName.toUpperCase();
	}

	public Integer getClarityMasterOrder() {
		return clarityMasterOrder;
	}

	public void setClarityMasterOrder(Integer clarityMasterOrder) {
		this.clarityMasterOrder = clarityMasterOrder;
	}

}
