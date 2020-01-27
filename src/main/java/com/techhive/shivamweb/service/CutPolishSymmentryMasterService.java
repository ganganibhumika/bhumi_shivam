package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface CutPolishSymmentryMasterService {
	/***
	 * @author neel
	 * save data after check already exist
	 */
	ResponseWrapperDTO saveCutPolishSymmentryMaster(MyRequestBody body, String path);
	/***
	 * @author neel
	 * update data after check already exist
	 */
	ResponseWrapperDTO updateCutPolishSymmentryMaster(String cutPolishSymmentryMasterId, MyRequestBody body,
			String path);
	/***
	 * @author neel
	 * delete
	 */
	ResponseWrapperDTO deleteCutPolishSymmentryMaster(String cutPolishSymmentryMasterId, String path);
	/***
	 * @author neel
	 * change order as provide by angular and save
	 */
	ResponseWrapperDTO changeAllCutPolishSymmentryMasterOrder(MyRequestBody body, String path);

}
