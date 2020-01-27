package com.techhive.shivamweb.master.model.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.model.SaveSearchCriteria;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_SAVESEARCHCRITERIA_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class SaveSearchCriteriaHistory {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "saveSearchCritHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	public SaveSearchCriteriaHistory() {
		super();
	}

	
	public SaveSearchCriteriaHistory(SaveSearchCriteria saveSearchCriteria, EnumForAction action, UserHistory userHistory) {
		super();
		this.action = action;
		this.idOfEntity = saveSearchCriteria.getId();
		this.user=userHistory;
		this.name=saveSearchCriteria.getName();
		this.starLength=saveSearchCriteria.getStarLength();
		this.lowerHalf=saveSearchCriteria.getLowerHalf();
		this.diameter=saveSearchCriteria.getDiameter();

		this.isFancyColor=saveSearchCriteria.getIsFancyColor();

		this.startPosition=saveSearchCriteria.getStartPosition();
		this.noOfRecords=saveSearchCriteria.getNoOfRecords();

		this.shape=saveSearchCriteria.getShape();

		this.listOfFromToWeights=saveSearchCriteria.getListOfFromToWeights();

		this.listOfCut=saveSearchCriteria.getListOfCut();
		this.listOfPolish=saveSearchCriteria.getListOfPolish();
		this.listOfSym=saveSearchCriteria.getListOfSym();
		this.listOfFlou=saveSearchCriteria.getListOfFlou();

		this.fromColor=saveSearchCriteria.getFromColor();
		this.toColor=saveSearchCriteria.getToColor();

		this.fromLength=saveSearchCriteria.getFromLength();
		this.toLength=saveSearchCriteria.getToLength();

		this.fromWidth=saveSearchCriteria.getFromWidth();
		this.toWidth=saveSearchCriteria.getToWidth();

		this.fromHeight=saveSearchCriteria.getFromHeight();
		this.toHeight=saveSearchCriteria.getToHeight();

		this.lab=saveSearchCriteria.getLab();

		this.fancyColor=saveSearchCriteria.getFancyColor();
		this.fancyColorIntensity=saveSearchCriteria.getFancyColorIntensity();
		this.fancyColorOvertone=saveSearchCriteria.getFancyColorOvertone();

		this.fromDepth=saveSearchCriteria.getFromDepth();
		this.toDepth=saveSearchCriteria.getToDepth();

		this.fromTable=saveSearchCriteria.getFromTable();
		this.toTable=saveSearchCriteria.getToTable();

		this.fromGirdleThin=saveSearchCriteria.getFromGirdleThin(); // pending
		this.toGirdleThin=saveSearchCriteria.getToGirdleThin();// pending

		this.fromGirdleThick=saveSearchCriteria.getFromGirdleThick();// pending
		this.toGirdleThick=saveSearchCriteria.getToGirdleThick();// pending

		this.fromCuletSize=saveSearchCriteria.getFromCuletSize();
		this.toCuletSize=saveSearchCriteria.getToCuletSize();

		this.fromTotal=saveSearchCriteria.getFromTotal();
		this.toTotal=saveSearchCriteria.getToTotal();

		this.fromCrownHeight=saveSearchCriteria.getFromCrownHeight();
		this.toCrownHeight=saveSearchCriteria.getToCrownHeight();

		this.fromCrownAngle=saveSearchCriteria.getFromCrownAngle();
		this.toCrownAngle=saveSearchCriteria.getToCrownAngle();

		this.fromPavilionDepth=saveSearchCriteria.getFromPavilionDepth(); // ???
		this.toPavilionDepth=saveSearchCriteria.getToPavilionDepth(); // ???

		this.fromPavilionAngle=saveSearchCriteria.getFromPavilionAngle();
		this.toPavilionAngle=saveSearchCriteria.getToPavilionAngle();

		this.fromPavilionHeight=saveSearchCriteria.getFromPavilionHeight();
		this.toPavilionHeight=saveSearchCriteria.getToPavilionHeight();

		this.fromGirdle=saveSearchCriteria.getFromGirdle();
		this.toGirdle=saveSearchCriteria.getToGirdle();

		this.fromShade=saveSearchCriteria.getFromShade();
		this.toShade=saveSearchCriteria.getToShade();

		this.fromMilky=saveSearchCriteria.getFromMilky();
		this.toMilky=saveSearchCriteria.getToMilky();

		// new list
		this.color=saveSearchCriteria.getColor();
		this.clarity=saveSearchCriteria.getClarity();
		this.milky=saveSearchCriteria.getMilky();
		this.shade=saveSearchCriteria.getShade();
		this.country=saveSearchCriteria.getCountry();

		this.offPer=saveSearchCriteria.getOffPer();

		this.setIs3EX(saveSearchCriteria.getIs3EX());
		this.setIs3VG(saveSearchCriteria.getIs3VG());
		this.setIsNOBGM(saveSearchCriteria.getIsNOBGM());


	}

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String idOfEntity;

	

	@OneToOne
	private UserHistory user;
	private String name;
	private Double starLength;
	private Double lowerHalf;
	private Double diameter;

	private Boolean isFancyColor;

	private Integer startPosition;
	private Integer noOfRecords;

	private String shape;

	private String listOfFromToWeights;

	private String listOfCut;
	private String listOfPolish;
	private String listOfSym;
	private String listOfFlou;

	private Integer fromColor;
	private Integer toColor;

	private Double fromLength;
	private Double toLength;

	private Double fromWidth;
	private Double toWidth;

	private Double fromHeight;
	private Double toHeight;

	private String lab;

	private String fancyColor;
	private String fancyColorIntensity;
	private String fancyColorOvertone;

	private Double fromDepth;
	private Double toDepth;

	private Double fromTable;
	private Double toTable;

	private Integer fromGirdleThin; // pending
	private Integer toGirdleThin;// pending

	private Integer fromGirdleThick;// pending
	private Integer toGirdleThick;// pending

	private Integer fromCuletSize;
	private Integer toCuletSize;

	private Double fromTotal;
	private Double toTotal;

	private Double fromCrownHeight;
	private Double toCrownHeight;

	private Double fromCrownAngle;
	private Double toCrownAngle;

	private Double fromPavilionDepth; // ???
	private Double toPavilionDepth; // ???

	private Double fromPavilionAngle;
	private Double toPavilionAngle;

	private Double fromPavilionHeight;
	private Double toPavilionHeight;

	private Double fromGirdle;
	private Double toGirdle;

	private Integer fromShade;
	private Integer toShade;

	private Integer fromMilky;
	private Integer toMilky;

	// new list
	private String color;
	private String clarity;
	private String milky;
	private String shade;
	private String country;

	private Double offPer;
	
	private Boolean is3EX;
	private Boolean is3VG;
	private Boolean isNOBGM;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public EnumForAction getAction() {
		return action;
	}

	public void setAction(EnumForAction action) {
		this.action = action;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
	}

	public UserHistory getUser() {
		return user;
	}

	public void setUser(UserHistory user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getStarLength() {
		return starLength;
	}

	public void setStarLength(Double starLength) {
		this.starLength = starLength;
	}

	public Double getLowerHalf() {
		return lowerHalf;
	}

	public void setLowerHalf(Double lowerHalf) {
		this.lowerHalf = lowerHalf;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Boolean getIsFancyColor() {
		return isFancyColor;
	}

	public void setIsFancyColor(Boolean isFancyColor) {
		this.isFancyColor = isFancyColor;
	}

	public Integer getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(Integer startPosition) {
		this.startPosition = startPosition;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getListOfFromToWeights() {
		return listOfFromToWeights;
	}

	public void setListOfFromToWeights(String listOfFromToWeights) {
		this.listOfFromToWeights = listOfFromToWeights;
	}

	public String getListOfCut() {
		return listOfCut;
	}

	public void setListOfCut(String listOfCut) {
		this.listOfCut = listOfCut;
	}

	public String getListOfPolish() {
		return listOfPolish;
	}

	public void setListOfPolish(String listOfPolish) {
		this.listOfPolish = listOfPolish;
	}

	public String getListOfSym() {
		return listOfSym;
	}

	public void setListOfSym(String listOfSym) {
		this.listOfSym = listOfSym;
	}

	public String getListOfFlou() {
		return listOfFlou;
	}

	public void setListOfFlou(String listOfFlou) {
		this.listOfFlou = listOfFlou;
	}

	public Integer getFromColor() {
		return fromColor;
	}

	public void setFromColor(Integer fromColor) {
		this.fromColor = fromColor;
	}

	public Integer getToColor() {
		return toColor;
	}

	public void setToColor(Integer toColor) {
		this.toColor = toColor;
	}

	public Double getFromLength() {
		return fromLength;
	}

	public void setFromLength(Double fromLength) {
		this.fromLength = fromLength;
	}

	public Double getToLength() {
		return toLength;
	}

	public void setToLength(Double toLength) {
		this.toLength = toLength;
	}

	public Double getFromWidth() {
		return fromWidth;
	}

	public void setFromWidth(Double fromWidth) {
		this.fromWidth = fromWidth;
	}

	public Double getToWidth() {
		return toWidth;
	}

	public void setToWidth(Double toWidth) {
		this.toWidth = toWidth;
	}

	public Double getFromHeight() {
		return fromHeight;
	}

	public void setFromHeight(Double fromHeight) {
		this.fromHeight = fromHeight;
	}

	public Double getToHeight() {
		return toHeight;
	}

	public void setToHeight(Double toHeight) {
		this.toHeight = toHeight;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getFancyColor() {
		return fancyColor;
	}

	public void setFancyColor(String fancyColor) {
		this.fancyColor = fancyColor;
	}

	public String getFancyColorIntensity() {
		return fancyColorIntensity;
	}

	public void setFancyColorIntensity(String fancyColorIntensity) {
		this.fancyColorIntensity = fancyColorIntensity;
	}

	public String getFancyColorOvertone() {
		return fancyColorOvertone;
	}

	public void setFancyColorOvertone(String fancyColorOvertone) {
		this.fancyColorOvertone = fancyColorOvertone;
	}

	public Double getFromDepth() {
		return fromDepth;
	}

	public void setFromDepth(Double fromDepth) {
		this.fromDepth = fromDepth;
	}

	public Double getToDepth() {
		return toDepth;
	}

	public void setToDepth(Double toDepth) {
		this.toDepth = toDepth;
	}

	public Double getFromTable() {
		return fromTable;
	}

	public void setFromTable(Double fromTable) {
		this.fromTable = fromTable;
	}

	public Double getToTable() {
		return toTable;
	}

	public void setToTable(Double toTable) {
		this.toTable = toTable;
	}

	public Integer getFromGirdleThin() {
		return fromGirdleThin;
	}

	public void setFromGirdleThin(Integer fromGirdleThin) {
		this.fromGirdleThin = fromGirdleThin;
	}

	public Integer getToGirdleThin() {
		return toGirdleThin;
	}

	public void setToGirdleThin(Integer toGirdleThin) {
		this.toGirdleThin = toGirdleThin;
	}

	public Integer getFromGirdleThick() {
		return fromGirdleThick;
	}

	public void setFromGirdleThick(Integer fromGirdleThick) {
		this.fromGirdleThick = fromGirdleThick;
	}

	public Integer getToGirdleThick() {
		return toGirdleThick;
	}

	public void setToGirdleThick(Integer toGirdleThick) {
		this.toGirdleThick = toGirdleThick;
	}

	public Integer getFromCuletSize() {
		return fromCuletSize;
	}

	public void setFromCuletSize(Integer fromCuletSize) {
		this.fromCuletSize = fromCuletSize;
	}

	public Integer getToCuletSize() {
		return toCuletSize;
	}

	public void setToCuletSize(Integer toCuletSize) {
		this.toCuletSize = toCuletSize;
	}

	public Double getFromTotal() {
		return fromTotal;
	}


	public void setFromTotal(Double fromTotal) {
		this.fromTotal = fromTotal;
	}


	public Double getToTotal() {
		return toTotal;
	}


	public void setToTotal(Double toTotal) {
		this.toTotal = toTotal;
	}


	public Double getFromCrownHeight() {
		return fromCrownHeight;
	}

	public void setFromCrownHeight(Double fromCrownHeight) {
		this.fromCrownHeight = fromCrownHeight;
	}

	public Double getToCrownHeight() {
		return toCrownHeight;
	}

	public void setToCrownHeight(Double toCrownHeight) {
		this.toCrownHeight = toCrownHeight;
	}

	public Double getFromCrownAngle() {
		return fromCrownAngle;
	}

	public void setFromCrownAngle(Double fromCrownAngle) {
		this.fromCrownAngle = fromCrownAngle;
	}

	public Double getToCrownAngle() {
		return toCrownAngle;
	}

	public void setToCrownAngle(Double toCrownAngle) {
		this.toCrownAngle = toCrownAngle;
	}

	public Double getFromPavilionDepth() {
		return fromPavilionDepth;
	}

	public void setFromPavilionDepth(Double fromPavilionDepth) {
		this.fromPavilionDepth = fromPavilionDepth;
	}

	public Double getToPavilionDepth() {
		return toPavilionDepth;
	}

	public void setToPavilionDepth(Double toPavilionDepth) {
		this.toPavilionDepth = toPavilionDepth;
	}

	public Double getFromPavilionAngle() {
		return fromPavilionAngle;
	}

	public void setFromPavilionAngle(Double fromPavilionAngle) {
		this.fromPavilionAngle = fromPavilionAngle;
	}

	public Double getToPavilionAngle() {
		return toPavilionAngle;
	}

	public void setToPavilionAngle(Double toPavilionAngle) {
		this.toPavilionAngle = toPavilionAngle;
	}

	public Double getFromPavilionHeight() {
		return fromPavilionHeight;
	}

	public void setFromPavilionHeight(Double fromPavilionHeight) {
		this.fromPavilionHeight = fromPavilionHeight;
	}

	public Double getToPavilionHeight() {
		return toPavilionHeight;
	}

	public void setToPavilionHeight(Double toPavilionHeight) {
		this.toPavilionHeight = toPavilionHeight;
	}

	public Double getFromGirdle() {
		return fromGirdle;
	}

	public void setFromGirdle(Double fromGirdle) {
		this.fromGirdle = fromGirdle;
	}

	public Double getToGirdle() {
		return toGirdle;
	}

	public void setToGirdle(Double toGirdle) {
		this.toGirdle = toGirdle;
	}

	public Integer getFromShade() {
		return fromShade;
	}

	public void setFromShade(Integer fromShade) {
		this.fromShade = fromShade;
	}

	public Integer getToShade() {
		return toShade;
	}

	public void setToShade(Integer toShade) {
		this.toShade = toShade;
	}

	public Integer getFromMilky() {
		return fromMilky;
	}

	public void setFromMilky(Integer fromMilky) {
		this.fromMilky = fromMilky;
	}

	public Integer getToMilky() {
		return toMilky;
	}

	public void setToMilky(Integer toMilky) {
		this.toMilky = toMilky;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getClarity() {
		return clarity;
	}

	public void setClarity(String clarity) {
		this.clarity = clarity;
	}

	public String getMilky() {
		return milky;
	}

	public void setMilky(String milky) {
		this.milky = milky;
	}

	public String getShade() {
		return shade;
	}

	public void setShade(String shade) {
		this.shade = shade;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Double getOffPer() {
		return offPer;
	}

	public void setOffPer(Double offPer) {
		this.offPer = offPer;
	}

	public Boolean getIs3EX() {
		return is3EX;
	}

	public void setIs3EX(Boolean is3ex) {
		is3EX = is3ex;
	}

	public Boolean getIs3VG() {
		return is3VG;
	}

	public void setIs3VG(Boolean is3vg) {
		is3VG = is3vg;
	}

	public Boolean getIsNOBGM() {
		return isNOBGM;
	}

	public void setIsNOBGM(Boolean isNOBGM) {
		this.isNOBGM = isNOBGM;
	}

	
}
