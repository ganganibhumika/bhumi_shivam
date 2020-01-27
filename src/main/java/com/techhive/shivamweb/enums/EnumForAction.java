package com.techhive.shivamweb.enums;

/***
 * This Enum is used to maintain action for audit.
 * 
 * @author Vishvas
 *
 */
public enum EnumForAction {

	INSERTED("INSERTED"), UPDATED("UPDATED"), DELETED("DELETED");

	private final String name;

	private EnumForAction(String value) {
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
