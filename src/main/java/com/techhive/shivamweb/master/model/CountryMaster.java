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
import com.techhive.shivamweb.listners.CountryMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_COUNTRYMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(CountryMasterListner.class)
public class CountryMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "countryMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "countryMasterName", columnDefinition = "varchar(30)")
	private String countryMasterName;

	@Column(name = "shortName", columnDefinition = "varchar(15)")
	private String shortName;
	
	@Column(name = "countryMasterOrder")
	private Integer countryMasterOrder;

	public CountryMaster(String countryMasterName, String shortName, Integer countryMasterOrder) {
		this.countryMasterName=countryMasterName;
		this.shortName=shortName;
		this.countryMasterOrder=countryMasterOrder;
	}
	
	public CountryMaster() {
		super();
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountryMasterName() {
		return countryMasterName;
	}

	public void setCountryMasterName(String countryMasterName) {
		this.countryMasterName = countryMasterName.toUpperCase();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName.toUpperCase();
	}

	public Integer getCountryMasterOrder() {
		return countryMasterOrder;
	}

	public void setCountryMasterOrder(Integer countryMasterOrder) {
		this.countryMasterOrder = countryMasterOrder;
	}
	
	

}
