package com.techhive.shivamweb.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.master.model.UpcomingFairMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.UpcomingFairMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.UpcomingFairMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class UpcomingFairMasterServiceImpl implements UpcomingFairMasterService {

	@Autowired
	UpcomingFairMasterRepository upcomingFairMasterRepository;

	@Override
	public ResponseWrapperDTO saveUpcomingFairManagerMaster(MyRequestBody body, String path) {
		UpcomingFairMaster upcomingFair = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				UpcomingFairMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string(), upcomingFair.getImageTitle(),
				upcomingFair.getStartDate(), upcomingFair.getEndDate(),
				upcomingFair.getIsFairActive()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Date start = ShivamWebMethodUtils.setTimeToZeroOfDate(upcomingFair.getStartDate());
		Date end = ShivamWebMethodUtils.setTimeToZeroOfDate(upcomingFair.getEndDate());
		if (upcomingFair.getIsFairActive() == true) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(start);

			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(end);

			while (!calendar.after(endCalendar)) {
				Date result = calendar.getTime();
				List<UpcomingFairMaster> popupExist = upcomingFairMasterRepository.findFairBetweenDates(result);
				if (!popupExist.isEmpty())
					return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"Active Fair exist between your entered range of date.", null, HttpStatus.CONFLICT, path);
				calendar.add(Calendar.DATE, 1);
			}
		}
		upcomingFairMasterRepository.saveAndFlush(upcomingFair);
		uplodeImage(body.getBase64string(), upcomingFair);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fair " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	public void uplodeImage(String base64String, UpcomingFairMaster upcomingFair) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					String base64Image = base64String.split(",")[1];
					byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps/ShivamImage/FairImage");
					if (!dir.exists())
						dir.mkdirs();
					String path = dir.getAbsolutePath();
					String fileName = "imgFair" + upcomingFair.getId() + ".jpg";
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + (fileName)));
					bout.write(bytes);
					bout.flush();
					bout.close();
					upcomingFair.setImage(fileName);
					upcomingFairMasterRepository.saveAndFlush(upcomingFair);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
	}

	@Override
	public ResponseWrapperDTO updateUpcomingFairManagerMaster(String popupManagerMasterId, MyRequestBody body, String path) {
		Optional<UpcomingFairMaster> fairFromDb = upcomingFairMasterRepository.findById(popupManagerMasterId);
		if (!fairFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Popup not found.", null,
					HttpStatus.BAD_REQUEST, path);
		UpcomingFairMaster upcomingFairMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				UpcomingFairMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(upcomingFairMaster.getImageTitle(),
				upcomingFairMaster.getStartDate(), upcomingFairMaster.getEndDate(),
				upcomingFairMaster.getIsFairActive()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Date start = ShivamWebMethodUtils.setTimeToZeroOfDate(upcomingFairMaster.getStartDate());
		Date end = ShivamWebMethodUtils.setTimeToZeroOfDate(upcomingFairMaster.getEndDate());
		if (upcomingFairMaster.getIsFairActive() == true) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(start);

			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(end);

			while (!calendar.after(endCalendar)) {
				Date result = calendar.getTime();
				List<UpcomingFairMaster> popupExist = upcomingFairMasterRepository
						.findFairBetweenDatesAndNotIdIn(result, popupManagerMasterId);
				if (!popupExist.isEmpty())
					return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"Active Fair exist between your entered range of date.", null, HttpStatus.CONFLICT, path);
				calendar.add(Calendar.DATE, 1);
			}
		}
		fairFromDb.get().setStartDate(upcomingFairMaster.getStartDate());
		fairFromDb.get().setEndDate(upcomingFairMaster.getEndDate());
		fairFromDb.get().setImageTitle(upcomingFairMaster.getImageTitle());
		fairFromDb.get().setIsFairActive(upcomingFairMaster.getIsFairActive());
		upcomingFairMasterRepository.saveAndFlush(fairFromDb.get());
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string()))
			uplodeImage(body.getBase64string(), fairFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fair " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteUpcomingFairManagerMaster(String fairId, String path) {
		Optional<UpcomingFairMaster> pManagerMasterFromDb = upcomingFairMasterRepository.findById(fairId);
		if (!pManagerMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Fair not found.", null,
					HttpStatus.BAD_REQUEST, path);
		upcomingFairMasterRepository.deleteById(fairId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fair " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllUpcomingFairManagerMaster(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path) {
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(sortColumn, sortOrder)) {
			request = PageRequest.of(pageNumber, noOfRecords,
					sortOrder.equalsIgnoreCase("A") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Fair List",
				upcomingFairMasterRepository.findAll(request), HttpStatus.OK);
	}

	@Override
	public UpcomingFairMaster getTodaysUpcomingFair() {
		List<UpcomingFairMaster> fairs = upcomingFairMasterRepository.findFairBetweenDates(new Date());
		UpcomingFairMaster fairDisplay = new UpcomingFairMaster();
		if (fairs.isEmpty())
			return fairDisplay;
		fairDisplay.setImageTitle(fairs.get(0).getImageTitle());
		fairDisplay.setImage(fairs.get(0).getImage());
		return fairDisplay;
	}
}
