package com.techhive.shivamweb.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.Cart;
import com.techhive.shivamweb.model.ConfirmOrder;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.model.OfferDiscountRequest;
import com.techhive.shivamweb.model.Wishlist;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.ConfirmOrderRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.OfferDiscountRequestRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.SoftwarePartyMasterRespository;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.repository.WishlistRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ConfirmOrderService;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.PushNotificationService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class ConfirmOrderServiceImpl implements ConfirmOrderService {

	@Autowired
	private ConfirmOrderRepository confirmOrderRepository;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	WishlistRepository wishlistRepository;

	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	@Autowired
	OfferDiscountRequestRepository offerDiscountRequestRepository;

	@Autowired
	FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	SoftwarePartyMasterRespository softwarePartyMasterRespository;

	@Autowired
	SoftwareSalesPersonRepository softwareSalesPersonRepository;

	@Autowired
	private PushNotificationService pushnotificationService;

	@Autowired
	NotificationService notificationService;

	double totalWeight = 0;
	double total = 0;
	double totalRapRate = 0;
	DecimalFormat df = new DecimalFormat("####0.00");
	List<Double> disc;

	String message = "";
	User emailUser;

	@Override
	public ResponseWrapperDTO saveConformOrder(MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		String userId = body.getUserId();
		Optional<User> user = userRepository.findById(userId);
		String ipAddress = request.getRemoteAddr();
		Optional<User> user2 = null;
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getOtherData())) {
			user2 = userRepository.findById(body.getOtherData());
			if (!user2.isPresent())
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
						null, HttpStatus.BAD_REQUEST, path);
		}
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);

		Optional<SoftwarePartyMaster> partyInDb = softwarePartyMasterRespository.getSoftwarePartyByUserId(userId);

		if (!partyInDb.isPresent()) {
			partyInDb = softwarePartyMasterRespository.findByPartyName("sj-EventParty");
			if (!partyInDb.isPresent()) {
				return new ResponseWrapperDTO(HttpServletResponse.SC_FORBIDDEN,
						"Sorry you do not have an party assigned contact admin.", null, HttpStatus.FORBIDDEN, path);
			}
		}

		Optional<SoftwarePartyMaster> party = partyInDb;

		System.err.println(" PArty name::" + party.get().getPartyName());
		Optional<SoftwareSalePersonMaster> salesPerson = softwareSalesPersonRepository.findByUser(userId);
		StringBuilder response = new StringBuilder();
		Set<String> yourConifrmOrder = new HashSet<>();
		Set<String> othersConfirm = new HashSet<>();
		Set<String> stoneUnderBuisnessProcess = new HashSet<>();
		Set<String> successConfirm = new HashSet<>();
		Set<String> failOnShivamSide = new HashSet<>();
		List<ConfirmOrder> confirmSuccess = new ArrayList<>();
		List<ConfirmOrder> confirmOrders = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<ConfirmOrder>>() {
				});
		if (ShivamWebMethodUtils.isListNullOrEmpty(confirmOrders))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		confirmOrders.forEach(confirmOrder -> {

			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(confirmOrder.getPktMasterId())) {
				Optional<PktMaster> pktMaster = pktMasterRepository.findById(confirmOrder.getPktMasterId());
				if (pktMaster.isPresent()) {
					if (pktMaster.get().getIsSold() == true) {
						if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.get().getConfirmOrder())
								&& pktMaster.get().getConfirmOrder().getUser().getId().equals(userId)) {
							yourConifrmOrder.add(pktMaster.get().getStoneId());
						} else {
							othersConfirm.add(pktMaster.get().getStoneId());
						}
					} else {
						if (pktMaster.get().getIsHold() == true) {
							stoneUnderBuisnessProcess.add(pktMaster.get().getStoneId());
						} else {
							confirmOrder.setUser(user.get());
							confirmOrder.setPktMaster(pktMaster.get());
							confirmOrder.setIpAddress(ipAddress);

							HttpStatus status = callApiShivam(confirmOrder, party, salesPerson);
							if (!ShivamWebMethodUtils.isObjectNullOrEmpty(status)) {
								if (status == HttpStatus.OK) {
									confirmOrderRepository.saveAndFlush(confirmOrder);
									successConfirm.add(pktMaster.get().getStoneId());
									confirmSuccess.add(confirmOrder);

									// remove from cart
									Optional<Cart> cartOfUser = cartRepository.findByUserAndPkt(userId,
											confirmOrder.getPktMasterId());
									if (cartOfUser.isPresent()) {
										cartRepository.deleteById(cartOfUser.get().getId());
									}
									/// remove from wishlist
									Optional<Wishlist> wishlistOfUser = wishlistRepository.findByUserAndPkt(userId,
											confirmOrder.getPktMasterId());
									if (wishlistOfUser.isPresent()) {
										wishlistRepository.deleteById(wishlistOfUser.get().getId());
									}
									// remove from offer
									Optional<OfferDiscountRequest> offerDiscountRequestOfUser = offerDiscountRequestRepository
											.findByUserAndPkt(userId, confirmOrder.getPktMasterId());
									if (offerDiscountRequestOfUser.isPresent()) {
										offerDiscountRequestRepository
												.deleteById(offerDiscountRequestOfUser.get().getId());
									}
									pktMaster.get().setIsSold(true);
									pktMasterRepository.saveAndFlush(pktMaster.get());
								} else if (status == HttpStatus.CONFLICT) {
									stoneUnderBuisnessProcess.add(pktMaster.get().getStoneId());
								} else if (status == HttpStatus.NOT_MODIFIED) {
									failOnShivamSide.add(pktMaster.get().getStoneId());
								} else {
									failOnShivamSide.add(pktMaster.get().getStoneId());
								}
							} else {
								failOnShivamSide.add(pktMaster.get().getStoneId());
							}
						}
					}
				}
			}
		});
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(yourConifrmOrder) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(yourConifrmOrder.toString())
						+ " you have already confirmed this stone(s).\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(stoneUnderBuisnessProcess) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(stoneUnderBuisnessProcess.toString())
						+ " stone(s) under buisness process.\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersConfirm) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersConfirm.toString())
						+ " this stone(s) have been confirmed by other.\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(failOnShivamSide) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(failOnShivamSide.toString())
						+ " sorry, we could not process this stone(s) at this time. Please try again.\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(successConfirm) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString())
						+ " stone(s) confirmed successfully.\n");

		if (!ShivamWebMethodUtils.isSetNullOrEmpty(successConfirm)) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getOtherData())) {

				message = EnumForUserTracking.STONEID.toString()
						+ ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString())
						+ EnumForUserTracking.STONEOFFERCONFORMED.toString() + "of " + user.get().getUsername();
				emailUser = user.get();
				userTrackingService.saveTracking(user2.get(), request.getRemoteAddr(), message);
			} else {
				message = EnumForUserTracking.STONEID.toString()
						+ ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString())
						+ EnumForUserTracking.STONEISCONFORMED;
				emailUser = user.get();
				userTrackingService.saveTracking(user.get(), request.getRemoteAddr(), message);
			}

			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {
					totalWeight = 0;
					total = 0;
					totalRapRate = 0;
					for (ConfirmOrder confirmOrder : confirmSuccess) {
						disc = new ArrayList<>();
						getDiscount(confirmOrder);
					}
					double finalTCTS = totalWeight;
					double finalDiscountPercentage = (1 - (total / totalRapRate)) * 100;

					double finalRapRate = totalRapRate / finalTCTS;
					double finalPriceOrFinalCarat = (finalRapRate * (100 - finalDiscountPercentage)) / 100;
					double finalTotal = total;

					confirmSuccess.get(0).getPktMaster().setFinalTCTS(Double.valueOf(df.format(finalTCTS)));
					confirmSuccess.get(0).getPktMaster()
							.setFinalDiscountPercentage(Double.valueOf(df.format(finalDiscountPercentage)));
					confirmSuccess.get(0).getPktMaster().setFinalRapRate(Double.valueOf(df.format(finalRapRate)));
					confirmSuccess.get(0).getPktMaster()
							.setFinalPriceOrFinalCarat(Double.valueOf(df.format(finalPriceOrFinalCarat)));
					confirmSuccess.get(0).getPktMaster().setFinalTotal(Double.valueOf(df.format(finalTotal)));
					if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getOtherData())) {
						// admin confirm stone of user from offer discount
						// admin template left

						// sendMailService.sendMailForConfirmOrder(emailUser,
						// ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()));
						Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
						Notification notification = new Notification();
						notification.setDescription(EnumForNotificationDescription.STONEOFFERCONFORMED.toString());
						Set<User> setOfUser = new HashSet<>();
						setOfUser.add(user.get());
						notification.setSetOfUserObject(setOfUser);
						notification.setCategory(EnumForNotificationType.CONFIRMORDER.toString());
						notification.setIsAdmin(false);
						notification.setStoneOrUserId(
								ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()));
						notificationService.sendNotification(notification);
						// send mail to admin if admin confirm user's offer from offer stone
						if (!users.isEmpty())
							sendMailService.sendMailForConfirmOffer(users, emailUser,
									ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()),
									confirmSuccess);
						// send mail to user when he admin confirms his/her stone from offer
						sendMailService.sendMailForConfirmOfferToUser(user.get(), emailUser,
								ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()),
								confirmSuccess);
					} else {
						// user confirm his stone directly then send notification to admin

						Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
						Notification notification = new Notification();
						notification.setDescription(EnumForNotificationDescription.CONFIRMTOADMIN.toString());
						notification.setSetOfUserObject(users);
						notification.setCategory(EnumForNotificationType.CONFIRMORDER.toString());
						notification.setIsAdmin(true);
						notification.setStoneOrUserId(user.get().getUsername());
						notificationService.sendNotification(notification);
						// send mail to admin if user confirm his /her stone
						sendMailService.sendMailForConfirmOrder(users, emailUser,
								ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()),
								confirmSuccess);
						// send mail to user when he confirm his/her stone
						sendMailService.sendMailForConfirmOrderToUser(user.get(), emailUser,
								ShivamWebMethodUtils.remove1StAndLastCharOfString(successConfirm.toString()),
								confirmSuccess);
					}
				}
			});
			service.shutdown();
			Logger l = LoggerFactory.getLogger(ConfirmOrderServiceImpl.class);
			l.warn("2sercvice terminated:-" + service.isTerminated());
			l.warn("2sercvice isshutdown:-" + service.isShutdown());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, response.toString(), null, HttpStatus.OK, path);
	}

	public HttpStatus callApiShivam(ConfirmOrder confirmOrder, Optional<SoftwarePartyMaster> party,
			Optional<SoftwareSalePersonMaster> salesPerson) {
		getDiscount(confirmOrder);
		String url = "http://103.54.99.76:8123/api/UpdateStone/Edit";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, List<Object>> map = new HashMap<>();
		Map<String, Object> innerMap = new HashMap<>();
		List<Object> l = new ArrayList<Object>();
		innerMap.put("stoneId", confirmOrder.getPktMaster().getStoneId());
		innerMap.put("jangadDate", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()));
		innerMap.put("partyName", party.get().getPartyName());
		innerMap.put("salesPerson", salesPerson.isPresent() ? salesPerson.get().getName() : "");
		innerMap.put("APer", confirmOrder.getPktMaster().getDiscOrignal());
		innerMap.put("Offer_Per", confirmOrder.getDiscount());
		innerMap.put("Offer_Rate", confirmOrder.getPktMaster().getTotalPrice());

		l.add(innerMap);

		map.put("jsonOfObject", l);
		HttpEntity<Map<String, List<Object>>> httpEntity = new HttpEntity<Map<String, List<Object>>>(map, headers);
		ResponseEntity<String> response = null;
		try {
			// comment this line when ofline or test purpose so it would not effect online
			// inventory of shivam database.

			// response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity,
			// String.class);

		} catch (Exception e) {
		}
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(response)) {
			return response.getStatusCode();
		}
		return null;
	}

	@Override
	public ResponseWrapperDTO getAllConfirmOrder(String userId, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			List<ConfirmOrder> list = confirmOrderRepository.findAllByOrderByCreatedDateDesc();
			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
				for (ConfirmOrder confirmOrder : list) {
					disc = new ArrayList<>();
					getDiscount(confirmOrder);
				}
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all confirm order", list, HttpStatus.OK,
					path);
		}
		List<ConfirmOrder> list = confirmOrderRepository.findAllByUser(userId);
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
			for (ConfirmOrder confirmOrder : list) {
				disc = new ArrayList<>();
				getDiscount(confirmOrder);
			}
			double finalTCTS = totalWeight;
			double finalDiscountPercentage = (1 - (total / totalRapRate)) * 100;

			double finalRapRate = totalRapRate / finalTCTS;
			double finalPriceOrFinalCarat = (finalRapRate * (100 - finalDiscountPercentage)) / 100;
			double finalTotal = total;

			list.get(0).getPktMaster().setFinalTCTS(Double.valueOf(df.format(finalTCTS)));// Total Carat
			list.get(0).getPktMaster().setFinalDiscountPercentage(Double.valueOf(df.format(finalDiscountPercentage)));// Rap
																														// Discount
			list.get(0).getPktMaster().setFinalRapRate(Double.valueOf(df.format(finalRapRate)));
			list.get(0).getPktMaster().setFinalPriceOrFinalCarat(Double.valueOf(df.format(finalPriceOrFinalCarat)));// Avg
																													// Price
			list.get(0).getPktMaster().setFinalTotal(Double.valueOf(df.format(finalTotal)));// Total Amount
			list.get(0).setTotalNoOfRecords(list.size());

		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all confirm order of user", list, HttpStatus.OK,
				path);
	}

	@Override
	public ResponseWrapperDTO deleteConfirmOrder(String confirmOrderId, String path) {
		Optional<ConfirmOrder> confirmOrder = confirmOrderRepository.findById(confirmOrderId);
		if (!confirmOrder.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Confirm Order not fount.", null,
					HttpStatus.BAD_REQUEST, path);
		confirmOrder.get().getPktMaster().setIsHold(false);
		pktMasterRepository.saveAndFlush(confirmOrder.get().getPktMaster());
		confirmOrderRepository.deleteById(confirmOrderId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Order deleted successfully.", null, HttpStatus.OK,
				path);
	}

	@Override
	public ResponseWrapperDTO updateOrderStatus(String confirmOrderId, MyRequestBody body, String path) {
		Optional<ConfirmOrder> confirmOrderFromDb = confirmOrderRepository.findById(confirmOrderId);
		if (!confirmOrderFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Confirm Order not fount.", null,
					HttpStatus.BAD_REQUEST, path);
		ConfirmOrder confirmOrder = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				ConfirmOrder.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(confirmOrder.getOrderStatus()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		confirmOrderFromDb.get().setOrderStatus(confirmOrder.getOrderStatus());
		confirmOrderRepository.saveAndFlush(confirmOrderFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Order status updated successfully.", null,
				HttpStatus.OK, path);
	}

	// hear take discount of confirm order discount
	@Override
	public void getDiscount(ConfirmOrder confirmOrder) {
		PktMaster responsePktMast = confirmOrder.getPktMaster();

		// Set image path for web and app
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast)) {
			ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(responsePktMast.getShape())
					.orElse(new ShapeMaster());
			responsePktMast.setShapeImage(shapeImage.getShapeImage());
		}

		responsePktMast.setDiscOrignal(responsePktMast.getDisc());
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(confirmOrder.getDiscount()))
			confirmOrder.setDiscount((double) 0);

		responsePktMast.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));

		// /* End Discount */

		totalWeight = totalWeight + responsePktMast.getCarat();
		totalRapRate = totalRapRate + (responsePktMast.getCarat() * responsePktMast.getgRap());
		// responsePktMast.setDisc(confirmOrder.getDiscount());
		responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - confirmOrder.getDiscount()) / 100);
		total = total + (responsePktMast.getCarat() * responsePktMast.getPerCaratePrice()); // weight*percarat
		responsePktMast.setTotalPrice(
				Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
		///

	}

	@Override
	public ResponseWrapperDTO getAllResentConfirm(String userId, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		List<ConfirmOrder> list = confirmOrderRepository.getAllResentConfirm(userId, new PageRequest(0, 10));
		for (ConfirmOrder confirmOrder : list) {
			disc = new ArrayList<>();
			getDiscount(confirmOrder);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get recent confirm order of user", list,
				HttpStatus.OK, path);
	}

}
