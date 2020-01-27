
package com.techhive.shivamweb.utils;

import java.text.SimpleDateFormat;

public class ShivamWebVariableUtils {

	/**
	 * table name declaration for master
	 */
	public static final String TABLE_NAME_FOR_USER = "tblUser";
	public static final String TABLE_NAME_FOR_PARTY = "tblPartyMaster";
	public static final String TABLE_NAME_FOR_USERTRACKING = "tblUserTracking";
	public static final String TABLE_NAME_FOR_SHAPEMASTER = "tblShapeMaster";
	public static final String TABLE_NAME_FOR_COLORMASTER = "tblColorMaster";
	public static final String TABLE_NAME_FOR_FANCYINTENCITYMASTER = "tblFancyIntensityMaster";

	public static final String TABLE_NAME_FOR_FANCYOVERTONEMASTER = "tblFancyOvertoneMaster";
	public static final String TABLE_NAME_FOR_FANCYCOLORMASTER = "tblFancyColorMaster";
	public static final String TABLE_NAME_FOR_CLARITYMASTER = "tblClarityMaster";
	public static final String TABLE_NAME_FOR_CUTPOLISHSYMMETRYMASTER = "tblCutPolishSymmetryMaster";
	public static final String TABLE_NAME_FOR_LABMASTER = "tblLabMaster";
	public static final String TABLE_NAME_FOR_MILKYMASTER = "tblMilkMaster";
	public static final String TABLE_NAME_FOR_COUNTRYMASTER = "tblCountryMaster";
	public static final String TABLE_NAME_FOR_BROWNSHADEMASTER = "tblBrownShadeMaster";
	public static final String TABLE_NAME_FOR_FLUORESENCEMASTER = "tblFluorescenceMaster";
	public static final String TABLE_NAME_FOR_DISCOUNTMASTER = "tblDiscountMaster";
	public static final String TABLE_NAME_FOR_SALESPERSONMASTER = "tblSalesPersonMaster";
	public static final String TABLE_NAME_FOR_SOFTWARE_SALES_PERSON_MASTER = "tblSoftWareSalesPersonMaster";
	public static final String TABLE_NAME_FOR_PKT_MASTER = "tblPktMaster";
	public static final String TABLE_NAME_FOR_RESULT_SCREEN_LABLES_NAME = "tblResultScreenLabelsName";
	public static final String TABLE_NAME_FOR_RESULT_SCREEN_LABLES_VALUE = "tblResultScreenLabelsValue";
	public static final String TABLE_NAME_FOR_SUGGESTIONFEEDBACK = "tblSuggestionFeedback";
	public static final String TABLE_NAME_FOR_POPUMANAGER = "tblPopupManager";
	public static final String TABLE_NAME_FOR_CONFIRMORDER = "tblConfirmOrder";
	public static final String TABLE_NAME_FOR_CART = "tblCart";
	public static final String TABLE_NAME_FOR_WISHLIST = "tblWishlist";
	public static final String TABLE_NAME_FOR_OFFERDISCOUNTREQUEST = "tblOfferDiscountRequest";
	public static final String TABLE_NAME_FOR_SAVESEARCHCRITERIA = "tblSaveSearchCriteria";
	public static final String TABLE_NAME_FOR_CREATE_DEMAND = "tblCreateDemand";
	public static final String TABLE_NAME_FOR_SEARCH_HISTORY = "tblSearchHistory";
	public static final String TABLE_NAME_FOR_NEWARRIVALSETTINGS = "tblNewArrivalSettings";
	public static final String TABLE_NAME_FOR_THIRD_PARTY_DISCOUNT_MASTER = "tblThirdPartyDiscountMaster";
	public static final String TABLE_NAME_FOR_USERMIGRATION = "tblUserMigration";
	public static final String TABLE_NAME_FOR_UPCOMINGFAIR = "tblUpcomingFair";
	public static final String TABLE_NAME_FOR_VIEWREQUEST = "tblViewRequest";
	public static final String TABLE_NAME_FOR_SOFTWARE_PATY_MASTER = "tblSoftwarePartyMaster";
	public static final String TABLE_NAME_FOR_NOTIFICATION = "tblNotification";
	public static final String TABLE_NAME_FOR_APP_VERSION_MASTER = "tblAppVersionMaster";

