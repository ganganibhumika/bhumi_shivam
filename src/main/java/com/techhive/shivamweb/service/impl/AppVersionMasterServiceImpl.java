package com.techhive.shivamweb.service.impl;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.AppVersionMaster;
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.AppVersionMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.AppVersionMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class AppVersionMasterServiceImpl implements AppVersionMasterService {

	@Autowired
	private AppVersionMasterRepository appVersionMasterRepository;

	@Override
	public ResponseWrapperDTO updateAppVersion(String id, MyRequestBody body, String path) {
		Optional<AppVersionMaster> appVersionFromDb = appVersionMasterRepository.findById(id);
		if (!appVersionFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "App version not found.", null,
					HttpStatus.OK, path);

		AppVersionMaster appVersionMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				AppVersionMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(appVersionMaster.getAndroidVersion(),
				appVersionMaster.getIosVersion()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		appVersionFromDb.get().setAndroidVersion(appVersionMaster.getAndroidVersion());
		appVersionFromDb.get().setIosVersion(appVersionMaster.getIosVersion());
				
		appVersionMasterRepository.saveAndFlush(appVersionFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"App version " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);

	}

}
