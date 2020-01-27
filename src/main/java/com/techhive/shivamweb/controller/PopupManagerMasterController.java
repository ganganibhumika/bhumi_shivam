package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.PopupManagerMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/popupManagerMaster")
public class PopupManagerMasterController {

	@Autowired
	PopupManagerMasterService popupManagerMasterService;

	@PostMapping("savePopupManagerMaster")
	public ResponseEntity<?> savePopupManagerMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = popupManagerMasterService.savePopupManagerMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updatePopupManagerMaster/{popupManagerMasterId}")
	public ResponseEntity<?> updatePopupManagerMaster(@PathVariable("popupManagerMasterId") String popupManagerMasterId,
			@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(popupManagerMasterId, body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = popupManagerMasterService.updatePopupManagerMaster(popupManagerMasterId, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deletePopupManagerMaster/{popupManagerMasterId}")
	public ResponseEntity<?> deletePopupManagerMaster(@PathVariable("popupManagerMasterId") String popupManagerMasterId,
			@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(popupManagerMasterId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = popupManagerMasterService.deletePopupManagerMaster(popupManagerMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getAllPopupManagerMaster")
	public ResponseEntity<?> getAllPopupManagerMaster(@RequestParam Integer pageNumber,
			@RequestParam Integer noOfRecords, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortOrder, @RequestParam(required = false) String searchText,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			return new ResponseEntity(popupManagerMasterService.getAllPopupManagerMaster(pageNumber, noOfRecords,
					sortColumn, sortOrder, searchText, path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getTodaysPopup")
	public ResponseEntity<?> getTodaysPopup(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "todays popup",
					popupManagerMasterService.getTodaysPopup(), path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