	/**
	 * table name declaration for history
	 */
	public static final String TABLE_NAME_FOR_USER_HISTORY = "historyForUser";
	public static final String TABLE_NAME_FOR_SHAPEMASTER_HISTORY = "historyForShapeMaster";

	public static final String TABLE_NAME_FOR_PKT_MASTER_HISTORY = "historyForPktMaster";

	public static final String TABLE_NAME_FOR_COLORMASTER_HISTORY = "historyForColorMaster";
	public static final String TABLE_NAME_FOR_FANCYINTENCITYMASTER_HISTORY = "historyForFancyIntensityMaster";
	public static final String TABLE_NAME_FOR_FANCYOVERTONEMASTER_HISTORY = "historyForFancyOvertoneMaster";
	public static final String TABLE_NAME_FOR_FANCYCOLORMASTER_HISTORY = "historyForFancyColorMaster";
	public static final String TABLE_NAME_FOR_CLARITYMASTER_HISTORY = "historyForClarityMaster";
	public static final String TABLE_NAME_FOR_CUTPOLISHSYMMETRYMASTER_HISTORY = "historyForCutPolishSymmentryMaster";
	public static final String TABLE_NAME_FOR_LABMASTER_HISTORY = "historyForLabMaster";
	public static final String TABLE_NAME_FOR_MILKYMASTER_HISTORY = "historyForMilkMaster";
	public static final String TABLE_NAME_FOR_COUNTRYMASTER_HISTORY = "historyForCountryMaster";
	public static final String TABLE_NAME_FOR_BROWNSHADEMASTER_HISTORY = "historyForBrownShadeMaster";
	public static final String TABLE_NAME_FOR_FLUORESENCEMASTER_HISTORY = "historyForFluorescenceMaster";
	public static final String TABLE_NAME_FOR_DISCOUNT_HISTORY = "historyForDiscountMaster";
	public static final String TABLE_NAME_FOR_SALESPERSON_HISTORY = "historyForSalesPerson";
	public static final String TABLE_NAME_FOR_SUGGESTIONFEEDBACK_HISTORY = "historyForSuggestionFeedback";
	public static final String TABLE_NAME_FOR_POPUMANAGER_HISTORY = "historyForPopupManager";
	public static final String TABLE_NAME_FOR_CONFIRMORDER_HISTORY = "historyForConfirmOrder";
	public static final String TABLE_NAME_FOR_CART_HISTORY = "historyForCart";
	public static final String TABLE_NAME_FOR_WISHLIST_HISTORY = "historyForWishlist";
	public static final String TABLE_NAME_FOR_SAVESEARCHCRITERIA_HISTORY = "historyForSaveSearchCriteria";
	public static final String TABLE_NAME_FOR_CREATE_DEMAND_HISTORY = "historyForCreateDemand";
	public static final String TABLE_NAME_FOR_OFFERDISCOUNTREQUEST_HISTORY = "historyForOfferDiscountRequest";
	public static final String TABLE_NAME_FOR_NEWARRIVALSETTINGS_HISTORY = "historyForNewArrivalSettings";
	public static final String TABLE_NAME_FOR_THIRD_PARTY_DISCOUNT_MASTER_HISTORY = "historyForThirdPartyDiscountMaster";
	public static final String TABLE_NAME_FOR_PARTYMASTER_HISTORY = "historyForPartyMaster";
	public static final String TABLE_NAME_FOR_USERMIGRATION_HISTORY = "historyForUserMigration";
	public static final String TABLE_NAME_FOR_UPCOMINGFAIR_HISTORY = "historyForUpcomingFair";
	public static final String TABLE_NAME_FOR_SOFTWARE_SALES_PERSON_MASTER_HISTORY = "historyForSoftWareSalesPersonMaster";
	public static final String TABLE_NAME_FOR_SOFTWARE_PATY_MASTER_HISTORY = "historyForSoftwarePartyMaster";
	public static final String TABLE_NAME_FOR_SOFTWAREPARTY_HISTORY = "historyForSoftwareParty";
	public static final String TABLE_NAME_FOR_NOTIFICATION_HISTORY = "historyForNotification";
	public static final String TABLE_NAME_FOR_VIEWREQUEST_HISTORY = "historyForViewrequest";

