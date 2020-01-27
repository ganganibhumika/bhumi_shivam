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
import com.techhive.shivamweb.repository.NewArrivalSettingsRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.NewArrivalSettingsService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/newArrivalSettings/")
public class NewArrivalSettingsController {

	@Autowired
	NewArrivalSettingsService newArrivalSettingsService;

	@Autowired
	NewArrivalSettingsRepository newArrivalSettingsRepository;

	@CrossOrigin
	@PutMapping("updateNewArrivalSettings/{id}")
	public ResponseEntity<?> updateNewArrivalSettings(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = newArrivalSettingsService.updateNewArrivalSettings(id, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllNewArrivalSettings")
	public ResponseEntity<?> getAllNewArrivalSettings(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All NewArrivalSettings",
					newArrivalSettingsRepository.findAll(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@GetMapping("newArrivalSettingsGetById")
	public ResponseEntity<?> newArrivalSettingsGetById(@RequestParam String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get By Id",
					newArrivalSettingsRepository.findById(id).orElse(null), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PostMapping("addNewArrivalSettings")
	public ResponseEntity<?> addNewArrivalSettings(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = newArrivalSettingsService.addNewArrivalSettings(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
