package com.techhive.shivamweb.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.UserTracking;
import com.techhive.shivamweb.repository.UserTrackingRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@Service
public class UserTrackingServiceImpl implements UserTrackingService {

	@Autowired
	UserTrackingRepository userTrackingRepository;

	@Override
	public void saveTracking(User user, String ipAddress, String description) {
		UserTracking userTracking = new UserTracking();
		userTracking.setUser(user);
		userTracking.setIpAddress(ipAddress);
		userTracking.setDescription(description);
		userTrackingRepository.saveAndFlush(userTracking);
	}

	@Override
	public ResponseWrapperDTO getAllUserTracking(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String userId, String path) {
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "dateTime");
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(searchText)) {
				return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "User Tracking List",
						userTrackingRepository.findAllWithSearch(searchText, request), HttpStatus.OK);
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "User Tracking List",
					userTrackingRepository.findAll(request), HttpStatus.OK);
		}
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(searchText)) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "User Tracking List",
					userTrackingRepository.findAllWithSearchByUser(searchText, userId, request), HttpStatus.OK);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "User Tracking List",
				userTrackingRepository.findAllByUser(userId, request), HttpStatus.OK);
	}

}
