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
import com.techhive.shivamweb.listners.FluorescenceMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_FLUORESENCEMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(FluorescenceMasterListner.class)
public class FluorescenceMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "fluorescenceMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "fluorescenceMasterName", columnDefinition = "varchar(30)")
	private String fluorescenceMasterName;

	@Column(name = "shortName", columnDefinition = "varchar(15)")
	private String shortName;

	@Column(name = "fluorescenceMasterOrder")
	private Integer fluorescenceMasterOrder;

	public FluorescenceMaster(String string, String string2, Integer fluorescenceOrder) {
		this.fluorescenceMasterName =string;
		this.shortName =string2;
		this.fluorescenceMasterOrder=fluorescenceOrder;
	}

	public FluorescenceMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFluorescenceMasterName() {
		return fluorescenceMasterName;
	}

	public void setFluorescenceMasterName(String fluorescenceMasterName) {
		this.fluorescenceMasterName = fluorescenceMasterName.toUpperCase();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName.toUpperCase();
	}

	public Integer getFluorescenceMasterOrder() {
		return fluorescenceMasterOrder;
	}

	public void setFluorescenceMasterOrder(Integer fluorescenceMasterOrder) {
		this.fluorescenceMasterOrder = fluorescenceMasterOrder;
	}

}
