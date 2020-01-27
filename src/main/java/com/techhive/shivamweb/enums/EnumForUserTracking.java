package com.techhive.shivamweb.enums;

public enum EnumForUserTracking {
	NEW_USER_REGISTRATION_FOR_CAMPAING("New User Registration For Campaing"),
	NEWREGISTRATION("New User Registration"),
	EMAILVERIFIED("User Email Verified"),
	ASSIGNSALESPERSON(" is Assigned SalesPerson to "),
	PARTYASSIGNED(" party assigned to "),
	STONEADDEDTOCART(" added to Cart"),
	STONEDELETEDFROMCART(" deleted from Cart"),
	STONEADDEDTOWISHLIST(" added to Wishlist"),
	STONEDELETEDTOWISHLIST(" deleted from Wishlist"),
	STONEADDEDTOOFFER(" Offer made for Stone"),
	CLINTACTIVATED(" Client Activated"),
	CLINTDEACTIVATED(" Client Deactivated"),
	CLINTDELETED(" Client Deleted"),
	ADDEDFEEDBACK("Added Feedback"),
	DELETEDFEEDBACK("Deleted Feedback"),
	STONEID("StoneID : "),
	LOGIN("User Login"),
	STONEREQUESTEDFORVIEW(" Stone requested for view "),
	STONEOFFERCONFORMED(" Stone Offer Confirmed "),
	STONEISCONFORMED(" Stone is Confirmed");
	
	
	private final String description;

	EnumForUserTracking(final String flag) {
		this.description = flag;
	}

	@Override
	public String toString() {
		return this.description;
	}
}
