package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.OtherService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/other")
public class OtherController {

	@Autowired
	OtherService otherService;

	@GetMapping(value = "getAllMasterDetails")
	public ResponseEntity<?> getAllMasterDetails() {
		ResponseWrapperDTO response=otherService.getAllMasterDetails();
		return new ResponseEntity<>(response,response.getHttpStatus());
	}

	@GetMapping(value = "getAllStonesForVideoImageCsv")
	public ResponseEntity<?> getAllStonesForVideoImageCsv(@RequestParam String tabName,HttpServletRequest request) {
		if(ShivamWebMethodUtils.isObjectNullOrEmpty(tabName))
			return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
		ResponseWrapperDTO response=otherService.getAllStonesForVideoImageCsv(tabName,request.getServletPath());
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	@GetMapping(value = "Test")
	public ResponseEntity<?> Test(HttpServletRequest request) {
		ResponseWrapperDTO response=otherService.Test(request.getServletPath());
		return new ResponseEntity<>(response,response.getHttpStatus());
	}
	@GetMapping("getTodaysUpcomingFairPopup")
	public ResponseEntity<?> getTodaysUpcomingFairPopup(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Todays Upcoming Fair & popup",
					otherService.getTodaysUpcomingFairPopup(), path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	@GetMapping("getCounterForDashboard")
	public ResponseEntity<?> getCounterForDashboard(@RequestParam String userId,HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get counter for dashboard.",
					otherService.getCounterForDashboard(userId), path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
