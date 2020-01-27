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
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.FluorescenceMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FluorescenceMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
@RestController
@RequestMapping("/api/fluorescenceMaster")
public class FluorescenceMasterController {

	@Autowired
	FluorescenceMasterRepository fluorescenceMasterRepository;

	@Autowired
	FluorescenceMasterService fluorescenceMasterService;
	
	@PostMapping("saveFluorescenceMaster")
	public ResponseEntity<?> saveFluorescenceMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fluorescenceMasterService.saveFluorescenceMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@CrossOrigin
	@PutMapping("updateFluorescenceMaster/{FluorescenceMasterId}")
	public ResponseEntity<?> updateFluorescenceMaster(@PathVariable("FluorescenceMasterId") String FluorescenceMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(FluorescenceMasterId,body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fluorescenceMasterService.updateFluorescenceMaster(FluorescenceMasterId,body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllFluorescenceMaster")
	public ResponseEntity<?> getAllFluorescenceMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All FluorescenceMaster", fluorescenceMasterRepository.findAllByOrderByFluorescenceMasterOrderAsc(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	@CrossOrigin
	@PutMapping("deleteFluorescenceMaster/{FluorescenceMasterId}")
	public ResponseEntity<?> deleteFluorescenceMaster(@PathVariable("FluorescenceMasterId") String FluorescenceMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(FluorescenceMasterId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fluorescenceMasterService.deleteFluorescenceMaster(FluorescenceMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	

	@PostMapping("changeAllFluorescenceMasterOrder")
	public ResponseEntity<?> changeAllFluorescenceMasterOrder(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fluorescenceMasterService.changeAllFluorescenceMasterOrder(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
