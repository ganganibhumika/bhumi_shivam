package com.techhive.shivamweb.enums;

/***
 * This Enum is used to maintain action for audit.
 * 
 * @author Vishvas
 *
 */
public enum EnumForTabName {

	IMAGE("image"), VIDEO("video"), PLOTTING("plotting"), CERTIFICATE("certificate");

	private final String name;

	private EnumForTabName(String value) {
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
