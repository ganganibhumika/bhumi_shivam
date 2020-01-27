package com.techhive.shivamweb.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.SuggestionFeedback;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.SuggestionFeedbackRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.SuggestionFeedbackService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SuggestionFeedbackServiceImpl implements SuggestionFeedbackService {
	@Autowired
	SuggestionFeedbackRepository suggestionFeedbackRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	NotificationService notificationService;
	
	@Autowired
	SendMailService sendMailService;

	@Override
	public ResponseWrapperDTO saveSuggestionFeedback(MyRequestBody body, HttpServletRequest request) {
		SuggestionFeedback feedback = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SuggestionFeedback.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(feedback.getUserId(), feedback.getEmail(),
				feedback.getContactPerson()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		Optional<User> userFromDb = userRepository.findById(feedback.getUserId());
		if (!userFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		feedback.setUsername(userFromDb.get().getUsername());
		suggestionFeedbackRepository.saveAndFlush(feedback);
		userTrackingService.saveTracking(userFromDb.get(), request.getRemoteAddr(),
				EnumForUserTracking.ADDEDFEEDBACK.toString());
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				// notification
				Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
				Notification notification = new Notification();
				notification.setDescription(EnumForNotificationDescription.SUGGESTIONFEEDBACK.toString());
				notification.setSetOfUserObject(users);
				notification.setCategory(EnumForNotificationType.SUGGESTIONFEEDBACK.toString());
				notification.setIsAdmin(true);
				notification.setStoneOrUserId(userFromDb.get().getUsername());
				notificationService.sendNotification(notification);
				for (User user2 : users) {
					sendMailService.sendMailForFeedback(user2, userFromDb.get());
				}
			}
		});
		service.shutdown();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Feedback " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK,
				request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO getAllSuggestionFeedback(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path) {
		PageRequest request = request = new PageRequest(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "All Suggestion and Feeedback",
				suggestionFeedbackRepository.findAll(request), HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteSuggestionFeedback(String id, String loginUserId, HttpServletRequest request) {
		Optional<SuggestionFeedback> feedback = suggestionFeedbackRepository.findById(id);
		if (!feedback.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Feedback not found.", null,
					HttpStatus.BAD_REQUEST, request.getServletPath());

		Optional<User> loginUserFromDb = userRepository.findById(loginUserId);
		if (!loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());

		userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
				EnumForUserTracking.DELETEDFEEDBACK.toString());
		suggestionFeedbackRepository.deleteById(id);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Client " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, request.getServletPath());
	}

}
