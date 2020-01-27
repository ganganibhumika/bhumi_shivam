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

import com.techhive.shivamweb.master.model.PopupManagerMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.PopupManagerMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.PopupManagerMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class PopupManagerMasterServiceImpl implements PopupManagerMasterService {

	@Autowired
	PopupManagerMasterRepository popupManagerMasterRepository;

	@Override
	public ResponseWrapperDTO savePopupManagerMaster(MyRequestBody body, String path) {
		PopupManagerMaster popupManagerMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				PopupManagerMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string(), popupManagerMaster.getImageTitle(),
				popupManagerMaster.getStartDate(), popupManagerMaster.getEndDate(),
				popupManagerMaster.getIsPopupActive()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Date start = ShivamWebMethodUtils.setTimeToZeroOfDate(popupManagerMaster.getStartDate());
		Date end = ShivamWebMethodUtils.setTimeToZeroOfDate(popupManagerMaster.getEndDate());
		if (popupManagerMaster.getIsPopupActive() == true) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(start);

			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(end);

			while (!calendar.after(endCalendar)) {
				Date result = calendar.getTime();
				List<PopupManagerMaster> popupExist = popupManagerMasterRepository.findPopupBetweenDates(result);
				if (!popupExist.isEmpty())
					return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"Active popup exist between your entered range of date.", null, HttpStatus.CONFLICT, path);
				calendar.add(Calendar.DATE, 1);
			}
		}
		popupManagerMasterRepository.saveAndFlush(popupManagerMaster);
		uplodeImage(body.getBase64string(), popupManagerMaster);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Popup " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	public void uplodeImage(String base64String, PopupManagerMaster popupManagerMaster) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					String base64Image = base64String.split(",")[1];
					byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps/ShivamImage/PopupImage");
					if (!dir.exists())
						dir.mkdirs();
					String path = dir.getAbsolutePath();
					String fileName = "imgPopup" + popupManagerMaster.getId() + ".jpg";
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + (fileName)));
					bout.write(bytes);
					bout.flush();
					bout.close();
					popupManagerMaster.setImage(fileName);
					popupManagerMasterRepository.saveAndFlush(popupManagerMaster);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
	}

	@Override
	public ResponseWrapperDTO updatePopupManagerMaster(String popupManagerMasterId, MyRequestBody body, String path) {
		Optional<PopupManagerMaster> pManagerMasterFromDb = popupManagerMasterRepository.findById(popupManagerMasterId);
		if (!pManagerMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Popup not found.", null,
					HttpStatus.BAD_REQUEST, path);
		PopupManagerMaster popupManagerMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				PopupManagerMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(popupManagerMaster.getImageTitle(),
				popupManagerMaster.getStartDate(), popupManagerMaster.getEndDate(),
				popupManagerMaster.getIsPopupActive()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Date start = ShivamWebMethodUtils.setTimeToZeroOfDate(popupManagerMaster.getStartDate());
		Date end = ShivamWebMethodUtils.setTimeToZeroOfDate(popupManagerMaster.getEndDate());
		if (popupManagerMaster.getIsPopupActive() == true) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(start);

			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(end);

			while (!calendar.after(endCalendar)) {
				Date result = calendar.getTime();
				List<PopupManagerMaster> popupExist = popupManagerMasterRepository
						.findPopupBetweenDatesAndNotIdIn(result, popupManagerMasterId);
				if (!popupExist.isEmpty())
					return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"Active popup exist between your entered range of date.", null, HttpStatus.CONFLICT, path);
				calendar.add(Calendar.DATE, 1);
			}
		}
		pManagerMasterFromDb.get().setStartDate(popupManagerMaster.getStartDate());
		pManagerMasterFromDb.get().setEndDate(popupManagerMaster.getEndDate());
		pManagerMasterFromDb.get().setImageTitle(popupManagerMaster.getImageTitle());
		pManagerMasterFromDb.get().setIsPopupActive(popupManagerMaster.getIsPopupActive());
		popupManagerMasterRepository.saveAndFlush(pManagerMasterFromDb.get());
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string()))
			uplodeImage(body.getBase64string(), pManagerMasterFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Popup " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deletePopupManagerMaster(String popupManagerMasterId, String path) {
		Optional<PopupManagerMaster> pManagerMasterFromDb = popupManagerMasterRepository.findById(popupManagerMasterId);
		if (!pManagerMasterFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Popup not found.", null,
					HttpStatus.BAD_REQUEST, path);
		popupManagerMasterRepository.deleteById(popupManagerMasterId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Popup " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllPopupManagerMaster(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path) {
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(sortColumn, sortOrder)) {
			request = PageRequest.of(pageNumber, noOfRecords,
					sortOrder.equalsIgnoreCase("A") ? Sort.Direction.ASC : Sort.Direction.DESC, sortColumn);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Popup List",
				popupManagerMasterRepository.findAll(request), HttpStatus.OK);
	}

	@Override
	public PopupManagerMaster getTodaysPopup() {
		List<PopupManagerMaster> popup = popupManagerMasterRepository.findPopupBetweenDates(new Date());
		PopupManagerMaster popupDisplay = new PopupManagerMaster();
		if (popup.isEmpty())
			return popupDisplay;
		popupDisplay.setImageTitle(popup.get(0).getImageTitle());
		popupDisplay.setImage(popup.get(0).getImage());
		return popupDisplay;
	}
}
