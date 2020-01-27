package com.techhive.shivamweb.service.impl;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.techhive.shivamweb.enums.EnumForMaster;
import com.techhive.shivamweb.enums.EnumForPopupFair;
import com.techhive.shivamweb.enums.EnumForTabName;
import com.techhive.shivamweb.master.model.AppVersionMaster;
import com.techhive.shivamweb.master.model.BrownShadeMaster;
import com.techhive.shivamweb.master.model.ClarityMaster;
import com.techhive.shivamweb.master.model.ColorMaster;
import com.techhive.shivamweb.master.model.CountryMaster;
import com.techhive.shivamweb.master.model.CutPolishSymmentryMaster;
import com.techhive.shivamweb.master.model.FancyColorMaster;
import com.techhive.shivamweb.master.model.FancyIntensityMaster;
import com.techhive.shivamweb.master.model.FancyOvertoneMaster;
import com.techhive.shivamweb.master.model.FluorescenceMaster;
import com.techhive.shivamweb.master.model.LabMaster;
import com.techhive.shivamweb.master.model.MilkMaster;
import com.techhive.shivamweb.master.model.PopupManagerMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.UpcomingFairMaster;
import com.techhive.shivamweb.master.model.DTO.PktMasterDto;
import com.techhive.shivamweb.repository.AppVersionMasterRepository;
import com.techhive.shivamweb.repository.BrownShadeMasterRepository;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.ClarityMasterRepository;
import com.techhive.shivamweb.repository.ColorMasterRepository;
import com.techhive.shivamweb.repository.ConfirmOrderRepository;
import com.techhive.shivamweb.repository.CountryMasterRepository;
import com.techhive.shivamweb.repository.CutPolishSymmentryMasterRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.FancyIntensityMasterRepository;
import com.techhive.shivamweb.repository.FancyOvertoneMasterRepository;
import com.techhive.shivamweb.repository.FluorescenceMasterRepository;
import com.techhive.shivamweb.repository.LabMasterRepository;
import com.techhive.shivamweb.repository.MilkMasterRepository;
import com.techhive.shivamweb.repository.OfferDiscountRequestRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.PopupManagerMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.UpcomingFairMasterRepository;
import com.techhive.shivamweb.repository.WishlistRepository;
import com.techhive.shivamweb.response.payload.MissingDetailsStone;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.response.payload.TodaysPopupFair;
import com.techhive.shivamweb.service.OtherService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Named
public class OtherServiceImpl<T> implements OtherService {

	@Autowired
	ShapeMasterRepository shapMasterRepository;

	@Autowired
	ColorMasterRepository colorMasterRepository;

	@Autowired
	FancyColorMasterRepository fancyColorRepository;

	@Autowired
	CutPolishSymmentryMasterRepository quaRepository;

	@Autowired
	FluorescenceMasterRepository fluorescenceRepository;

	@Autowired
	FancyOvertoneMasterRepository fancyOvertoneRepository;

	@Autowired
	FancyIntensityMasterRepository fancyIntensityMasterRepository;

	@Autowired
	ClarityMasterRepository clarityMasterRepository;

	@Autowired
	MilkMasterRepository milkyMasterRepository;

	@Autowired
	BrownShadeMasterRepository brownShadeRepository;

	@Autowired
	LabMasterRepository labMasterRepository;

	@Autowired
	CountryMasterRepository countryMasterRepository;

	@Autowired
	PktMasterRepository pktMasterRepository;

	@Autowired
	UpcomingFairMasterRepository upcomingFairMasterRepository;

	@Autowired
	PopupManagerMasterRepository popupManagerMasterRepository;

	@Autowired
	ConfirmOrderRepository confirmOrderRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	OfferDiscountRequestRepository offerDiscountRequestRepository;

	@Autowired
	AppVersionMasterRepository appVersionMasterRepository;

	@Override
	public ResponseWrapperDTO getAllMasterDetails() {
		Map<String, Object> mapOfMasters = new HashMap<>();

		List<ShapeMaster> listOfShaps = shapMasterRepository.findAllByOrderByShapeOrderAsc();
		mapOfMasters.put(EnumForMaster.SHAPE_MASTER.toString(), listOfShaps);

		List<ColorMaster> listOfColorMaster = colorMasterRepository.findAllByOrderByColorOrderAsc();
		mapOfMasters.put(EnumForMaster.COLOR_MASTER.toString(), listOfColorMaster);

		List<FancyColorMaster> listOfFancyColor = fancyColorRepository.findAllByOrderByFancyColorOrderAsc();
		mapOfMasters.put(EnumForMaster.FANCY_COLOR_MASTER.toString(), listOfFancyColor);

		List<CutPolishSymmentryMaster> listOfCutPolishSymmetry = quaRepository
				.findAllByOrderByCutPolishSymmentryMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.CUT_POLISH_SYMMETRY_MASTER.toString(), listOfCutPolishSymmetry);

