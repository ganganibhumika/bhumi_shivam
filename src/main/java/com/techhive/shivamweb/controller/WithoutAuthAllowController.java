package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.repository.SalesPersonMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/withoutAuth")
public class WithoutAuthAllowController {

	@Autowired
	SalesPersonMasterRepository salesPersonMasterRepository;

	@GetMapping("getAllSalesPerson")
	public ResponseEntity<?> getAllSalesPerson(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All SalesPerson",
					salesPersonMasterRepository.findAllByisActive(true), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
