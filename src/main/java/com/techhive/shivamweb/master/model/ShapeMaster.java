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
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.listners.ShapeMasterListner;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SHAPEMASTER)
@EntityListeners(ShapeMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
public class ShapeMaster extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "shapeId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "shapeName", columnDefinition = "varchar(15)")
	private String shapeName;

	@Column(name = "shortName", columnDefinition = "varchar(15)")
	private String shortName;

	@Column(name = "shapeImage", columnDefinition = "varchar(50)")
	private String shapeImage;

	@Column(name = "shapeOrder")
	private Integer shapeOrder;

	public ShapeMaster(String string, String string2, Integer shapeOrd, String shapeImage) {
		this.shapeName = string;
		this.shortName = string2;
		this.shapeOrder = shapeOrd;
		this.shapeImage = shapeImage;
	}

	public ShapeMaster() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShapeName() {
		return shapeName;
	}

	public void setShapeName(String shapeName) {
		this.shapeName = shapeName.toUpperCase();
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName.toUpperCase();
	}

	public String getShapeImage() {
		return shapeImage;
	}

	public void setShapeImage(String shapeImage) {
		this.shapeImage = shapeImage;
	}

	public Integer getShapeOrder() {
		return shapeOrder;
	}

	public void setShapeOrder(Integer shapeOrder) {
		this.shapeOrder = shapeOrder;
	}

}
