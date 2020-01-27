package com.techhive.shivamweb.service.impl;

import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForLableName;
import com.techhive.shivamweb.master.model.DTO.UserDto;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.ResultScreenLabelsName;
import com.techhive.shivamweb.model.ResultScreenLabelsValue;
import com.techhive.shivamweb.repository.ResultScreenLabelsNameRepository;
import com.techhive.shivamweb.repository.ResultScreenLabelsValueRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ResultScreenLabelsService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Named
public class ResultScreenLabelsServiceImpl implements ResultScreenLabelsService {

	@Autowired
	private ResultScreenLabelsNameRepository resultScreenLabels;

	@Autowired
	private ResultScreenLabelsValueRepository labelsValueRepository;
	
	@Autowired
	private UserRepository  userRepository;

	public ResponseWrapperDTO getAllResultScreenLablesByUserId(String userId) {
		List<ResultScreenLabelsValue> listOfLablesValues = labelsValueRepository.findAllLabelsValueByUserId(userId);
		if (!ShivamWebMethodUtils.isListNullOrEmpty(listOfLablesValues)) {
			listOfLablesValues.forEach(labels -> {
				if (!ShivamWebMethodUtils.isObjectNullOrEmpty(labels.getResultScreenLablesName())) {
					labels.setField(labels.getResultScreenLablesName().getField());
					labels.setHeaderName(labels.getResultScreenLablesName().getHeaderName());
					labels.setWidth(labels.getResultScreenLablesName().getWidth());
					labels.setIdOfLabel(labels.getResultScreenLablesName().getId());
				}
			});
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Labels Of Result Screen.", listOfLablesValues,
				HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO saveOrderOfResultScreenLable(MyRequestBody body) {
		List<ResultScreenLabelsValue> listOfResultScreenLabelsValue = ShivamWebMethodUtils.MAPPER
				.convertValue(body.getListOfJsonObject(), new TypeReference<List<ResultScreenLabelsValue>>() {
				});
		listOfResultScreenLabelsValue.forEach(labes -> {
			labelsValueRepository.saveAndFlush(labes);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Lable Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, "", HttpStatus.OK);
	}

	public void saveResultScreenLableValueOfAllUser() {
		List<UserDto> listOfUser=userRepository.getAllUser();
		listOfUser.forEach(user->{
			saveResultScreenLableValueByUserId(user.getId());
		});

	}

	@Override
	public ResponseWrapperDTO saveResultScreenLableValueByUserId(String userId) {
		ResultScreenLabelsValue valueOfLables = new ResultScreenLabelsValue();
		/**
		 * for loop for save user wise label property of labelName
		 */
		for (EnumForLableName enumValue : EnumForLableName.values()) {
			ResultScreenLabelsName getLabelName = resultScreenLabels.findAllByField(enumValue.getField());
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(getLabelName)) {
				List<ResultScreenLabelsValue> listOfLabelsValue = labelsValueRepository
						.findAllLabelsValueByUserIdAndLabelNameId(userId, getLabelName.getId());
				if (ShivamWebMethodUtils.isListNullOrEmpty(listOfLabelsValue)) {//
					valueOfLables.setHide(enumValue.getHideColumn());
					valueOfLables.setLabelOrder(enumValue.getLabelOrder());
					valueOfLables.setLockPosition(enumValue.getIsPositionLoack());
					valueOfLables.setPinned(enumValue.getPinned());
					valueOfLables.setHeaderClass(enumValue.getHeaderClass());
					valueOfLables.setCellClass(enumValue.getCellClass());
					valueOfLables.setUserId(userId);
					valueOfLables.setResultScreenLablesName(getLabelName);
					labelsValueRepository.saveAndFlush(valueOfLables);
					valueOfLables = new ResultScreenLabelsValue();
				} else {
				}
			}
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Save Label Property.", "", HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO updateColumnnChooserLabels(MyRequestBody body, String path) {
		ResultScreenLabelsValue labelsValue = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				ResultScreenLabelsValue.class);
		labelsValueRepository.saveAndFlush(labelsValue);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Column Chooser Labels " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, "", HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllLabelsForColumnChooser(String path) {
		List<ResultScreenLabelsName> listOfLabelNames = resultScreenLabels.findAll();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Labels Of Column Chooser.", listOfLabelNames,
				HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllResultScreenLables(String path) {
		List<ResultScreenLabelsName> listOfLabelName= resultScreenLabels.findAllLabelNameByHeaderNameAndField();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Labels Of Column Chooser.", listOfLabelName,
				HttpStatus.OK, path);
	}
}
