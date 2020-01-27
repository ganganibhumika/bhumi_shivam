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

import com.techhive.shivamweb.master.model.DiscountMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.DiscountMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Named
public class DiscountMasterServiceImpl implements DiscountMasterService {

	@Autowired
	DiscountMasterRepository discountMasterRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public List<Double> getDiscountByUserId(String userId, Date giDate, Double carat, String shape, boolean isDefault) {

		boolean isShapeFancy = true;
		int daysBetweenForDisc = ShivamWebMethodUtils.getTotalNoOfDay(giDate);
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(shape)
				&& shape.equalsIgnoreCase(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim())) {
			isShapeFancy = false;
		}
		return discountMasterRepository.getDiscountByUserId(userId, carat, daysBetweenForDisc, isShapeFancy);

	}

	@Override
	public ResponseWrapperDTO saveDiscountMaster(MyRequestBody body, String path) {
		DiscountMaster discountMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				DiscountMaster.class);
		if (!discountMaster.getIsDefaultUser()) {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(discountMaster.getIdOfUser())) {
				return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
			}
		}
		StringBuilder alredyExist = new StringBuilder();
		List<DiscountMaster> listOfDiscountMaster = discountMasterRepository
				.findAllByUserAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive(
						discountMaster.getUser(), discountMaster.getDiscount(), discountMaster.getToCarat(),
						discountMaster.getFromCarat(), discountMaster.getToDays(), discountMaster.getFromDays(),
						discountMaster.getIsFancy(), discountMaster.getIsActive());

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfDiscountMaster)) {
			alredyExist.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(listOfDiscountMaster.get(0).getUser())
					? listOfDiscountMaster.get(0).getUser().getUsername()
					: ShivamWebVariableUtils.DEFAULT_USER_NAME + " user");
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
					"Discount already exist for " + alredyExist + " with same criteria.", null, HttpStatus.BAD_REQUEST,
					path);
		}
		discountMasterRepository.saveAndFlush(discountMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Discount " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, listOfDiscountMaster, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateDiscountById(MyRequestBody body, String path) {
		DiscountMaster discountMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				DiscountMaster.class);

		if (!discountMaster.getIsDefaultUser()) {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(discountMaster.getIdOfUser())) {
				return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
			}
		}
		StringBuilder alredyExist = new StringBuilder();
		List<DiscountMaster> listOfDiscountMaster = discountMasterRepository
				.findAllByUserAndDiscountAndToCaratAndFromCaratAndToDaysAndFromDaysAndIsFancyAndIsActive(
						discountMaster.getUser(), discountMaster.getDiscount(), discountMaster.getToCarat(),
						discountMaster.getFromCarat(), discountMaster.getToDays(), discountMaster.getFromDays(),
						discountMaster.getIsFancy(), discountMaster.getIsActive());

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfDiscountMaster)) {
			if (!listOfDiscountMaster.get(0).getId().equalsIgnoreCase(discountMaster.getId())) {
				alredyExist.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(listOfDiscountMaster.get(0).getUser())
						? listOfDiscountMaster.get(0).getUser().getUsername()
						: ShivamWebVariableUtils.DEFAULT_USER_NAME);
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
						"Discount already exist for " + alredyExist + " user with same criteria.", null,
						HttpStatus.BAD_REQUEST, path);
			}
		}
		discountMasterRepository.saveAndFlush(discountMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Discount " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteDiscountById(String discountMasterId, String path) {
		discountMasterRepository.deleteById(discountMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Discount " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllDiscountDetail(Integer pageNumber, Integer noOfRecords, String sortOrder,
			String sortColumn, String path) {
		PageRequest requestPage = PageRequest.of(pageNumber, noOfRecords,Sort.Direction.DESC,"createdDate");
//		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(sortColumn, sortOrder)) {
//			requestPage = PageRequest.of(pageNumber, noOfRecords,
//					sortOrder.equalsIgnoreCase("A") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
//		}
		Page<DiscountMaster> listOfDiscountmaster = discountMasterRepository.findAll(requestPage);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all Discount master details.",
				listOfDiscountmaster, HttpStatus.OK, path);
	}

}
