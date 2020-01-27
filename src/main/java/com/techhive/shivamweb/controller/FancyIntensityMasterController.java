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
import com.techhive.shivamweb.repository.FancyIntensityMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FancyIntensityMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
@RestController
@RequestMapping("/api/fancyIntensityMaster")
public class FancyIntensityMasterController {

	@Autowired
	FancyIntensityMasterRepository fancyIntensityMasterRepository;
	
	@Autowired
	FancyIntensityMasterService fancyIntensityMasterService;
	
	@PostMapping("saveFancyIntensityMaster")
	public ResponseEntity<?> saveFancyIntensityMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fancyIntensityMasterService.saveFancyIntensityMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@CrossOrigin
	@PutMapping("updateFancyIntensityMaster/{FancyIntensityMasterId}")
	public ResponseEntity<?> updateFancyIntensityMaster(@PathVariable("FancyIntensityMasterId") String FancyIntensityMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(FancyIntensityMasterId,body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fancyIntensityMasterService.updateFancyIntensityMaster(FancyIntensityMasterId,body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllFancyIntensityMaster")
	public ResponseEntity<?> getAllFancyIntensityMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All FancyIntensityMaster", fancyIntensityMasterRepository.findAllByOrderByFancyIntensityOrderAsc(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	@CrossOrigin
	@PutMapping("deleteFancyIntensityMaster/{FancyIntensityMasterId}")
	public ResponseEntity<?> deleteFancyIntensityMaster(@PathVariable("FancyIntensityMasterId") String FancyIntensityMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(FancyIntensityMasterId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fancyIntensityMasterService.deleteFancyIntensityMaster(FancyIntensityMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	

	@PostMapping("changeAllFancyIntensityMasterOrder")
	public ResponseEntity<?> changeAllFancyIntensityMasterOrder(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = fancyIntensityMasterService.changeAllFancyIntensityMasterOrder(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
