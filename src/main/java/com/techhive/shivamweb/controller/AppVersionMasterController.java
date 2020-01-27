package com.techhive.shivamweb.controller;

import java.util.List;

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

import com.techhive.shivamweb.master.model.AppVersionMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.AppVersionMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.AppVersionMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("/api/appVersionMaster")
public class AppVersionMasterController {

	@Autowired
	private AppVersionMasterService appVersionMasterService;

	@Autowired
	private AppVersionMasterRepository appVersionMasterRepository;

	@PostMapping("addVersion")
	public ResponseEntity<?> addVersion(@RequestBody MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			AppVersionMaster appVersionData = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
					AppVersionMaster.class);
			
			List<AppVersionMaster> appVersionDataFromDb=appVersionMasterRepository.findAll();
			if(!ShivamWebMethodUtils.isListIsNullOrEmpty(appVersionDataFromDb)) {
				appVersionData.setId(appVersionDataFromDb.get(0).getId());
			}
			
			appVersionMasterRepository.saveAndFlush(appVersionData);
			return new ResponseEntity<>(
					new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Add successfully app vesion.", null, path),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("getAllAppVersion")
	public ResponseEntity<?> getAllAppVersion(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All App version",
					appVersionMasterRepository.findAll().isEmpty()?new AppVersionMaster():appVersionMasterRepository.findAll().get(0), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("updateAppVersion/{id}")
	public ResponseEntity<?> updateAppVersion(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = appVersionMasterService.updateAppVersion(id, body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

}
