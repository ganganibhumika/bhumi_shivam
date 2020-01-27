package com.techhive.shivamweb.master.model.history;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.enums.EnumForAction;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_PKT_MASTER_HISTORY)
@JsonInclude(Include.NON_EMPTY)
@EntityListeners(AuditingEntityListener.class)
public class PktMasterHistory {

	public PktMasterHistory(PktMaster target, EnumForAction action) {
		super();
		this.idOfEntity = target.getId();
		this.action = action;
		this.seqNo = target.getSeqNo();
		this.stoneId = target.getStoneId();
		this.carat = target.getCarat();
		this.country = target.getCountry();
		this.height = target.getHeight();
		this.length = target.getLength();
		this.width = target.getWidth();
		this.certNo = target.getCertNo();
		this.fColor = target.getfColor();
		this.fIntensity = target.getfIntensity();
		this.fOvertone = target.getfOvertone();
		this.codeOfShape = target.getCodeOfShape();
		this.codeOfColor = target.getCodeOfColor();
		this.codeOfClarity = target.getCodeOfClarity();
		this.codeOfCut = target.getCodeOfCut();
		this.codeOfPolish = target.getCodeOfPolish();
		this.codeOfSymmentry = target.getCodeOfSymmentry();
		this.eyeClean = target.getEyeClean();
		this.codeOfFluorescence = target.getCodeOfFluorescence();
		this.codeOfLuster = target.getCodeOfLuster();
		this.codeOfShade = target.getCodeOfShade();
		this.diameter = target.getDiameter();
		this.codeOfMilky = target.getCodeOfMilky();
		this.totDepth = target.getTotDepth();
		this.tablePercentage = target.getTablePercentage();
		this.crownAngle = target.getCrownAngle();
		this.crownHeight = target.getCrownHeight();
		this.pavilionAngle = target.getPavilionAngle();
		this.pavilionHeight = target.getPavilionHeight();
		this.strLn = target.getStrLn();
		this.lrHalf = target.getLrHalf();
		this.codeOfCulet = target.getCodeOfCulet();
		this.keyToSym = target.getKeyToSym();
		this.comment = target.getComment();
		this.efCode = target.getEfCode();
		this.isHold = target.getIsHold();
		this.lab = target.getLab(); // lab name = crName
		this.gRate = target.getgRate();
		this.disc = target.getDisc();
		this.shape = target.getShape();
		this.tinName = target.getTinName();
		this.sinName = target.getSinName();
		this.toinName = target.getToinName();
		this.tbinName = target.getTbinName();
		this.soinName = target.getSoinName();
		this.sbinName = target.getSbinName();
		this.giDate = target.getGiDate();
		this.isSold = target.getIsSold();
		this.isFeatured = target.getIsFeatured();
		this.discountUpdateDate = target.getDiscountUpdateDate();
		this.showId = target.getShowId();
	}

	public PktMasterHistory(String id) {
		super();
		this.id = id;
	}

	public PktMasterHistory() {
		super();
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "pktMasterHistoryId", nullable = false, insertable = false, updatable = false)
	private String id;

	private String idOfEntity;

	@Column(name = "stoneId", columnDefinition = "varchar(20)")
	private String stoneId;

	@Column(name = "seqNo", columnDefinition = "Numeric(18,0)")
	private Double seqNo;

	@Column(name = "carat", columnDefinition = "Numeric(10,2)")
	private Double carat;

	@Column(name = "giDate")
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date giDate;

	@Column(name = "height", columnDefinition = "Numeric(8,2)")
	private Double height;

	@Column(name = "length", columnDefinition = "Numeric(8,2)")
	private Double length;

	@Column(name = "width", columnDefinition = "Numeric(8,2)")
	private Double width;

	@Column(name = "certNo", columnDefinition = "varchar(30)")
	private String certNo;

	@Column(name = "codeOfShape", columnDefinition = "varchar(5)")
	private String codeOfShape;

	@Column(name = "codeOfColor", columnDefinition = "varchar(5)")
	private String codeOfColor;

	@Column(name = "codeOfClarity", columnDefinition = "varchar(5)")
	private String codeOfClarity;

	@Column(name = "codeOfPolish", columnDefinition = "varchar(5)")
	private String codeOfPolish;