		List<FluorescenceMaster> listOfFluorescence = fluorescenceRepository
				.findAllByOrderByFluorescenceMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.FLUORESCENCE_MASTER.toString(), listOfFluorescence);

		List<FancyOvertoneMaster> listOfFancyOvertone = fancyOvertoneRepository.findAllByOrderByFancyOvertoneOrderAsc();
		mapOfMasters.put(EnumForMaster.FANCY_COLOR_OVERTONE_MASTER.toString(), listOfFancyOvertone);

		List<FancyIntensityMaster> listOfIntensity = fancyIntensityMasterRepository
				.findAllByOrderByFancyIntensityOrderAsc();
		mapOfMasters.put(EnumForMaster.FANCY_COLOR_INTENSITY_MASTER.toString(), listOfIntensity);

		List<ClarityMaster> listOfClarityMaster = clarityMasterRepository.findAllByOrderByClarityMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.CLARITY_MASTER.toString(), listOfClarityMaster);

		List<MilkMaster> listOfMilkyMaster = milkyMasterRepository.findAllByOrderByMilkMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.MILK_MASTER.toString(), listOfMilkyMaster);

		List<BrownShadeMaster> listOfBrownShap = brownShadeRepository.findAllByOrderByBrownShadeMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.BROWN_SHADE_MASTRE.toString(), listOfBrownShap);

		List<LabMaster> listOfLab = labMasterRepository.findAllByOrderByLabMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.LAB_MASTER.toString(), listOfLab);

		List<CountryMaster> listOfCountry = countryMasterRepository.findAllByOrderByCountryMasterOrderAsc();
		mapOfMasters.put(EnumForMaster.COUNTRY_MASTER.toString(), listOfCountry);

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Master Details.", mapOfMasters,
				HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO getAllStonesForVideoImageCsv(String tabName, String path) {
		List<PktMasterDto> pktMasters = pktMasterRepository.findAllIdAndIsHold();
		MissingDetailsStone missingDetailsStone = new MissingDetailsStone();
		Set<String> totalStoneId = new HashSet<>();
		Set<String> dataUnAvailableStoneId = new HashSet<>();
		Set<String> dataAvailableStoneId = new HashSet<>();
		Set<String> soldStoneId = new HashSet<>();
		String extension = getExtensionByTabName(tabName);
		// parallelStream().
		pktMasters.forEach(pktMaster -> {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(pktMaster.getStoneId())) {
				totalStoneId.add(pktMaster.getStoneId());
				if (existsOnTomcat(ShivamWebVariableUtils.FIRST_IMAGE_LINK_FROM_PEACOCK + pktMaster.getStoneId()
						+ extension) == false) {
					dataUnAvailableStoneId.add(pktMaster.getStoneId());

				} else {
					dataAvailableStoneId.add(pktMaster.getStoneId());
					if (pktMaster.getIsHold() == true)
						soldStoneId.add(pktMaster.getStoneId());
				}
			}
		});
		missingDetailsStone.setTotalStoneId(totalStoneId);
		missingDetailsStone.setDataUnAvailableStoneId(dataUnAvailableStoneId);
		missingDetailsStone.setDataAvailableStoneId(dataAvailableStoneId);
		missingDetailsStone.setSoldStoneId(soldStoneId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All stones with missing image ,video ,csv",
				missingDetailsStone, HttpStatus.OK, path);
	}

	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			// may also need
			// HttpURLConnection.setInstanceFollowRedirects(false)
			HttpURLConnection con = (HttpURLConnection) new URL(URLName).openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean existsOnTomcat(String StoneId) {
		String rootPath = System.getProperty("catalina.home") + "\\webapps\\ShivamImage\\profilePicture\\";
		File file = new File(rootPath + StoneId);
		return file.exists();
	}

	public String getExtensionByTabName(String tabName) {
		String result = null;
		if (tabName.equals(EnumForTabName.IMAGE.toString()))
			result = ShivamWebVariableUtils.FIRST_IMAGE_EXTENTION;
		if (tabName.equals(EnumForTabName.VIDEO.toString()))
			result = ShivamWebVariableUtils.FIRST_VIDEO_EXTENTION;
		return result;
	}

	@Override
	public ResponseWrapperDTO Test(String servletPath) {
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All stones with missing image ,video ,csv",
				existsOnTomcat("imgProfile4028822567587841016758c002b5433e.jpg"), HttpStatus.OK, servletPath);
	}

	@Override
	public List<TodaysPopupFair> getTodaysUpcomingFairPopup() {
		List<TodaysPopupFair> popupFairs = new ArrayList<TodaysPopupFair>();
		List<UpcomingFairMaster> fairs = upcomingFairMasterRepository
				.findFairBetweenDates(ShivamWebMethodUtils.setTimeToZeroOfDate(new Date()));
		if (!fairs.isEmpty()) {
			popupFairs.add(new TodaysPopupFair(fairs.get(0).getImageTitle(), fairs.get(0).getImage(),
					EnumForPopupFair.FAIR.toString()));
		}
		List<PopupManagerMaster> popup = popupManagerMasterRepository
				.findPopupBetweenDates(ShivamWebMethodUtils.setTimeToZeroOfDate(new Date()));
		if (!popup.isEmpty()) {
			popupFairs.add(new TodaysPopupFair(popup.get(0).getImageTitle(), popup.get(0).getImage(),
					EnumForPopupFair.POPUP.toString()));
		}
		return popupFairs;
	}

	@Override
	public Map<String, String> getCounterForDashboard(String userId) {
		Map<String, String> map = new HashMap<>();
		map.put("confirmOrder", confirmOrderRepository.countforUser(userId).toString());
		map.put("wishlist", wishlistRepository.countforUser(userId).toString());
		map.put("cart", cartRepository.countforUser(userId).toString());
		map.put("offerDiscount", offerDiscountRequestRepository.countforUser(userId).toString());
		// set app version
		List<AppVersionMaster> appVersionMaster = appVersionMasterRepository.findAll();
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(appVersionMaster)) {
			map.put("androidVersion", appVersionMaster.get(0).getAndroidVersion());
			map.put("iosVersion", appVersionMaster.get(0).getIosVersion());
		}
		return map;
	}

}
