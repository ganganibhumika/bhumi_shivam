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
import com.techhive.shivamweb.listners.BrownShadeMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_BROWNSHADEMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(BrownShadeMasterListner.class)

public class BrownShadeMaster extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "brownShadeMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "brownShadeMasterName", columnDefinition = "varchar(30)")
	private String brownShadeMasterName;

	@Column(name = "shortName", columnDefinition = "varchar(15)")
	private String shortName;

	@Column(name = "brownShadeMasterOrder")
	private Integer brownShadeMasterOrder;

	/**
	 * @bhumi
	 * 
	 */
	public BrownShadeMaster(String brownShadeMasterName, Integer brownShadeMasterOrder, String shortName) {
		this.brownShadeMasterName = brownShadeMasterName;
		this.brownShadeMasterOrder = brownShadeMasterOrder;
		this.shortName = shortName;
	}

	public BrownShadeMaster() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrownShadeMasterName() {
		return brownShadeMasterName;
	}

	public void setBrownShadeMasterName(String brownShadeMasterName) {
		this.brownShadeMasterName = brownShadeMasterName.toUpperCase();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName.toUpperCase();
	}

	public Integer getBrownShadeMasterOrder() {
		return brownShadeMasterOrder;
	}

	public void setBrownShadeMasterOrder(Integer brownShadeMasterOrder) {
		this.brownShadeMasterOrder = brownShadeMasterOrder;
	}

}
