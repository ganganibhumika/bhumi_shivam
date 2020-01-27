package com.techhive.shivamweb.service.impl;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.techhive.shivamweb.enums.EnumForLableName;
import com.techhive.shivamweb.master.model.AppVersionMaster;
import com.techhive.shivamweb.master.model.BrownShadeMaster;
import com.techhive.shivamweb.master.model.ClarityMaster;
import com.techhive.shivamweb.master.model.ColorMaster;
import com.techhive.shivamweb.master.model.CountryMaster;
import com.techhive.shivamweb.master.model.CutPolishSymmentryMaster;
import com.techhive.shivamweb.master.model.DiscountMaster;
import com.techhive.shivamweb.master.model.FancyColorMaster;
import com.techhive.shivamweb.master.model.FancyIntensityMaster;
import com.techhive.shivamweb.master.model.FancyOvertoneMaster;
import com.techhive.shivamweb.master.model.FluorescenceMaster;
import com.techhive.shivamweb.master.model.LabMaster;
import com.techhive.shivamweb.master.model.MilkMaster;
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.UserMigration;
import com.techhive.shivamweb.model.ResultScreenLabelsName;
import com.techhive.shivamweb.repository.AppVersionMasterRepository;
import com.techhive.shivamweb.repository.BrownShadeMasterRepository;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.ClarityMasterRepository;
import com.techhive.shivamweb.repository.ColorMasterRepository;
import com.techhive.shivamweb.repository.ConfirmOrderRepository;
import com.techhive.shivamweb.repository.CountryMasterRepository;
import com.techhive.shivamweb.repository.CreateDemandRepository;
import com.techhive.shivamweb.repository.CutPolishSymmentryMasterRepository;
import com.techhive.shivamweb.repository.DiscountMasterRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.FancyIntensityMasterRepository;
import com.techhive.shivamweb.repository.FancyOvertoneMasterRepository;
import com.techhive.shivamweb.repository.FluorescenceMasterRepository;
import com.techhive.shivamweb.repository.LabMasterRepository;
import com.techhive.shivamweb.repository.MilkMasterRepository;
import com.techhive.shivamweb.repository.NewArrivalSettingsRepository;
import com.techhive.shivamweb.repository.NotificationRepository;
import com.techhive.shivamweb.repository.OfferDiscountRequestRepository;
import com.techhive.shivamweb.repository.PartyMastertRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ResultScreenLabelsNameRepository;
import com.techhive.shivamweb.repository.ResultScreenLabelsValueRepository;
import com.techhive.shivamweb.repository.SalesPersonMasterRepository;
import com.techhive.shivamweb.repository.SaveSearchCriteriaRepository;
import com.techhive.shivamweb.repository.SearchHistoryRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.SoftwarePartyMasterRespository;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.repository.UserMigrationRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.repository.UserTrackingRepository;
import com.techhive.shivamweb.repository.ViewRequestRepository;
import com.techhive.shivamweb.repository.WishlistRepository;
import com.techhive.shivamweb.request.payload.PasswordEncryption;
//gitlab.techhive.co.in/root/shivam-java.git
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.ResetAllService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@Named
public class ResetAllServiceImpl implements ResetAllService {

	@Autowired
	ResultScreenLabelsNameRepository resultScreenLabelNameRepository;

	@Autowired
	private BrownShadeMasterRepository brownShadeMasterRepository;

	@Autowired
	private ClarityMasterRepository clarityMasterRepository;

	@Autowired
	private ColorMasterRepository colorMasterRepository;

	@Autowired
	private CutPolishSymmentryMasterRepository cutPolishSymmentryMasterRepository;

	@Autowired
	private DiscountMasterRepository discountMasterRepository;

	@Autowired
	private FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	private FancyIntensityMasterRepository fancyIntensityMasterRepository;

	@Autowired
	private FancyOvertoneMasterRepository fancyOvertoneMasterRepository;

	@Autowired
	private FluorescenceMasterRepository fluorescenceMasterRepository;

	@Autowired
	private LabMasterRepository labMasterRepository;

	@Autowired
	private MilkMasterRepository milkMasterRepository;

	@Autowired
	private ShapeMasterRepository shapeMasterRepository;

	@Autowired
	private SalesPersonMasterRepository salesPersonMasterRepository;

	@Autowired
	private UserTrackingRepository userTrackingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SaveSearchCriteriaRepository saveSearchCriteriaRepository;

	@Autowired
	ResultScreenLabelsValueRepository labelsValueRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ConfirmOrderRepository confirmOrderRepository;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	CountryMasterRepository countryMasterRepository;

	@Autowired
	CreateDemandRepository createDemandRepository;

	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Autowired
	NewArrivalSettingsRepository newArrivalSettingsRepository;

	@Autowired
	PartyMastertRepository partyMastertRepository;
	@Autowired
	private OfferDiscountRequestRepository offerDiscountRequestRepository;

	@Autowired
	PktMasterRepository pktMasterRepository;

	@Autowired
	private ResultScreenLabelsServiceImpl resultScreenLabelsServiceImpl;

	Map<String, Integer> mapForFieldsAndOrder;

	@Autowired
	UserMigrationRepository userMigrationRepository;

	@Autowired
	private SoftwareSalesPersonRepository softwareSalesPersonRepository;

	@Autowired
	private ViewRequestRepository viewRequestRepository;

	@Autowired
	private SoftwarePartyMasterRespository softwarePartyMasterRespository;

