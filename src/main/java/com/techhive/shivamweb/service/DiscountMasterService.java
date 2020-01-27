package com.techhive.shivamweb.service;

import java.util.Date;
import java.util.List;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface DiscountMasterService {

	public ResponseWrapperDTO saveDiscountMaster(MyRequestBody body, String path);

	public ResponseWrapperDTO updateDiscountById(MyRequestBody body, String path);

	public ResponseWrapperDTO deleteDiscountById(String discountMasterId, String path);

	public ResponseWrapperDTO getAllDiscountDetail(Integer pageNumber, Integer noOfRecords, String sortOrder,
			String sortColumn, String path);

	/*
	 * @bhumi user for get user wise discount this method use for calculate discount
	 */
	public List<Double> getDiscountByUserId(String userId, Date giDate, Double carat, String shape,
			boolean isDefault);

}
