package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ShapeMasterService {
	/***
	 * @author neel
	 * save data after check already exist
	 */
	ResponseWrapperDTO saveShape(MyRequestBody jsonOfObject, String path);
	/***
	 * @author neel
	 * update data after check already exist
	 */
	ResponseWrapperDTO updateShape(String shapeId, MyRequestBody body, String path);
	/***
	 * @author neel
	 * delete
	 */
	ResponseWrapperDTO deleteShape(String shapeId, String path);
	/***
	 * @author neel
	 * change order as provide by angular and save
	 */
	ResponseWrapperDTO changeAllShapeOrder(MyRequestBody body, String path);

}
