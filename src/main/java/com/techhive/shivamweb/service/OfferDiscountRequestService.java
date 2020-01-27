package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.OfferDiscountRequest;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface OfferDiscountRequestService {
	/***
	 * @author neel save data after check already exist in own ,confirm
	 */
	ResponseWrapperDTO saveOfferDiscountRequest(MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel get all user wise and calculate discount total , final for all
	 */
	ResponseWrapperDTO getAllOfferDiscount(String sortColumn, String sortOrder);

	/***
	 * @author neel delete
	 */
	ResponseWrapperDTO deleteOfferDiscount(String offerDiscountId, String path);

	/***
	 * @author neel update
	 * @param ip 
	 */
	ResponseWrapperDTO updateOfferStatus(String offerDiscountId, MyRequestBody body, String path, String ip);

	/***
	 * @author discount calculate
	 */
	public void getDiscount(OfferDiscountRequest offerDiscountRequest);

}
