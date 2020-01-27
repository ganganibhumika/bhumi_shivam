package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ResultScreenLabelsService {

	/**
	 * @author Heena
	 * @return user wise lable name and property of result screen table
	 */

	public ResponseWrapperDTO getAllResultScreenLablesByUserId(String userId);

	/**
	 * @author Heena
	 * @return success on save change of index in result screen lable
	 */

	public ResponseWrapperDTO saveOrderOfResultScreenLable(MyRequestBody body);

	public ResponseWrapperDTO saveResultScreenLableValueByUserId(String userId);
	
	public ResponseWrapperDTO updateColumnnChooserLabels(MyRequestBody body,String path);
	
	public ResponseWrapperDTO getAllLabelsForColumnChooser(String path);
	
	public ResponseWrapperDTO getAllResultScreenLables(String path);
	

}
