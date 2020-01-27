package com.techhive.shivamweb.request.payload;

import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class StoneSearchCriteria {

	private String showId; // add new for compaing

	// new for order
	private String name;
	private Double starLength;

	private String sortKey;
	private String sortOrder;

	private Boolean isFancyColor;

	private Integer startPosition;
	private Integer noOfRecords;

	private List<String> listOfStoneId; // pid
	// private List<String> availability;
	private List<String> shape;

	private List<Map<String, String>> listOfFromToWeights; // size == weight == carat.

	private List<String> listOfCut;
	private List<String> listOfPolish;
	private List<String> listOfSym;
	private List<String> listOfFlou;

	// private String country;

	private Integer fromColor;
	private Integer toColor;

	private Double fromLength;
	private Double toLength;

	private Double fromWidth;
	private Double toWidth;

	private Double fromHeight;
	private Double toHeight;

	private List<String> lab;

	// private double rap;
	// private double discount;
	// private double percaret;

	private List<String> fancyColor;
	private List<String> fancyColorIntensity;
	private List<String> fancyColorOvertone;

	private Double fromDepth;
	private Double toDepth;

	private Double fromTable;
	private Double toTable;

	private Double fromGirdleThin; // pending
	private Double toGirdleThin;// pending

	private Double fromGirdleThick;// pending
	private Double toGirdleThick;// pending

	// private List<String> girdleCondition; // pending

	private Double fromCuletSize;
	private Double toCuletSize;

	private Double fromTotal;
	private Double toTotal;

	// private String culetCondition;

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

	// private Integer fromShade;
	// private Integer toShade;
	//
	// private Integer fromMilky;
	// private Integer toMilky;

	// new add
	private String stoneId;
	private String certificateNo;

	// new list
	private List<String> color;
	private List<String> clarity;
	private List<String> milky;
	private List<String> shade;
	private List<String> country;

	// private List<String> listOfStoneId;

	private String userId;

	private Double offPer;

	private Double fromLowerHalf;
	private Double toLowerHalf;

	private Double fromDiameter;
	private Double toDiameter;

	private Boolean is3EX;
	private Boolean is3VG;
	private Boolean isNOBGM;

	private Boolean isShow; // use for compaing user

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

	public List<String> getListOfStoneId() {
		return listOfStoneId;
	}

	public void setListOfStoneId(List<String> listOfStoneId) {
		this.listOfStoneId = listOfStoneId;
	}

	public List<String> getShape() {
		return shape;
	}

	public void setShape(List<String> shape) {
		this.shape = shape;
	}

	public List<Map<String, String>> getListOfFromToWeights() {
		return listOfFromToWeights;
	}

	public void setListOfFromToWeights(List<Map<String, String>> listOfFromToWeights) {
		this.listOfFromToWeights = listOfFromToWeights;
	}

	public List<String> getListOfCut() {
		return listOfCut;
	}

	public void setListOfCut(List<String> listOfCut) {
		this.listOfCut = listOfCut;
	}

	public List<String> getListOfPolish() {
		return listOfPolish;
	}

	public void setListOfPolish(List<String> listOfPolish) {
		this.listOfPolish = listOfPolish;
	}

	public List<String> getListOfSym() {
		return listOfSym;
	}

	public void setListOfSym(List<String> listOfSym) {
		this.listOfSym = listOfSym;
	}

	public List<String> getListOfFlou() {
		return listOfFlou;
	}

	public void setListOfFlou(List<String> listOfFlou) {
		this.listOfFlou = listOfFlou;
	}

	public List<String> getCountry() {
		return country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
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

	public List<String> getLab() {
		return lab;
	}

	public void setLab(List<String> lab) {
		this.lab = lab;
	}

	public List<String> getFancyColor() {
		return fancyColor;
	}

	public void setFancyColor(List<String> fancyColor) {
		this.fancyColor = fancyColor;
	}

	public List<String> getFancyColorIntensity() {
		return fancyColorIntensity;
	}

	public void setFancyColorIntensity(List<String> fancyColorIntensity) {
		this.fancyColorIntensity = fancyColorIntensity;
	}

	public List<String> getFancyColorOvertone() {
		return fancyColorOvertone;
	}

	public void setFancyColorOvertone(List<String> fancyColorOvertone) {
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

	public String getStoneId() {
		return stoneId;
	}

	public void setStoneId(String stoneId) {
		this.stoneId = stoneId;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}

	public List<String> getColor() {
		return color;
	}

	public void setColor(List<String> color) {
		this.color = color;
	}

	public List<String> getClarity() {
		return clarity;
	}

	public void setClarity(List<String> clarity) {
		this.clarity = clarity;
	}

	public List<String> getMilky() {
		return milky;
	}

	public void setMilky(List<String> milky) {
		this.milky = milky;
	}

	public List<String> getShade() {
		return shade;
	}

	public void setShade(List<String> shade) {
		this.shade = shade;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getOffPer() {
		return offPer;
	}

	public void setOffPer(Double offPer) {
		this.offPer = offPer;
	}

	public Boolean getIsFancyColor() {
		return isFancyColor;
	}

	public void setIsFancyColor(Boolean isFancyColor) {
		this.isFancyColor = isFancyColor;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
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

	public Double getFromLowerHalf() {
		return fromLowerHalf;
	}

	public void setFromLowerHalf(Double fromLowerHalf) {
		this.fromLowerHalf = fromLowerHalf;
	}

	public Double getToLowerHalf() {
		return toLowerHalf;
	}

	public void setToLowerHalf(Double toLowerHalf) {
		this.toLowerHalf = toLowerHalf;
	}

	public Double getFromDiameter() {
		return fromDiameter;
	}

	public void setFromDiameter(Double fromDiameter) {
		this.fromDiameter = fromDiameter;
	}

	public Double getToDiameter() {
		return toDiameter;
	}

	public void setToDiameter(Double toDiameter) {
		this.toDiameter = toDiameter;
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

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

}
