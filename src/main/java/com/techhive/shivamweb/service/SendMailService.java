package com.techhive.shivamweb.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SendMailService {
	/***
	 * @author send mail using send grid
	 */
	void sendMailForRegistrationVerifactrion(User user, String urlForEmailVerification);

	/***
	 * @author send mail to admin when user confirm his stone.
	 * @param user2
	 *            new
	 */
	void sendMailToAdmin(User toUser, User From);

	/***
	 * @author send mail using send grid new
	 */
	void sendMailForApproved(Optional<User> userFromDb);

	// new
	ResponseWrapperDTO sendMailForForgotPassword(String email, String forgotPasswordUrl, HttpServletRequest request);

	/***
	 * @author send mail using send grid
	 * @param confirmSuccess
	 * @param emailUser
	 */
	void sendMailForConfirmOrder(Set<User> users, User From, String confirmStoneId, List<ConfirmOrder> confirmSuccess);

	/***
	 * @author send mail using send grid with attachment of excel
	 * @param user 
	 */
	ResponseWrapperDTO emailSelectedStone( String email, String fileName, MultipartFile file,
			String path)
			throws IllegalStateException, IOException;

	void sendMailForViewRequest(User user, Date startDate, Date endDate, String stoneId);
	
	void sendMailForDemand(User toUser, User demandFrom, String shape);

	void sendMailForFeedback(User toUser, User user);

	void sendMailForOfferDiscount(User toUser, User From, String offerStone);

	void sendMailForFullfilDemand(User user, String shape);

	void sendMailForConfirmOrderToUser(User user, User emailUser, String stoneList,
			List<ConfirmOrder> confirmSuccess);

	void sendMailToUserOfSuccess(User user);

	void sendMailForConfirmOffer(Set<User> users, User From, String confirmStoneId, List<ConfirmOrder> confirmSuccess);

	void sendMailForConfirmOfferToUser(User user, User emailUser, String stoneList,
			List<ConfirmOrder> confirmSuccess);

	void sendMailForViewRequestToAdmin(Set<User> admins, User ofUser, Date from, Date time, String stoneList);

}
