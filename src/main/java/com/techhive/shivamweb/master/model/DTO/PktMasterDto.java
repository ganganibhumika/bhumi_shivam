package com.techhive.shivamweb.master.model.DTO;

import java.util.Date;
import java.util.Set;

import com.techhive.shivamweb.model.ConfirmOrder;

public class PktMasterDto {
	private String id;

	private String stoneId;

	private Double seqNo;

	private Double carat;
	private Date giDate;

	private Double height;

	private Double length;

	private Double width;

	private String certNo;

	private String codeOfShape;

	private String codeOfColor;

	private String codeOfClarity;

	private String codeOfPolish;

	private String codeOfCut;

	private String codeOfFluorescence;

	private String codeOfSymmentry;

	private String codeOfLuster;

	private String codeOfShade;

	private String codeOfMilky;

	private String eyeClean;

	private Double diameter;

	private Double totDepth;

	private Double tablePercentage;

	private Double crownAngle;

	private Double crownHeight;

	private Double pavilionAngle;

	private Double pavilionHeight;

	private String efCode;

	private Boolean isHold;

	private Double disc;

	private Double gRate;

	private Double gRap;

	private String lab;

	private String shape;

	private String tinName;

	private String sinName;

	private String toinName;

	private String tbinName;

	private String soinName;

	private String sbinName;

	private String country;

	private String fColor;

	private String fIntensity;

	private String fOvertone;

	private String strLn;

	private String lrHalf;

	private String codeOfCulet;

	private String keyToSym;

	private String comment;
	private Double girdle;

	private Boolean isNewArrival;

	private String ErrorMsg;
	// /* Use for Master key Sorting */
	// @Transient
	// private Integer shapeOrder;
	//
	// @Transient
	// private Integer milkMasterOrder;
	//
	// @Transient
	// private Integer labMasterOrder;
	//
	// @Transient
	// private Integer fluorescenceMasterOrder;
	//
	// @Transient
	// private Integer fancyOvertoneOrder;
	//
	// @Transient
	// private Integer fancyIntensityOrder;
	//
	// @Transient
	// private Integer fancyColorOrder;
	//
	// @Transient
	// private Integer cutPolishSymmentryMasterOrder;
	//
	// @Transient
	// private Integer countryMasterOrder;
	//
	// @Transient
	// private Integer colorOrder;
	//
	// @Transient
	// private Integer clarityMasterOrder;
	//
	// @Transient
	// private Integer brownShadeMasterOrder;
	//
	// /* -------------------- End ------------------------ */

	private ConfirmOrder confirmOrder;

	/* use for calculation */
	private Double perCaratePrice;

	private Double rapRate;

	private Double totalPrice; // old-totalAmount

	private Long totalNoOfRecords;

	private String measurement;
	private String cutPolishSymmetry;

	private String shapeImage;

	// only use for return not in constructor
	private double finalTCTS;
	private double finalDiscountPercentage;
	private double finalRapRate;
	private double finalPriceOrFinalCarat;
	private double finalTotal;
	private Integer totalPriceWithNoRecord; //

	private Set<String> listOfNotFoundShape;

	private String imageUrl;

	private String videoUrl;

	Boolean isSold;
	Boolean isFeatured;

	private Integer gen;

