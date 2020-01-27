package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.FancyIntensityMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.FancyIntensityMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FancyIntensityMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class FancyIntensityMasterServiceImpl implements FancyIntensityMasterService{

	@Autowired
	FancyIntensityMasterRepository fancyIntensityMasterRepository;

	@Override
	public ResponseWrapperDTO saveFancyIntensityMaster(MyRequestBody body, String path) {
		FancyIntensityMaster fancyIntensity = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyIntensityMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyIntensity.getFancyIntensityName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyIntensityMaster> fancyIntensityName = fancyIntensityMasterRepository.findByfancyIntensityName(fancyIntensity.getFancyIntensityName());

		if (fancyIntensityName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Intensity with name '" + fancyIntensity.getFancyIntensityName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = fancyIntensityMasterRepository.getMaxOrder();
		fancyIntensity.setFancyIntensityOrder(order == null ? 0 : order + 1);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensity);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Intensity " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateFancyIntensityMaster(String fancyIntensityMasterId, MyRequestBody body,
			String path) {

		Optional<FancyIntensityMaster> fancyIntensityFromDb = fancyIntensityMasterRepository.findById(fancyIntensityMasterId);
		if (!fancyIntensityFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Intensity not found.", null,
					HttpStatus.BAD_REQUEST, path);
		FancyIntensityMaster fancyIntensity = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyIntensityMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyIntensity.getFancyIntensityName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyIntensityMaster> fancyIntensityName = fancyIntensityMasterRepository.findByfancyIntensityName(fancyIntensity.getFancyIntensityName());
		if (fancyIntensityName.isPresent() && !fancyIntensityName.get().getFancyIntensityName().equals(fancyIntensityFromDb.get().getFancyIntensityName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Intensity with name '" + fancyIntensity.getFancyIntensityName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		fancyIntensityFromDb.get().setFancyIntensityName(fancyIntensity.getFancyIntensityName());
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Intensity " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteFancyIntensityMaster(String fancyIntensityMasterId, String path) {
		Optional<FancyIntensityMaster> fancyIntensityFromDb = fancyIntensityMasterRepository.findById(fancyIntensityMasterId);
		if (!fancyIntensityFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Intensity not found.", null,
					HttpStatus.BAD_REQUEST, path);
		fancyIntensityMasterRepository.deleteById(fancyIntensityMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Intensity " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllFancyIntensityMasterOrder(MyRequestBody body, String path) {
		List<FancyIntensityMaster> listOfFancyIntensityMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<FancyIntensityMaster>>() {
				});
		listOfFancyIntensityMaster.forEach(fancyIntensityMaster -> {
			fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Fancy Intensity Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
