package com.techhive.shivamweb.enums;

public enum EnumForNotificationDescription {

	WISHLIST("Stone price changed which is in your Wishlist."),
	CART("Stone price changed which is in your Cart."),
	CONFIRMTOADMIN("New stone(s) arrived for confirmation."),
	ABANDONDCART("There are some stone(s) in your Cart ,would you like to complete your purchase ?."),
	NEWSTONEDEMAND("New stone demand."),
	NEWUSERREGISTER("New user registered."),
	NEWSTONEVIEWREQUEST("New stone(s) requested for view."),
	SUGGESTIONFEEDBACK("New Suggestion And Feedback."),
	SALESPERSONASSIGNED("New SalesPerson Assigned."),
	STONEOFFERMADE("New offer request for stone(s)."),
	STONEOFFERCONFORMED("Your stone offer Confirmed by Admin."),
	NEWSTONEDEMANDFULFILLED("New stone matching your demand arrived."),
	WISHLISTSTONEAVAILABLE("Stone in your Wishlist is now available."),
	CARTSTONEAVAILABLE("Stone in your Cart is now available."),
	PLACEOFFERSTONEAVAILABLE("Stone in your Offer discount is now available."),
	PLACE_OFFER("Stone price changed which is in your Offer discount.");

	
	private final  String description;

	EnumForNotificationDescription(final String flag) {
		this.description = flag;
	}

	@Override
	public String toString() {
		return this.description;
	}
}
