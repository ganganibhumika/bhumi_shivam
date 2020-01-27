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
import com.techhive.shivamweb.listners.FancyColorMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_FANCYCOLORMASTER)
@EntityListeners(FancyColorMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class FancyColorMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "fancyColorId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "fancyColorName", columnDefinition = "varchar(30)")
	private String fancyColorName;

	@Column(name = "fancyColorOrder")
	private Integer fancyColorOrder;

	public FancyColorMaster(String fancyColorName, int fancyColor) {
		this.fancyColorName=fancyColorName.toUpperCase();
		this.fancyColorOrder=fancyColor;
	}

	public FancyColorMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFancyColorName() {
		return fancyColorName;
	}

	public void setFancyColorName(String fancyColorName) {
		this.fancyColorName = fancyColorName.toUpperCase();
	}

	public Integer getFancyColorOrder() {
		return fancyColorOrder;
	}

	public void setFancyColorOrder(Integer fancyColorOrder) {
		this.fancyColorOrder = fancyColorOrder;
	}

}