	/**
	 * @author neel findAllByParam
	 */
	public PktMasterDto(String pktMasterId, Double carat, String certNo, String codeOfClarity, String codeOfColor,
			String codeOfCulet, String codeOfCut, String codeOfFluorescence, String codeOfLuster, String codeOfMilky,
			String codeOfPolish, String codeOfShade, String codeOfShape, String codeOfSymmentry, String comment,
			String country, Double crownAngle, Double crownHeight, Double diameter, Double disc, String efCode,
			String eyeClean, String fColor, String fIntensity, String fOvertone, Double gRap, Double gRate, Date giDate,
			Double girdle, Double height, Boolean isFeatured, Boolean isHold, Boolean isSold, String keyToSym,
			String lab, Double length, String lrHalf, Double pavilionAngle, Double pavilionHeight, String sbinName,
			Double seqNo, String shape, String sinName, String soinName, String stoneId, String strLn,
			Double tablePercentage, String tbinName, String tinName, String toinName, Double totDepth, Double width,
			Integer gen) {

		super();

		this.id = pktMasterId;
		this.carat = carat;
		this.shape = shape;
		this.stoneId = stoneId;
		this.giDate = giDate;
		this.gRap = gRap;
		this.certNo = certNo;
		this.codeOfLuster = codeOfLuster;
		this.codeOfFluorescence = codeOfFluorescence;
		this.codeOfCut = codeOfCut;
		this.codeOfColor = codeOfColor;
		this.codeOfClarity = codeOfClarity;
		this.codeOfCulet = codeOfCulet;
		this.codeOfMilky = codeOfMilky;
		this.codeOfPolish = codeOfPolish;
		this.codeOfShade = codeOfShade;
		this.codeOfShape = codeOfShape;
		this.codeOfSymmentry = codeOfSymmentry;
		this.comment = comment;
		this.country = country;
		this.crownAngle = crownAngle;
		this.crownHeight = crownHeight;
		this.diameter = diameter;
		this.disc = disc;
		this.efCode = efCode;
		this.eyeClean = eyeClean;
		this.fColor = fColor;
		this.fIntensity = fIntensity;
		this.fOvertone = fOvertone;
		this.gRate = gRate;
		this.height = height;
		this.isHold = isHold;
		this.isSold = isSold;
		this.isFeatured = isFeatured;
		this.keyToSym = keyToSym;
		this.lab = lab;
		this.length = length;
		this.lrHalf = lrHalf;
		this.pavilionAngle = pavilionAngle;
		this.pavilionHeight = pavilionHeight;
		this.sbinName = sbinName;
		this.seqNo = seqNo;
		this.width = width;
		this.totDepth = totDepth;
		this.tablePercentage = tablePercentage;
		this.tinName = tinName;
		this.sinName = sinName;
		this.toinName = toinName;
		this.tbinName = tbinName;
		this.soinName = soinName;
		this.strLn = strLn;
		this.girdle = girdle;
		this.gen = gen;
	}

	public PktMasterDto(String id, String stoneId, Boolean isHold) {
		super();
		this.id = id;
		this.stoneId = stoneId;
		this.isHold = isHold;
	}

	public PktMasterDto() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStoneId() {
		return stoneId;
	}

	public void setStoneId(String stoneId) {
		this.stoneId = stoneId;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double seqNo) {
		this.seqNo = seqNo;
	}

	public Double getCarat() {
		return carat;
	}

	public void setCarat(Double carat) {
		this.carat = carat;
	}

	public Date getGiDate() {
		return giDate;
	}

