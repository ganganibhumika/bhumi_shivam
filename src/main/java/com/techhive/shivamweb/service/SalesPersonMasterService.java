package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SalesPersonMasterService {
	/***
	 * @author neel save date after checking already exist and allow ony one primary
	 *         sales person
	 */
	ResponseWrapperDTO saveSalesPerson(MyRequestBody body, String path);

	/***
	 * @author neel update date after checking already exist
	 */
	ResponseWrapperDTO updateSalesPerson(String id, MyRequestBody body, String path);

	/***
	 * @author neel delete date except those that are primary and assign primary
	 *         sales person to all user that sales person are deleted
	 */
	ResponseWrapperDTO deleteSalesPerson(String id, String path);

}
