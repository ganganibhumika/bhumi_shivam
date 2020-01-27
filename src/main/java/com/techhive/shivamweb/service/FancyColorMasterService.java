package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface FancyColorMasterService {
	/***
	 * @author neel
	 * save data after check already exist
	 */
	ResponseWrapperDTO saveFancyColorMaster(MyRequestBody body, String path);
	/***
	 * @author neel
	 * update data after check already exist
	 */
	ResponseWrapperDTO updateFancyColorMaster(String fancyColorMasterId, MyRequestBody body, String path);
	/***
	 * @author neel
	 * delete
	 */
	ResponseWrapperDTO deleteColorMaster(String fancyColorMasterId, String path);
	/***
	 * @author neel
	 * change order as provide by angular and save
	 */
	ResponseWrapperDTO changeAllColorMasterOrder(MyRequestBody body, String path);

}
