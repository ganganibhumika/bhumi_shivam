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
import com.techhive.shivamweb.listners.MilkMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_MILKYMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(MilkMasterListner.class)
public class MilkMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "milkMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "milkMasterName", columnDefinition = "varchar(30)")
	private String milkMasterName;

	@Column(name = "shortName", columnDefinition = "varchar(15)")
	private String shortName;
	
	@Column(name = "milkMasterOrder")
	private Integer milkMasterOrder;

	public MilkMaster(String string, String string2, Integer milkMasterOrder) {
		this.milkMasterName =string;
		this.shortName =string2;
		this.milkMasterOrder=milkMasterOrder;
	}

	public MilkMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMilkMasterName() {
		return milkMasterName;
	}

	public void setMilkMasterName(String milkMasterName) {
		this.milkMasterName = milkMasterName.toUpperCase();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName.toUpperCase();
	}

	public Integer getMilkMasterOrder() {
		return milkMasterOrder;
	}

	public void setMilkMasterOrder(Integer milkMasterOrder) {
		this.milkMasterOrder = milkMasterOrder;
	}
	
	
}
