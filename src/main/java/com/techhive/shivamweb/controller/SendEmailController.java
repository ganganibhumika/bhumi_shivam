package com.techhive.shivamweb.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@RestController
@RequestMapping("/api/email/")
public class SendEmailController {

	@Autowired
	SendMailService sendMailService;

	@Autowired
	UserRepository userRepository;

	@PostMapping("emailSelectedStone")
	public ResponseEntity<?> emailSelectedStone(@RequestParam("file") MultipartFile file, String email,
			String fileName, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (file.isEmpty() || ShivamWebMethodUtils.isObjectisNullOrEmpty(fileName, email)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = sendMailService.emailSelectedStone(email, fileName, file, path);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
