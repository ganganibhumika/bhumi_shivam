package com.techhive.shivamweb.service;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface UserTrackingService {
	/***
	 * @author neel
	 * @param user
	 *            : user who modified
	 * @param ipAddress
	 *            : ipaddress of user
	 * @param description
	 *            : description of task
	 */
	void saveTracking(User user, String ipAddress, String description);

	/***
	 * @author neel
	 * @return get all tracking details 
	 */
	ResponseWrapperDTO getAllUserTracking(Integer pageNumber, Integer noOfRecords, String sortColumn, String sortOrder,
			String searchText, String userId, String path);

}
