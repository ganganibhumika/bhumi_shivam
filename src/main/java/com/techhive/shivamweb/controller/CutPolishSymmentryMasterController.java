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
import com.techhive.shivamweb.repository.CutPolishSymmentryMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.CutPolishSymmentryMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/cutPolishSymmentry")
public class CutPolishSymmentryMasterController {
	@Autowired
	CutPolishSymmentryMasterRepository cutPolishSymmentryMasterRepository;
	
	@Autowired
	CutPolishSymmentryMasterService cutPolishSymmentryMasterService;
	
	@PostMapping("saveCutPolishSymmentryMaster")
	public ResponseEntity<?> saveCutPolishSymmentryMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = cutPolishSymmentryMasterService.saveCutPolishSymmentryMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@CrossOrigin
	@PutMapping("updateCutPolishSymmentryMaster/{CutPolishSymmentryMasterId}")
	public ResponseEntity<?> updateCutPolishSymmentryMaster(@PathVariable("CutPolishSymmentryMasterId") String CutPolishSymmentryMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(CutPolishSymmentryMasterId,body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = cutPolishSymmentryMasterService.updateCutPolishSymmentryMaster(CutPolishSymmentryMasterId,body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllCutPolishSymmentryMaster")
	public ResponseEntity<?> getAllCutPolishSymmentryMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All CutPolishSymmentryMaster", cutPolishSymmentryMasterRepository.findAllByOrderByCutPolishSymmentryMasterOrderAsc(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	@CrossOrigin
	@PutMapping("deleteCutPolishSymmentryMaster/{CutPolishSymmentryMasterId}")
	public ResponseEntity<?> deleteCutPolishSymmentryMaster(@PathVariable("CutPolishSymmentryMasterId") String CutPolishSymmentryMasterId ,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(CutPolishSymmentryMasterId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = cutPolishSymmentryMasterService.deleteCutPolishSymmentryMaster(CutPolishSymmentryMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	

	@PostMapping("changeAllCutPolishSymmentryMasterOrder")
	public ResponseEntity<?> changeAllCutPolishSymmentryMasterOrder(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = cutPolishSymmentryMasterService.changeAllCutPolishSymmentryMasterOrder(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
