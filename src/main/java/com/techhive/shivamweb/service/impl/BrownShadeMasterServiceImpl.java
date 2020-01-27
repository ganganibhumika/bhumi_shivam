package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.BrownShadeMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.BrownShadeMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.BrownShadeMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class BrownShadeMasterServiceImpl implements BrownShadeMasterService{

	@Autowired
	BrownShadeMasterRepository brownShadeMasterRepository;

	@Override
	public ResponseWrapperDTO saveBrownShadeMaster(MyRequestBody body, String path) {
		BrownShadeMaster brownShadeMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), BrownShadeMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(brownShadeMaster.getBrownShadeMasterName(), brownShadeMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<BrownShadeMaster> brownShadeMasterName = brownShadeMasterRepository.findBybrownShadeMasterName(brownShadeMaster.getBrownShadeMasterName());
		Optional<BrownShadeMaster> shortName = brownShadeMasterRepository.findByshortName(brownShadeMaster.getShortName());
		if (brownShadeMasterName.isPresent() && shortName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Brown Shade with name '" + brownShadeMaster.getBrownShadeMasterName()
							+ "' and short name '" + brownShadeMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (brownShadeMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Brown Shade with name '" + brownShadeMaster.getBrownShadeMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Brown Shade with short name '" + brownShadeMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = brownShadeMasterRepository.getMaxOrder();
		brownShadeMaster.setBrownShadeMasterOrder(order == null ? 0 : order + 1);
		brownShadeMasterRepository.saveAndFlush(brownShadeMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Brown Shade " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateBrownShadeMaster(String brownShadeMasterId, MyRequestBody body, String path) {
		Optional<BrownShadeMaster> brownShadeMasterFromDb = brownShadeMasterRepository.findById(brownShadeMasterId);
		if (!brownShadeMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Brown Shade not found.", null,
					HttpStatus.BAD_REQUEST, path);
		BrownShadeMaster brownShadeMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), BrownShadeMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(brownShadeMaster.getBrownShadeMasterName(), brownShadeMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<BrownShadeMaster> brownShadeMasterName = brownShadeMasterRepository.findBybrownShadeMasterName(brownShadeMaster.getBrownShadeMasterName());
		Optional<BrownShadeMaster> shortName = brownShadeMasterRepository.findByshortName(brownShadeMaster.getShortName());
		if ((brownShadeMasterName.isPresent() && !brownShadeMasterName.get().getBrownShadeMasterName().equals(brownShadeMasterFromDb.get().getBrownShadeMasterName()))
				&& (shortName.isPresent() && !shortName.get().getShortName().equals(brownShadeMasterFromDb.get().getShortName())))
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Brown Shade with name '" + brownShadeMaster.getBrownShadeMasterName()
							+ "' and short name '" + brownShadeMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (brownShadeMasterName.isPresent() && !brownShadeMasterName.get().getBrownShadeMasterName().equals(brownShadeMasterFromDb.get().getBrownShadeMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Brown Shade with name '" + brownShadeMaster.getBrownShadeMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent() && !shortName.get().getShortName().equals(brownShadeMasterFromDb.get().getShortName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Brown Shade with short name '" + brownShadeMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		brownShadeMasterFromDb.get().setBrownShadeMasterName(brownShadeMaster.getBrownShadeMasterName());
		brownShadeMasterFromDb.get().setShortName(brownShadeMaster.getShortName());
		brownShadeMasterRepository.saveAndFlush(brownShadeMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Brown Shade " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteBrownShadeMaster(String brownShadeMasterId, String path) {
		Optional<BrownShadeMaster> brownShadeMasterFromDb = brownShadeMasterRepository.findById(brownShadeMasterId);
		if (!brownShadeMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Brown Shade not found.", null,
					HttpStatus.BAD_REQUEST, path);
		brownShadeMasterRepository.deleteById(brownShadeMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Brown Shade " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllBrownShadeMasterOrder(MyRequestBody body, String path) {
		List<BrownShadeMaster> listOfBrownShadeMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<BrownShadeMaster>>() {
				});
		listOfBrownShadeMaster.forEach(brownShadeMaster -> {
			brownShadeMasterRepository.saveAndFlush(brownShadeMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Brown Shade Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
