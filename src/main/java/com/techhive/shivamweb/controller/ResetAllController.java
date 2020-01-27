package com.techhive.shivamweb.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ResetAllService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/reset")
public class ResetAllController {

	@Autowired
	ResetAllService resetAllService;

	@CrossOrigin
	@PostMapping(value = "resetAll")
	public ResponseEntity<?> resetAll(HttpServletRequest request) throws ParseException {
		String path = request.getContextPath();
		ResponseWrapperDTO response = resetAllService.resetAll(path);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@CrossOrigin
	@PostMapping("/resetResultScreenLabel")
	public ResponseEntity<?> resetResultScreenLabel(HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			ResponseWrapperDTO response = resetAllService.resetResultScreenLabel();
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PostMapping("/resetPkt")
	public ResponseEntity<?> resetPkt(HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			ResponseWrapperDTO response = resetAllService.resetPkt();
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@GetMapping("/resetUserMigration")
	public ResponseEntity<?> resetUserMigration(HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			ResponseWrapperDTO response = resetAllService.resetUserMigration();
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
