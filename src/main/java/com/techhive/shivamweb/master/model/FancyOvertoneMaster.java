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
import com.techhive.shivamweb.listners.FancyOvertoneMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_FANCYOVERTONEMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(FancyOvertoneMasterListner.class)
public class FancyOvertoneMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "fancyOvertoneId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "fancyOvertoneName", columnDefinition = "varchar(30)")
	private String fancyOvertoneName;

	@Column(name = "fancyOvertoneOrder")
	private Integer fancyOvertoneOrder;

	public FancyOvertoneMaster(String fancyOvertoneName, int fancyOvertoneOrder) {
		this.fancyOvertoneName =fancyOvertoneName.toUpperCase();
		this.fancyOvertoneOrder=fancyOvertoneOrder;
	}

	public FancyOvertoneMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFancyOvertoneName() {
		return fancyOvertoneName;
	}

	public void setFancyOvertoneName(String fancyOvertoneName) {
		this.fancyOvertoneName = fancyOvertoneName.toUpperCase();
	}

	public Integer getFancyOvertoneOrder() {
		return fancyOvertoneOrder;
	}

	public void setFancyOvertoneOrder(Integer fancyOvertoneOrder) {
		this.fancyOvertoneOrder = fancyOvertoneOrder;
	}
	
}
