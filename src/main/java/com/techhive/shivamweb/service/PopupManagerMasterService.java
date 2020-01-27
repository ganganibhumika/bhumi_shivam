package com.techhive.shivamweb.service;

import com.techhive.shivamweb.master.model.PopupManagerMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface PopupManagerMasterService {
	/**
	 * @author neel save date check if entered Popup is active than check enter date
	 *         of range has already active popup
	 */
	ResponseWrapperDTO savePopupManagerMaster(MyRequestBody body, String path);

	/**
	 * @author neel update date check if entered Popup is active than check enter
	 *         date of range has already active popup
	 */
	ResponseWrapperDTO updatePopupManagerMaster(String popupManagerMasterId, MyRequestBody body, String path);

	/**
	 * @author neel delete
	 */
	ResponseWrapperDTO deletePopupManagerMaster(String popupManagerMasterId, String path);

	/**
	 * @author neel getall with pagination by default shotrd data by created date
	 */
	ResponseWrapperDTO getAllPopupManagerMaster(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path);
	/**
	 * @author neel get todays Popup
	 */
	PopupManagerMaster getTodaysPopup();

}