	@Autowired
	private AppVersionMasterRepository appVersionMasterRepository;

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public ResponseWrapperDTO resetAll(String path) throws ParseException {

		removeAllEntity();

		removeLabels(); // use for set default lables

		addEntity();

		addLabels();

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset All Tables Successfully. ", null, HttpStatus.OK,
				path);
	}

	public void removeAllEntity() {

		// resultScreenLabelNameRepository.deleteAll();

		brownShadeMasterRepository.deleteAll();

		clarityMasterRepository.deleteAll();

		countryMasterRepository.deleteAll();

		colorMasterRepository.deleteAll();

		cutPolishSymmentryMasterRepository.deleteAll();

		discountMasterRepository.deleteAll();

		fancyColorMasterRepository.deleteAll();

		fancyIntensityMasterRepository.deleteAll();

		fancyOvertoneMasterRepository.deleteAll();

		fluorescenceMasterRepository.deleteAll();

		labMasterRepository.deleteAll();

		milkMasterRepository.deleteAll();

		shapeMasterRepository.deleteAll();

		salesPersonMasterRepository.deleteAll();

		userTrackingRepository.deleteAll();

		confirmOrderRepository.deleteAll();

		offerDiscountRequestRepository.deleteAll();

		cartRepository.deleteAll();

		wishlistRepository.deleteAll();

		saveSearchCriteriaRepository.deleteAll();

		createDemandRepository.deleteAll();

		searchHistoryRepository.deleteAll();

		viewRequestRepository.deleteAll();

		userRepository.deleteAll();

		labelsValueRepository.deleteAll();

		resultScreenLabelNameRepository.deleteAll();

		newArrivalSettingsRepository.deleteAll();

		partyMastertRepository.deleteAll();
	
		softwareSalesPersonRepository.deleteAll();

		softwarePartyMasterRespository.deleteAll();

		appVersionMasterRepository.deleteAll();

		notificationRepository.deleteAll();

	}

	public ResponseWrapperDTO addEntity() throws ParseException {

		/***
		 * @author bhumi
		 * 
		 */
		AppVersionMaster appVersionMaster = new AppVersionMaster(null, "1.1", "1.1");
		appVersionMasterRepository.saveAndFlush(appVersionMaster);

		/***
		 * @author neel
		 * 
		 */
		SoftwarePartyMaster softwarePartyMaster1 = new SoftwarePartyMaster("p101", "G101", "P1", "p1 comp", "p1 street",
				"4455566632", "02112356544", "p1@gmail.com", "india", "surat", "B1", "hi.", "111111111");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster1);

		SoftwarePartyMaster softwarePartyMaster2 = new SoftwarePartyMaster("p102", "G102", "P2", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster2);

		SoftwarePartyMaster softwarePartyMaster3 = new SoftwarePartyMaster("p103", "G101", "P3", "p1 comp", "p1 street",
				"4455566632", "02112356544", "p1@gmail.com", "india", "surat", "B1", "hi.", "111111111");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster3);

		SoftwarePartyMaster softwarePartyMaster4 = new SoftwarePartyMaster("p104", "G102", "P4", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster4);

		SoftwarePartyMaster softwarePartyMaster5 = new SoftwarePartyMaster("p105", "G103", "P5", "p1 comp", "p1 street",
				"4455566632", "02112356544", "p1@gmail.com", "india", "surat", "B1", "hi.", "111111111");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster5);

		SoftwarePartyMaster softwarePartyMaster6 = new SoftwarePartyMaster("p106", "G104", "P6", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster6);

		SoftwarePartyMaster softwarePartyMaster7 = new SoftwarePartyMaster("p107", "G105", "P7", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster7);

		SoftwarePartyMaster softwarePartyMaster8 = new SoftwarePartyMaster("p108", "G102", "P8", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster8);

		SoftwarePartyMaster softwarePartyMaster9 = new SoftwarePartyMaster("p109", "G102", "P9", "p2 comp", "p2 street",
				"4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster9);

		SoftwarePartyMaster softwarePartyMaster10 = new SoftwarePartyMaster("p110", "G102", "P10", "p2 comp",
				"p2 street", "4455566631", "02112356541", "p2@gmail.com", "india", "surat", "B2", "hi.", "111111112");
		softwarePartyMasterRespository.saveAndFlush(softwarePartyMaster10);

		/**
		 * @author bhumi
		 */
		SoftwareSalePersonMaster softwareSalePersonMaster = getsoftwareSalePersonMaster("softwareUser1");
		softwareSalesPersonRepository.saveAndFlush(softwareSalePersonMaster);

		SoftwareSalePersonMaster softwareSalePersonMaster2 = getsoftwareSalePersonMaster("softwareUser2");
		softwareSalesPersonRepository.saveAndFlush(softwareSalePersonMaster2);

		/**
		 * @author neel
		 */

		SalesPersonMaster salesPersonMaster = getSalesPerson("sp one", "3333333333", "sp1@gmail.com", "adasdEQWEsd",
				"S101", "asdad", true, true);
		salesPersonMaster.setSoftwareSalePersonMaster(softwareSalePersonMaster);

		salesPersonMasterRepository.saveAndFlush(salesPersonMaster);
		SalesPersonMaster salesPersonMaster2 = getSalesPerson("sp two", "4444444444", "sp2@gmail.com", "sfsdsdf",
				"S102", "sgd", true, false);

		salesPersonMaster.setSoftwareSalePersonMaster(softwareSalePersonMaster2);
		salesPersonMasterRepository.saveAndFlush(salesPersonMaster2);
		/**
		 * @author neel
		 */
		User admin = getUserDate("admin", "Test@123", "admin@gmail.com", true, "admin", "patel", "1111111111", "Male");
		admin.setIsSuperAdmin(true);
		admin.setSoftwarePartyMaster(softwarePartyMaster1);
		admin.setSoftwarePartyMaster(softwarePartyMaster1);
		userRepository.saveAndFlush(admin);

		User user = getUserDate("user", "Test@123", "user@gmail.com", false, "user", "patel", "2222222222", "Male");
		user.setSalesPersonMaster(salesPersonMaster2);
		user.setSoftwarePartyMaster(softwarePartyMaster2);
		user.setIsSuperAdmin(false);
		userRepository.saveAndFlush(user);

		/**
		 * @author Bhumi
		 * @param Add
		 *            to brownShade master;
		 * 
		 */
		int cnt = 0;
		BrownShadeMaster BrownShadeMaster = new BrownShadeMaster("NONE", cnt + 1, "NON");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster);

		BrownShadeMaster BrownShadeMaster1 = new BrownShadeMaster("FNT BROWN", cnt + 1, "BR1");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster1);

		BrownShadeMaster BrownShadeMaster2 = new BrownShadeMaster("MED BROWN", cnt + 1, "BR2");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster2);

		BrownShadeMaster BrownShadeMaster3 = new BrownShadeMaster("STG BROWN", cnt + 1, "BR3");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster3);

		BrownShadeMaster BrownShadeMaster4 = new BrownShadeMaster("VST BROWN", cnt + 1, "BR4");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster4);

		BrownShadeMaster BrownShadeMaster5 = new BrownShadeMaster("FNT GREEN", cnt + 1, "GR1");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster5);

		BrownShadeMaster BrownShadeMaster6 = new BrownShadeMaster("MED GREEN", cnt + 1, "GR2");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster6);

		BrownShadeMaster BrownShadeMaster7 = new BrownShadeMaster("STG GREEN", cnt + 1, "GR3");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster7);

		BrownShadeMaster BrownShadeMaster8 = new BrownShadeMaster("VST GREEN", cnt + 1, "GR4");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster8);

		BrownShadeMaster BrownShadeMaster9 = new BrownShadeMaster("FNT BLUE", cnt + 1, "BL1");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster9);

		BrownShadeMaster BrownShadeMaster10 = new BrownShadeMaster("MED BLUE", cnt + 1, "BL2");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster10);

		BrownShadeMaster BrownShadeMaster11 = new BrownShadeMaster("STG BLUE", cnt + 1, "BL3");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster11);

		BrownShadeMaster BrownShadeMaster12 = new BrownShadeMaster("VST BLUE", cnt + 1, "BL4");
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster12);

		BrownShadeMaster BrownShadeMaster13 = new BrownShadeMaster("MIX TINGE", cnt + 1, "MT1");
		cnt++;
		brownShadeMasterRepository.saveAndFlush(BrownShadeMaster13);

		/**
		 * @author Bhumi
		 * @param Add
		 *            to clarity master;
		 * 
		 */
		int clarityCnt = 0;
		ClarityMaster ClarityMaster1 = new ClarityMaster("FL", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster1);

		ClarityMaster ClarityMaster2 = new ClarityMaster("IF", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster2);

		ClarityMaster ClarityMaster3 = new ClarityMaster("VVS1", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster3);

		ClarityMaster ClarityMaster4 = new ClarityMaster("VVS2", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster4);

		ClarityMaster ClarityMaster5 = new ClarityMaster("VS1", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster5);

		ClarityMaster ClarityMaster6 = new ClarityMaster("VS2", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster6);

		ClarityMaster ClarityMaster7 = new ClarityMaster("SI1", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster7);

		ClarityMaster ClarityMaster8 = new ClarityMaster("SI2", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster8);

		ClarityMaster ClarityMaster9 = new ClarityMaster("SI3", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster9);

		ClarityMaster ClarityMaster10 = new ClarityMaster("I1", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster10);

		ClarityMaster ClarityMaster11 = new ClarityMaster("I2", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster11);

		ClarityMaster ClarityMaster12 = new ClarityMaster("I3", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster12);

		ClarityMaster ClarityMaster13 = new ClarityMaster("I4", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster13);

		ClarityMaster ClarityMaster14 = new ClarityMaster("I5", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster14);

		ClarityMaster ClarityMaster15 = new ClarityMaster("I6", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster15);

		ClarityMaster ClarityMaster16 = new ClarityMaster("I7", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster16);

		ClarityMaster ClarityMaster17 = new ClarityMaster("I8", clarityCnt + 1);
		clarityCnt++;
		clarityMasterRepository.saveAndFlush(ClarityMaster17);

		int colorMaster = 0;

		ColorMaster colorMaster1 = new ColorMaster("D", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster1);

		ColorMaster colorMaster2 = new ColorMaster("E", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster2);

		ColorMaster colorMaster3 = new ColorMaster("F", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster3);

		ColorMaster colorMaster4 = new ColorMaster("G", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster4);

		ColorMaster colorMaster5 = new ColorMaster("H", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster5);

		ColorMaster colorMaster6 = new ColorMaster("I", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster6);

		ColorMaster colorMaster7 = new ColorMaster("J", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster7);

		ColorMaster colorMaster8 = new ColorMaster("K", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster8);

		ColorMaster colorMaster9 = new ColorMaster("L", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster9);

		ColorMaster colorMaster10 = new ColorMaster("M", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster10);

		ColorMaster colorMaster11 = new ColorMaster("N", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster11);

		ColorMaster colorMaster12 = new ColorMaster("O-P", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster12);

		ColorMaster colorMaster13 = new ColorMaster("S-T", colorMaster + 1);
		colorMaster++;
		colorMasterRepository.saveAndFlush(colorMaster13);

		int cutCnt = 0;
		CutPolishSymmentryMaster cutPolishSymmentryMaster1 = new CutPolishSymmentryMaster("EX", cutCnt + 1);
		cutCnt++;
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster1);

		CutPolishSymmentryMaster cutPolishSymmentryMaster2 = new CutPolishSymmentryMaster("VG", cutCnt + 1);
		cutCnt++;
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster2);

		CutPolishSymmentryMaster cutPolishSymmentryMaster3 = new CutPolishSymmentryMaster("G", cutCnt + 1);
		cutCnt++;
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster3);

		CutPolishSymmentryMaster cutPolishSymmentryMaster4 = new CutPolishSymmentryMaster("F", cutCnt + 1);
		cutCnt++;
		cutPolishSymmentryMasterRepository.saveAndFlush(cutPolishSymmentryMaster4);

		DiscountMaster discountMaster = getDiscount(20.0, 0.8, 0.1, 10, 1, true, true, 1);
		discountMaster.setUser(user);
		discountMaster.setIdOfUser(user.getId());
		discountMasterRepository.saveAndFlush(discountMaster);

		DiscountMaster discountMaster2 = getDiscount(10.0, 0.8, 0.1, 20, 11, false, true, 1);
		discountMaster2.setUser(user);
		discountMaster2.setIdOfUser(user.getId());
		discountMasterRepository.saveAndFlush(discountMaster2);

		int fancyColor = 0;
		FancyColorMaster fancyColorMaster = new FancyColorMaster("brown", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster);
		fancyColor++;
		FancyColorMaster fancyColorMaster2 = new FancyColorMaster("brown-pink", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster2);
		fancyColor++;
		FancyColorMaster fancyColorMaster3 = new FancyColorMaster("brown-yellow", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster3);
		fancyColor++;
		FancyColorMaster fancyColorMaster4 = new FancyColorMaster("green", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster4);
		fancyColor++;
		FancyColorMaster fancyColorMaster5 = new FancyColorMaster("gray", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster5);
		fancyColor++;
		FancyColorMaster fancyColorMaster6 = new FancyColorMaster("orange-brown", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster6);
		fancyColor++;
		FancyColorMaster fancyColorMaster7 = new FancyColorMaster("orange-yellow", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster7);
		fancyColor++;
		FancyColorMaster fancyColorMaster8 = new FancyColorMaster("pink", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster8);
		fancyColor++;
		FancyColorMaster fancyColorMaster9 = new FancyColorMaster("purple-pink", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster9);
		fancyColor++;
		FancyColorMaster fancyColorMaster10 = new FancyColorMaster("pink-purple", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster10);
		fancyColor++;
		FancyColorMaster fancyColorMaster11 = new FancyColorMaster("u-v", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster11);
		fancyColor++;
		FancyColorMaster fancyColorMaster12 = new FancyColorMaster("w-x", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster12);
		fancyColor++;
		FancyColorMaster fancyColorMaster13 = new FancyColorMaster("y-z", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster13);
		fancyColor++;
		FancyColorMaster fancyColorMaster14 = new FancyColorMaster("yellow", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster14);
		fancyColor++;
		FancyColorMaster fancyColorMaster15 = new FancyColorMaster("yellow-orange", fancyColor);
		fancyColorMasterRepository.saveAndFlush(fancyColorMaster15);

		int fancyIntensityOrder = 0;
		FancyIntensityMaster fancyIntensityMaster = new FancyIntensityMaster("Fancy", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster2 = new FancyIntensityMaster("Fancy Dark", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster2);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster3 = new FancyIntensityMaster("Fancy Deep", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster3);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster4 = new FancyIntensityMaster("Fancy Intense", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster4);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster5 = new FancyIntensityMaster("Fancy Light", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster5);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster6 = new FancyIntensityMaster("Fancy Vivid", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster6);
		fancyIntensityOrder++;
		FancyIntensityMaster fancyIntensityMaster7 = new FancyIntensityMaster("Light", fancyIntensityOrder);
		fancyIntensityMasterRepository.saveAndFlush(fancyIntensityMaster7);

		int fancyOvertoneOrder = 0;
		FancyOvertoneMaster fancyOvertoneMaster = new FancyOvertoneMaster("none", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster1 = new FancyOvertoneMaster("brownish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster1);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster2 = new FancyOvertoneMaster("brownish-greenish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster2);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster3 = new FancyOvertoneMaster("brownish-orangy", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster3);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster4 = new FancyOvertoneMaster("grayish-yellowish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster4);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster5 = new FancyOvertoneMaster("greenish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster5);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster6 = new FancyOvertoneMaster("orangy", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster6);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster7 = new FancyOvertoneMaster("pinkish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster7);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster8 = new FancyOvertoneMaster("purplis", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster8);
		fancyOvertoneOrder++;
		FancyOvertoneMaster fancyOvertoneMaster9 = new FancyOvertoneMaster("yellowish", fancyOvertoneOrder);
		fancyOvertoneMasterRepository.saveAndFlush(fancyOvertoneMaster9);

		Integer fluorescenceOrder = 0;
		FluorescenceMaster fluorescenceMaster = new FluorescenceMaster("NON", "NON", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster1 = new FluorescenceMaster("VSL", "VSL", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster1);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster2 = new FluorescenceMaster("SL", "SL", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster2);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster3 = new FluorescenceMaster("FNT", "FNT", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster3);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster4 = new FluorescenceMaster("MED", "MED", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster4);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster5 = new FluorescenceMaster("STG", "STG", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster5);
		fluorescenceOrder++;
		FluorescenceMaster fluorescenceMaster6 = new FluorescenceMaster("VST", "VST", fluorescenceOrder);
		fluorescenceMasterRepository.saveAndFlush(fluorescenceMaster6);

		Integer labOrd = 0;
		LabMaster labMaster = new LabMaster("GIA", labOrd);
		labMasterRepository.saveAndFlush(labMaster);
		labOrd++;
		LabMaster labMaster2 = new LabMaster("IGI", labOrd);
		labMasterRepository.saveAndFlush(labMaster2);
		labOrd++;
		LabMaster labMaster3 = new LabMaster("HRD", labOrd);
		labMasterRepository.saveAndFlush(labMaster3);

		Integer milkMasterOrder = 0;
		MilkMaster milkMaster = new MilkMaster("NON", "NON", milkMasterOrder);
		milkMasterRepository.saveAndFlush(milkMaster);
		milkMasterOrder++;
		MilkMaster milkMaster1 = new MilkMaster("ML1", "ML1", milkMasterOrder);
		milkMasterRepository.saveAndFlush(milkMaster1);
		milkMasterOrder++;
		MilkMaster milkMaster2 = new MilkMaster("ML2", "ML2", milkMasterOrder);
		milkMasterRepository.saveAndFlush(milkMaster2);
		milkMasterOrder++;
		MilkMaster milkMaster3 = new MilkMaster("ML3", "ML3", milkMasterOrder);
		milkMasterRepository.saveAndFlush(milkMaster3);
		milkMasterOrder++;
		MilkMaster milkMaster4 = new MilkMaster("ML4", "ML4", milkMasterOrder);
		milkMasterRepository.saveAndFlush(milkMaster4);

		Integer shapeOrd = 0;
		ShapeMaster shapeMaster = new ShapeMaster("ROUND", "R", shapeOrd, "ROUND.png");
		shapeMasterRepository.saveAndFlush(shapeMaster);
		shapeOrd++;
		ShapeMaster shapeMaster1 = new ShapeMaster("MARQUISE", "M", shapeOrd, "MARQUISE.png");
		shapeMasterRepository.saveAndFlush(shapeMaster1);
		shapeOrd++;
		ShapeMaster shapeMaster2 = new ShapeMaster("PEAR", "P", shapeOrd, "PEAR.png");
		shapeMasterRepository.saveAndFlush(shapeMaster2);
		shapeOrd++;
		ShapeMaster shapeMaster3 = new ShapeMaster("PRINCESS", "PRI", shapeOrd, "PRINCESS.png");
		shapeMasterRepository.saveAndFlush(shapeMaster3);
		shapeOrd++;
		ShapeMaster shapeMaster4 = new ShapeMaster("OVAL", "O", shapeOrd, "OVAL.png");
		shapeMasterRepository.saveAndFlush(shapeMaster4);
		shapeOrd++;
		ShapeMaster shapeMaster55 = new ShapeMaster("RADIANT", "CCMB", shapeOrd, "RADIANT.png");
		shapeMasterRepository.saveAndFlush(shapeMaster55);
		shapeOrd++;
		ShapeMaster shapeMaster6 = new ShapeMaster("CUSHION", "CUS", shapeOrd, "CUSHION.png");
		shapeMasterRepository.saveAndFlush(shapeMaster6);
		shapeOrd++;
		ShapeMaster shapeMaster7 = new ShapeMaster("HEART", "H", shapeOrd, "HEART.png");
		shapeMasterRepository.saveAndFlush(shapeMaster7);
		shapeOrd++;
		ShapeMaster shapeMaster8 = new ShapeMaster("EMERALD", "E", shapeOrd, "EMERALD.png");
		shapeMasterRepository.saveAndFlush(shapeMaster8);

		Integer countryMasterOrder = 0;
		CountryMaster countryMaster = new CountryMaster("MUMBAI", "MUM", countryMasterOrder);
		countryMasterRepository.saveAndFlush(countryMaster);
		countryMasterOrder++;
		CountryMaster countryMaster2 = new CountryMaster("HONGKONG", "HK", countryMasterOrder);
		countryMasterRepository.saveAndFlush(countryMaster2);
		countryMasterOrder++;
		CountryMaster countryMaster3 = new CountryMaster("EVENT", "EVENT", countryMasterOrder);
		countryMasterRepository.saveAndFlush(countryMaster3);
		countryMasterOrder++;

		NewArrivalSettings newArrivalSettings = new NewArrivalSettings(null, 10);
		newArrivalSettingsRepository.saveAndFlush(newArrivalSettings);

		PartyMaster partyMaster = new PartyMaster("Party1", passwordEncoder.encode("Test@123"), "Party1@gmail.com",
				true, true);
		partyMastertRepository.saveAndFlush(partyMaster);

		PartyMaster partyMaster2 = new PartyMaster("Party2", passwordEncoder.encode("Test@123"), "Party2@gmail.com",
				true, true);
		partyMastertRepository.saveAndFlush(partyMaster2);
		/**
		 * @author Heena
		 * @param headerName,field
		 * 
		 */
		// resetResultScreenLabel();

		// addPKTmasterData();

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset All Tables Successfully.", null, HttpStatus.OK);
	}

	private SoftwareSalePersonMaster getsoftwareSalePersonMaster(String name) {
		SoftwareSalePersonMaster softwareSalePersonMaster = new SoftwareSalePersonMaster();
		softwareSalePersonMaster.setName(name);
		return softwareSalePersonMaster;
	}

	public ResultScreenLabelsName getAllLablesName(String headerName, String fieldName, Integer width) {
		ResultScreenLabelsName labels = new ResultScreenLabelsName();
		labels.setHeaderName(headerName);
		labels.setField(fieldName);
		labels.setWidth(width);
		return labels;
	}

	@Override
	public ResponseWrapperDTO resetResultScreenLabel() {

		removeLabels();
		addLabels();

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset All Tables Successfully.", null, HttpStatus.OK);
	}

	public void removeLabels() {

		labelsValueRepository.deleteAll();

		resultScreenLabelNameRepository.deleteAll();
	}

	public void addLabels() {
		/**
		 * @author Heena
		 * @param headerName,field
		 * 
		 */

		for (EnumForLableName enumValue : EnumForLableName.values()) {

			ResultScreenLabelsName resultScreenLables = getAllLablesName(enumValue.getHeaderName(),
					enumValue.getField(), enumValue.getWidth());
			resultScreenLabelNameRepository.saveAndFlush(resultScreenLables);

		}
		resultScreenLabelsServiceImpl.saveResultScreenLableValueOfAllUser();

	}

	/**
	 * @author neel
	 */
	public User getUserDate(String username, String password, String email, Boolean isAdmin, String firstName,
			String lastName, String mobileNo, String gender) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setIsAdmin(isAdmin);
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMobileNo(mobileNo);
		user.setGender(gender);
		user.setIsDeleted(false);
		user.setIsEmailVerified(true);
		user.setIsApproved(true);
		return user;
	}

	/**
	 * @author neel
	 */
	public DiscountMaster getDiscount(Double discount, Double toCarat, Double fromCarat, Integer toDays,
			Integer fromDays, Boolean isFancy, Boolean isActive, Integer discountMasterOrder) {
		DiscountMaster discountMaster = new DiscountMaster();
		discountMaster.setDiscount(discount);
		discountMaster.setToCarat(toCarat);
		discountMaster.setFromCarat(fromCarat);
		discountMaster.setToDays(toDays);
		discountMaster.setFromDays(fromDays);
		discountMaster.setIsFancy(isFancy);
		discountMaster.setIsActive(isActive);
		discountMaster.setDiscountMasterOrder(discountMasterOrder);
		return discountMaster;
	}

	/**
	 * @author neel
	 */
	public SalesPersonMaster getSalesPerson(String name, String mobileNo, String email, String skype,
			String softwareUserId, String qQaddress, Boolean isActive, Boolean isPrimary) {
		SalesPersonMaster salesPersonMaster = new SalesPersonMaster();
		salesPersonMaster.setName(name);
		salesPersonMaster.setMobileNo(mobileNo);
		salesPersonMaster.setEmail(email);
		salesPersonMaster.setSkype(skype);
		// salesPersonMaster.setSoftwareUserId(softwareUserId);
		salesPersonMaster.setqQaddress(qQaddress);
		salesPersonMaster.setIsActive(isActive);
		salesPersonMaster.setIsPrimary(isPrimary);
		return salesPersonMaster;
	}

	/**
	 * @author neel
	 * @throws ParseException
	 */
	public void addPKTmasterData() throws ParseException {
		// addPKTmasterData();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String csvFile = "\\\\NEEL\\Users\\neel\\migration\\pkt.csv";
		String line = "";
		String cvsSplitBy = ",";
		int lineNumber = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {
				// use comma as separator
				String[] pkt = line.split(cvsSplitBy);
				if (lineNumber == 0) {
					mapForFieldsAndOrder = ShivamWebMethodUtils.getFieldsNameAndItsOrderInCSVFile(pkt);
				}
				if (lineNumber >= 1) {
					PktMaster pktMaster = new PktMaster();
					pktMaster.setStoneId(pkt[0]);// Pid or PId or use pkt[0] if null pointer error comes
					pktMaster.setSeqNo(Double.valueOf(pkt[mapForFieldsAndOrder.get("SeqNo")]));// SeqNo
					pktMaster.setCarat(Double.valueOf(pkt[mapForFieldsAndOrder.get("Carat")]));// Carat
					pktMaster.setGiDate(sdf.parse(pkt[mapForFieldsAndOrder.get("GI_Date")]));// "GI_Date"
					pktMaster.setHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("Height")]));
					pktMaster.setLength(Double.valueOf(pkt[mapForFieldsAndOrder.get("Length")]));
					pktMaster.setWidth(Double.valueOf(pkt[mapForFieldsAndOrder.get("Width")]));
					pktMaster.setCertNo(pkt[mapForFieldsAndOrder.get("CertNo")]);
					pktMaster.setCodeOfShape(pkt[mapForFieldsAndOrder.get("S_CODE")]);
					pktMaster.setCodeOfColor(pkt[mapForFieldsAndOrder.get("C_Name")]);
					pktMaster.setCodeOfClarity(pkt[mapForFieldsAndOrder.get("Q_Name")]);
					pktMaster.setCodeOfPolish(pkt[mapForFieldsAndOrder.get("POLISH_NAME")]);
					pktMaster.setCodeOfCut(pkt[mapForFieldsAndOrder.get("CUT_NAME")]);
					pktMaster.setCodeOfFluorescence(pkt[mapForFieldsAndOrder.get("FL_Name")]);
					pktMaster.setCodeOfSymmentry(pkt[mapForFieldsAndOrder.get("SYM_NAME")]);
					pktMaster.setCodeOfLuster(pkt[mapForFieldsAndOrder.get("SortName")]);
					pktMaster.setCodeOfShade(pkt[mapForFieldsAndOrder.get("BS_SORTNAME")]);
					pktMaster.setCodeOfMilky(pkt[mapForFieldsAndOrder.get("MILK_CODE")]);
					pktMaster.setEyeClean(pkt[mapForFieldsAndOrder.get("EyeClean")]);
					pktMaster.setDiameter(Double.valueOf(pkt[mapForFieldsAndOrder.get("Diameter")]));
					pktMaster.setTotDepth(Double.valueOf(pkt[mapForFieldsAndOrder.get("TotDepth")]));
					pktMaster.setTablePercentage(Double.valueOf(pkt[mapForFieldsAndOrder.get("Table1")]));
					pktMaster.setCrownAngle(Double.valueOf(pkt[mapForFieldsAndOrder.get("CAngle")]));
					pktMaster.setCrownHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("CHeight")]));
					pktMaster.setPavilionAngle(Double.valueOf(pkt[mapForFieldsAndOrder.get("PAngle")]));
					pktMaster.setPavilionHeight(Double.valueOf(pkt[mapForFieldsAndOrder.get("PHeight")]));
					pktMaster.setEfCode(pkt[mapForFieldsAndOrder.get("EF_Code")]);
					pktMaster.setDisc(Double.valueOf(pkt[mapForFieldsAndOrder.get("Disc")]));
					pktMaster.setgRate(Double.valueOf(pkt[mapForFieldsAndOrder.get("GRate")]));
					pktMaster.setgRap(Double.valueOf(pkt[mapForFieldsAndOrder.get("GRap")]));
					pktMaster.setLab(pkt[mapForFieldsAndOrder.get("CR_Name")]);
					pktMaster.setShape(pkt[mapForFieldsAndOrder.get("S_NAME")]);
					pktMaster.setTinName(pkt[mapForFieldsAndOrder.get("TIN_Name")]);
					pktMaster.setSinName(pkt[mapForFieldsAndOrder.get("SI_NAME")]);
					pktMaster.setToinName(pkt[mapForFieldsAndOrder.get("TOIN_Name")]);
					pktMaster.setTbinName(pkt[mapForFieldsAndOrder.get("TBIN_Name")]);
					pktMaster.setSoinName(pkt[mapForFieldsAndOrder.get("SOIN_Name")]);
					pktMaster.setSbinName(pkt[mapForFieldsAndOrder.get("SBIN_Name")]);
					pktMaster.setCountry(pkt[mapForFieldsAndOrder.get("Country_Name")]);
					pktMaster.setfColor(pkt[mapForFieldsAndOrder.get("fColor")]);
					pktMaster.setfIntensity(pkt[mapForFieldsAndOrder.get("fIntensity")]);
					pktMaster.setfOvertone(pkt[mapForFieldsAndOrder.get("fOvertone")]);
					pktMaster.setStrLn(pkt[mapForFieldsAndOrder.get("StrLn")]);
					pktMaster.setLrHalf(pkt[mapForFieldsAndOrder.get("LrHalf")]);
					pktMaster.setCodeOfCulet(pkt[mapForFieldsAndOrder.get("CULET_CODE")]);
					pktMaster.setGirdle(Double.valueOf(pkt[mapForFieldsAndOrder.get("Girdle")]));
					pktMaster.setIsHold(false);
					pktMaster.setIsSold(false);
					pktMaster.setIsFeatured(false);
					pktMasterRepository.saveAndFlush(pktMaster);
				}
				lineNumber++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// /**
	// * @author neel
	// * @throws ParseException
	// */
	// @Override
	// public void addUserMigration() throws ParseException {
	// // addPKTmasterData();
	// userMigrationRepository.deleteAll();
	// String csvFile = "\\\\NEEL\\Users\\neel\\migration\\userMigration2.csv";
	// String line = "";
	// String cvsSplitBy = ",";
	// int lineNumber = 0;
	// try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	// List<UserMigration> migrations = new ArrayList<>();
	// while ((line = br.readLine()) != null) {
	// // use comma as separator
	// String[] pkt = line.split(cvsSplitBy);
	// if (lineNumber == 0) {
	// mapForFieldsAndOrder =
	// ShivamWebMethodUtils.getFieldsNameAndItsOrderInCSVFile(pkt);
	// System.err.println(mapForFieldsAndOrder);
	// }
	// if (lineNumber >= 1) {
	// UserMigration userMigration = new UserMigration();
	// System.err.println(pkt[0] + "------" + pkt[1] + "--------" + pkt[2]);
	// userMigration.setUsername(pkt[0]);
	//// userMigration.setPassword(passwordEncoder.encode(decryptPassword(pkt[1])));
	// userMigration.setPassword(pkt[mapForFieldsAndOrder.get("pass")]);
	//// System.err.println(pkt[0] + ":--->" + decryptPassword(pkt[1]));
	// userMigration.setEmail(pkt[mapForFieldsAndOrder.get("email")]);
	// userMigration.setFirstName(pkt[mapForFieldsAndOrder.get("FirstName")]);
	//
	// userMigration.setLastName(pkt[mapForFieldsAndOrder.get("LastName")]);
	//
	// userMigration.setCompanyAddress(pkt[mapForFieldsAndOrder.get("Address")]);
	//
	// userMigration.setCompanyName(pkt[mapForFieldsAndOrder.get("CompanyName")]);
	//
	// userMigration.setPhoneNo(pkt[mapForFieldsAndOrder.get("TelephoneNo")]);
	//
	// userMigration.setMobileNo(pkt[mapForFieldsAndOrder.get("MobileNo")]);
	//
	// userMigration.setGender(pkt[mapForFieldsAndOrder.get("Sex")]);
	//
	// userMigration.setPinCode(pkt[mapForFieldsAndOrder.get("PinCode")]);
	//
	// userMigration.setCity(pkt[mapForFieldsAndOrder.get("City")]);
	//
	// userMigration.setState(pkt[mapForFieldsAndOrder.get("State")]);
	//
	// userMigration.setCountry(pkt[mapForFieldsAndOrder.get("Country")]);
	//
	// userMigration.setMobileCCode(pkt[mapForFieldsAndOrder.get("MobileCCode")]);
	//
	// userMigration.setTeleCCode(pkt[mapForFieldsAndOrder.get("TeleCCode")]);
	// userMigration.setTeleACode(pkt[mapForFieldsAndOrder.get("TeleACode")]);
	//
	// userMigration.setPrefix(pkt[mapForFieldsAndOrder.get("Prefix")]);
	//
	// migrations.add(userMigration);
	// }
	// lineNumber++;
	// }
	// UserMigration userMigration = new UserMigration();
	// userMigration.setUsername("diamkim");
	// userMigration.setPassword(passwordEncoder.encode(decryptPassword("Gyi089kKgtVQDEAiPAXQtQ==")));
	// userMigration.setEmail("diamkim2@hotmail.com");
	// migrations.add(userMigration);
	// userMigrationRepository.saveAll(migrations);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * @author neel
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void addUserMigration() throws ParseException {


//		String csvFile = "\\\\NEEL\\Users\\neel\\migration\\userMigration2.csv";

		userMigrationRepository.deleteAll();
		String csvFile = "\\\\NEEL\\Users\\neel\\migration\\userMigration3.csv";

		String line = "";
		String cvsSplitBy = ",";
		int lineNumber = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			List<UserMigration> migrations = new ArrayList<>();

			CSVReader csvReader = new CSVReader(br, ',', '"', 0);
			HeaderColumnNameTranslateMappingStrategy<UserMigration> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
			strategy.setType(UserMigration.class);

			Map<String, String> columnMapping = new HashMap<>();
			// key = csvName, value = modelName
			columnMapping.put("userId", "username");
			columnMapping.put("sex", "gender");
			columnMapping.put("pass", "Password");
			columnMapping.put("email", "Email");
			columnMapping.put("FirstName", "FirstName");
			columnMapping.put("LastName", "LastName");
			columnMapping.put("Address", "companyAddress");
			columnMapping.put("CompanyName", "CompanyName");
			columnMapping.put("phoneNo", "phoneNo");
			columnMapping.put("MobileNo", "MobileNo");
			columnMapping.put("PinCode", "PinCode");
			columnMapping.put("City", "City");
			columnMapping.put("State", "State");
			columnMapping.put("Country", "Country");
			columnMapping.put("MobileCCode", "MobileCCode");
			columnMapping.put("TeleCCode", "TeleCCode");
			columnMapping.put("TeleACode", "TeleACode");
			columnMapping.put("Prefix", "Prefix");
			strategy.setColumnMapping(columnMapping);

			// CsvToBean<UserMigration> ctb = new CsvToBean<>();
			CsvToBean ctb = new CsvToBean() {
				@Override
				protected Object convertValue(String value, PropertyDescriptor prop)
						throws InstantiationException, IllegalAccessException {
					// System.err.println(value);
					if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(value) && value.equalsIgnoreCase("null")) {
						value = null;
					}

					return super.convertValue(value, prop);
				}
			};

			migrations = ctb.parse(strategy, csvReader);

			migrations.forEach(a -> {
				a.setPassword(passwordEncoder.encode(decryptPassword(a.getPassword())));
			});
			UserMigration userMigration = new UserMigration();
			userMigration.setUsername("diamkim");
			userMigration.setPassword(passwordEncoder.encode(decryptPassword("Gyi089kKgtVQDEAiPAXQtQ==")));
			userMigration.setEmail("diamkim2@hotmail.com");
			userMigration.setFirstName("young");
			userMigration.setLastName("kim");
			userMigration.setCompanyAddress("#110 sam sam bldg, don wha mun ro 10-20.  jong ro gu");
			userMigration.setCompanyName("Euro diamonds co.ltd");
			userMigration.setPhoneNo("1488");
			userMigration.setMobileNo("97983367");
			userMigration.setGender("M");
			userMigration.setPinCode("110123");
			userMigration.setCity("seoul");
			userMigration.setState("seoul");
			userMigration.setCountry("South Korea");
			userMigration.setMobileCCode("8210");
			userMigration.setTeleCCode("822");
			userMigration.setTeleACode("743");
			userMigration.setPrefix("");
			migrations.add(userMigration);
			userMigrationRepository.saveAll(migrations);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String decryptPassword(String encryptrdPass) {

		final String url = "http://shivamjewels.in/shivam_app.asmx/APP_Decrypt";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("password", encryptrdPass);
		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<MultiValueMap<String, String>> request1 = new HttpEntity<MultiValueMap<String, String>>(map,
				headers);
		ResponseEntity<PasswordEncryption> response = restTemplate.postForEntity(url, request1,
				PasswordEncryption.class);
		PasswordEncryption res = response.getBody();
		return res.getResult();
	}

	@Override
	public ResponseWrapperDTO resetPkt() {
		try {
			pktMasterRepository.deleteAll();
			addPKTmasterData();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset PKT Tables Successfully.", null, HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO resetUserMigration() {
		try {
			userMigrationRepository.deleteAll();
			addUserMigration();
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset user migration table successfully.", null,
				HttpStatus.OK);
	}

}
