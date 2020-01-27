package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.ColorMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ColorMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ColorMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class ColorMasterServiceImpl implements ColorMasterService{

	@Autowired
	ColorMasterRepository colorMasterRepository;

	@Override
	public ResponseWrapperDTO saveColorMaster(MyRequestBody body, String path) {
		ColorMaster color = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ColorMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(color.getColorName()
			)) 
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ColorMaster> colorName = colorMasterRepository.findBycolorName(color.getColorName());

		if (colorName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Color with name '" + color.getColorName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = colorMasterRepository.getMaxOrder();
		color.setColorOrder(order == null ? 0 : order + 1);
		colorMasterRepository.saveAndFlush(color);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Color " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateColorMaster(String colorMasterId, MyRequestBody body, String path) {

		Optional<ColorMaster> colorFromDb = colorMasterRepository.findById(colorMasterId);
		if (!colorFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Color not found.", null,
					HttpStatus.BAD_REQUEST, path);
		ColorMaster color = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ColorMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(color.getColorName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ColorMaster> colorName = colorMasterRepository.findBycolorName(color.getColorName());
		if (colorName.isPresent() && !colorName.get().getColorName().equals(colorFromDb.get().getColorName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Color with name '" + color.getColorName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		colorFromDb.get().setColorName(color.getColorName());
		colorMasterRepository.saveAndFlush(colorFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Color " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
		
	}

	@Override
	public ResponseWrapperDTO deleteColorMaster(String colorMasterId, String path) {
		Optional<ColorMaster> colorFromDb = colorMasterRepository.findById(colorMasterId);
		if (!colorFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Color not found.", null,
					HttpStatus.BAD_REQUEST, path);
		colorMasterRepository.deleteById(colorMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Color " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllColorMasterOrder(MyRequestBody body, String path) {
		List<ColorMaster> listOfColorMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<ColorMaster>>() {
				});
		listOfColorMaster.forEach(colorMaster -> {
			colorMasterRepository.saveAndFlush(colorMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Color Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
