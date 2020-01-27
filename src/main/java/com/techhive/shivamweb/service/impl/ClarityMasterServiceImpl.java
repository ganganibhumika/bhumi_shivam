package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.ClarityMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ClarityMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ClarityMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;
@Service
public class ClarityMasterServiceImpl implements ClarityMasterService{

	@Autowired
	ClarityMasterRepository clarityMasterRepository;

	@Override
	public ResponseWrapperDTO saveClarityMaster(MyRequestBody body, String path) {
		ClarityMaster clarity = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ClarityMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(clarity.getClarityMasterName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ClarityMaster> clarityName = clarityMasterRepository.findByclarityMasterName(clarity.getClarityMasterName());

		if (clarityName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Clarity with name '" + clarity.getClarityMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = clarityMasterRepository.getMaxOrder();
		clarity.setClarityMasterOrder(order == null ? 0 : order + 1);
		clarityMasterRepository.saveAndFlush(clarity);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Clarity " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateClarityMaster(String clarityMasterId, MyRequestBody body, String path) {

		Optional<ClarityMaster> clarityFromDb = clarityMasterRepository.findById(clarityMasterId);
		if (!clarityFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Clarity not found.", null,
					HttpStatus.BAD_REQUEST, path);
		ClarityMaster clarity = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ClarityMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(clarity.getClarityMasterName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ClarityMaster> clarityName = clarityMasterRepository.findByclarityMasterName(clarity.getClarityMasterName());
		if (clarityName.isPresent() && !clarityName.get().getClarityMasterName().equals(clarityFromDb.get().getClarityMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Clarity with name '" + clarity.getClarityMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		clarityFromDb.get().setClarityMasterName(clarity.getClarityMasterName());
		clarityMasterRepository.saveAndFlush(clarityFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Clarity " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteClarityMaster(String clarityMasterId, String path) {
		Optional<ClarityMaster> clarityFromDb = clarityMasterRepository.findById(clarityMasterId);
		if (!clarityFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Clarity not found.", null,
					HttpStatus.BAD_REQUEST, path);
		clarityMasterRepository.deleteById(clarityMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Clarity " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllClarityMasterOrder(MyRequestBody body, String path) {
		List<ClarityMaster> listOfClarityMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<ClarityMaster>>() {
				});
		listOfClarityMaster.forEach(clarityMaster -> {
			clarityMasterRepository.saveAndFlush(clarityMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Clarity Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
	
}
