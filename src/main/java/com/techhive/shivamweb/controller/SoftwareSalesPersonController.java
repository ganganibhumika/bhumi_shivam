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
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SoftwareSalesPersonService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("api/softwareSalesPersonMaster")
public class SoftwareSalesPersonController {

	@Autowired
	private SoftwareSalesPersonService softwareSalesPersonService;

	@Autowired
	private SoftwareSalesPersonRepository softwareSalesPersonRepository;

	@PostMapping("allow/saveSoftwareSalesPersonMaster")
	public ResponseEntity<?> saveSoftwareSalesPersonMaster(@RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);

			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwareSalesPersonService.saveSoftwareSalesPersonMaster(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("allow/updateSoftwareSalesPersonMaster/{softwareSalePersonId}")
	public ResponseEntity<?> updateSoftwareSalesPersonMaster(
			@PathVariable("softwareSalePersonId") String softwareSalePersonId, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSalePersonId, body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwareSalesPersonService.updateSoftwareSalesPersonMaster(softwareSalePersonId, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	/* Delete from sale_shivam admin */

	@CrossOrigin
	@DeleteMapping("allow/deleteSoftwareSalesPersonMaster/{softwareSalePersonId}")
	public ResponseEntity<?> deleteSoftwareSalesPersonMaster(
			@PathVariable("softwareSalePersonId") String softwareSalePersonId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSalePersonId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = softwareSalesPersonService.deleteSoftwareSalesPersonMaster(softwareSalePersonId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("allow/getAllSoftwareSalesPersonMaster")
	public ResponseEntity<?> getAllColorMaster(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All software sales person",
					softwareSalesPersonRepository.findAll(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
