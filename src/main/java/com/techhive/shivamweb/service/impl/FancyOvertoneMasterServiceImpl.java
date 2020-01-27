package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.FancyOvertoneMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.FancyOvertoneMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FancyOvertoneMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class FancyOvertoneMasterServiceImpl implements FancyOvertoneMasterService{

	@Autowired
	FancyOvertoneMasterRepository fancyOvertoneMasterRepository;

	@Override
	public ResponseWrapperDTO saveFancyOvertoneMaster(MyRequestBody body, String path) {
		FancyOvertoneMaster fancyOvertone = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyOvertoneMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyOvertone.getFancyOvertoneName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyOvertoneMaster> fancyOvertoneName = fancyOvertoneMasterRepository.findByfancyOvertoneName(fancyOvertone.getFancyOvertoneName());

		if (fancyOvertoneName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Overtone with name '" + fancyOvertone.getFancyOvertoneName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = fancyOvertoneMasterRepository.getMaxOrder();
		fancyOvertone.setFancyOvertoneOrder(order == null ? 0 : order + 1);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertone);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Overtone " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateFancyOvertoneMaster(String fancyOvertoneMasterId, MyRequestBody body, String path) {

		Optional<FancyOvertoneMaster> fancyOvertoneFromDb = fancyOvertoneMasterRepository.findById(fancyOvertoneMasterId);
		if (!fancyOvertoneFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Overtone not found.", null,
					HttpStatus.BAD_REQUEST, path);
		FancyOvertoneMaster fancyOvertone = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyOvertoneMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyOvertone.getFancyOvertoneName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyOvertoneMaster> fancyOvertoneName = fancyOvertoneMasterRepository.findByfancyOvertoneName(fancyOvertone.getFancyOvertoneName());
		if (fancyOvertoneName.isPresent() && !fancyOvertoneName.get().getFancyOvertoneName().equals(fancyOvertoneFromDb.get().getFancyOvertoneName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Overtone with name '" + fancyOvertone.getFancyOvertoneName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		fancyOvertoneFromDb.get().setFancyOvertoneName(fancyOvertone.getFancyOvertoneName());
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Overtone " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteFancyOvertoneMaster(String fancyOvertoneMasterId, String path) {
		Optional<FancyOvertoneMaster> fancyOvertoneMasterFromDb = fancyOvertoneMasterRepository.findById(fancyOvertoneMasterId);
		if (!fancyOvertoneMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Overtone not found.", null,
					HttpStatus.BAD_REQUEST, path);
		fancyOvertoneMasterRepository.deleteById(fancyOvertoneMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Overtone " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllFancyOvertoneMasterOrder(MyRequestBody body, String path) {
		List<FancyOvertoneMaster> listOfFancyOvertoneMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<FancyOvertoneMaster>>() {
				});
		listOfFancyOvertoneMaster.forEach(fancyOvertoneMaster -> {
			fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Fancy Overtone Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
