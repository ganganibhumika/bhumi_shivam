package com.techhive.shivamweb.service.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.techhive.shivamweb.master.model.ThirdPartyDiscountMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ThirdPartyDiscountMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ThirdPartyDiscountMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Named
public class ThirdPartyDiscountMasterServiceImpl implements ThirdPartyDiscountMasterService {

	@Autowired
	ThirdPartyDiscountMasterRepository thirdPartyDiscountMasterRepository;

	@Override
	public ResponseWrapperDTO saveThirdPartyDiscount(MyRequestBody body, String path) {
		ThirdPartyDiscountMaster thirdPartyDiscountMaster = ShivamWebMethodUtils.MAPPER
				.convertValue(body.getJsonOfObject(), ThirdPartyDiscountMaster.class);
		if(ShivamWebMethodUtils.isObjectisNullOrEmpty(thirdPartyDiscountMaster.getIdOfParty())) {
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);	
		}
		StringBuilder alredyExist = new StringBuilder();
		List<ThirdPartyDiscountMaster> listOfThirdPartyDiscountMaster = thirdPartyDiscountMasterRepository
				.findAllByPartyMasterAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive(
						thirdPartyDiscountMaster.getPartyMaster(), thirdPartyDiscountMaster.getDiscount(), thirdPartyDiscountMaster.getToCarat(),
						thirdPartyDiscountMaster.getFromCarat(), thirdPartyDiscountMaster.getToDays(), thirdPartyDiscountMaster.getFromDays(),
						thirdPartyDiscountMaster.getIsFancy(), thirdPartyDiscountMaster.getIsActive());
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfThirdPartyDiscountMaster)) {
			alredyExist.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(listOfThirdPartyDiscountMaster.get(0).getPartyMaster())?listOfThirdPartyDiscountMaster.get(0).getPartyMaster().getPartyname():" User");
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_BAD_REQUEST, "Discount already exist for "
							+ alredyExist + " with same criteria.",
					null, HttpStatus.BAD_REQUEST, path);
		}
		thirdPartyDiscountMasterRepository.saveAndFlush(thirdPartyDiscountMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Third Party Discount " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateThirdPartyDiscount(MyRequestBody body, String path) {
		ThirdPartyDiscountMaster thirdPartyDiscountMaster = ShivamWebMethodUtils.MAPPER
				.convertValue(body.getJsonOfObject(), ThirdPartyDiscountMaster.class);
		if(ShivamWebMethodUtils.isObjectisNullOrEmpty(thirdPartyDiscountMaster.getIdOfParty())) {
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);	
		}
		StringBuilder alredyExist = new StringBuilder();
		List<ThirdPartyDiscountMaster> listOfThirdPartyDiscountMaster = thirdPartyDiscountMasterRepository
				.findAllByPartyMasterAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive(
						thirdPartyDiscountMaster.getPartyMaster(), thirdPartyDiscountMaster.getDiscount(), thirdPartyDiscountMaster.getToCarat(),
						thirdPartyDiscountMaster.getFromCarat(), thirdPartyDiscountMaster.getToDays(), thirdPartyDiscountMaster.getFromDays(),
						thirdPartyDiscountMaster.getIsFancy(), thirdPartyDiscountMaster.getIsActive());
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfThirdPartyDiscountMaster)) {
			if(!listOfThirdPartyDiscountMaster.get(0).getId().equalsIgnoreCase(thirdPartyDiscountMaster.getId())) {
			alredyExist.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(listOfThirdPartyDiscountMaster.get(0).getPartyMaster())?listOfThirdPartyDiscountMaster.get(0).getPartyMaster().getPartyname():"");
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_BAD_REQUEST, "Discount already exist for "
							+ alredyExist + " user with same criteria.",
					null, HttpStatus.BAD_REQUEST, path);
			}
		}
		thirdPartyDiscountMasterRepository.saveAndFlush(thirdPartyDiscountMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Third Party Discount " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteThirdPartyDiscount(String discountMasterId, String path) {
		thirdPartyDiscountMasterRepository.deleteById(discountMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Third Party Discount " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllThirdPartyDiscountDetail(Integer noOfRecords, Integer pageNumber, String sortOrder,
			String sortColumn, String path) {
		
		PageRequest pageRequest=PageRequest.of(pageNumber, noOfRecords,Sort.Direction.DESC,"createdDate");
		
//		if(!ShivamWebMethodUtils.isObjectisNullOrEmpty(sortOrder,sortColumn)) {
//			pageRequest = PageRequest.of(pageNumber, noOfRecords,
//					sortOrder.equalsIgnoreCase("A") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
//		}
		Page<ThirdPartyDiscountMaster> listOfThirdPartyDisount = thirdPartyDiscountMasterRepository.findAll(pageRequest);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all Third Party Discount master details.",
				listOfThirdPartyDisount, HttpStatus.OK, path);
	}

	public List<Double> getThirdPartyDiscountByUserId(String userId, Date giDate, Double carat, String shape,
			boolean isDefault) {
		boolean isShapeFancy = true;
		int daysBetweenForDisc = ShivamWebMethodUtils.getTotalNoOfDay(giDate);
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(shape)
				&& shape.equalsIgnoreCase(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim())) {
			isShapeFancy = false;
		}
		return thirdPartyDiscountMasterRepository.getThirdPartyDiscountByUserId(userId, carat, daysBetweenForDisc, isShapeFancy);

	}

}
