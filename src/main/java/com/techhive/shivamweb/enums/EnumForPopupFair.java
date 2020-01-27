package com.techhive.shivamweb.enums;

/***
 * This Enum is used to maintain action for audit.
 * 
 * @author neel
 *
 */
public enum EnumForPopupFair {

	FAIR("fair"), POPUP("popup");

	private final String name;

	private EnumForPopupFair(String value) {
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
