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

import com.techhive.shivamweb.repository.SaveSearchCriteriaRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SaveSearchCriteriaService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/saveSearchCriteria/")
public class SaveSearchCriteriaController {

	@Autowired
	SaveSearchCriteriaService saveSearchCriteriaService;

	@Autowired
	SaveSearchCriteriaRepository saveSearchCriteriaRepository;

	@PostMapping("saveSearchCriteriaAdd")
	public ResponseEntity<?> saveSearchCriteriaAdd(@RequestBody StoneSearchCriteria body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body, body.getName(), body.getUserId()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = saveSearchCriteriaService.saveSearchCriteriaAdd(body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getAllSearchCriteriaOfUser")
	public ResponseEntity<?> getAllSearchCriteriaOfUser(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			return new ResponseEntity<>(
					new ResponseWrapperDTO(HttpServletResponse.SC_OK, "List of Search criteria of User",
							saveSearchCriteriaRepository.getAllSearchCriteriaOfUser(userId), path),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("searchCriteriaGetById")
	public ResponseEntity<?> searchCriteriaGetById(@RequestParam String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = saveSearchCriteriaService.searchCriteriaGetById(id, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteSearchCriteria/{searchCriteriaId}")
	public ResponseEntity<?> deleteSearchCriteria(@PathVariable("searchCriteriaId") String searchCriteriaId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(searchCriteriaId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = saveSearchCriteriaService.deleteSearchCriteria(searchCriteriaId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateSearchCriteria/{searchCriteriaId}")
	public ResponseEntity<?> updateSearchCriteria(@PathVariable("searchCriteriaId") String searchCriteriaId,
			@RequestBody StoneSearchCriteria body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(searchCriteriaId, body, body.getName(), body.getUserId()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = saveSearchCriteriaService.updateSearchCriteria(searchCriteriaId, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
