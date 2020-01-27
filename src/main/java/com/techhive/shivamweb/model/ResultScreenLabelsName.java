package com.techhive.shivamweb.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_RESULT_SCREEN_LABLES_NAME)
@JsonInclude(Include.NON_EMPTY)
public class ResultScreenLabelsName {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(strategy = "system-uuid", name = "uuid")
	@Column(name = "lableNameId", insertable = false, nullable = false, updatable = false)
	private String id;

	@Column(name = "headerName", columnDefinition = "varchar(30)")
	private String headerName;

	@Column(name = "field", columnDefinition = "varchar(30)")
	private String field;
	
	private Integer width;

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	/**
	 * @author Heena one Label have multiple property value
	 * @return mapping with ResultScreenValue
	 */
	@OneToMany(mappedBy="resultScreenLablesName")
	private  Set<ResultScreenLabelsValue> setOfResultScreenLabelsValue;
	
	
	

	public ResultScreenLabelsName() {
		super();
	}

	public ResultScreenLabelsName(String id) {
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Set<ResultScreenLabelsValue> getSetOfResultScreenLabelsValue() {
		return setOfResultScreenLabelsValue;
	}

	public void setSetOfResultScreenLabelsValue(Set<ResultScreenLabelsValue> setOfResultScreenLabelsValue) {
		this.setOfResultScreenLabelsValue = setOfResultScreenLabelsValue;
	}

}
