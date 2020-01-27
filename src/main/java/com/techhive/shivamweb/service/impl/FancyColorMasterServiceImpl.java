package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.FancyColorMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FancyColorMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;
@Service
public class FancyColorMasterServiceImpl implements FancyColorMasterService{

	@Autowired
	FancyColorMasterRepository fancyColorMasterRepository;

	@Override
	public ResponseWrapperDTO saveFancyColorMaster(MyRequestBody body, String path) {
		FancyColorMaster fancyColor = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyColorMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyColor.getFancyColorName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyColorMaster> fancyColorName = fancyColorMasterRepository.findByfancyColorName(fancyColor.getFancyColorName());

		if (fancyColorName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Color with name '" + fancyColor.getFancyColorName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = fancyColorMasterRepository.getMaxOrder();
		fancyColor.setFancyColorOrder(order == null ? 0 : order + 1);
		fancyColorMasterRepository.saveAndFlush(fancyColor);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Color " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateFancyColorMaster(String fancyColorMasterId, MyRequestBody body, String path) {

		Optional<FancyColorMaster> fancyColorFromDb = fancyColorMasterRepository.findById(fancyColorMasterId);
		if (!fancyColorFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Color not found.", null,
					HttpStatus.BAD_REQUEST, path);
		FancyColorMaster fancyColor = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FancyColorMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fancyColor.getFancyColorName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FancyColorMaster> fancyColorName = fancyColorMasterRepository.findByfancyColorName(fancyColor.getFancyColorName());
		if (fancyColorName.isPresent() && !fancyColorName.get().getFancyColorName().equals(fancyColorFromDb.get().getFancyColorName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fancy Color with name '" + fancyColor.getFancyColorName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		fancyColorFromDb.get().setFancyColorName(fancyColor.getFancyColorName());
		fancyColorMasterRepository.saveAndFlush(fancyColorFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Color " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteColorMaster(String fancyColorMasterId, String path) {
		Optional<FancyColorMaster> fancyColorFromDb = fancyColorMasterRepository.findById(fancyColorMasterId);
		if (!fancyColorFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fancy Color not found.", null,
					HttpStatus.BAD_REQUEST, path);
		fancyColorMasterRepository.deleteById(fancyColorMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fancy Color " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllColorMasterOrder(MyRequestBody body, String path) {
		List<FancyColorMaster> listOfFancyColorMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<FancyColorMaster>>() {
				});
		listOfFancyColorMaster.forEach(fancyColorMaster -> {
			fancyColorMasterRepository.saveAndFlush(fancyColorMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Fancy Color Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
