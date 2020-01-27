package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface BrownShadeMasterService {
	/***
	 * @author neel
	 * save data after check already exist
	 */
	ResponseWrapperDTO saveBrownShadeMaster(MyRequestBody body, String path);
	/***
	 * @author neel
	 * update data after check already exist
	 */
	ResponseWrapperDTO updateBrownShadeMaster(String brownShadeMasterId, MyRequestBody body, String path);
	/***
	 * @author neel
	 * delete
	 */
	ResponseWrapperDTO deleteBrownShadeMaster(String brownShadeMasterId, String path);
	/***
	 * @author neel
	 * change order as provide by angular and save
	 */
	ResponseWrapperDTO changeAllBrownShadeMasterOrder(MyRequestBody body, String path);

}
