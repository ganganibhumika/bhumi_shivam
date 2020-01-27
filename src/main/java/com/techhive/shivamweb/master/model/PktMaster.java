package com.techhive.shivamweb.master.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.audit.Auditable;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;
import com.techhive.shivamweb.listners.PktMasterListner;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

/* use for set sql to key value format */
@SqlResultSetMapping(name = ShivamWebVariableUtils.SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST, classes = {

		@ConstructorResult(targetClass = PktMaster.class, columns = {
				@ColumnResult(name = "pktMasterId", type = String.class),
				@ColumnResult(name = "carat", type = Double.class), @ColumnResult(name = "shape", type = String.class),
				@ColumnResult(name = "stoneId", type = String.class), @ColumnResult(name = "giDate", type = Date.class),
				@ColumnResult(name = "gRap", type = Double.class), @ColumnResult(name = "certNo", type = String.class),
				@ColumnResult(name = "codeOfLuster", type = String.class),
				@ColumnResult(name = "codeOfFluorescence", type = String.class),
				@ColumnResult(name = "codeOfCut", type = String.class),
				@ColumnResult(name = "codeOfColor", type = String.class),
				@ColumnResult(name = "codeOfClarity", type = String.class),
				@ColumnResult(name = "codeOfCulet", type = String.class),
				@ColumnResult(name = "codeOfMilky", type = String.class),
				@ColumnResult(name = "codeOfPolish", type = String.class),
				@ColumnResult(name = "codeOfShade", type = String.class),
				@ColumnResult(name = "codeOfShape", type = String.class),
				@ColumnResult(name = "codeOfSymmentry", type = String.class),

				@ColumnResult(name = "comment", type = String.class),
				@ColumnResult(name = "country", type = String.class),
				@ColumnResult(name = "crownAngle", type = Double.class),
				@ColumnResult(name = "crownHeight", type = Double.class),

				@ColumnResult(name = "diameter", type = Double.class),
				@ColumnResult(name = "disc", type = Double.class), @ColumnResult(name = "efCode", type = String.class),
				@ColumnResult(name = "eyeClean", type = String.class),

				@ColumnResult(name = "fColor", type = String.class),
				@ColumnResult(name = "fIntensity", type = String.class),
				@ColumnResult(name = "fOvertone", type = String.class),

				@ColumnResult(name = "gRate", type = Double.class), @ColumnResult(name = "height", type = Double.class),
				@ColumnResult(name = "isHold", type = Boolean.class),
				@ColumnResult(name = "isSold", type = Boolean.class),
				@ColumnResult(name = "isFeatured", type = Boolean.class),
				@ColumnResult(name = "keyToSym", type = String.class),

				@ColumnResult(name = "lab", type = String.class), @ColumnResult(name = "length", type = Double.class),
				@ColumnResult(name = "lrHalf", type = String.class),
				@ColumnResult(name = "pavilionAngle", type = Double.class),
				@ColumnResult(name = "pavilionHeight", type = Double.class),
				@ColumnResult(name = "sbinName", type = String.class),
				@ColumnResult(name = "seqNo", type = Double.class),

				@ColumnResult(name = "width", type = Double.class),
				@ColumnResult(name = "totDepth", type = Double.class),
				@ColumnResult(name = "tablePercentage", type = Double.class),

				@ColumnResult(name = "tinName", type = String.class),
				@ColumnResult(name = "sinName", type = String.class),
				@ColumnResult(name = "toinName", type = String.class),
				@ColumnResult(name = "tbinName", type = String.class),
				@ColumnResult(name = "soinName", type = String.class),
				@ColumnResult(name = "strLn", type = String.class), @ColumnResult(name = "girdle", type = Double.class),
				@ColumnResult(name = "showId", type = String.class),

				@ColumnResult(name = "shapeImage", type = String.class),
				@ColumnResult(name = "shapeOrder", type = Integer.class),
				@ColumnResult(name = "milkMasterOrder", type = Integer.class),
				@ColumnResult(name = "labMasterOrder", type = Integer.class),
				@ColumnResult(name = "fluorescenceMasterOrder", type = Integer.class),
				@ColumnResult(name = "fancyOvertoneOrder", type = Integer.class),
				@ColumnResult(name = "fancyIntensityOrder", type = Integer.class),
				@ColumnResult(name = "fancyColorOrder", type = Integer.class),
				@ColumnResult(name = "cutPolishSymmentryMasterOrder", type = Integer.class),
				@ColumnResult(name = "countryMasterOrder", type = Integer.class),
				@ColumnResult(name = "colorOrder", type = Integer.class),
				@ColumnResult(name = "clarityMasterOrder", type = Integer.class),
				@ColumnResult(name = "brownShadeMasterOrder", type = Integer.class),

		}), })

