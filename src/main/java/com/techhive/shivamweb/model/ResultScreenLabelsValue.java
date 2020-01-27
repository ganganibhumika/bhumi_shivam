package com.techhive.shivamweb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_RESULT_SCREEN_LABLES_VALUE)
@JsonInclude(Include.NON_EMPTY)
public class ResultScreenLabelsValue {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "lableValueId", insertable = false, nullable = false, updatable = false)
	private String id;

	private boolean hide;

	private boolean rowDrag;

	private boolean lockPosition ;
	
	private String headerClass;
	
	private String cellClass;

	@Column(name = "labelOrder")
	private Integer labelOrder;

	@Column(name = "pinned", columnDefinition = "varchar(30)")
	private String pinned;

	@Transient
	private String headerName;

	@Transient
	private String field;
	
	@Transient
	private Boolean suppressSorting;
	
	@Transient
	private Integer width;

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Transient
	private String idOfLabel;

	/**
	 * @author Heena one property value have only one lable name
	 * @return mapping with ResultScreenValue
	 */

	@ManyToOne
	@JsonIgnore
	private ResultScreenLabelsName resultScreenLablesName;

	/**
	 * @author Heena one to many mapping with ResultScreenLabelValue one user have
	 *         multiple property of labelValue
	 */
	private String userId;

	public String getHeaderClass() {
		return headerClass;
	}

	public void setHeaderClass(String headerClass) {
		this.headerClass = headerClass;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public boolean isRowDrag() {
		return rowDrag;
	}

	public void setRowDrag(boolean rowDrag) {
		this.rowDrag = rowDrag;
	}

	public boolean isLockPosition() {
		return lockPosition;
	}

	public void setLockPosition(boolean lockPosition) {
		this.lockPosition = lockPosition;
	}

	public Integer getLabelOrder() {
		return labelOrder;
	}

	public void setLabelOrder(Integer labelOrder) {
		this.labelOrder = labelOrder;
	}

	public String getPinned() {
		return pinned;
	}

	public void setPinned(String pinned) {
		this.pinned = pinned;
	}

	public ResultScreenLabelsName getResultScreenLablesName() {
		return resultScreenLablesName;
	}

	public void setResultScreenLablesName(ResultScreenLabelsName resultScreenLablesName) {
		this.resultScreenLablesName = resultScreenLablesName;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getIdOfLabel() {
		return ShivamWebMethodUtils.isObjectNullOrEmpty(idOfLabel) ? idOfLabel
				: this.getResultScreenLablesName().getId();
	}

	public void setIdOfLabel(String idOfLabel) {
		if(!ShivamWebMethodUtils.isObjectNullOrEmpty(idOfLabel)) {
			this.setResultScreenLablesName(new ResultScreenLabelsName(idOfLabel));
		}
		this.idOfLabel= idOfLabel;
	}

	public String getCellClass() {
		return cellClass;
	}

	public void setCellClass(String cellClass) {
		this.cellClass = cellClass;
	}

	public Boolean getSuppressSorting() {
		return suppressSorting;
	}

	public void setSuppressSorting(Boolean suppressSorting) {
		this.suppressSorting = suppressSorting;
	}

		
	

}
