package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("api/userTracking/")
public class UserTrackingController {

	@Autowired
	UserTrackingService userTrackingService;

	@GetMapping("getAllUserTracking")
	private ResponseEntity<?> getAllUserTracking(@RequestParam Integer pageNumber, @RequestParam Integer noOfRecords,
			@RequestParam(required = false) String sortColumn, @RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) String searchText, @RequestParam(required = false) String userId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(searchText)) {
				searchText = searchText.trim();
			}
			return new ResponseEntity<>(userTrackingService.getAllUserTracking(pageNumber, noOfRecords, sortColumn,
					sortOrder, searchText, userId, path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
