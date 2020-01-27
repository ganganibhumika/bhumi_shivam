package com.techhive.shivamweb.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techhive.shivamweb.custom.repository.PktMasterCustomRepository;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.CreateDemand;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.model.OfferDiscountRequest;
import com.techhive.shivamweb.model.SearchHistory;
import com.techhive.shivamweb.repository.CreateDemandRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.NewArrivalSettingsRepository;
import com.techhive.shivamweb.repository.NotificationRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.SearchHistoryRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.response.payload.TwinStoneResponse;
import com.techhive.shivamweb.service.CretaeDemandService;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.NewArrivalSettingsService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.PktMasterService;
import com.techhive.shivamweb.service.SearchHistoryService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.ThirdPartyDiscountMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class PktMasterServiceImpl implements PktMasterService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SearchHistoryService searchHistoryService;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	private PktMasterCustomRepository pktMasterCustomRepository;

	@Autowired
	private FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	private DiscountMasterService discountMasterService;

	@Autowired
	private ThirdPartyDiscountMasterService thirdPartyDiscountMasterService;

	@Autowired
	NewArrivalSettingsService newArrivalSettingsService;

	@Autowired
	NewArrivalSettingsRepository newArrivalSettingsRepository;

	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Autowired
	NotificationService notificationService;

	@Autowired
	CreateDemandRepository createDemandRepository;

	@Autowired
	CretaeDemandService cretaeDemandService;
	@Autowired
	SendMailService sendMailService;

	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	@Autowired
	NotificationRepository notificationRepository;

	List<Double> disc;

	double totalWeight = 0;
	double total = 0;
	double totalRapRate = 0;

	@Override
	public ResponseWrapperDTO getStonesViaLazzyLoadingOfSearchCriteria(StoneSearchCriteria body)
			throws JsonProcessingException {

		Optional<User> user = userRepository.findById(body.getUserId());
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST);
		return pktMasterCustomRepository.getStonesViaLazzyLoadingOfSearchCriteria(body);
	}

	@Override
	public ResponseWrapperDTO addStoneDetail(MyRequestBody body, String path) {
		PktMaster stoneDetail = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), PktMaster.class);

		// System.err.println("stoneDetail.getGiDate()::" + stoneDetail.getGiDate());

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(stoneDetail))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<PktMaster> stoneDetailById = pktMasterRepository.findByStoneId(stoneDetail.getStoneId());

		if (stoneDetailById.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"StoneId with'" + stoneDetail.getStoneId() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);

		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getGiDate())) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_NOT_ACCEPTABLE, "Gi Date format not valid.", null,
					HttpStatus.NOT_ACCEPTABLE, path);
		}

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getgRap())
				|| String.valueOf(stoneDetail.getgRap()).equalsIgnoreCase("null")) {
			stoneDetail.setgRap((double) 0);
		}
		stoneDetail = pktMasterRepository.saveAndFlush(stoneDetail);
		String stoneId = stoneDetail.getId();
		String shape = stoneDetail.getShape();
		String stoneIdShivam = stoneDetail.getStoneId();
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				List<CreateDemand> demands = createDemandRepository.findAllByShape(shape);
				Set<User> users = new HashSet<>();
				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(demands)) {
					for (CreateDemand createDemand : demands) {
						try {
							Set<PktMaster> pkt = pktMasterCustomRepository.getDemandStone(
									cretaeDemandService.convertCreateDemandToStoneSearchCriteria(createDemand),
									stoneId);
							if (!pkt.isEmpty()) {
								users.add(createDemand.getUser());

								// System.err.println(createDemand.getId() + "-00000--" +
								// createDemand.getShape());

								createDemandRepository.delete(createDemand);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (!ShivamWebMethodUtils.isSetNullOrEmpty(users)) {
						Notification notification = new Notification();
						notification.setDescription(EnumForNotificationDescription.NEWSTONEDEMANDFULFILLED.toString());
						notification.setSetOfUserObject(users);
						notification.setCategory(EnumForNotificationType.DEMAND.toString());
						notification.setIsAdmin(false);
						notification.setStoneOrUserId(stoneIdShivam);
						notificationService.sendNotification(notification);
						users.forEach(user -> {
							sendMailService.sendMailForFullfilDemand(user, '"' + shape + '"');
						});
					}
				}
			}
		});
		service.shutdown();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Stone detail " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateStoneDetail(String stoneId, MyRequestBody body, String path) {
		Optional<PktMaster> stoneFromDb = pktMasterRepository.findByStoneId(stoneId);
		if (!stoneFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Stone not found.", null,
					HttpStatus.BAD_REQUEST, path);
		PktMaster stoneDetail = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), PktMaster.class);
		stoneDetail.setId(stoneFromDb.get().getId());
		stoneDetail.setStoneId(stoneFromDb.get().getStoneId());
		stoneDetail.setCreatedBy(stoneFromDb.get().getCreatedBy());
		stoneDetail.setCreatedDate(stoneFromDb.get().getCreatedDate());
		stoneDetail.setIsFeatured(stoneFromDb.get().getIsFeatured());
		System.out.println("IS sold::" + stoneDetail.getIsSold());
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getIsSold())) {
			stoneDetail.setIsSold(stoneFromDb.get().getIsSold());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getIsHold())) {
			stoneDetail.setIsHold(stoneFromDb.get().getIsHold());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getGiDate())) {
			stoneDetail.setGiDate(stoneFromDb.get().getGiDate());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getgRap())) {
			stoneDetail.setgRap(stoneFromDb.get().getgRap());
		}
		stoneDetail.setDiscountUpdateDate(stoneFromDb.get().getDiscountUpdateDate());

		/* send notification for changed disc or price */

		if (Double.compare(stoneFromDb.get().getDisc(), stoneDetail.getDisc()) != 0) {
			stoneDetail.setDiscountUpdateDate(new Date());
			System.err.println(stoneDetail.getDiscountUpdateDate());
			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {
					try {
						notificationService.setCriteriaForNotification(stoneFromDb.get().getId(),
								stoneDetail.getStoneId(), ShivamWebVariableUtils.updateStatus);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			service.shutdown();
		}

		/* send notification for changed disc or price */

		pktMasterRepository.saveAndFlush(stoneDetail);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Stone " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);

	}

	@Override
	public List<PktMaster> getAllStoneDetailForThirdParty(String userId) {

		// List<PktMaster> listOfPkt = pktMasterRepository.findAll();

		List<PktMaster> listOfPkt = pktMasterRepository.findAllByParam();

		/* Discount */
		DecimalFormat df = new DecimalFormat("####0.00");
		List<Double> disc;
		List<NewArrivalSettings> newArrivalSettingFromDb = newArrivalSettingsRepository.findAll();
		for (PktMaster responsePktMast : listOfPkt) {
			/* Set id new arrival stone */
			responsePktMast.setIsNewArrival(newArrivalSettingsService
					.isNewArrival(newArrivalSettingFromDb.get(0).getNoOfDays(), responsePktMast.getGiDate()));

			/* End */

			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
					&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
							.contains(responsePktMast.getfColor().trim().toLowerCase())) {
				disc = ShivamWebMethodUtils.getDefaultDiscForCal();
				responsePktMast.setDisc((double) 0);
			} else {
				/* Add user wise discount */

				disc = thirdPartyDiscountMasterService.getThirdPartyDiscountByUserId(userId,
						responsePktMast.getGiDate(), responsePktMast.getCarat(), responsePktMast.getShape(), false);

			}
			Double perCaratDisc = (double) 0;
			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
				Double perCaratDisFromDisMast = disc.get(0).doubleValue();
				perCaratDisc = responsePktMast.getDisc() + perCaratDisFromDisMast;

			} else if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
					&& ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
				Double perCaratDisFromDisMast = disc.get(0).doubleValue();
				perCaratDisc = 0 + perCaratDisFromDisMast;

			} else if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
				perCaratDisc = responsePktMast.getDisc() + 0;

			}

			/* .....................End get discount base on userID ................. */
			// getgRapAsRapRate==gRape

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {

				responsePktMast
						.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));
				responsePktMast.setDisc(perCaratDisc);
				responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
				responsePktMast.setTotalPrice(
						Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));

			}

			/* End Discount */
		}

		return listOfPkt;

	}

	@Override
	public ResponseWrapperDTO getAllPktMasterByStatus(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, Boolean isFeatured, String path, String userId) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(isFeatured)) {
			Page<PktMaster> list = pktMasterRepository.findAllByIsSold(false, request);
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(userId)
					&& !ShivamWebMethodUtils.isListIsNullOrEmpty(list.getContent())) {
				for (PktMaster viewRequest : list.getContent()) {
					disc = new ArrayList<>();
					getDiscount(viewRequest, userId);
				}
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get all pkt", list, HttpStatus.OK, path);
		}

		Page<PktMaster> list = pktMasterRepository.findAllByisFeaturedAndIsSold(isFeatured, false, request);
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(userId)
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(list.getContent())) {
			for (PktMaster viewRequest : list.getContent()) {
				disc = new ArrayList<>();
				getDiscount(viewRequest, userId);
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get all pkt", list, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateFeaturedStone(MyRequestBody body, String servletPath) {
		List<PktMaster> pktMasters = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<PktMaster>>() {
				});
		String Status;
		if (!pktMasters.isEmpty()) {
			if (pktMasters.get(0).getIsFeatured() == true) {
				Status = "added";
			} else {
				Status = "removed";
			}
			for (PktMaster pktMaster : pktMasters) {
				Optional<PktMaster> pktFromDb = pktMasterRepository.findById(pktMaster.getId());
				if (pktFromDb.isPresent()) {
					pktFromDb.get().setIsFeatured(pktMaster.getIsFeatured());
					pktMasterRepository.saveAndFlush(pktFromDb.get());
				}
			}
		} else {
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(servletPath);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Featured stone(s) " + Status + " successfully.", null,
				HttpStatus.OK, servletPath);
	}

	@Override
	public ResponseWrapperDTO getRecomendedStone(String userId, String path)
			throws JsonParseException, JsonMappingException, IOException {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST);
		List<SearchHistory> findAllOfUser = searchHistoryRepository.findAllOfUser(userId, new PageRequest(0, 2));

		Set<PktMaster> pkt = new HashSet<>();
		// SortedSet<PktMaster> sortPkt = new
		// TreeSet<>(Comparator.comparing(PktMaster::getDisc).reversed());
		List<PktMaster> pktList10 = new ArrayList<>();
		List<PktMaster> pktListfinal = new ArrayList<>();
		List<String> pktidsNotToCheck = new ArrayList<>();
		if (!findAllOfUser.isEmpty()) {
			findAllOfUser.get(0).setUserId(userId);
			pkt = pktMasterCustomRepository.getRecomendedStone(pktidsNotToCheck,
					searchHistoryService.convertSearchHistoryToStoneSearchCriteria(findAllOfUser.get(0)));
			if (findAllOfUser.size() >= 2 && !ShivamWebMethodUtils.isObjectisNullOrEmpty(findAllOfUser.get(1))) {
				if (!pkt.isEmpty())
					pktidsNotToCheck.addAll(pkt.stream().map(PktMaster::getId).collect(Collectors.toList()));
				findAllOfUser.get(1).setUserId(userId);
				Set<PktMaster> pkt2 = new HashSet<>();
				pkt2 = pktMasterCustomRepository.getRecomendedStone(pktidsNotToCheck,
						searchHistoryService.convertSearchHistoryToStoneSearchCriteria(findAllOfUser.get(1)));
				if (!pkt2.isEmpty())
					pkt.addAll(pkt2);
			}
		}
		Comparator<PktMaster> discount = new Comparator<PktMaster>() {
			@Override
			public int compare(PktMaster o1, PktMaster o2) {
				return (int) (o2.getDisc() - o1.getDisc());
			}
		};
		// sortPkt.addAll(pkt);
		pktList10.addAll(pkt);
		java.util.Collections.sort(pktList10, discount);
		if (pktList10.size() >= 10) {
			for (int i = 0; i < 10; i++) {
				pktListfinal.add(pktList10.get(i));
			}
		} else {
			pktListfinal.addAll(pktList10);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "recommended stones.", pktListfinal, HttpStatus.OK,
				path);
	}

	Map<String, Integer> clarityOrder = new HashMap<>();
	Map<String, Integer> colorOrder = new HashMap<>();
	Set<String> clarityList = new HashSet<>();
	Set<String> colorList = new HashSet<>();

	DecimalFormat df = new DecimalFormat("###.##");

	@Override
	public ResponseWrapperDTO getTwinStone5(String path, String userId) {
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);
		List<PktMaster> pkt = pktMasterRepository.findAllByParam();
		// Map<String, List<PktMaster>> map = new HashMap<>();
		List<TwinStoneResponse> twinStone = new ArrayList<>();
		clarityOrder.put("FL", 1);
		clarityOrder.put("IF", 2);
		clarityOrder.put("VVS1", 3);
		clarityOrder.put("VVS2", 4);
		clarityOrder.put("VS1", 5);
		clarityOrder.put("VS2", 6);
		clarityOrder.put("SI1", 7);
		clarityOrder.put("SI2", 8);
		clarityOrder.put("SI3", 9);
		clarityOrder.put("I1", 10);
		clarityOrder.put("I2", 11);
		clarityOrder.put("I3", 12);
		clarityOrder.put("I4", 13);
		clarityOrder.put("I5", 14);
		clarityOrder.put("I6", 15);
		clarityOrder.put("I7", 16);

		colorOrder.put("D", 1);
		colorOrder.put("E", 2);
		colorOrder.put("F", 3);
		colorOrder.put("G", 4);
		colorOrder.put("H", 5);
		colorOrder.put("I", 6);
		colorOrder.put("J", 7);
		colorOrder.put("K", 8);
		colorOrder.put("L", 9);
		colorOrder.put("M", 10);
		colorOrder.put("N", 11);
		colorOrder.put("O-P", 12);
		Set<String> clarity1 = new HashSet<>();
		// clarity1.add("FL");
		clarity1.add("IF");
		clarity1.add("VVS1");
		clarity1.add("VVS2");
		clarity1.add("VS1");
		Set<String> clarity2 = new HashSet<>();
		clarity2.add("VS2");
		clarity2.add("SI1");
		int cnt = 1;
		List<PktMaster> similar = new ArrayList<>();
		String pair = "Pair ";
		Set<String> pktIdNotToCheck = new HashSet<>();
		if (!pkt.isEmpty()) {
			for (PktMaster pktMaster : pkt) {

				if (similar.isEmpty() && !pktIdNotToCheck.contains(pktMaster.getId())) {

					similar.add(pktMaster);
					pktIdNotToCheck.add(pktMaster.getId());
					if (similar.size() <= 1) {
						PktMaster p = similar.get(0);

						// check equals
						PktMaster pkt2 = pkt.stream().filter(pktt -> {
							if (!pktIdNotToCheck.contains(pktt.getId())
									&& p.getCodeOfClarity().equals(pktt.getCodeOfClarity())
									&& p.getCodeOfColor().equals(pktt.getCodeOfColor())
									&& Double.compare(pktt.getTablePercentage(), p.getTablePercentage()) == 0
									&& Double.compare(pktt.getWidth(), p.getWidth()) == 0
									&& Double.compare(pktt.getTotDepth(), p.getTotDepth()) == 0
									&& Double.compare(pktt.getLength(), p.getLength()) == 0) {

								return true;
							} else {
								return false;
							}
						}).findFirst().orElse(null);
						if (!ShivamWebMethodUtils.isObjectNullOrEmpty(pkt2)) {
							if (clarity1.contains(p.getCodeOfClarity()) && clarity1.contains(pkt2.getCodeOfClarity())) {
								if (p.getCodeOfFluorescence().equals(pkt2.getCodeOfFluorescence())) {

									similar.add(pkt2);
									pktIdNotToCheck.add(pkt2.getId());
								}
							}
							if (clarity2.contains(p.getCodeOfClarity()) && clarity2.contains(pkt2.getCodeOfClarity())) {
								if (p.getCodeOfFluorescence().equals(pkt2.getCodeOfFluorescence())
										|| (p.getCodeOfFluorescence().equals("NON")
												&& pkt2.getCodeOfFluorescence().equals("FNT"))
										|| (p.getCodeOfFluorescence().equals("FNT")
												&& pkt2.getCodeOfFluorescence().equals("NON"))) {
									similar.add(pkt2);
									pktIdNotToCheck.add(pkt2.getId());
								}
							}
						}
						if (similar.size() <= 1) {
							PktMaster pkt3 = pkt.stream().filter(pktt -> {
								if (!pktIdNotToCheck.contains(pktt.getId())
										&& (pktt.getCodeOfClarity().equals(getClarityOrder(p.getCodeOfClarity(), "<"))
												|| pktt.getCodeOfClarity()
														.equals(getClarityOrder(p.getCodeOfClarity(), ">"))
												|| pktt.getCodeOfClarity().equals(p.getCodeOfClarity()))
										&& (pktt.getCodeOfColor().equals(getColorOrder(p.getCodeOfColor(), "<"))
												|| pktt.getCodeOfColor().equals(getColorOrder(p.getCodeOfColor(), ">"))
												|| pktt.getCodeOfColor().equals(p.getCodeOfColor()))
										&& (pktt.getTablePercentage() >= Math.abs(p.getTablePercentage() - 1)
												&& pktt.getTablePercentage() <= p.getTablePercentage() + 1)
										&& (pktt.getWidth() >= p.getWidth() - (p.getWidth() * (1 / 100.0f))
												&& pktt.getWidth() <= (p.getWidth() * (1 / 100.0f)) + p.getWidth())
										&& (pktt.getTotDepth() >= Math.abs(p.getTotDepth() - 2)
												&& pktt.getTotDepth() <= p.getTotDepth() + 2)
										&& (pktt.getLength() >= p.getLength() - (p.getLength() * (1 / 100.0f)) && pktt
												.getLength() <= (p.getLength() * (1 / 100.0f)) + p.getLength())) {
									return true;
								} else {
									return false;
								}
							}).findFirst().orElse(null);
							if (!ShivamWebMethodUtils.isObjectNullOrEmpty(pkt3)) {
								if (clarity1.contains(p.getCodeOfClarity())
										&& clarity1.contains(pkt3.getCodeOfClarity())) {
									if (p.getCodeOfFluorescence().equals(pkt3.getCodeOfFluorescence())) {

										similar.add(pkt3);
										pktIdNotToCheck.add(pkt3.getId());
									}
								}
								if (clarity2.contains(p.getCodeOfClarity())
										&& clarity2.contains(pkt3.getCodeOfClarity())) {
									if (p.getCodeOfFluorescence().equals(pkt3.getCodeOfFluorescence())
											|| (p.getCodeOfFluorescence().equals("NON")
													&& pkt3.getCodeOfFluorescence().equals("FNT"))
											|| (p.getCodeOfFluorescence().equals("FNT")
													&& pkt3.getCodeOfFluorescence().equals("NON"))) {

										similar.add(pkt3);
										pktIdNotToCheck.add(pkt3.getId());
									}
								}
							}
						}
						if (similar.size() > 1) {
							similar.forEach(s -> {
								getDiscount(s, userId);
							});
							twinStone.add(new TwinStoneResponse(pair + cnt, similar));
							// map.put(pair + cnt, similar);
							similar = new ArrayList<>();
							cnt++;
						} else {
							similar = new ArrayList<>();
						}
					}
				}
			}
		}
		// Map<String, List<PktMaster>> map2 = new TreeMap<String,
		// List<PktMaster>>(map);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Twin stones.", twinStone, HttpStatus.OK, path);
	}

	public String getClarityOrder(String Clarity, String greaterOrLess) {
		if (greaterOrLess.equals("<")) {
			if (clarityOrder.containsKey(Clarity) && clarityOrder.get(Clarity) != 1) {
				return getKey(clarityOrder, (clarityOrder.get(Clarity) - 1));
			} else {
				return Clarity;
			}
		} else {
			if (clarityOrder.containsKey(Clarity) && clarityOrder.get(Clarity) != 16) {
				return getKey(clarityOrder, (clarityOrder.get(Clarity) + 1));
			} else {
				return Clarity;
			}
		}

	}

	public String getColorOrder(String color, String greaterOrLess) {
		if (greaterOrLess.equals("<")) {
			if (colorOrder.containsKey(color) && colorOrder.get(color) != 1) {
				return getKey(colorOrder, (colorOrder.get(color) - 1));
			} else {
				return color;
			}
		} else {
			if (colorOrder.containsKey(color) && colorOrder.get(color) != 12) {
				return getKey(colorOrder, (colorOrder.get(color) + 1));
			} else {
				return color;
			}
		}

	}

	public static <K, V> K getKey(Map<K, V> map, V value) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public Set<String> generateCarityList(String Clarity) {
		clarityList = new HashSet<>();
		if (clarityOrder.containsKey(Clarity)) {
			clarityList.add(Clarity);
			if (clarityOrder.get(Clarity) != 1) {
				clarityList.add(getKey(clarityOrder, (clarityOrder.get(Clarity) - 1)));
			}
			if (clarityOrder.get(Clarity) != 16) {
				clarityList.add(getKey(clarityOrder, (clarityOrder.get(Clarity) + 1)));
			}
		}

		return clarityList;
	}

	public Set<String> generateColourList(String colour) {
		colorList = new HashSet<>();

		if (colorOrder.containsKey(colour)) {
			colorList.add(colour);
			if (colorOrder.get(colour) != 1) {
				colorList.add(getKey(colorOrder, (colorOrder.get(colour) - 1)));
			}
			if (colorOrder.get(colour) != 12) {
				colorList.add(getKey(colorOrder, (colorOrder.get(colour) + 1)));
			}
		}

		return colorList;
	}

	@Override
	public PktMaster getDiscount(PktMaster responsePktMast, String userId) {

		// Set image path for web and app
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast)) {
			ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(responsePktMast.getShape())
					.orElse(new ShapeMaster());
			responsePktMast.setShapeImage(shapeImage.getShapeImage());
		}

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
				&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
						.contains(responsePktMast.getfColor().trim().toLowerCase())) {
			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			responsePktMast.setDisc((double) 0);
		} else {
			/* Add user wise discount */
			// responsePktMast.getId() + "kkk"
			// + responsePktMast.getGiDate());
			disc = discountMasterService.getDiscountByUserId(userId, responsePktMast.getGiDate(),
					responsePktMast.getCarat(), responsePktMast.getShape(), false);
			// get default discount
			if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)) {
				disc = discountMasterService.getDiscountByUserId(ShivamWebVariableUtils.DEFAULT_USER_ID,
						responsePktMast.getGiDate(), responsePktMast.getCarat(), responsePktMast.getShape(), true);
			}
			// END default discount get
		}
		Double perCaratDisc = (double) 0;
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			Double perCaratDisFromDisMast = disc.get(0).doubleValue();
			perCaratDisc = responsePktMast.getDisc() + perCaratDisFromDisMast;

		} else if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			Double perCaratDisFromDisMast = disc.get(0).doubleValue();
			perCaratDisc = 0 + perCaratDisFromDisMast;

		} else if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
				&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
			perCaratDisc = responsePktMast.getDisc() + 0;

		}

		/* .....................End get discount base on userID ................. */
		// getgRapAsRapRate==gRape
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {
			responsePktMast
					.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));

			// /* End Discount */

			totalWeight = totalWeight + responsePktMast.getCarat();
			totalRapRate = totalRapRate + (responsePktMast.getCarat() * responsePktMast.getgRap());
			responsePktMast.setDisc(perCaratDisc);
			responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
			total = total + (responsePktMast.getCarat() * responsePktMast.getPerCaratePrice()); // weight*percarat
			responsePktMast.setTotalPrice(
					Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
			///
			// responsePktMast.setComment("test
			// --------------------------------------------------------");
		}
		return responsePktMast;
	}

	@Override
	public ResponseWrapperDTO getRecentDiscountChangedStones(String path, String userId) {
		List<PktMaster> pkts = pktMasterRepository.getRecentDiscountChangedStones();
		if (!pkts.isEmpty()) {
			for (PktMaster pktMaster : pkts) {
				getDiscount(pktMaster, userId);
			}
			pkts.get(0).setTotalNoOfRecords(pkts.size());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Recent Discount Changed Stones", pkts, HttpStatus.OK,
				path);
	}

	@Override
	public ResponseWrapperDTO viewStoneByStoneIdAndUserId(String stoneId, String userId) {

		return pktMasterCustomRepository.viewStoneByStoneIdAndUserId(stoneId, userId);
	}

	@Override
	public ResponseWrapperDTO addStoneDetailMultiple(MyRequestBody body, String path) {

		Set<String> alreadyExistInPktMaster = new HashSet<>(); // use for return already exist.
		StringBuilder finalReturnString = new StringBuilder();
		List<PktMaster> listOfpktMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<PktMaster>>() {
				});

		if (ShivamWebMethodUtils.isListIsNullOrEmpty(listOfpktMaster))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		listOfpktMaster.forEach(pktMaster -> {

			Optional<PktMaster> stoneDetailById = pktMasterRepository.findByStoneId(pktMaster.getStoneId());

			if (stoneDetailById.isPresent()) {
				alreadyExistInPktMaster.add(pktMaster.getStoneId());
			} else {

				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.getGiDate())) {
					pktMaster.setGiDate(new Date());
				}
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.getgRap())) {
					pktMaster.setgRap((double) 0);
				}

				pktMaster = pktMasterRepository.saveAndFlush(pktMaster);
				String stoneId = pktMaster.getId();
				String shape = pktMaster.getShape();
				String stoneIdShivam = pktMaster.getStoneId();

				List<CreateDemand> demands = createDemandRepository.findAllByShape(shape);
				Set<User> users = new HashSet<>();
				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(demands)) {
					for (CreateDemand createDemand : demands) {
						try {
							Set<PktMaster> pkt = pktMasterCustomRepository.getDemandStone(
									cretaeDemandService.convertCreateDemandToStoneSearchCriteria(createDemand),
									stoneId);
							if (!pkt.isEmpty()) {
								users.add(createDemand.getUser());
								// System.err.println(createDemand.getId() + "-00000--" +
								// createDemand.getShape());
								createDemandRepository.delete(createDemand);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (!ShivamWebMethodUtils.isSetNullOrEmpty(users)) {
						Notification notification = new Notification();
						notification.setDescription(EnumForNotificationDescription.NEWSTONEDEMANDFULFILLED.toString());
						notification.setSetOfUserObject(users);
						notification.setCategory(EnumForNotificationType.DEMAND.toString());
						notification.setIsAdmin(false);
						notification.setStoneOrUserId(stoneIdShivam);
						notificationService.sendNotification(notification);
						users.forEach(user -> {
							sendMailService.sendMailForFullfilDemand(user, '"' + shape + '"');
						});
					}
				}

			}
		});

		finalReturnString.append(ShivamWebMethodUtils.isSetNullOrEmpty(alreadyExistInPktMaster) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(alreadyExistInPktMaster.toString())
						+ " already in inventory.\n");

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				" Stone detail " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY + finalReturnString, null, HttpStatus.OK,
				path);

	}

	@Override
	public ResponseWrapperDTO addOrUpdateStoneDetail(MyRequestBody body, String path) {
		// Set<String> alreadyExistInPktMaster = new HashSet<>(); // use for return
		// already exist.
		// StringBuilder finalReturnString = new StringBuilder();
		List<PktMaster> listOfpktMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<PktMaster>>() {
				});

		if (ShivamWebMethodUtils.isListIsNullOrEmpty(listOfpktMaster))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);

		listOfpktMaster.forEach(pktMaster -> {

			Optional<PktMaster> stoneDetailById = pktMasterRepository.findByStoneId(pktMaster.getStoneId());

			if (stoneDetailById.isPresent()) {
				// alreadyExistInPktMaster.add(pktMaster.getStoneId());
				MyRequestBody bodyForUpdateStone = new MyRequestBody();
				bodyForUpdateStone.setJsonOfObject(pktMaster);
				System.out.println("get");
				updateStoneDetailFromAddOrUpdate(pktMaster.getStoneId(), bodyForUpdateStone, path);

			} else {
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.getGiDate())) {
					pktMaster.setGiDate(new Date());
				}
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.getgRap())) {
					pktMaster.setgRap((double) 0);
				}

				pktMaster = pktMasterRepository.saveAndFlush(pktMaster);
				String stoneId = pktMaster.getId();
				String shape = pktMaster.getShape();
				String stoneIdShivam = pktMaster.getStoneId();

				List<CreateDemand> demands = createDemandRepository.findAllByShape(shape);
				Set<User> users = new HashSet<>();
				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(demands)) {
					for (CreateDemand createDemand : demands) {
						try {
							Set<PktMaster> pkt = pktMasterCustomRepository.getDemandStone(
									cretaeDemandService.convertCreateDemandToStoneSearchCriteria(createDemand),
									stoneId);
							if (!pkt.isEmpty()) {
								users.add(createDemand.getUser());
								// System.err.println(createDemand.getId() + "-00000--" +
								// createDemand.getShape());
								createDemandRepository.delete(createDemand);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (!ShivamWebMethodUtils.isSetNullOrEmpty(users)) {
						Notification notification = new Notification();
						notification.setDescription(EnumForNotificationDescription.NEWSTONEDEMANDFULFILLED.toString());
						notification.setSetOfUserObject(users);
						notification.setCategory(EnumForNotificationType.DEMAND.toString());
						notification.setIsAdmin(false);
						notification.setStoneOrUserId(stoneIdShivam);
						notificationService.sendNotification(notification);
						users.forEach(user -> {
							sendMailService.sendMailForFullfilDemand(user, '"' + shape + '"');
						});
					}
				}

			}
		});

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, " Stone detail add or update successfully.", null,
				HttpStatus.OK, path);
	}

	/* Only use for multiple add or update */
	public void updateStoneDetailFromAddOrUpdate(String stoneId, MyRequestBody body, String path) {
		Optional<PktMaster> stoneFromDb = pktMasterRepository.findByStoneId(stoneId);

		PktMaster stoneDetail = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), PktMaster.class);
		stoneDetail.setId(stoneFromDb.get().getId());
		stoneDetail.setStoneId(stoneFromDb.get().getStoneId());
		stoneDetail.setCreatedBy(stoneFromDb.get().getCreatedBy());
		stoneDetail.setCreatedDate(stoneFromDb.get().getCreatedDate());
		stoneDetail.setIsFeatured(stoneFromDb.get().getIsFeatured());
//		System.out.println("IS sold::" + stoneDetail.getIsSold());
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getIsSold())) {
			stoneDetail.setIsSold(stoneFromDb.get().getIsSold());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getIsHold())) {
			stoneDetail.setIsHold(stoneFromDb.get().getIsHold());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getGiDate())) {
			stoneDetail.setGiDate(stoneFromDb.get().getGiDate());
		}
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneDetail.getgRap())) {
			stoneDetail.setgRap(stoneFromDb.get().getgRap());
		}
		stoneDetail.setDiscountUpdateDate(stoneFromDb.get().getDiscountUpdateDate());

		/* send notification for changed disc or price */

		if (Double.compare(stoneFromDb.get().getDisc(), stoneDetail.getDisc()) != 0) {
			stoneDetail.setDiscountUpdateDate(new Date());
			System.err.println(stoneDetail.getDiscountUpdateDate());
			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {
					try {
						notificationService.setCriteriaForNotification(stoneFromDb.get().getId(),
								stoneDetail.getStoneId(), ShivamWebVariableUtils.updateStatus);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			service.shutdown();
		}

		/* send notification for changed disc or price */

		pktMasterRepository.saveAndFlush(stoneDetail);

	}

}
