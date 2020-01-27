package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface WishlistService {
	/***
	 * @author neel save data after check already exist in own ,confirm ,cart and
	 *         delete from cart if present
	 */
	ResponseWrapperDTO saveWishlist(MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel get all user wise and calculate discount total , final for all
	 */
	ResponseWrapperDTO getAllWishlist(String sortColumn, String sortOrder);
	/***
	 * @author neel delete & tracking
	 * @param ip 
	 */
	ResponseWrapperDTO deleteWishlist(String wishlistId, String path, String ip);
	/***
	 * @author neel delete multiple & tracking
	 * @param ip 
	 */
	ResponseWrapperDTO deleteMultipleWishlist(MyRequestBody body, String path, String ip);

}
