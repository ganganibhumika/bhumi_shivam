package com.techhive.shivamweb.enums;

public enum EnumForNotificationType {

	CONFIRMORDER("confirmOrder"),WISHLIST("wishlist"), CART("cart"), PLACE_OFFER("placeOffer"),CLINTACTIVATION("clientActivation"),SALSPERSON("salesPerson"),SUGGESTIONFEEDBACK("suggestion&Feedback"),VIEWREQUEST("viewRequest"),OFFEREQUEST("offerRequestAdmin"), DEMAND("demand");

	private final String name;

	private EnumForNotificationType(String value) {
		this.name = value;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

}
