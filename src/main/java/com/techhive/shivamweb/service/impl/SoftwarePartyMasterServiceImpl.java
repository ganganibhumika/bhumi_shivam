package com.techhive.shivamweb.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SoftwarePartyMasterRespository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SoftwarePartyMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SoftwarePartyMasterServiceImpl implements SoftwarePartyMasterService {

	@Autowired
	private SoftwarePartyMasterRespository softwarePartyMasterRespository;

	@Override
	public ResponseWrapperDTO saveSoftwarePartyMaster(MyRequestBody body, String path) {
		SoftwarePartyMaster softwarePartyMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SoftwarePartyMaster.class);

		// if
		// (ShivamWebMethodUtils.isObjectNullOrEmpty(softwarePartyMaster.getPartyCode(),
		// softwarePartyMaster.getGroupCode(), softwarePartyMaster.getPartyName(),
		// softwarePartyMaster.getPartyCompanyName(), softwarePartyMaster.getMobileNo(),
		// softwarePartyMaster.getEmailId(), softwarePartyMaster.getBrokerName(),
		// softwarePartyMaster.getGstin())) {

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwarePartyMaster.getPartyCode(),
				softwarePartyMaster.getGroupCode(), softwarePartyMaster.getPartyName())) {
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		}

		Optional<SoftwarePartyMaster> softwarePartyName = softwarePartyMasterRespository
				.findByPartyName(softwarePartyMaster.getPartyName());

		if (softwarePartyName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Software party  with name '"
					+ softwarePartyMaster.getPartyName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		Optional<SoftwarePartyMaster> softwarePartyEmailId = softwarePartyMasterRespository
				.findByEmailId(softwarePartyMaster.getEmailId());

		if (softwarePartyEmailId.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Software party with email id '"
					+ softwarePartyMaster.getEmailId() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster);

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software party " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteSoftwarePartyMaster(String softwareSaleKeyForDelete, String path) {
		Optional<SoftwarePartyMaster> softwarePartyFromDb = softwarePartyMasterRespository
				.findByPartyName(softwareSaleKeyForDelete);

		if (!softwarePartyFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Software party not found.", null,
					HttpStatus.BAD_REQUEST, path);

		softwarePartyMasterRespository.deleteById(softwarePartyFromDb.get().getId());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software sales person " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);

	}

	@Override
	public ResponseWrapperDTO updateSoftwarePartyMaster(String softwareSaleKeyForUpdate, MyRequestBody body,
			String path) {
		Optional<SoftwarePartyMaster> softwarePartyFromDb = softwarePartyMasterRespository
				.findByPartyName(softwareSaleKeyForUpdate);
		if (!softwarePartyFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Software party not found.", null,
					HttpStatus.BAD_REQUEST, path);

		SoftwarePartyMaster softwareParty = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SoftwarePartyMaster.class);

		Optional<SoftwarePartyMaster> softwarePartyName = softwarePartyMasterRespository
				.findByPartyName(softwareParty.getPartyName());

		if (softwarePartyName.isPresent() && !softwareParty.getPartyName().equals(softwareSaleKeyForUpdate))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Software party  with name '"
					+ softwareParty.getPartyName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		Optional<SoftwarePartyMaster> softwarePartyNameFromDb = softwarePartyMasterRespository
				.findByEmailId(softwareParty.getEmailId());

		if (softwarePartyNameFromDb.isPresent()
				&& !softwarePartyNameFromDb.get().getPartyName().equals(softwareSaleKeyForUpdate))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Software party with email id'"
					+ softwareParty.getEmailId() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		softwareParty.setId(softwarePartyFromDb.get().getId());

		softwarePartyMasterRespository.saveAndFlush(softwareParty); // required all db field data

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software party " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);

	}

}
