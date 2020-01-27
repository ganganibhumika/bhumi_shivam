package com.techhive.shivamweb.enums;

public enum EnumForOfferDiscountStatus {

	PENDING("Pending"),
	DECLINE("Decline"),
	APPROVR("Approve");
	
	private final String offerStatus;

	EnumForOfferDiscountStatus(final String flag) {
		this.offerStatus = flag;
	}

	@Override
	public String toString() {
		return this.offerStatus;
	}
}
