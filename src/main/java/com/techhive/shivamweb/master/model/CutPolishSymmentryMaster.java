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
import com.techhive.shivamweb.listners.CutPolishSymmentryMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_CUTPOLISHSYMMETRYMASTER)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(CutPolishSymmentryMasterListner.class)
public class CutPolishSymmentryMaster {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "cutPolishSymmentryMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "cutPolishSymmentryMasterName", columnDefinition = "varchar(30)")
	private String cutPolishSymmentryMasterName;

	@Column(name = "cutPolishSymmentryMasterOrder")
	private Integer cutPolishSymmentryMasterOrder;

	public CutPolishSymmentryMaster(String cutPolishSymmentryMasterName, int cutPolishSymmentryMasterOrder) {
		this.cutPolishSymmentryMasterName = cutPolishSymmentryMasterName;
		this.cutPolishSymmentryMasterOrder = cutPolishSymmentryMasterOrder;
	}

	public CutPolishSymmentryMaster() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCutPolishSymmentryMasterName() {
		return cutPolishSymmentryMasterName;
	}

	public void setCutPolishSymmentryMasterName(String cutPolishSymmentryMasterName) {
		this.cutPolishSymmentryMasterName = cutPolishSymmentryMasterName.toUpperCase();
	}

	public Integer getCutPolishSymmentryMasterOrder() {
		return cutPolishSymmentryMasterOrder;
	}

	public void setCutPolishSymmentryMasterOrder(Integer cutPolishSymmentryMasterOrder) {
		this.cutPolishSymmentryMasterOrder = cutPolishSymmentryMasterOrder;
	}

}
