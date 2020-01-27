package com.techhive.shivamweb.service;


import com.techhive.shivamweb.master.model.UpcomingFairMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface UpcomingFairMasterService {
	/**
	 * @author neel save date check if entered Popup is active than check enter date
	 *         of range has already active popup
	 */
	ResponseWrapperDTO saveUpcomingFairManagerMaster(MyRequestBody body, String path);

	/**
	 * @author neel update date check if entered Popup is active than check enter
	 *         date of range has already active popup
	 */
	ResponseWrapperDTO updateUpcomingFairManagerMaster(String fairId, MyRequestBody body, String path);

	/**
	 * @author neel delete
	 */
	ResponseWrapperDTO deleteUpcomingFairManagerMaster(String fairId, String path);

	/**
	 * @author neel getall with pagination by default shotrd data by created date
	 */
	ResponseWrapperDTO getAllUpcomingFairManagerMaster(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path);
	/**
	 * @author neel get todays Popup
	 */
	UpcomingFairMaster getTodaysUpcomingFair();

}
