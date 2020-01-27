package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.LabMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.LabMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.LabMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class LabMasterServiceImpl implements LabMasterService{

	@Autowired
	LabMasterRepository labMasterRepository;

	@Override
	public ResponseWrapperDTO saveLabMaster(MyRequestBody body, String path) {
		LabMaster labMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), LabMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(labMaster.getLabMasterName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<LabMaster> labMasterName = labMasterRepository.findBylabMasterName(labMaster.getLabMasterName());

		if (labMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Lab with name '" + labMaster.getLabMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = labMasterRepository.getMaxOrder();
		labMaster.setLabMasterOrder(order == null ? 0 : order + 1);
		labMasterRepository.saveAndFlush(labMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Lab " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateLabMaster(String labMasterId, MyRequestBody body, String path) {

		Optional<LabMaster> labMasterFromDb = labMasterRepository.findById(labMasterId);
		if (!labMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Lab not found.", null,
					HttpStatus.BAD_REQUEST, path);
		LabMaster labMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), LabMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(labMaster.getLabMasterName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<LabMaster> labMasterName = labMasterRepository.findBylabMasterName(labMaster.getLabMasterName());
		if (labMasterName.isPresent() && !labMasterName.get().getLabMasterName().equals(labMasterFromDb.get().getLabMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Lab with name '" + labMaster.getLabMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		labMasterFromDb.get().setLabMasterName(labMaster.getLabMasterName());
		labMasterRepository.saveAndFlush(labMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Lab " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteLabMaster(String labMasterId, String path) {
		Optional<LabMaster> labMasterFromDb = labMasterRepository.findById(labMasterId);
		if (!labMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Lab not found.", null,
					HttpStatus.BAD_REQUEST, path);
		labMasterRepository.deleteById(labMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Lab " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllLabMasterOrder(MyRequestBody body, String path) {
		List<LabMaster> listOfLabMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<LabMaster>>() {
				});
		listOfLabMaster.forEach(labMaster -> {
			labMasterRepository.saveAndFlush(labMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Lab Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
	
	
}
