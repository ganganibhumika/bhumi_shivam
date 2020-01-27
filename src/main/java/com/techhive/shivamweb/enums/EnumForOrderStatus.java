package com.techhive.shivamweb.enums;

public enum EnumForOrderStatus {

	PENDING("Pending"),
	SHIPPING("Shipping"),
	DELIVERY("Delivery"),
	CONFIRMED("Confirmed");
	
	private final String orderStatus;

	EnumForOrderStatus(final String flag) {
		this.orderStatus = flag;
	}

	@Override
	public String toString() {
		return this.orderStatus;
	}
}