	@Column(name = "codeOfCut", columnDefinition = "varchar(5)")
	private String codeOfCut;

	@Column(name = "codeOfFluorescence", columnDefinition = "varchar(5)")
	private String codeOfFluorescence;

	@Column(name = "codeOfSymmentry", columnDefinition = "varchar(5)")
	private String codeOfSymmentry;

	@Column(name = "codeOfLuster", columnDefinition = "varchar(5)")
	private String codeOfLuster;

	@Column(name = "codeOfShade", columnDefinition = "varchar(5)")
	private String codeOfShade;

	@Column(name = "codeOfMilky", columnDefinition = "varchar(5)")
	private String codeOfMilky;

	@Column(name = "eyeClean", columnDefinition = "varchar(50)")
	private String eyeClean;

	@Column(name = "diameter", columnDefinition = "Numeric(8,2)")
	private Double diameter;

	@Column(name = "totDepth", columnDefinition = "Numeric(8,2)")
	private Double totDepth;

	@Column(name = "tablePercentage", columnDefinition = "Numeric(8,2)")
	private Double tablePercentage;

	@Column(name = "crownAngle", columnDefinition = "Numeric(8,2)")
	private Double crownAngle;

	@Column(name = "crownHeight", columnDefinition = "Numeric(8,2)")
	private Double crownHeight;

	@Column(name = "pavilionAngle", columnDefinition = "Numeric(8,2)")
	private Double pavilionAngle;

	@Column(name = "pavilionHeight", columnDefinition = "Numeric(8,2)")
	private Double pavilionHeight;

	@Column(name = "efCode", columnDefinition = "varchar(10)")
	private String efCode;

	@Column(name = "isHold", columnDefinition = "bit")
	private Boolean isHold;

	@Column(name = "disc", columnDefinition = "Numeric(10,2)")
	private Double disc;

	@Column(name = "gRate", columnDefinition = "Numeric(10,2)")
	private Double gRate;

	@Column(name = "gRap", columnDefinition = "Numeric(10,2)")
	private Double gRap;

	@Column(name = "lab", columnDefinition = "varchar(10)")
	private String lab;

	@Column(name = "shape", columnDefinition = "varchar(15)")
	private String shape;

	@Column(name = "tinName", columnDefinition = "varchar(30)")
	private String tinName;

	@Column(name = "sinName", columnDefinition = "varchar(30)")
	private String sinName;

	@Column(name = "toinName", columnDefinition = "varchar(30)")
	private String toinName;

	@Column(name = "tbinName", columnDefinition = "varchar(30)")
	private String tbinName;

	@Column(name = "soinName", columnDefinition = "varchar(30)")
	private String soinName;

	@Column(name = "sbinName", columnDefinition = "varchar(30)")
	private String sbinName;

	@Column(name = "country", columnDefinition = "varchar(30)")
	private String country;

	@Column(name = "fColor", columnDefinition = "varchar(30)")
	private String fColor;

	@Column(name = "fIntensity", columnDefinition = "varchar(30)")
	private String fIntensity;

	@Column(name = "fOvertone", columnDefinition = "varchar(30)")
	private String fOvertone;

	@Column(name = "strLn", columnDefinition = "varchar(30)")
	private String strLn;

	@Column(name = "lrHalf", columnDefinition = "varchar(30)")
	private String lrHalf;

	@Column(name = "codeOfCulet", columnDefinition = "varchar(5)")
	private String codeOfCulet;

	@Column(name = "keyToSym", columnDefinition = "varchar(100)")
	private String keyToSym;

	@Column(name = "comment", columnDefinition = "varchar(200)")
	private String comment;

	private Boolean isSold;

	private Boolean isFeatured;

	@Column(name = "discountUpdateDate", columnDefinition = "date")
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date discountUpdateDate;

	@Enumerated(EnumType.STRING)
	EnumForAction action;

	@CreatedBy
	private String createdBy;

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String showId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdOfEntity() {
		return idOfEntity;
	}

	public void setIdOfEntity(String idOfEntity) {
		this.idOfEntity = idOfEntity;
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

	public Date getDiscountUpdateDate() {
		return discountUpdateDate;
	}

	public void setDiscountUpdateDate(Date discountUpdateDate) {
		this.discountUpdateDate = discountUpdateDate;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}
	

}
