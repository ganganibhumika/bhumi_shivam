package com.techhive.shivamweb.enums;

public enum EnumForCartStatus {

	PENDING("Pending"), INPROCESS("Inprocess"), COMPLETED("Completed");

	private final String cartStatus;

	EnumForCartStatus(final String status) {
		this.cartStatus = status;
	}

	@Override
	public String toString() {
		return this.cartStatus;
	}

}
