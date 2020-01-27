package com.techhive.shivamweb.enums;

/**
 * 
 * @author Heena
 *
 */

public enum EnumForLableName {
	/**
	 * lock position of Diamond Detail,Color,Carat,Cut-Polish-sym.
	 */
//headerName,field,hide,lockPosition,pinned,labelOrder,width,header classname,cell classname
	
	SHAP_OF_IMAGE("Shape Img","shapeImage",false,true,"left",0,97,"th-shapeImage","tc-shapeImage"),//INCOMMING add by bhumi
	DIAMONDDETAILS("StoneId","stoneId",false,true,"left",1,140,"th-stoneId","tc-stoneId"),
	COLOR("Color","codeOfColor",false,true,"left",2,80,"th-color","tc-color"),
	CARAT("Carat","carat",false,true,"left",3,80,"th-carat","tc-carat"),
	CUT_PLOISH_SYMMETRY("Cut-Polish-Sym","cutPolishSymmetry",false,true,"left",4,150,"th-cutPolishSymmetry","tc-cutPolishSymmetry"),//incomming
	/**
	 * end Lock
	 */
//	SHAP_OF_IMAGE("Shape Img","shapeImage",false,false,null,4,97,"th-shapeImage","tc-shapeImage"),//INCOMMING
	SHAPE("Shape","shape",false,false,null,5,80,"th-shape","tc-shape"),
	CLARITY("Clarity","codeOfClarity",false,false,null,6,90,"th-clarity","tc-clarity"),
	FLUORESCENCE("FLO","codeOfFluorescence",false,false,null,7,80,"th-flo","tc-flo"),
	DEPTH("T.Depth(%)","totDepth",false,false,null,8,110,"th-depth","tc-depth"),
	TABLE_PER("Table(%)","tablePercentage",false,false,null,9,95,"th-table","tc-table"),
	OUR_COMMENT("Comment","comment",false,false,null,10,250,"th-comment","tc-comment"),
	MEASUREMENT("Measurement.","measurement",false,false,null,11,120,"th-measurmenet","tc-measurmenet"),//incomming
	DISCOUNT("Rap(%)","disc",false,false,null,12,90,"th-rap","tc-rap"),
	$_CTS("Price ($)/CTS.","perCaratePrice",false,false,null,13,120,"th-price-cts","tc-price-cts"),//incomming
	TOTAL("Total","totalPrice",false,false,null,14,100,"th-total","tc-total"),//incomming
	
