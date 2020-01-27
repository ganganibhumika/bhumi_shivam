package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.CutPolishSymmentryMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.CutPolishSymmentryMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.CutPolishSymmentryMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class CutPolishSymmentryMasterServiceImpl implements CutPolishSymmentryMasterService{

	@Autowired
	CutPolishSymmentryMasterRepository cutPolishSymmentryMasterRepository;

	@Override
	public ResponseWrapperDTO saveCutPolishSymmentryMaster(MyRequestBody body, String path) {
		CutPolishSymmentryMaster cutPolishSymmentryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), CutPolishSymmentryMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(cutPolishSymmentryMaster.getCutPolishSymmentryMasterName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<CutPolishSymmentryMaster> cutPolishSymmentryMasterName = cutPolishSymmentryMasterRepository.findBycutPolishSymmentryMasterName(cutPolishSymmentryMaster.getCutPolishSymmentryMasterName());

		if (cutPolishSymmentryMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Cut-Polish-Symmetry with name '" + cutPolishSymmentryMaster.getCutPolishSymmentryMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = cutPolishSymmentryMasterRepository.getMaxOrder();
		cutPolishSymmentryMaster.setCutPolishSymmentryMasterOrder(order == null ? 0 : order + 1);
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Cut-Polish-Symmetry " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateCutPolishSymmentryMaster(String cutPolishSymmentryMasterId, MyRequestBody body,
			String path) {

		Optional<CutPolishSymmentryMaster> cutPolishSymmentryMasterFromDb = cutPolishSymmentryMasterRepository.findById(cutPolishSymmentryMasterId);
		if (!cutPolishSymmentryMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Cut-Polish-Symmetry not found.", null,
					HttpStatus.BAD_REQUEST, path);
		CutPolishSymmentryMaster cutPolishSymmentryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), CutPolishSymmentryMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(cutPolishSymmentryMaster.getCutPolishSymmentryMasterName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<CutPolishSymmentryMaster> cutPolishSymmentryMasterName = cutPolishSymmentryMasterRepository.findBycutPolishSymmentryMasterName(cutPolishSymmentryMaster.getCutPolishSymmentryMasterName());
		if (cutPolishSymmentryMasterName.isPresent() && !cutPolishSymmentryMasterName.get().getCutPolishSymmentryMasterName().equals(cutPolishSymmentryMasterFromDb.get().getCutPolishSymmentryMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Cut-Polish-Symmetry with name '" + cutPolishSymmentryMaster.getCutPolishSymmentryMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		cutPolishSymmentryMasterFromDb.get().setCutPolishSymmentryMasterName(cutPolishSymmentryMaster.getCutPolishSymmentryMasterName());
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Cut-Polish-Symmetry " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteCutPolishSymmentryMaster(String cutPolishSymmentryMasterId, String path) {
		Optional<CutPolishSymmentryMaster> cutPolishSymmentryMasterFromDb = cutPolishSymmentryMasterRepository.findById(cutPolishSymmentryMasterId);
		if (!cutPolishSymmentryMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Cut-Polish-Symmetry not found.", null,
					HttpStatus.BAD_REQUEST, path);
		cutPolishSymmentryMasterRepository.deleteById(cutPolishSymmentryMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Cut-Polish-Symmetry " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllCutPolishSymmentryMasterOrder(MyRequestBody body, String path) {
		List<CutPolishSymmentryMaster> listOfCutPolishSymmentryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<CutPolishSymmentryMaster>>() {
				});
		listOfCutPolishSymmentryMaster.forEach(cutPolishSymmentryMaster -> {
			cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Cut-Polish-Symmetry Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
