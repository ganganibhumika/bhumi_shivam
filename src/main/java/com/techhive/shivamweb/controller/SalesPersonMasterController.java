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

import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SalesPersonMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SalesPersonMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/salesPersonMaster")
public class SalesPersonMasterController {

	@Autowired
	SalesPersonMasterService salesPersonMasterService;

	@Autowired
	SalesPersonMasterRepository salesPersonMasterRepository;

	@PostMapping("saveSalesPerson")
	private ResponseEntity<?> saveSalesPerson(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = salesPersonMasterService.saveSalesPerson(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateSalesPerson/{id}")
	private ResponseEntity<?> updateSalesPerson(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();

		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), id)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = salesPersonMasterService.updateSalesPerson(id, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllSalesPerson")
	public ResponseEntity<?> getAllSalesPerson(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All SalesPerson",
					salesPersonMasterRepository.findAllByisActive(true), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteSalesPerson/{id}")
	private ResponseEntity<?> deleteSalesPerson(@PathVariable("id") String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = salesPersonMasterService.deleteSalesPerson(id, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getSalesPersonById")
	public ResponseEntity<?> getSalesPersonById(@RequestParam String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			SalesPersonMaster user = salesPersonMasterRepository.findById(id).orElse(new SalesPersonMaster());
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get By Id", user, path),
					HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getSalesPersonByUserId")
	public ResponseEntity<?> getSalesPersonByUserId(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			SalesPersonMaster user = salesPersonMasterRepository.getSalesPersonByUserId(userId)
					.orElse(new SalesPersonMaster());
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get By user Id", user, path),
					HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