	/**
	 * hidden field display only in column chooser
	 */
	SEQNO("SeqNo","seqNo",true,false,null,15,120,"th-seqno","tc-seqno"),
//	CERTNO("Report","certNo",true,false,null,16,120,"th-report","tc-report"),
	CERTNO("Cert.No","certNo",true,false,null,16,120,"th-certNo","tc-certNo"),
	COUNTRY("Country","country",true,false,null,17,100,"th-country","tc-country"),
//	F_INTENSITY("Intensity","fIntensity",true,false,null,18,100,"th-intensity","tc-intensity"), //comment by bhumi
//	F_OVERTON("Overtone","fOvertone",true,false,null,19,100,"th-overtone","tc-overtone"), //comment by bhumi
	EYE_CLEAN("Eye Clean","eyeClean",true,false,null,20,100,"th-eyeclean","tc-eyeclean"),
	CODE_OF_CLUSTER("Luster","codeOfLuster",true,false,null,21,80,"th-luster","tc-luster"),
	CODE_OF_SHADE("Shade","codeOfShade",true,false,null,22,80,"th-shade","tc-shade"),
	CODE_OF_MILKY("Milky","codeOfMilky",true,false,null,23,80,"th-milky","tc-milky"),
	DIAMETER("Ratio","diameter",true,false,null,24,80,"th-ratio","tc-ratio"),
	CROWN_ANGLE("C.Angle","crownAngle",true,false,null,25,100,"th-cangle","tc-cangle"),
	CROWN_HEIGHT("C.Height","crownHeight",true,false,null,26,95,"th-height","tc-height"),
	PAVILION_ANGLE("P.Angle","pavilionAngle",true,false,null,27,95,"th-pangle","tc-pangle"),
	STR_LN("S.Len(%)","strLn",true,false,null,28,95,"th-len","tc-len"),
	LR_HALF("S.Half(%)","lrHalf",true,false,null,29,95,"th-half","tc-half"),
	KEY_TO_SYM("KeyToSymbol","keyToSym",true,false,null,30,200,"th-keytosymbol","tc-keytosymbol"),
	EF_CODE("E.Facet","efCode",true,false,null,31,85,"th-facet","tc-facet"),
	G_RATE("GRate","gRate",true,false,null,32,85,"th-grate","tc-grate"),
	CODE_OF_SHAPE("Shape Code","codeOfShape",true,false,null,33,100,"th-shapecode","tc-shapecode"),
	TIN_NAME("Table","tinName",true,false,null,34,90,"th-table","tc-table"),
	SIN_NAME("Side","sinName",true,false,null,35,100,"th-side","tc-side"),
	TOIN_NAME("Table Open","toinName",true,false,null,36,110,"th-tableopen","tc-tableopen"),
	SOIN_NAME("Side Open","soinName",true,false,null,37,110,"th-sideopen","tc-sideopen"),
	SBIN_NAME("Side Black ","sbinName",true,false,null,38,110,"th-sideblack","tc-sideblack"),
	//RAP_RATE("RapRate","rapRate",true,false,null,39,100,"th-raprate","tc-raprate"), confirm by jayesh sir
	GRAP("RapRate","gRap",true,false,null,39,80,"th-gRap","tc-gRap"),
	CR_NAME("Lab","lab",true,false,null,40,80,"th-lab","tc-lab"),
	CODE_OF_CULET("Culet","codeOfCulet",true,false,null,41,100,"th-culet","tc-culet"),
//	F_COLOR("FColor","fColor",true,false,null,42,200,"th-fcolor","tc-fcolor"), //comment by bhumi
	LENGTH("Length","length",true,false,null,43,80,"th-length","tc-length"),
	HEIGHT("Height","height",true,false,null,44,80,"th-height","tc-height"),
	WIDTH("Width","width",true,false,null,45,80,"th-width","tc-width"),
	GRIDLE("Girdle","girdle",true,false,null,46,80,"th-girdle","tc-girdle"),
//	GRAP("GRap","gRap",true,false,null,47,80,"th-gRap","tc-gRap"),
	PAVILION_HEIGHT("P.Height","pavilionHeight",true,false,null,47,110,"th-pavilionHeight","tc-pavilionHeight"),
	TABLE_BLACK("Table Black","tbinName",true,false,null,48,120,"th-tbinName","tc-tbinName"),
	
	;

	private final String headerName;
	private final String field;
	private final Boolean isPositionLoack;
	private final Boolean hideColumn;
	private final String pinned;
	private final Integer labelOrder;
	private final Integer width;
	private final String headerClass;
	private final String cellClass;

	private EnumForLableName(String headerName,String field,Boolean hideColumn,Boolean isLockPositionLoack,String pinned,Integer order,Integer width,String headerClass,String cellClass) {
		this.headerName = headerName;
		this.field = field;
		this.hideColumn=hideColumn;
		this.isPositionLoack=isLockPositionLoack;
		this.pinned=pinned;
		this.labelOrder=order;
		this.width=width;
		this.headerClass=headerClass;
		this.cellClass=cellClass;
	}
	
	public String getCellClass() {
		return cellClass;
	}

	public String getHeaderClass() {
		return headerClass;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getLabelOrder() {
		return labelOrder;
	}
	public String getPinned() {
		return pinned;
	}

	public String getHeaderName() {
		return headerName;
	}

	public String getField() {
		return field;
	}

	public Boolean getIsPositionLoack() {
		return isPositionLoack;
	}
	public Boolean getHideColumn() {
		return hideColumn;
	}
	
	
}