	/**
	 * date format declaration
	 */

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT_OF_DATABASE = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT_FOR_SEND_APP = new SimpleDateFormat("dd/MM/yyyy");

	public static final String DATE_FORMAT_FROM_ANGULAR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

//	public static final String DATE_FORMAT_FROM_SHIVAM = "dd-MM-yyyy HH:mm:ss a";
	public static final String DATE_FORMAT_FROM_SHIVAM = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	/**
	 * error message declaration
	 */
	public static final String ERROR = "error";
	public static final String INCOMPLETE_DATA_FROM_ANGULAR = "Incomplete Data.";

	/**
	 * message for CRUD
	 */
	public static final String INSERT_SUCCESSFULLY = "added successfully.";
	public static final String UPDATE_SUCCESSFULLY = "updated successfully.";
	public static final String DELETE_SUCCESSFULLY = "deleted successfully.";
	public static final String REGISTERED_SUCCESSFULLY = "registered successfully.";
	public static final String REGISTER_OR_SENDMAIL_EXCEPTION = "Employee registered succesfully.\n"
			+ "OTP for Login has been successfully sent your registered Email.";

	public static final String MSG_FOR_RESET_PASSWORD_MAIL = "Link for Reset Password has been sent to your registered Email.";
	public static final String MSG_FOR_DELETE_USER = "Sorry you're not authorized to access, \n"
			+ "Please contact to your administrator.";

	public static final String REGISTER_OR_SENDMAIL_SUCCESS = "Your account create succesfully.\n"
			+ "Please verify your email address.";

	public static final String REGISTER_OR_SENDMAIL_SUCCESS_MIGRATION = "Your profile updated succesfully.\n"
			+ "Please verify your email address.";

	public static final String UPDATE_OR_SENDMAIL_SUCCESS = "Employee Details update succesfully.\n"
			+ "OTP successfully sent to your registered Email.";

	public static final String MSG_FOR_NOT_REUSE_FORGOT_PASSWORD_LINK = "Sorry you can not reuse this link, \n"
			+ "Please resend request for forgot password.";

	public static final String MSG_FOR_CAMPING_USER_REGISTERED = "Your account create succesfully";

	/**
	 * other's
	 */

	public static final String AES_KEY = "mvLBiZsiTbGwrf@!";
	public static final String ALREADYEXIST = "already exist.";
	public static final String SUCCESS = "Success";
	public static final String FAIL = "Fail";
	public static final String NOT_FOUND_IN_DB = "not found record.";

	public static final Integer MAX_RECORDS_LIMITS = 300;
	public static final String MORE_THAN_MAX_RECORDS_LIMITS = "We have more than " + MAX_RECORDS_LIMITS
			+ " stones in this criteria, kindly filter your search options.";

	public static final String RECORD_NOT_FOUND_IN_DB = "Records not found.";

	/*
	 * Admin msg
	 * 
	 */
	public static final String MSG_FOR_WAIT_ADMIN_APPROVE = "You are not approved by admin.";

	public static final String USER_NOT_FOUND = "User not Found.";
	public static final String PARTY_NOT_FOUND = "Party not Found.";
	/*
	 * Admin settigns for email send
	 */

