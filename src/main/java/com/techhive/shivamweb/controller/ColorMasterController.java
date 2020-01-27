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
import com.techhive.shivamweb.repository.ColorMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ColorMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/colorMaster")
public class ColorMasterController {
	@Autowired
	ColorMasterRepository colorMasterRepository;

	@Autowired
	ColorMasterService colorMasterService;

	@PostMapping("saveColorMaster")
	public ResponseEntity<?> saveColorMaster(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = colorMasterService.saveColorMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateColorMaster/{ColorMasterId}")
	public ResponseEntity<?> updateColorMaster(@PathVariable("ColorMasterId") String ColorMasterId,
			@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(ColorMasterId, body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = colorMasterService.updateColorMaster(ColorMasterId, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllColorMaster")
	public ResponseEntity<?> getAllColorMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All ColorMaster",
					colorMasterRepository.findAllByOrderByColorOrderAsc(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteColorMaster/{ColorMasterId}")
	public ResponseEntity<?> deleteColorMaster(@PathVariable("ColorMasterId") String ColorMasterId,
			@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(ColorMasterId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = colorMasterService.deleteColorMaster(ColorMasterId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@PostMapping("changeAllColorMasterOrder")
	public ResponseEntity<?> changeAllColorMasterOrder(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = colorMasterService.changeAllColorMasterOrder(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
