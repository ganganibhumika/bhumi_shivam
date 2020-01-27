package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface UserService {

	public ResponseWrapperDTO findByEmailOrUsernameAndPassword(String userNameOrEmail, String password,String fcmToken, Boolean isTracking,HttpServletRequest request);

	/***
	 * @author neel register new user and manage tracking and send mail
	 * @param user 
	 * @param userMigration 
	 */
	ResponseEntity<?> registerUser(User user, MyRequestBody body, HttpServletRequest request, String urlForEmailVerification, boolean userMigration);

	/***
	 * @author neel email varify of new user and manage tracking and send mail
	 */
	ResponseWrapperDTO verify(String userId, HttpServletRequest request);

	/***
	 * @author neel get all client for activaton with pagination order by created
	 *         date desc
	 */
	public ResponseWrapperDTO getAllUserForClientActivation(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, Boolean isApprove, String path);

	/***
	 * @author neel approve new user and manage tracking and send mail
	 */
	public ResponseWrapperDTO changeApproveStatus(String id, MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel assignSalesPerson to user and manage tracking
	 */
	public ResponseWrapperDTO assignSalesPerson(String id, MyRequestBody body, HttpServletRequest request);

//	/***
//	 * 
//	 * @author neel delete client set is deleted to true no physical detele
//	 * 
//	 */
//
//	public ResponseWrapperDTO deleteClient(String id);

	/***
	 * @author bhumi check reset password request status with time limit
	 */
	public ResponseWrapperDTO checkResetPassRequest(String userId);

	/***
	 * @author bhumi check reset password request status with time limit
	 */
	public ResponseWrapperDTO resetPassword(String newPassword, String userId);

	public ResponseWrapperDTO deleteClient(String id, String loginUserId, HttpServletRequest request);

	/***
	 * @author neel update date for client profile
	 */
	public ResponseWrapperDTO changeClientProfile(String id, MyRequestBody body, HttpServletRequest request);

	/***
	 * @author neel update date for client password
	 */
	public ResponseWrapperDTO changePassword(String id, MyRequestBody body, HttpServletRequest request);

	public ResponseWrapperDTO setFirebaseToken(String userId, String fcmToken, HttpServletRequest request);

	public ResponseWrapperDTO assignParty(String id, MyRequestBody body, HttpServletRequest request);

	public ResponseWrapperDTO setUserAsAdmin(String id, MyRequestBody body, HttpServletRequest request);

	/***
	 * @author bhumi Registered use for show
	 */
	
	public ResponseEntity<?> registerUserForShow(User user, MyRequestBody body, HttpServletRequest request);

//	public ResponseWrapperDTO getNewAccessTocken(String userId);

}