	// public static final String API_KEY_FOR_SENDGRID =
	// "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";
	// public static final String SENDER_EMAIL_FOR_SEND_MAIL =
	// "techhivetest@gmail.com";
	//
	// public static final String ADMIN_EMAIL_FOR_NEW_REGISTER_USER =
	// "techhivetest@gmail.com";

	public static final String API_KEY_FOR_SENDGRID = "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";
	public static final String SENDER_EMAIL_FOR_SEND_MAIL = "sales@shivamjewels.in";
	// public static final String ADMIN_EMAIL_FOR_NEW_REGISTER_USER =
	// "usertechhive@gmail.com";

	/* password criteria.. */
	public static final Integer RESET_PASSWORD_TIME_LIMIT = 3;

	/* use for send response format */

	public static final String SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST = "SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST";

	/* White Stone ROUND */
	public static final String WHITE_SHAPE_ROUND = "ROUND";
	public static final String DEFAULT_USER_ID = null;
	public static final String DEFAULT_USER_NAME = "Default";

	// stone image video url

	public static final String FIRST_VIDEO_LINK_SAVE_EXTENTION = "/video.mp4";

	public static final String FIRST_VIDEO_LINK_FOR_SAVE = "http://pckstgsj.blob.core.windows.net/hdfile/Movie/Viewer4/imaged/";

	public static final String SECOND_VIDEO_LINK_FOR_SAVE = "http://pckstgsj.blob.core.windows.net/hdfile/Mp4/";

	public static final String SECOND_IMAGE_EXTENTION = ".jpg";
	public static final String FIRST_IMAGE_EXTENTION = "/still.jpg";

	public static final String FIRST_VIDEO_EXTENTION = "/movie.mp4";

	public static final String FIRST_IMAGE_LINK_FROM_PEACOCK = "http://pckstgsj.blob.core.windows.net/hdfile/Movie/Viewer4/imaged/"
			.trim();
	public static final String SECOND_IMAGE_LINK_FROM_PEACOCK = "http://pckstgsj.blob.core.windows.net/hdfile/DimImg/"
			.trim();
	// public static final String VIDEO_LINK_FROM_PEACOCK =
	// "http://www.shivamjewels.in/DetailMovie.aspx?StoneId=".trim();
	public static final String VIDEO_LINK_FROM_360_VIEWER = "https://pckcdnsj.azureedge.net/hdfile/Movie/Viewer4/Vision360.html?d="
			.trim();
	public static final String INCLUSION_PLOTTING = "http://pckstgsj.blob.core.windows.net/hdfile/Movie/Viewer4/imaged/";
	public static final String KEY_FOR_CONTETNT = "content";
	public static final String KET_FOR_NOT_FOUND_SHAPE = "listOfNotFountStone";
	public static final String IS_MORE_THEN_300 = "isMorethenLimits";

	public static final String ADMIN_USER_NAME_IN_DB = "Admin";

	public static final String SHIVAM_LOGO_LINK = "http://www.techhive.co.in/demo/shivam/logo.png";

	public static final String updateStatus = "updateStone";

	public static final String STATIC_PASSWORD_FOR_COMPAING_USER = "sj-Test@123";

	// public static final String
	// verifyEmailUrlForAndroid="http://shivam.auroradiam.com/app/#/home/verifyEmail";

	/* Route for meial verify use in app */

	// public static final String verifyEmailUrlForAndroid =
	// "http://shivam-test.auroradiam.com/app/#/home/verifyEmail";
	//
	// public static final String forgotPasswordUrlForAndroid =
	// "http://shivam-test.auroradiam.com/app/#/home/reset-password/";

	// onlline

	public static final String verifyEmailUrlForAndroid = "http://sj.world/app/#/home/verifyEmail";

	public static final String forgotPasswordUrlForAndroid = "http://sj.world/app/#/home/reset-password/";

	
	// offline
	
	// public static final String verifyEmailUrlForAndroid =
	// "http://sj.world/app/#/home/verifyEmail";
	//
	// public static final String forgotPasswordUrlForAndroid =
	// "http://sj.world/app/#/home/reset-password/";

}