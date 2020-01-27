package com.techhive.shivamweb.enums;

/**
 * 
 * @author Heena
 *
 */
public enum EnumForMaster {
	SHAPE_MASTER("ShapeMaster"), COLOR_MASTER("ColorMaster"), FANCY_COLOR_MASTER("FancyColorMaster"),
	CUT_POLISH_SYMMETRY_MASTER("CutPolishSymmetryMaster"), FLUORESCENCE_MASTER("FluorescenceMaster"), FANCY_COLOR_OVERTONE_MASTER("FancyOvertoneMaster"),
	FANCY_COLOR_INTENSITY_MASTER("FancyColorIntensityMaster"), CLARITY_MASTER("ClarityMaster"),MILK_MASTER("MilkMaster"),
	BROWN_SHADE_MASTRE("BrownShadeMaster"),LAB_MASTER("LabMaster"),COUNTRY_MASTER("CountryMaster");

	private final String name;

	private EnumForMaster(String value) {
		this.name = value;
	}

	public String value() {
		return this.name;
	}

	@Override
	public String toString() {
		return name;
	}
}
