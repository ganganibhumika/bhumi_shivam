package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SoftwarePartyMasterRespository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SoftwarePartyMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/SoftwarePartyMaster")
public class SoftwarePartyMasterController {

	@Autowired
	SoftwarePartyMasterService softwarePartyMasterService;

	@Autowired
	private SoftwarePartyMasterRespository softwarePartyMasterRespository;

	@PostMapping("allow/saveSoftwarePartyMaster")
	public ResponseEntity<?> saveSoftwareSalesPersonMaster(@RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);

			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwarePartyMasterService.saveSoftwarePartyMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("allow/updateSoftwarePartyMaster/{softwareSaleKeyForUpdate}")
	public ResponseEntity<?> updateSoftwareSalesPersonMaster(
			@PathVariable("softwareSaleKeyForUpdate") String softwareSaleKeyForUpdate, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSaleKeyForUpdate, body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwarePartyMasterService.updateSoftwarePartyMaster(softwareSaleKeyForUpdate, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	/* Delete from sale_shivam admin */

	@CrossOrigin
	@DeleteMapping("allow/deleteSoftwarePartyMaster/{softwareSaleKeyForDelete}")
	public ResponseEntity<?> deleteSoftwareSalesPersonMaster(
			@PathVariable("softwareSaleKeyForDelete") String softwareSaleKeyForDelete, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSaleKeyForDelete))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwarePartyMasterService.deleteSoftwarePartyMaster(softwareSaleKeyForDelete, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllSoftwarePartyMaster")
	public ResponseEntity<?> getAllSoftwarePartyMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All software party person",
					softwarePartyMasterRespository.findAllIdName(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@GetMapping("getSoftwarePartyByUserId")
	public ResponseEntity<?> getSoftwarePartyByUserId(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			SoftwarePartyMaster party = softwarePartyMasterRespository.getSoftwarePartyByUserId(userId)
					.orElse(new SoftwarePartyMaster());
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get By user Id", party, path),
					HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	
}
