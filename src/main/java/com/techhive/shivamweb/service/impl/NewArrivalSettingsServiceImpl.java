package com.techhive.shivamweb.service.impl;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.NewArrivalSettingsRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.NewArrivalSettingsService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class NewArrivalSettingsServiceImpl implements NewArrivalSettingsService {

	@Autowired
	NewArrivalSettingsRepository newArrivalSettingsRepository;

	@Override
	public ResponseWrapperDTO updateNewArrivalSettings(String id, MyRequestBody body, String path) {
		Optional<NewArrivalSettings> newArrivalSettingFromDb = newArrivalSettingsRepository.findById(id);
		if (!newArrivalSettingFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "New Arrival Settings not found.", null,
					HttpStatus.OK, path);

		NewArrivalSettings newArrivalSettings = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				NewArrivalSettings.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(newArrivalSettings.getNoOfDays()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		newArrivalSettingFromDb.get().setNoOfDays(newArrivalSettings.getNoOfDays());
		newArrivalSettingsRepository.saveAndFlush(newArrivalSettingFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"New Arrival Settings " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public Boolean isNewArrival(Integer NoOfDays, Date giDate) {

		int daysBetweenForDisc = ShivamWebMethodUtils.getTotalNoOfDay(giDate);
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(NoOfDays)) {
			if (NoOfDays >= daysBetweenForDisc)
				return true;
		}

		return false;
	}

	@Override
	public ResponseWrapperDTO addNewArrivalSettings(MyRequestBody body, String path) {
		NewArrivalSettings newArrivalSetting = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				NewArrivalSettings.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(newArrivalSetting.getNoOfDays()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		newArrivalSettingsRepository.saveAndFlush(newArrivalSetting);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"New Arrival Settings " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

}
