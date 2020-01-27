package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface MilkMasterService {
	/***
	 * @author neel
	 * save data after check already exist
	 */
	ResponseWrapperDTO saveMilkMaster(MyRequestBody body, String path);
	/***
	 * @author neel
	 * update data after check already exist
	 */
	ResponseWrapperDTO updateMilkMaster(String milkMasterId, MyRequestBody body, String path);
	/***
	 * @author neel
	 * delete
	 */
	ResponseWrapperDTO deleteMilkMaster(String milkMasterId, String path);
	/***
	 * @author neel
	 * change order as provide by angular and save
	 */
	ResponseWrapperDTO changeAllMilkMasterOrder(MyRequestBody body, String path);

}
