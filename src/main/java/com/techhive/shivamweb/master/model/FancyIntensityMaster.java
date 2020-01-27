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
import com.techhive.shivamweb.listners.FancyIntensityMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_FANCYINTENCITYMASTER)
@EntityListeners(FancyIntensityMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class FancyIntensityMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "fancyIntensityId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "fancyIntensityName", columnDefinition = "varchar(30)")
	private String fancyIntensityName;

	@Column(name = "fancyIntensityOrder")
	private Integer fancyIntensityOrder;

	public FancyIntensityMaster(String fancyIntensityName, int fancyIntensityOrder) {
		this.fancyIntensityName=fancyIntensityName.toUpperCase();
		this.fancyIntensityOrder=fancyIntensityOrder;
	}

	public FancyIntensityMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFancyIntensityName() {
		return fancyIntensityName;
	}

	public void setFancyIntensityName(String fancyIntensityName) {
		this.fancyIntensityName = fancyIntensityName.toUpperCase();
	}

	public Integer getFancyIntensityOrder() {
		return fancyIntensityOrder;
	}

	public void setFancyIntensityOrder(Integer fancyIntensityOrder) {
		this.fancyIntensityOrder = fancyIntensityOrder;
	}
	
}
