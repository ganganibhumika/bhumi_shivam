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
import com.techhive.shivamweb.listners.ColorMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_COLORMASTER)
@EntityListeners(ColorMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class ColorMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "colorId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "colorName", columnDefinition = "varchar(15)")
	private String colorName;

	@Column(name = "colorOrder")
	private Integer colorOrder;

	public ColorMaster() {

	}

	public ColorMaster(String colorName, int colorOrder) {
		this.colorName = colorName;
		this.colorOrder = colorOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName.toUpperCase();
	}

	public Integer getColorOrder() {
		return colorOrder;
	}

	public void setColorOrder(Integer colorOrder) {
		this.colorOrder = colorOrder;
	}

}
