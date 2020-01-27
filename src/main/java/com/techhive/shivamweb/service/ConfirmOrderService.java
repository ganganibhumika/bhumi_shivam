package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ConfirmOrderService {
	/***
	 * @author neel save data after check already exist in own ,wishlist ,cart and
	 *         delete from cart,wishlist,offerdiscount if present pass userId for
	 *         whome to confirm in body.userId from admin side pass admin id in
	 *         body.otherdate
	 */
	ResponseWrapperDTO saveConformOrder(MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel get all user wise and calculate discount total , final for all
	 */
	ResponseWrapperDTO getAllConfirmOrder(String userId, String path);

	/***
	 * @author neel delete
	 */
	ResponseWrapperDTO deleteConfirmOrder(String confirmOrderId, String path);

	/***
	 * @author neel update
	 */
	ResponseWrapperDTO updateOrderStatus(String confirmOrderId, MyRequestBody body, String path);

	/***
	 * @author discount calculate
	 */
	void getDiscount(ConfirmOrder confirmOrder);

	/***
	 * @author neel 
	 * get recent confirmation stone top 10
	 */
	ResponseWrapperDTO getAllResentConfirm(String userId, String path);

}
