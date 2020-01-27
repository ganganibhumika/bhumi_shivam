package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.MilkMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.MilkMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.MilkMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;
@Service
public class MilkMasterServiceImpl implements MilkMasterService{

	@Autowired
	MilkMasterRepository milkMasterRepository;

	@Override
	public ResponseWrapperDTO saveMilkMaster(MyRequestBody body, String path) {
		MilkMaster milkMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), MilkMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(milkMaster.getMilkMasterName(), milkMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<MilkMaster> milkMasterName = milkMasterRepository.findBymilkMasterName(milkMaster.getMilkMasterName());
		Optional<MilkMaster> shortName = milkMasterRepository.findByshortName(milkMaster.getShortName());
		if (milkMasterName.isPresent() && shortName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Milky with name '" + milkMaster.getMilkMasterName()
							+ "' and short name '" + milkMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (milkMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Milky with name '" + milkMaster.getMilkMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Milky with short name '" + milkMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = milkMasterRepository.getMaxOrder();
		milkMaster.setMilkMasterOrder(order == null ? 0 : order + 1);
		milkMasterRepository.saveAndFlush(milkMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Milky " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateMilkMaster(String milkMasterId, MyRequestBody body, String path) {
		Optional<MilkMaster> milkMasterFromDb = milkMasterRepository.findById(milkMasterId);
		if (!milkMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Milky not found.", null,
					HttpStatus.BAD_REQUEST, path);
		MilkMaster milkMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), MilkMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(milkMaster.getMilkMasterName(), milkMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<MilkMaster> milkMasterName = milkMasterRepository.findBymilkMasterName(milkMaster.getMilkMasterName());
		Optional<MilkMaster> shortName = milkMasterRepository.findByshortName(milkMaster.getShortName());
		if ((milkMasterName.isPresent() && !milkMasterName.get().getMilkMasterName().equals(milkMasterFromDb.get().getMilkMasterName()))
				&& (shortName.isPresent() && !shortName.get().getShortName().equals(milkMasterFromDb.get().getShortName())))
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Milky with name '" + milkMaster.getMilkMasterName()
							+ "' and short name '" + milkMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (milkMasterName.isPresent() && !milkMasterName.get().getMilkMasterName().equals(milkMasterFromDb.get().getMilkMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Milky with name '" + milkMaster.getMilkMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent() && !shortName.get().getShortName().equals(milkMasterFromDb.get().getShortName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Milky with short name '" + milkMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		milkMasterFromDb.get().setMilkMasterName(milkMaster.getMilkMasterName());
		milkMasterFromDb.get().setShortName(milkMaster.getShortName());
		milkMasterRepository.saveAndFlush(milkMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Milky " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteMilkMaster(String milkMasterId, String path) {
		Optional<MilkMaster> milkMasterFromDb = milkMasterRepository.findById(milkMasterId);
		if (!milkMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Milky not found.", null,
					HttpStatus.BAD_REQUEST, path);
		milkMasterRepository.deleteById(milkMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Milky " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllMilkMasterOrder(MyRequestBody body, String path) {
		List<MilkMaster> listOfMilkMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<MilkMaster>>() {
				});
		listOfMilkMaster.forEach(milkMaster -> {
			milkMasterRepository.saveAndFlush(milkMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Milky Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
