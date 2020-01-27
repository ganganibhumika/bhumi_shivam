package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

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
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SuggestionFeedbackService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("api/suggestionFeedback")
public class SuggestionFeedbackController {

	@Autowired
	SuggestionFeedbackService suggestionFeedbackService;

	@PostMapping("saveSuggestionFeedback")
	public ResponseEntity<?> saveSuggestionFeedback(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = suggestionFeedbackService.saveSuggestionFeedback(body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	@GetMapping("getAllSuggestionFeedback")
	public ResponseEntity<?> getAllSuggestionFeedback(@RequestParam Integer pageNumber,
			@RequestParam Integer noOfRecords, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortOrder, @RequestParam(required = false) String searchText
			, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if(ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber,noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			return new ResponseEntity<>(suggestionFeedbackService.getAllSuggestionFeedback(pageNumber, noOfRecords, sortColumn,
					sortOrder, searchText,path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	
	
	@CrossOrigin
	@PutMapping("deleteSuggestionFeedback/{id}")
	private ResponseEntity<?> deleteSuggestionFeedback(@PathVariable("id") String id, @RequestParam String loginUserId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id, loginUserId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = suggestionFeedbackService.deleteSuggestionFeedback(id, loginUserId, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
