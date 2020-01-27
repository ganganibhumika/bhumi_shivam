package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.CountryMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.CountryMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.CountryMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class CountryMasterServiceImpl implements CountryMasterService{

	@Autowired
	CountryMasterRepository countryMasterRepository;

	@Override
	public ResponseWrapperDTO saveCountryMaster(MyRequestBody body, String path) {
		CountryMaster countryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), CountryMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(countryMaster.getCountryMasterName(), countryMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<CountryMaster> countryMasterName = countryMasterRepository.findBycountryMasterName(countryMaster.getCountryMasterName());
		Optional<CountryMaster> shortName = countryMasterRepository.findByshortName(countryMaster.getShortName());
		if (countryMasterName.isPresent() && shortName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Country with name '" + countryMaster.getCountryMasterName()
							+ "' and short name '" + countryMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (countryMasterName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Country with name '" + countryMaster.getCountryMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Country with short name '" + countryMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = countryMasterRepository.getMaxOrder();
		countryMaster.setCountryMasterOrder(order == null ? 0 : order + 1);
		countryMasterRepository.saveAndFlush(countryMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Country " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateCountryMaster(String countryMasterId, MyRequestBody body, String path) {
		Optional<CountryMaster> countryMasterFromDb = countryMasterRepository.findById(countryMasterId);
		if (!countryMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Country not found.", null,
					HttpStatus.BAD_REQUEST, path);
		CountryMaster countryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), CountryMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(countryMaster.getCountryMasterName(), countryMaster.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<CountryMaster> countryMasterName = countryMasterRepository.findBycountryMasterName(countryMaster.getCountryMasterName());
		Optional<CountryMaster> shortName = countryMasterRepository.findByshortName(countryMaster.getShortName());
		if ((countryMasterName.isPresent() && !countryMasterName.get().getCountryMasterName().equals(countryMasterFromDb.get().getCountryMasterName()))
				&& (shortName.isPresent() && !shortName.get().getShortName().equals(countryMasterFromDb.get().getShortName())))
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Country with name '" + countryMaster.getCountryMasterName()
							+ "' and short name '" + countryMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (countryMasterName.isPresent() && !countryMasterName.get().getCountryMasterName().equals(countryMasterFromDb.get().getCountryMasterName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Country with name '" + countryMaster.getCountryMasterName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent() && !shortName.get().getShortName().equals(countryMasterFromDb.get().getShortName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Country with short name '" + countryMaster.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		countryMasterFromDb.get().setCountryMasterName(countryMaster.getCountryMasterName());
		countryMasterFromDb.get().setShortName(countryMaster.getShortName());
		countryMasterRepository.saveAndFlush(countryMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Country " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteCountryMaster(String countryMasterId, String path) {
		Optional<CountryMaster> countryMasterFromDb = countryMasterRepository.findById(countryMasterId);
		if (!countryMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Country not found.", null,
					HttpStatus.BAD_REQUEST, path);
		countryMasterRepository.deleteById(countryMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Country " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllCountryMasterOrder(MyRequestBody body, String path) {
		List<CountryMaster> listOfCountryMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<CountryMaster>>() {
				});
		listOfCountryMaster.forEach(countryMaster -> {
			countryMasterRepository.saveAndFlush(countryMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Country Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
