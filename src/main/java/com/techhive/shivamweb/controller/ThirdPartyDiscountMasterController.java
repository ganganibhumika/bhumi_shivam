package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.techhive.shivamweb.service.ThirdPartyDiscountMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping(value = "/api/thirdPartyDiscountMaster")
public class ThirdPartyDiscountMasterController {

	@Autowired
	ThirdPartyDiscountMasterService thirdPartyDiscountMasterService;

	@CrossOrigin
	@PostMapping("saveThirdPartyDiscount")
	public ResponseEntity<?> saveThirdPartyDiscount(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = thirdPartyDiscountMasterService.saveThirdPartyDiscount(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}
	
	@CrossOrigin
	@PutMapping("updateThirdPartyDiscount/{discountMasterId}")
	public ResponseEntity<?> updateThirdPartyDiscount(@PathVariable (value="discountMasterId") String discountMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = thirdPartyDiscountMasterService.updateThirdPartyDiscount(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}
	
	@CrossOrigin
	@PutMapping("deleteThirdPartyDiscount/{discountMasterId}")
	public ResponseEntity<?> deleteThirdPartyDiscount(@PathVariable (value="discountMasterId") String discountMasterId , HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(discountMasterId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = thirdPartyDiscountMasterService.deleteThirdPartyDiscount(discountMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}
	
	@CrossOrigin
	@GetMapping("getAllThirdPartyDiscountDetail")
	public ResponseEntity<?> getAllThirdPartyDiscountDetail(@RequestParam Integer noOfRecords, @RequestParam Integer pageNumber,
			String sortOrder, String sortColumn,HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = thirdPartyDiscountMasterService.getAllThirdPartyDiscountDetail(noOfRecords,pageNumber, sortOrder,sortColumn, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}

}
