package com.techhive.shivamweb.service;

import java.util.Date;
import java.util.List;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ThirdPartyDiscountMasterService {
	
	public ResponseWrapperDTO saveThirdPartyDiscount(MyRequestBody body,String path);
	
	public ResponseWrapperDTO updateThirdPartyDiscount(MyRequestBody body,String path);
	
	public ResponseWrapperDTO deleteThirdPartyDiscount(String discountMasterId,String path);
	
	public ResponseWrapperDTO getAllThirdPartyDiscountDetail(Integer noOfRecords,Integer pageNumber,String sortOrder,String sortColumn,String path);

	public List<Double> getThirdPartyDiscountByUserId(String userId, Date giDate, Double carat, String shape,
			boolean b);

}
