package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.repository.SearchHistoryRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SearchHistoryService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/searchHistory/")
public class SearchHistoryController {

	@Autowired
	SearchHistoryService searchHistoryService;

	@Autowired
	SearchHistoryRepository searchHistoryRepository;


//	@PostMapping("saveSearchHistory")
//	public void saveSearchHistory(@RequestBody StoneSearchCriteria body, HttpServletRequest request) {
//		String path = request.getServletPath();
//		try {
//			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body, body.getUserId())) {
//				searchHistoryService.saveSearchHistory(body);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	/* get all created demand */

	@GetMapping("getAllsaveSearchHistory")
	public ResponseEntity<?> getAllsaveSearchHistory(@RequestParam Integer pageNumber,
			@RequestParam Integer noOfRecords, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortOrder, @RequestParam(required = false) String searchText,
			@RequestParam(required = false) String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = searchHistoryService.getAllsaveSearchHistory(pageNumber, noOfRecords, sortColumn, sortOrder,
					searchText, userId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getAllPastSearch")
	public ResponseEntity<?> getAllPastSearch(@RequestParam Integer pageNumber, @RequestParam Integer noOfRecords,
			@RequestParam(required = false) String sortColumn, @RequestParam(required = false) String sortOrder,
			@RequestParam(required = false) String searchText, @RequestParam(required = false) String userId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = searchHistoryService.getAllPastSearch(pageNumber, noOfRecords, sortColumn, sortOrder, searchText,
					userId, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getSearchHistoryById")
	public ResponseEntity<?> getSearchHistoryById(String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO(
					HttpServletResponse.SC_OK, "get by id", searchHistoryService
							.convertSearchHistoryToStoneSearchCriteria(searchHistoryRepository.findById(id).get()),
					HttpStatus.OK, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
