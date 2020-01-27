package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ViewRequestRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ViewRequestService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/viewRequest")
public class ViewRequestController {

	@Autowired
	ViewRequestRepository viewRequestRepository;

	@Autowired
	ViewRequestService viewRequestService;

	@PostMapping("saveViewRequest")
	public ResponseEntity<?> saveViewRequest(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getUserId(), body.getListOfJsonObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = viewRequestService.saveViewRequest(body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}

	@GetMapping("getAllViewRequest")
	private ResponseEntity<?> getAllViewRequest(@RequestParam Integer pageNumber, @RequestParam Integer noOfRecords,
			@RequestParam(required = false) String sortColumn, @RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) String userId, @RequestParam(required = true) Boolean inProgress,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(inProgress, pageNumber, noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			return new ResponseEntity<>(viewRequestService.getAllViewRequest(userId, inProgress, pageNumber, noOfRecords,sortColumn,sortOrder, path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	// @PutMapping("deleteViewRequest/{ViewRequestId}")
	public ResponseEntity<?> deleteViewRequest(@PathVariable("ViewRequestId") String ViewRequestId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(ViewRequestId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = viewRequestService.deleteViewRequest(ViewRequestId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