	public void setGiDate(Date giDate) {
		this.giDate = giDate;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getCodeOfShape() {
		return codeOfShape;
	}

	public void setCodeOfShape(String codeOfShape) {
		this.codeOfShape = codeOfShape;
	}

	public String getCodeOfColor() {
		return codeOfColor;
	}

	public void setCodeOfColor(String codeOfColor) {
		this.codeOfColor = codeOfColor;
	}

	public String getCodeOfClarity() {
		return codeOfClarity;
	}

	public void setCodeOfClarity(String codeOfClarity) {
		this.codeOfClarity = codeOfClarity;
	}

	public String getCodeOfPolish() {
		return codeOfPolish;
	}

	public void setCodeOfPolish(String codeOfPolish) {
		this.codeOfPolish = codeOfPolish;
	}

	public String getCodeOfCut() {
		return codeOfCut;
	}

	public void setCodeOfCut(String codeOfCut) {
		this.codeOfCut = codeOfCut;
	}

	public String getCodeOfFluorescence() {
		return codeOfFluorescence;
	}

	public void setCodeOfFluorescence(String codeOfFluorescence) {
		this.codeOfFluorescence = codeOfFluorescence;
	}

	public String getCodeOfSymmentry() {
		return codeOfSymmentry;
	}

	public void setCodeOfSymmentry(String codeOfSymmentry) {
		this.codeOfSymmentry = codeOfSymmentry;
	}

	public String getCodeOfLuster() {
		return codeOfLuster;
	}

	public void setCodeOfLuster(String codeOfLuster) {
		this.codeOfLuster = codeOfLuster;
	}

	public String getCodeOfShade() {
		return codeOfShade;
	}

	public void setCodeOfShade(String codeOfShade) {
		this.codeOfShade = codeOfShade;
	}

	public String getCodeOfMilky() {
		return codeOfMilky;
	}

	public void setCodeOfMilky(String codeOfMilky) {
		this.codeOfMilky = codeOfMilky;
	}

	public String getEyeClean() {
		return eyeClean;
	}

	public void setEyeClean(String eyeClean) {
		this.eyeClean = eyeClean;
	}

	public Double getDiameter() {
		return diameter;
	}

	public void setDiameter(Double diameter) {
		this.diameter = diameter;
	}

	public Double getTotDepth() {
		return totDepth;
	}

	public void setTotDepth(Double totDepth) {
		this.totDepth = totDepth;
	}

	public Double getTablePercentage() {
		return tablePercentage;
	}

	public void setTablePercentage(Double tablePercentage) {
		this.tablePercentage = tablePercentage;
	}

	public Double getCrownAngle() {
		return crownAngle;
	}

	public void setCrownAngle(Double crownAngle) {
		this.crownAngle = crownAngle;
	}

	public Double getCrownHeight() {
		return crownHeight;
	}

	public void setCrownHeight(Double crownHeight) {
		this.crownHeight = crownHeight;
	}

	public Double getPavilionAngle() {
		return pavilionAngle;
	}

	public void setPavilionAngle(Double pavilionAngle) {
		this.pavilionAngle = pavilionAngle;
	}

	public Double getPavilionHeight() {
		return pavilionHeight;
	}

	public void setPavilionHeight(Double pavilionHeight) {
		this.pavilionHeight = pavilionHeight;
	}

	public String getEfCode() {
		return efCode;
	}

	public void setEfCode(String efCode) {
		this.efCode = efCode;
	}

	public Boolean getIsHold() {
		return isHold;
	}

	public void setIsHold(Boolean isHold) {
		this.isHold = isHold;
	}

	public Double getDisc() {
		return disc;
	}

	public void setDisc(Double disc) {
		this.disc = disc;
	}

	public Double getgRate() {
		return gRate;
	}

	public void setgRate(Double gRate) {
		this.gRate = gRate;
	}

	public Double getgRap() {
		return gRap;
	}

	public void setgRap(Double gRap) {
		this.gRap = gRap;
	}

	public String getLab() {
		return lab;
	}

	public void setLab(String lab) {
		this.lab = lab;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getTinName() {
		return tinName;
	}

	public void setTinName(String tinName) {
		this.tinName = tinName;
	}

	public String getSinName() {
		return sinName;
	}

	public void setSinName(String sinName) {
		this.sinName = sinName;
	}

	public String getToinName() {
		return toinName;
	}

	public void setToinName(String toinName) {
		this.toinName = toinName;
	}

	public String getTbinName() {
		return tbinName;
	}

	public void setTbinName(String tbinName) {
		this.tbinName = tbinName;
	}

	public String getSoinName() {
		return soinName;
	}

	public void setSoinName(String soinName) {
		this.soinName = soinName;
	}

	public String getSbinName() {
		return sbinName;
	}

	public void setSbinName(String sbinName) {
		this.sbinName = sbinName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getfColor() {
		return fColor;
	}

	public void setfColor(String fColor) {
		this.fColor = fColor;
	}

	public String getfIntensity() {
		return fIntensity;
	}

	public void setfIntensity(String fIntensity) {
		this.fIntensity = fIntensity;
	}

	public String getfOvertone() {
		return fOvertone;
	}

	public void setfOvertone(String fOvertone) {
		this.fOvertone = fOvertone;
	}

	public String getStrLn() {
		return strLn;
	}

	public void setStrLn(String strLn) {
		this.strLn = strLn;
	}

	public String getLrHalf() {
		return lrHalf;
	}

	public void setLrHalf(String lrHalf) {
		this.lrHalf = lrHalf;
	}

	public String getCodeOfCulet() {
		return codeOfCulet;
	}

	public void setCodeOfCulet(String codeOfCulet) {
		this.codeOfCulet = codeOfCulet;
	}

	public String getKeyToSym() {
		return keyToSym;
	}

	public void setKeyToSym(String keyToSym) {
		this.keyToSym = keyToSym;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getGirdle() {
		return girdle;
	}

	public void setGirdle(Double girdle) {
		this.girdle = girdle;
	}

	public Boolean getIsNewArrival() {
		return isNewArrival;
	}

	public void setIsNewArrival(Boolean isNewArrival) {
		this.isNewArrival = isNewArrival;
	}

	public String getErrorMsg() {
		return ErrorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}

	public ConfirmOrder getConfirmOrder() {
		return confirmOrder;
	}

	public void setConfirmOrder(ConfirmOrder confirmOrder) {
		this.confirmOrder = confirmOrder;
	}

	public Double getPerCaratePrice() {
		return perCaratePrice;
	}

	public void setPerCaratePrice(Double perCaratePrice) {
		this.perCaratePrice = perCaratePrice;
	}

	public Double getRapRate() {
		return rapRate;
	}

	public void setRapRate(Double rapRate) {
		this.rapRate = rapRate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(Long totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;
	}

	public String getCutPolishSymmetry() {
		return cutPolishSymmetry;
	}

	public void setCutPolishSymmetry(String cutPolishSymmetry) {
		this.cutPolishSymmetry = cutPolishSymmetry;
	}

	public String getShapeImage() {
		return shapeImage;
	}

	public void setShapeImage(String shapeImage) {
		this.shapeImage = shapeImage;
	}

	public double getFinalTCTS() {
		return finalTCTS;
	}

	public void setFinalTCTS(double finalTCTS) {
		this.finalTCTS = finalTCTS;
	}

	public double getFinalDiscountPercentage() {
		return finalDiscountPercentage;
	}

	public void setFinalDiscountPercentage(double finalDiscountPercentage) {
		this.finalDiscountPercentage = finalDiscountPercentage;
	}

	public double getFinalRapRate() {
		return finalRapRate;
	}

	public void setFinalRapRate(double finalRapRate) {
		this.finalRapRate = finalRapRate;
	}

	public double getFinalPriceOrFinalCarat() {
		return finalPriceOrFinalCarat;
	}

	public void setFinalPriceOrFinalCarat(double finalPriceOrFinalCarat) {
		this.finalPriceOrFinalCarat = finalPriceOrFinalCarat;
	}

	public double getFinalTotal() {
		return finalTotal;
	}

	public void setFinalTotal(double finalTotal) {
		this.finalTotal = finalTotal;
	}

	public Integer getTotalPriceWithNoRecord() {
		return totalPriceWithNoRecord;
	}

	public void setTotalPriceWithNoRecord(Integer totalPriceWithNoRecord) {
		this.totalPriceWithNoRecord = totalPriceWithNoRecord;
	}

	public Set<String> getListOfNotFoundShape() {
		return listOfNotFoundShape;
	}

	public void setListOfNotFoundShape(Set<String> listOfNotFoundShape) {
		this.listOfNotFoundShape = listOfNotFoundShape;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getGen() {
		return gen;
	}

	public void setGen(Integer gen) {
		this.gen = gen;
	}

	public Boolean getIsSold() {
		return isSold;
	}

	public void setIsSold(Boolean isSold) {
		this.isSold = isSold;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

}
