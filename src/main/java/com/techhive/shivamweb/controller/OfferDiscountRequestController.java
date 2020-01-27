package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.techhive.shivamweb.service.OfferDiscountRequestService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/offerDiscountRequest")
public class OfferDiscountRequestController {
	@Autowired
	OfferDiscountRequestService offerDiscountRequestService;

	@PostMapping("saveOfferDiscountRequest")
	public ResponseEntity<?> saveOfferDiscountRequest(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject(), body.getUserId()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = offerDiscountRequestService.saveOfferDiscountRequest(body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getAllOfferDiscount")
	private ResponseEntity<?> getAllOfferDiscount( @RequestParam(required = false) String userId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity<>(offerDiscountRequestService.getAllOfferDiscount( userId, path), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteOfferDiscount/{offerDiscountId}")
	public ResponseEntity<?> deleteOfferDiscount(@PathVariable("offerDiscountId") String offerDiscountId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(offerDiscountId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = offerDiscountRequestService.deleteOfferDiscount(offerDiscountId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateOfferStatus/{offerDiscountId}")
	public ResponseEntity<?> updateOfferStatus(@PathVariable("offerDiscountId") String offerDiscountId,
			@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		String ip = request.getRemoteAddr();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(offerDiscountId, body.getJsonOfObject(), body.getUserId()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = offerDiscountRequestService.updateOfferStatus(offerDiscountId, body, path,ip);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
