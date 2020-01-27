package com.techhive.shivamweb.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.DTO.NotificationDto;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.model.Notification_;
import com.techhive.shivamweb.repository.NotificationRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.PushNotificationService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepository;

	@Autowired
	private PushNotificationService pushnotificationService;

	@Autowired
	private UserRepository userRepository;

	/* Test */
	@Override
	public ResponseWrapperDTO saveNotification(MyRequestBody body, String path) {

		Notification notification = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				Notification.class);

		notification.getSetOfUserObject().forEach(user -> {
			Notification notification1 = new Notification();
			notification1.setCategory(notification.getCategory());
			notification1.setDescription(notification.getDescription());
			notification1.setIsAdmin(notification.getIsAdmin());
			notification1.setIsShow(false);
			notification1.setStoneOrUserId(notification.getStoneOrUserId());
			notification1.setIsRead(false);
			notification1.setUser(user);
			notificationRepository.saveAndFlush(notification1);

		});

		/* test send notification */

		pushNotification(notification);

		/* End send notification */
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Notification " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
	/* End test */

	public void sendNotification(Notification notification) {
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(notification.getSetOfUserObject())) {

			notification.getSetOfUserObject().forEach(user -> {
				Notification notification1 = new Notification();
				notification1.setCategory(notification.getCategory());
				notification1.setDescription(notification.getDescription());
				notification1.setIsAdmin(notification.getIsAdmin());
				notification1.setIsShow(false);
				notification1.setStoneOrUserId(notification.getStoneOrUserId());
				notification1.setIsRead(false);
				notification1.setUser(user);
				notificationRepository.saveAndFlush(notification1);

			});
			// push fcm notification to firebse..
			
			pushNotification(notification);

		}

	}

	/* ..........Send notification..... */

	public void pushNotification(Notification notification) {
		try {

			Set<String> listOfTocken = new HashSet<>();

			JSONObject body = new JSONObject();
			// body.put("to", fcmToken); // Single device.

			Set<String> setOfUserId = notification.getSetOfUserObject().stream().map(User::getId)
					.collect(Collectors.toSet());

			listOfTocken = notificationRepository.getListOfTocken(setOfUserId);

			if (!ShivamWebMethodUtils.isSetNullOrEmpty(listOfTocken)) {
				body.put("registration_ids", listOfTocken); // Multiple device
				body.put("priority", "high");

				JSONObject notificationObj = new JSONObject();
				notificationObj.put("title", "Shivam Jewels..!");
				notificationObj.put("body", notification.getDescription());

				JSONObject data = new JSONObject();
				// data.put("Key-1", "JSA Data 1...............");
				// data.put("Key-2", "JSA Data 2...............");

				data.put(Notification_.isAdmin.getName(), notification.getIsAdmin());
				data.put(Notification_.isRead.getName(), notification.getIsRead());
				data.put(Notification_.isShow.getName(), notification.getIsRead());

				body.put("notification", notificationObj);
				body.put("data", data);

				HttpEntity<String> request = new HttpEntity<>(body.toString());

				System.out.println("In notification::" + listOfTocken);

				pushnotificationService.send(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setCriteriaForNotification(String pktId, String stoneId, String status) {

		Set<User> setOfUserList = notificationRepository.getListOfwishListUserIdBypkt(pktId);
		Notification notification = new Notification();
		if (status.equals(ShivamWebVariableUtils.updateStatus)) {
			notification.setDescription(EnumForNotificationDescription.WISHLIST.toString());
		} else {
			notification.setDescription(EnumForNotificationDescription.WISHLISTSTONEAVAILABLE.toString());
		}
		notification.setSetOfUserObject(setOfUserList);
		notification.setIsAdmin(false);
		notification.setStoneOrUserId(stoneId);
		notification.setCategory(EnumForNotificationType.WISHLIST.toString());
		sendNotification(notification);

		// Send cart notification

		Set<User> setOfUserListCart = notificationRepository.getListOfCartUserIdBypkt(pktId);
		Notification notificationCart = new Notification();
		if (status.equals(ShivamWebVariableUtils.updateStatus)) {
			notificationCart.setDescription(EnumForNotificationDescription.CART.toString());
		} else {
			notificationCart.setDescription(EnumForNotificationDescription.CARTSTONEAVAILABLE.toString());
		}
		notificationCart.setSetOfUserObject(setOfUserListCart);
		notificationCart.setIsAdmin(false);
		notificationCart.setStoneOrUserId(stoneId);
		notificationCart.setCategory(EnumForNotificationType.CART.toString());
		sendNotification(notificationCart);

		// Send place offer notification

		Set<User> setOfUserListPlaceOffer = notificationRepository.getListOfPlaceOfferUserIdBypkt(pktId);
		Notification notificationPlaceOffer = new Notification();
		if (status.equals(ShivamWebVariableUtils.updateStatus)) {
			notificationPlaceOffer.setDescription(EnumForNotificationDescription.PLACE_OFFER.toString());
		} else {
			notificationPlaceOffer.setDescription(EnumForNotificationDescription.PLACEOFFERSTONEAVAILABLE.toString());
		}
		notificationPlaceOffer.setSetOfUserObject(setOfUserListPlaceOffer);
		notificationPlaceOffer.setIsAdmin(false);
		notificationPlaceOffer.setStoneOrUserId(stoneId);
		notificationPlaceOffer.setCategory(EnumForNotificationType.PLACE_OFFER.toString());

		sendNotification(notificationPlaceOffer);

	}

	@Override
	public ResponseWrapperDTO getAllNotificationByUserId(String userId, boolean isAdmin, Integer pageNumber,
			Integer noOfRecords, String path) {
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		Page<NotificationDto> listOfNotification = notificationRepository.getAllNotificationByUserId(userId, isAdmin,
				request);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all notification ", listOfNotification,
				HttpStatus.OK, path);

	}

	@Override
	public ResponseWrapperDTO updateShowStatus(String userId, String path, boolean isAdmin) {
		List<Notification> listOfNotification = notificationRepository.getUnShowNotification(userId, isAdmin);
		for (Notification notification : listOfNotification) {
			notification.setIsShow(true);
			notificationRepository.saveAndFlush(notification);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Notification seen successfully.", null, HttpStatus.OK,
				path);

	}

	@Override
	public ResponseWrapperDTO updateStatusOfNotification(String notificationId, String path) {
		Optional<Notification> noti = notificationRepository.findById(notificationId);
		if (!noti.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Notification not found.", null,
					HttpStatus.BAD_REQUEST, path);
		noti.get().setIsRead(true);
		notificationRepository.saveAndFlush(noti.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Notification readed successfully.", null,
				HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllUnShowCntByUserId(String userId, boolean isAdmin, String path) {
		Long unShowNotificationCounter = notificationRepository.getAllUnShowCntByUserId(userId, isAdmin);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all unshow notification counter.",
				unShowNotificationCounter, HttpStatus.OK, path);
	}

}
