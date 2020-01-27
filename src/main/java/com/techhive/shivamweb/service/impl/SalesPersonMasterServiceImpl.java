package com.techhive.shivamweb.service.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.SalesPersonMasterRepository;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SalesPersonMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SalesPersonMasterServiceImpl implements SalesPersonMasterService {

	@Autowired
	SalesPersonMasterRepository salesPersonMasterRepository;

	@Autowired
	SoftwareSalesPersonRepository softwareSalesPersonRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseWrapperDTO saveSalesPerson(MyRequestBody body, String path) {
		SalesPersonMaster salesPersonMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SalesPersonMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(salesPersonMaster.getIsPrimary(), salesPersonMaster.getName(),
				salesPersonMaster.getMobileNo(), salesPersonMaster.getSoftwareUserIdNew()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		if (salesPersonMaster.getIsPrimary() == true) {
			Optional<SalesPersonMaster> isPrimary = salesPersonMasterRepository
					.findByisPrimary(salesPersonMaster.getIsPrimary());
			if (isPrimary.isPresent())
				return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"Primary Sales Person " + ShivamWebVariableUtils.ALREADYEXIST, null, HttpStatus.CONFLICT, path);
		}
		Optional<SalesPersonMaster> name = salesPersonMasterRepository.findByname(salesPersonMaster.getName());
		if (name.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Sales Person with Name '"
					+ salesPersonMaster.getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Optional<SalesPersonMaster> mobileNo = salesPersonMasterRepository
				.findBymobileNo(salesPersonMaster.getMobileNo());
		if (mobileNo.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Sales Person with Mobile no '"
					+ salesPersonMaster.getMobileNo() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		Optional<SalesPersonMaster> softwareIdNew = salesPersonMasterRepository
				.findBysoftwareUserIdNew(salesPersonMaster.getSoftwareUserIdNew());

		if (softwareIdNew.isPresent()) {
			Optional<SoftwareSalePersonMaster> softwareSalePersonMaster = softwareSalesPersonRepository
					.findById(salesPersonMaster.getSoftwareUserIdNew());
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Sales Person with Software user Id '"
							+ softwareSalePersonMaster.get().getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		}

		salesPersonMasterRepository.saveAndFlush(salesPersonMaster);

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Sales Person " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateSalesPerson(String id, MyRequestBody body, String path) {
		Optional<SalesPersonMaster> salesPersonMasterFromDb = salesPersonMasterRepository.findById(id);
		if (!salesPersonMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Sales Person not found.", null,
					HttpStatus.BAD_REQUEST, path);
		SalesPersonMaster salesPersonMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				SalesPersonMaster.class);
		
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(salesPersonMaster.getIsPrimary(), salesPersonMaster.getName(),
				salesPersonMaster.getMobileNo(), salesPersonMaster.getSoftwareUserIdNew()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		if (salesPersonMaster.getIsPrimary() == true) {
			Optional<SalesPersonMaster> isPrimary = salesPersonMasterRepository
					.findByisPrimaryAndIdNotIn(salesPersonMaster.getIsPrimary(), id);
			if (isPrimary.isPresent())
				return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"Primary Sales Person " + ShivamWebVariableUtils.ALREADYEXIST, null, HttpStatus.CONFLICT, path);
		}
		Optional<SalesPersonMaster> name = salesPersonMasterRepository.findByname(salesPersonMaster.getName());
		if (name.isPresent() && !salesPersonMaster.getName().equals(salesPersonMasterFromDb.get().getName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Sales Person with Name '"
					+ salesPersonMaster.getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Optional<SalesPersonMaster> mobileNo = salesPersonMasterRepository
				.findBymobileNo(salesPersonMaster.getMobileNo());
		if (mobileNo.isPresent()
				&& !salesPersonMaster.getMobileNo().equals(salesPersonMasterFromDb.get().getMobileNo()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT, "Sales Person with Mobile no '"
					+ salesPersonMaster.getMobileNo() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		Optional<SalesPersonMaster> softwareIdNew = salesPersonMasterRepository
				.findBysoftwareUserIdNew(salesPersonMaster.getSoftwareUserIdNew());

		if (softwareIdNew.isPresent() && softwareIdNew.get().getId() != salesPersonMasterFromDb.get().getId() ) {
			Optional<SoftwareSalePersonMaster> softwareSalePersonMaster = softwareSalesPersonRepository
					.findById(salesPersonMaster.getSoftwareUserIdNew());
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Sales Person with Software user Id '"
							+ softwareSalePersonMaster.get().getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		}

		salesPersonMasterFromDb.get().setName(salesPersonMaster.getName());
		salesPersonMasterFromDb.get().setMobileNo(salesPersonMaster.getMobileNo());
		salesPersonMasterFromDb.get().setSoftwareUserIdNew(salesPersonMaster.getSoftwareUserIdNew());
		salesPersonMasterFromDb.get().setEmail(salesPersonMaster.getEmail());
		salesPersonMasterFromDb.get().setSkype(salesPersonMaster.getSkype());
		salesPersonMasterFromDb.get().setqQaddress(salesPersonMaster.getqQaddress());
		salesPersonMasterFromDb.get().setIsPrimary(salesPersonMaster.getIsPrimary());
		salesPersonMasterRepository.saveAndFlush(salesPersonMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Sales Person " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteSalesPerson(String id, String path) {
		Optional<SalesPersonMaster> milkMasterFromDb = salesPersonMasterRepository.findById(id);
		if (!milkMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Sales Person not found.", null,
					HttpStatus.BAD_REQUEST, path);
		if (milkMasterFromDb.get().getIsPrimary() != null && milkMasterFromDb.get().getIsPrimary() == true)
			return new ResponseWrapperDTO(HttpServletResponse.SC_FORBIDDEN, "Primary sales Person cannot be deleted.",
					null, HttpStatus.FORBIDDEN, path);
		List<User> users = userRepository.getAllClientOfSalesPerson(id);
		salesPersonMasterRepository.deleteById(id);
		if (!ShivamWebMethodUtils.isListNullOrEmpty(users)) {
			Optional<SalesPersonMaster> defaultSalesPerson = salesPersonMasterRepository.findByisPrimary(true);
			if (defaultSalesPerson.isPresent()) {
				users.forEach(user -> {
					user.setSalesPersonMaster(defaultSalesPerson.get());
					userRepository.saveAndFlush(user);
				});
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Sales Person " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}
}