/* END use for set sql to key value format */

@Entity
@Table(name = ShivamWebVariableUtils.TABLE_NAME_FOR_PKT_MASTER)
@EntityListeners(PktMasterListner.class)
@JsonInclude(Include.NON_EMPTY)
@XmlRootElement
public class PktMaster extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "pktMasterId", nullable = false, insertable = false, updatable = false)
	private String id;

	@Column(name = "stoneId", columnDefinition = "varchar(20)", unique = true)
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
	private String certNo;// Report No

	@Column(name = "codeOfShape", columnDefinition = "varchar(5)")
	private String codeOfShape;

	@Column(name = "codeOfColor", columnDefinition = "varchar(20)")
	private String codeOfColor; // Col

	@Column(name = "codeOfClarity", columnDefinition = "varchar(5)")
	private String codeOfClarity;// Clar

	@Column(name = "codeOfPolish", columnDefinition = "varchar(5)")
	private String codeOfPolish;// Pol

	@Column(name = "codeOfCut", columnDefinition = "varchar(5)")
	private String codeOfCut;// Cut

	@Column(name = "codeOfFluorescence", columnDefinition = "varchar(5)")
	private String codeOfFluorescence;// Flo

	@Column(name = "codeOfSymmentry", columnDefinition = "varchar(5)")
	private String codeOfSymmentry;// Sym

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
	private Double totDepth;// Depth

	@Column(name = "tablePercentage", columnDefinition = "Numeric(8,2)")
	private Double tablePercentage;// Tab

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
	private Double disc;// Disc

	@Column(name = "gRate", columnDefinition = "Numeric(10,2)")
	private Double gRate;

	@Column(name = "gRap", columnDefinition = "Numeric(10,2)")
	private Double gRap;// RapRat //result screen

	@Column(name = "lab", columnDefinition = "varchar(10)")
	private String lab;// Cert

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

	@Column(name = "girdle", columnDefinition = "Numeric(8,2)")
	private Double girdle;

	private Boolean isSold;

	private Boolean isFeatured;

	@Column(name = "showId", columnDefinition = "varchar(30)")
	private String showId; // add new field for show

	@Column(name = "discountUpdateDate", columnDefinition = "date")
	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date discountUpdateDate;

	@Transient
	private Boolean isNewArrival;

	@Transient
	private String ErrorMsg;

	// /* -------------------- End ------------------------ */

	@OneToOne(mappedBy = "pktMaster")
	@JsonIgnore
	private ConfirmOrder confirmOrder;

	/* use for calculation */
	@Transient
	private Double perCaratePrice;// Rate

	@Transient
	private Double rapRate;

	@Transient
	private Double totalPrice; // old-totalAmount//amount

	@Transient
	private Integer totalNoOfRecords;

	@Transient
	private String measurement;// Meas

	@Transient
	private String cutPolishSymmetry;

	@Transient
	private String shapeImage;

	// only use for return not in constructor
	@Transient
	private double finalTCTS;
	@Transient
	private double finalDiscountPercentage;
	@Transient
	private double finalRapRate;
	@Transient
	private double finalPriceOrFinalCarat;
	@Transient
	private double finalTotal;

	@Transient
	private Integer totalPriceWithNoRecord;

	@Transient
	private String imageUrl;

	@Transient
	private String videoUrl;

	@Transient
	private String saveVideoUrl;

	@Transient
	private String InclusionPlottingUrl;

	/* use for return order */

	@Transient
	private Integer shapeOrder;

	@Transient
	private Integer milkMasterOrder;
	@Transient
	private Integer labMasterOrder;

	@Transient
	private Integer fluorescenceMasterOrder;

	@Transient
	private Integer fancyOvertoneOrder;

	@Transient
	private Integer fancyIntensityOrder;

	@Transient
	private Integer fancyColorOrder;

	@Transient
	private Integer cutPolishSymmentryMasterOrder;

	@Transient
	private Integer countryMasterOrder;

	@Transient
	private Integer colorOrder;

	@Transient
	private Integer clarityMasterOrder;

	@Transient
	private Integer brownShadeMasterOrder;

	@Transient
	private Double discOrignal;

	@Transient
	private String codeOfColorForDisplay;

	@Transient
	private Integer sync_Idn; // use for shivam only

	/* End master order */

	public PktMaster() {
		super();
	}

	// response for API via_lazzyloading

	public PktMaster(String pktMasterId, Double carat, String shape, String stoneId, Date giDate, Double gRap,
			String certNo, String codeOfLuster, String codeOfFluorescence, String codeOfCut, String codeOfColor,
			String codeOfClarity, String codeOfCulet, String codeOfMilky, String codeOfPolish, String codeOfShade,
			String codeOfShape, String codeOfSymmentry, String comment, String country, Double crownAngle,
			Double crownHeight, Double diameter, Double disc, String efCode, String eyeClean, String fColor,
			String fIntensity, String fOvertone, Double gRate, Double height, Boolean isHold, Boolean isSold,
			Boolean isFeatured, String keyToSym, String lab, Double length, String lrHalf, Double pavilionAngle,
			Double pavilionHeight, String sbinName, Double seqNo, Double width, Double totDepth, Double tablePercentage,
			String tinName, String sinName, String toinName, String tbinName, String soinName, String strLn,
			Double girdle, String showId, String shapeImage, Integer shapeOrder, Integer milkMasterOrder,
			Integer labMasterOrder, Integer fluorescenceMasterOrder, Integer fancyOvertoneOrder,
			Integer fancyIntensityOrder, Integer fancyColorOrder, Integer cutPolishSymmentryMasterOrder,
			Integer countryMasterOrder, Integer colorOrder, Integer clarityMasterOrder, Integer brownShadeMasterOrder) {

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
		this.showId = showId; // add new field

		this.shapeImage = shapeImage;
		this.shapeOrder = shapeOrder;
		this.milkMasterOrder = milkMasterOrder;
		this.labMasterOrder = labMasterOrder;
		this.fluorescenceMasterOrder = fluorescenceMasterOrder;
		this.fancyOvertoneOrder = fancyOvertoneOrder;
		this.fancyIntensityOrder = fancyIntensityOrder;
		this.fancyColorOrder = fancyColorOrder;
		this.cutPolishSymmentryMasterOrder = cutPolishSymmentryMasterOrder;
		this.countryMasterOrder = countryMasterOrder;
		this.colorOrder = colorOrder;
		this.clarityMasterOrder = clarityMasterOrder;
		this.brownShadeMasterOrder = brownShadeMasterOrder;

	}

	public PktMaster(String id, String stoneId, Double seqNo, Double carat, Date giDate, Double height, Double length,
			Double width, String certNo, String codeOfShape, String codeOfColor, String codeOfClarity,
			String codeOfPolish, String codeOfCut, String codeOfFluorescence, String codeOfSymmentry,
			String codeOfLuster, String codeOfShade, String codeOfMilky, String eyeClean, Double diameter,
			Double totDepth, Double tablePercentage, Double crownAngle, Double crownHeight, Double pavilionAngle,
			Double pavilionHeight, String efCode, Boolean isHold, Double disc, Double gRate, Double gRap, String lab,
			String shape, String tinName, String sinName, String toinName, String tbinName, String soinName,
			String sbinName, String country, String fColor, String fIntensity, String fOvertone, String strLn,
			String lrHalf, String codeOfCulet, String keyToSym, String comment, Double girdle) {
		super();
		this.id = id;
		this.stoneId = stoneId;
		this.seqNo = seqNo;
		this.carat = carat;
		this.giDate = giDate;
		this.height = height;
		this.length = length;
		this.width = width;
		this.certNo = certNo;
		this.codeOfShape = codeOfShape;
		this.codeOfColor = codeOfColor;
		this.codeOfClarity = codeOfClarity;
		this.codeOfPolish = codeOfPolish;
		this.codeOfCut = codeOfCut;
		this.codeOfFluorescence = codeOfFluorescence;
		this.codeOfSymmentry = codeOfSymmentry;
		this.codeOfLuster = codeOfLuster;
		this.codeOfShade = codeOfShade;
		this.codeOfMilky = codeOfMilky;
		this.eyeClean = eyeClean;
		this.diameter = diameter;
		this.totDepth = totDepth;
		this.tablePercentage = tablePercentage;
		this.crownAngle = crownAngle;
		this.crownHeight = crownHeight;
		this.pavilionAngle = pavilionAngle;
		this.pavilionHeight = pavilionHeight;
		this.efCode = efCode;
		this.isHold = isHold;
		this.disc = disc;
		this.gRate = gRate;
		this.gRap = gRap;
		this.lab = lab;
		this.shape = shape;
		this.tinName = tinName;
		this.sinName = sinName;
		this.toinName = toinName;
		this.tbinName = tbinName;
		this.soinName = soinName;
		this.sbinName = sbinName;
		this.country = country;
		this.fColor = fColor;
		this.fIntensity = fIntensity;
		this.fOvertone = fOvertone;
		this.strLn = strLn;
		this.lrHalf = lrHalf;
		this.codeOfCulet = codeOfCulet;
		this.keyToSym = keyToSym;
		this.comment = comment;
		this.girdle = girdle;

	}

	/**
	 * @author neel findAllByParam
	 */
	public PktMaster(String pktMasterId, Double carat, String certNo, String codeOfClarity, String codeOfColor,
			String codeOfCulet, String codeOfCut, String codeOfFluorescence, String codeOfLuster, String codeOfMilky,
			String codeOfPolish, String codeOfShade, String codeOfShape, String codeOfSymmentry, String comment,
			String country, Double crownAngle, Double crownHeight, Double diameter, Double disc, String efCode,
			String eyeClean, String fColor, String fIntensity, String fOvertone, Double gRap, Double gRate, Date giDate,
			Double girdle, Double height, Boolean isFeatured, Boolean isHold, Boolean isSold, String keyToSym,
			String lab, Double length, String lrHalf, Double pavilionAngle, Double pavilionHeight, String sbinName,
			Double seqNo, String shape, String sinName, String soinName, String stoneId, String strLn,
			Double tablePercentage, String tbinName, String tinName, String toinName, Double totDepth, Double width) {

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
	}

	@PreRemove
	public void preRemove() {
		ConfirmOrder confirmOrder = this.confirmOrder;
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(confirmOrder)) {
			confirmOrder.setPktMaster(null);
		}
	}

	@PrePersist
	public void onCreate() {
		// this.isHold = false; // from shivam_db add stone which is already hold
		this.isFeatured = false;
		this.isSold = false;
	}

	public PktMaster(String pktMasterId) {
		this.id = pktMasterId;
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
		// if (codeOfColor.equalsIgnoreCase("*")) {
		// return this.fColor + " " + this.fIntensity + " " + this.fOvertone;
		// }
		// if (codeOfColor.equals("*")) {
		// if (this.fOvertone.equalsIgnoreCase("NONE")) {
		// return this.fIntensity + " " + "" + " " + this.fColor;
		// }
		// return this.fIntensity + " " + this.fOvertone + " " + this.fColor;
		// }

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
		this.fColor = fColor.trim();
	}

	public String getfIntensity() {
		return fIntensity;
	}

	public void setfIntensity(String fIntensity) {
		this.fIntensity = fIntensity.trim();
	}

	public String getfOvertone() {
		return fOvertone;
	}

	public void setfOvertone(String fOvertone) {
		this.fOvertone = fOvertone.trim();
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

	public Integer getTotalNoOfRecords() {
		return totalNoOfRecords;
	}

	public void setTotalNoOfRecords(Integer totalNoOfRecords) {
		this.totalNoOfRecords = totalNoOfRecords;
	}

	public String getMeasurement() {
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(this.length)
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(this.width)
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(this.height)) {
			return null;
		}
		return this.length + " * " + this.width + " * " + this.height;
	}

	public void setMeasurement(String measurement) {
		this.measurement = measurement;

	}

	public String getCutPolishSymmetry() {

		String cut = ShivamWebMethodUtils.isObjectNullOrEmpty(this.codeOfCut) ? " * " : this.codeOfCut;
		String polish = ShivamWebMethodUtils.isObjectNullOrEmpty(this.codeOfPolish) ? " * " : this.codeOfPolish;
		String sysmm = ShivamWebMethodUtils.isObjectNullOrEmpty(this.codeOfSymmentry) ? " * " : this.codeOfSymmentry;

		return cut + " - " + polish + " - " + sysmm;
		// return this.codeOfCut + " - " + this.codeOfPolish + " - " +
		// this.codeOfSymmentry;

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

	public Integer getShapeOrder() {
		return shapeOrder;
	}

	public void setShapeOrder(Integer shapeOrder) {
		this.shapeOrder = shapeOrder;
	}

	public Integer getMilkMasterOrder() {
		return milkMasterOrder;
	}

	public void setMilkMasterOrder(Integer milkMasterOrder) {
		this.milkMasterOrder = milkMasterOrder;
	}

	public Integer getLabMasterOrder() {
		return labMasterOrder;
	}

	public void setLabMasterOrder(Integer labMasterOrder) {
		this.labMasterOrder = labMasterOrder;
	}

	public Integer getFluorescenceMasterOrder() {
		return fluorescenceMasterOrder;
	}

	public void setFluorescenceMasterOrder(Integer fluorescenceMasterOrder) {
		this.fluorescenceMasterOrder = fluorescenceMasterOrder;
	}

	public Integer getFancyOvertoneOrder() {
		return fancyOvertoneOrder;
	}

	public void setFancyOvertoneOrder(Integer fancyOvertoneOrder) {
		this.fancyOvertoneOrder = fancyOvertoneOrder;
	}

	public Integer getFancyIntensityOrder() {
		return fancyIntensityOrder;
	}

	public void setFancyIntensityOrder(Integer fancyIntensityOrder) {
		this.fancyIntensityOrder = fancyIntensityOrder;
	}

	public Integer getFancyColorOrder() {
		return fancyColorOrder;
	}

	public void setFancyColorOrder(Integer fancyColorOrder) {
		this.fancyColorOrder = fancyColorOrder;
	}

	public Integer getCutPolishSymmentryMasterOrder() {
		return cutPolishSymmentryMasterOrder;
	}

	public void setCutPolishSymmentryMasterOrder(Integer cutPolishSymmentryMasterOrder) {
		this.cutPolishSymmentryMasterOrder = cutPolishSymmentryMasterOrder;
	}

	public Integer getCountryMasterOrder() {
		return countryMasterOrder;
	}

	public void setCountryMasterOrder(Integer countryMasterOrder) {
		this.countryMasterOrder = countryMasterOrder;
	}

	public Integer getColorOrder() {
		return colorOrder;
	}

	public void setColorOrder(Integer colorOrder) {
		this.colorOrder = colorOrder;
	}

	public Integer getClarityMasterOrder() {
		return clarityMasterOrder;
	}

	public void setClarityMasterOrder(Integer clarityMasterOrder) {
		this.clarityMasterOrder = clarityMasterOrder;
	}

	public Integer getBrownShadeMasterOrder() {
		return brownShadeMasterOrder;
	}

	public void setBrownShadeMasterOrder(Integer brownShadeMasterOrder) {
		this.brownShadeMasterOrder = brownShadeMasterOrder;
	}

	public String getSaveVideoUrl() {
		return saveVideoUrl;
	}

	public void setSaveVideoUrl(String saveVideoUrl) {
		this.saveVideoUrl = saveVideoUrl;
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

	public String getInclusionPlottingUrl() {
		return InclusionPlottingUrl;
	}

	public void setInclusionPlottingUrl(String inclusionPlottingUrl) {
		InclusionPlottingUrl = inclusionPlottingUrl;
	}

	public Double getDiscOrignal() {
		return discOrignal;
	}

	public void setDiscOrignal(Double discOrignal) {
		this.discOrignal = discOrignal;
	}

	public Date getDiscountUpdateDate() {
		return discountUpdateDate;
	}

	public void setDiscountUpdateDate(Date discountUpdateDate) {
		this.discountUpdateDate = discountUpdateDate;
	}

	public String getCodeOfColorForDisplay() {
		if (codeOfColor.equals("*")) {
			if (this.fOvertone.equalsIgnoreCase("NONE")) {
				return this.fIntensity + " " + "" + " " + this.fColor;
			}
			return this.fIntensity + " " + this.fOvertone + " " + this.fColor;
		}
		return codeOfColor;
	}

	public void setCodeOfColorForDisplay(String codeOfColorForDisplay) {
		this.codeOfColorForDisplay = codeOfColorForDisplay;
	}

	public Integer getSync_Idn() {
		return sync_Idn;
	}

	public void setSync_Idn(Integer sync_Idn) {
		this.sync_Idn = sync_Idn;
	}

	public String getShowId() {
		return showId;
	}

	public void setShowId(String showId) {
		this.showId = showId;
	}

}
