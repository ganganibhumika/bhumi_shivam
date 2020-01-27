package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface CartService {
	/***
	 * @author neel save data after check already exist in own ,confirm ,wishlist
	 *         and delete from wishlist if present
	 */
	ResponseWrapperDTO saveCart(MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel get all user wise and calculate discount total , final for all
	 */
	ResponseWrapperDTO getAllCart(String sortColumn, String sortOrder);

	/***
	 * @author neel delete
	 * @param ip
	 */
	ResponseWrapperDTO deleteCart(String cartId, String path, String ip);

	ResponseWrapperDTO getAllCartByAdmin(String path);

	/***
	 * @author bhumi 
	 * @param cartStatus 
	 * @param update cart list status by admin manually only for show usre
	 */
	ResponseWrapperDTO updateCartStatus(MyRequestBody body, String cartStatus, HttpServletRequest request);

}
