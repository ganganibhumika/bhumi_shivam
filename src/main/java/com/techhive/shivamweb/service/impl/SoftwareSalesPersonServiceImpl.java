package com.techhive.shivamweb.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SoftwareSalesPersonService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SoftwareSalesPersonServiceImpl implements SoftwareSalesPersonService {

	@Autowired
	private SoftwareSalesPersonRepository softwareSalesPersonRepository;

	@Override
	public ResponseWrapperDTO saveSoftwareSalesPersonMaster(MyRequestBody body, String path) {
		SoftwareSalePersonMaster softwareSalePersonMaster = ShivamWebMethodUtils.MAPPER
				.convertValue(body.getJsonOfObject(), SoftwareSalePersonMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(softwareSalePersonMaster.getName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		Optional<SoftwareSalePersonMaster> softwareSalePersonName = softwareSalesPersonRepository
				.findByName(softwareSalePersonMaster.getName());

		if (softwareSalePersonName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Software sales person with name '"
							+ softwareSalePersonMaster.getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);

		softwareSalesPersonRepository.saveAndFlush(softwareSalePersonMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software sales person " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);

	}

	@Override
	public ResponseWrapperDTO updateSoftwareSalesPersonMaster(String softwareSalePersonName, MyRequestBody body,
			String path) {
		Optional<SoftwareSalePersonMaster> softwareSpFromDb = softwareSalesPersonRepository
				.findByName(softwareSalePersonName);

		if (!softwareSpFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Software sales person not found.", null,
					HttpStatus.BAD_REQUEST, path);

		SoftwareSalePersonMaster softwareSp = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SoftwareSalePersonMaster.class);

		Optional<SoftwareSalePersonMaster> softwareSalePersonNameFromDb = softwareSalesPersonRepository
				.findByName(softwareSp.getName());

		if (softwareSalePersonNameFromDb.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Software sales person with name '"
							+ softwareSalePersonNameFromDb.get().getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);

		softwareSpFromDb.get().setName(softwareSp.getName());
		softwareSalesPersonRepository.saveAndFlush(softwareSpFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software sales person " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteSoftwareSalesPersonMaster(String softwareSalePersonName, String path) {

		Optional<SoftwareSalePersonMaster> softwareSpFromDb = softwareSalesPersonRepository
				.findByName(softwareSalePersonName);

		if (!softwareSpFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Software sales person not found.", null,
					HttpStatus.BAD_REQUEST, path);

		softwareSalesPersonRepository.deleteById(softwareSpFromDb.get().getId());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Software sales person " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

}
