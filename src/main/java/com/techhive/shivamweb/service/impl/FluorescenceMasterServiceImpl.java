package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.FluorescenceMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.FluorescenceMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.FluorescenceMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;
@Service
public class FluorescenceMasterServiceImpl implements FluorescenceMasterService{

	@Autowired
	FluorescenceMasterRepository fluorescenceMasterRepository;

	@Override
	public ResponseWrapperDTO saveFluorescenceMaster(MyRequestBody body, String path) {
		FluorescenceMaster fluorescenceMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FluorescenceMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fluorescenceMaster.getFluorescenceMasterName(), fluorescenceMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FluorescenceMaster> fluorescenceMasterName = fluorescenceMasterRepository.findByfluorescenceMasterName(fluorescenceMaster.getFluorescenceMasterName());
		Optional<FluorescenceMaster> shortName = fluorescenceMasterRepository.findByshortName(fluorescenceMaster.getShortName());
		if (fluorescenceMasterName.isPresent() && shortName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Fluorescence with name '" + fluorescenceMaster.getFluorescenceMasterName()
							+ "' and short name '" + fluorescenceMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (fluorescenceMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fluorescence with name '" + fluorescenceMaster.getFluorescenceMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fluorescence with short name '" + fluorescenceMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = fluorescenceMasterRepository.getMaxOrder();
		fluorescenceMaster.setFluorescenceMasterOrder(order == null ? 0 : order + 1);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fluorescence " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateFluorescenceMaster(String fluorescenceMasterId, MyRequestBody body, String path) {
		Optional<FluorescenceMaster> fluorescenceMasterFromDb = fluorescenceMasterRepository.findById(fluorescenceMasterId);
		if (!fluorescenceMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fluorescence not found.", null,
					HttpStatus.BAD_REQUEST, path);
		FluorescenceMaster fluorescenceMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), FluorescenceMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(fluorescenceMaster.getFluorescenceMasterName(), fluorescenceMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<FluorescenceMaster> fluorescenceMasterName = fluorescenceMasterRepository.findByfluorescenceMasterName(fluorescenceMaster.getFluorescenceMasterName());
		Optional<FluorescenceMaster> shortName = fluorescenceMasterRepository.findByshortName(fluorescenceMaster.getShortName());
		if ((fluorescenceMasterName.isPresent() && !fluorescenceMasterName.get().getFluorescenceMasterName().equals(fluorescenceMasterFromDb.get().getFluorescenceMasterName()))
				&& (shortName.isPresent() && !shortName.get().getShortName().equals(fluorescenceMasterFromDb.get().getShortName())))
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Fluorescence with name '" + fluorescenceMaster.getFluorescenceMasterName()
							+ "' and short name '" + fluorescenceMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (fluorescenceMasterName.isPresent() && !fluorescenceMasterName.get().getFluorescenceMasterName().equals(fluorescenceMasterFromDb.get().getFluorescenceMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fluorescence with name '" + fluorescenceMaster.getFluorescenceMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent() && !shortName.get().getShortName().equals(fluorescenceMasterFromDb.get().getShortName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Fluorescence with short name '" + fluorescenceMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		fluorescenceMasterFromDb.get().setFluorescenceMasterName(fluorescenceMaster.getFluorescenceMasterName());
		fluorescenceMasterFromDb.get().setShortName(fluorescenceMaster.getShortName());
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fluorescence " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteFluorescenceMaster(String fluorescenceMasterId, String path) {
		Optional<FluorescenceMaster> fluorescenceMasterFromDb = fluorescenceMasterRepository.findById(fluorescenceMasterId);
		if (!fluorescenceMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fluorescence not found.", null,
					HttpStatus.BAD_REQUEST, path);
		fluorescenceMasterRepository.deleteById(fluorescenceMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fluorescence " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllFluorescenceMasterOrder(MyRequestBody body, String path) {
		List<FluorescenceMaster> listOfFluorescenceMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<FluorescenceMaster>>() {
				});
		listOfFluorescenceMaster.forEach(fluorescenceMaster -> {
			fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Fluorescence Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
