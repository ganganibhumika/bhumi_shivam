package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.PartyMastertRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.PartyMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/partyMaster/")
public class PartyMasterController {

	@Autowired
	PartyMasterService partyMasterService;

	@Autowired
	PartyMastertRepository partyMastertRepository;

	@PostMapping("savePartyMaster")
	public ResponseEntity<?> savePartyMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = partyMasterService.savePartyMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@GetMapping("allow/login")
	public ResponseEntity<?> login(@RequestParam String userNameOrEmail, @RequestParam String password,
			HttpServletRequest request) {
		String partyName = userNameOrEmail; //@bhumi
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(partyName, password)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			ResponseWrapperDTO response = partyMasterService.login(partyName, password);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}
	}

	@GetMapping("getAllPartyMaster")
	public ResponseEntity<?> getAllPartyMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All PartyMaster",
					partyMastertRepository.findAllIdName(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
