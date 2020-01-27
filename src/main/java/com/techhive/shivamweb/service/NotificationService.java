package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface NotificationService {

	ResponseWrapperDTO saveNotification(MyRequestBody body, String path);

	void sendNotification(Notification notification);

	void setCriteriaForNotification(String pktId, String stoneId, String status);
	
	/***
	 * @author neel 
	 * @param isAdmin 
	 */
	ResponseWrapperDTO getAllNotificationByUserId(String userId, boolean isAdmin, Integer pageNumber, Integer noOfRecords, String path);

	ResponseWrapperDTO updateShowStatus(String userId, String path, boolean isAdmin);

	ResponseWrapperDTO updateStatusOfNotification(String notificationId, String path);

	ResponseWrapperDTO getAllUnShowCntByUserId(String userId, boolean isAdmin, String path);


}
