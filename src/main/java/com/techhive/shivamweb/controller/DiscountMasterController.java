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
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/discountMaster")
public class DiscountMasterController {

	@Autowired
	DiscountMasterService discountMasterService;

	/**
	 * save discount of user
	 * 
	 * @return success message on save of discount
	 */
	@CrossOrigin
	@PostMapping("saveDiscountMaster")
	public ResponseEntity<?> saveDiscountMaster(@RequestBody MyRequestBody body,
			HttpServletRequest httpServletRequest) {
		String path = httpServletRequest.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = discountMasterService.saveDiscountMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateDiscountById/{discountMasterId}")
	public ResponseEntity<?> updateDiscountById(@RequestBody MyRequestBody body,
			@PathVariable(value = "discountMasterId") String discountMasterId, HttpServletRequest httpServletRequest) {
		String path = httpServletRequest.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = discountMasterService.updateDiscountById(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteDiscountById/{discountMasterId}")
	public ResponseEntity<?> deleteDiscountById(@PathVariable(value = "discountMasterId") String discountMasterId,
			HttpServletRequest httpServletRequest) {
		String path = httpServletRequest.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(discountMasterId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = discountMasterService.deleteDiscountById(discountMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@CrossOrigin
	@GetMapping("getAllDiscountDetail")
	public ResponseEntity<?> getAllDiscountDetail(@RequestParam Integer noOfRecords, @RequestParam Integer pageNumber,
			String sortOrder, String sortColumn, HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = discountMasterService.getAllDiscountDetail(pageNumber, noOfRecords, sortOrder,
					sortColumn, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
