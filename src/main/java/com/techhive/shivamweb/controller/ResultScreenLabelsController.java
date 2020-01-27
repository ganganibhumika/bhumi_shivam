package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.techhive.shivamweb.service.ResultScreenLabelsService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/labels")
public class ResultScreenLabelsController {

	@Autowired
	ResultScreenLabelsService resultScreenService;

	/**
	 * used
	 * 
	 * @author Heena
	 * @return result screen lable list
	 */
	@GetMapping("getAllResultScreenLablesByUserId")
	public ResponseEntity<?> getAllResultScreenLablesByUserId(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = resultScreenService.getAllResultScreenLablesByUserId(userId);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}
	
	@GetMapping("allow/getAllResultScreenLables")
	public ResponseEntity<?> allowGetAllResultScreenLablesByUserId( HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			ResponseWrapperDTO response = resultScreenService.getAllResultScreenLables(path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}

	@PostMapping("saveOrderOfResultScreenLable")
	public ResponseEntity<?> saveOrderOfResultScreenLable(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = resultScreenService.saveOrderOfResultScreenLable(body);

			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}

	/**
	 * used save label screen value
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@PutMapping("saveResultScreenLableValueByUserId/{userId}")
	public ResponseEntity<?> saveResultScreenLableValueByUserId(@PathVariable(value = "userId") String userId,
			HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = resultScreenService.saveResultScreenLableValueByUserId(userId);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateColumnnChooserLabels/{userId}")
	public ResponseEntity<?> updateColumnnChooserLabels(@PathVariable(value="userId") String labelId,@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = resultScreenService.updateColumnnChooserLabels(body,path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
		return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);	
				}

	}
	
	@GetMapping("getAllLabelsForColumnChooser")
	public ResponseEntity<?> getAllLabelsForColumnChooser(HttpServletRequest request) {
		String path = request.getContextPath();
		try {
			ResponseWrapperDTO response = resultScreenService.getAllLabelsForColumnChooser(path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}

	}
}
