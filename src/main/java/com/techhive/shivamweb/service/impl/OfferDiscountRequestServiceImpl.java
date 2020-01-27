package com.techhive.shivamweb.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.enums.EnumForOfferDiscountStatus;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.model.OfferDiscountRequest;
import com.techhive.shivamweb.repository.ConfirmOrderRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.OfferDiscountRequestRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.OfferDiscountRequestService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class OfferDiscountRequestServiceImpl implements OfferDiscountRequestService {

	@Autowired
	OfferDiscountRequestRepository offerDiscountRequestRepository;

	@Autowired
	ConfirmOrderRepository confirmOrderRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	NotificationService notificationService;

	@Autowired
	SendMailService sendMailService;
	
	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	DecimalFormat df = new DecimalFormat("####0.00");
	List<Double> disc;

	@Override
	public ResponseWrapperDTO saveOfferDiscountRequest(MyRequestBody body, HttpServletRequest request) {
		String path = request.getServletPath();
		String userId = body.getUserId();
		List<OfferDiscountRequest> offerDiscountRequests = ShivamWebMethodUtils.MAPPER
				.convertValue(body.getListOfJsonObject(), new TypeReference<List<OfferDiscountRequest>>() {
				});
		Optional<User> user = userRepository.findById(userId);
		if (ShivamWebMethodUtils.isListNullOrEmpty(offerDiscountRequests))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);
		StringBuilder result = new StringBuilder();
		Set<String> confirmOrderList = new HashSet<>();
		Set<String> othersConfirmOrder = new HashSet<>();
		Set<String> addedToOffer = new HashSet<>();
		Set<String> stoneUnderBuisnessProcess = new HashSet<>();
		Set<String> alreadyInOffer = new HashSet<>();
		offerDiscountRequests.forEach(offerDiscountRequest -> {
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(offerDiscountRequest.getPktMasterId())) {
				Optional<PktMaster> pktMaster = pktMasterRepository.findById(offerDiscountRequest.getPktMasterId());
				if (pktMaster.isPresent()) {
					if (pktMaster.get().getIsSold() == true) {
						// your confirm order
						if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.get().getConfirmOrder())&&pktMaster.get().getConfirmOrder().getUser().getId().equals(userId)) {
							confirmOrderList.add(pktMaster.get().getStoneId());
						} else
						// other's confirm order
						{
							// add to offer request list
							// if not present in offer request than only
							// Optional<OfferDiscountRequest> offerDiscountRequestOfUser =
							// offerDiscountRequestRepository
							// .findByUserAndPkt(userId, offerDiscountRequest.getPktMasterId());
							// if (offerDiscountRequestOfUser.isPresent()) {
							// alreadyInOffer.add(pktMaster.get().getresponsePktMastId());
							// } else {
							// offerDiscountRequest.setUser(user.get());
							// offerDiscountRequest.setPktMaster(pktMaster.get());
							// offerDiscountRequestRepository.saveAndFlush(offerDiscountRequest);
							// addedToOffer.add(pktMaster.get().getresponsePktMastId());
							// }
							othersConfirmOrder.add(pktMaster.get().getStoneId());
						}
					} else {
						if (pktMaster.get().getIsHold() == true) {
							stoneUnderBuisnessProcess.add(pktMaster.get().getStoneId());
						} else {

							Optional<OfferDiscountRequest> offerDiscountRequestOfUser = offerDiscountRequestRepository
									.findByUserAndPkt(userId, offerDiscountRequest.getPktMasterId());
							if (offerDiscountRequestOfUser.isPresent()) {
								alreadyInOffer.add(pktMaster.get().getStoneId());
							} else {
								offerDiscountRequest.setUser(user.get());
								offerDiscountRequest.setPktMaster(pktMaster.get());
								offerDiscountRequestRepository.saveAndFlush(offerDiscountRequest);
								addedToOffer.add(pktMaster.get().getStoneId());
							}

						}
					}
				}
			}
		});
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(confirmOrderList) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(confirmOrderList.toString())
						+ " already Confirmed.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(stoneUnderBuisnessProcess) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(stoneUnderBuisnessProcess.toString())
						+ " stone(s) under buisness process.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersConfirmOrder) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersConfirmOrder.toString())
						+ " this stone(s) have been confirmed by other.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(alreadyInOffer) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(alreadyInOffer.toString())
						+ " already applied for Offer Discount.\n ");
		result.append(ShivamWebMethodUtils.isSetNullOrEmpty(addedToOffer) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToOffer.toString())
						+ " added to Offer Discount. ");
		if (!ShivamWebMethodUtils.isSetNullOrEmpty(addedToOffer)) {
			userTrackingService.saveTracking(user.get(), request.getRemoteAddr(),
					EnumForUserTracking.STONEID.toString()
							+ ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToOffer.toString())
							+ EnumForUserTracking.STONEADDEDTOOFFER);
			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {
					// notification

					Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
					Notification notification = new Notification();
					notification.setDescription(EnumForNotificationDescription.STONEOFFERMADE.toString());
					notification.setSetOfUserObject(users);
					notification.setCategory(EnumForNotificationType.OFFEREQUEST.toString());
					notification.setIsAdmin(true);
					notification.setStoneOrUserId(user.get().getUsername());
					notificationService.sendNotification(notification);
					for (User user2 : users) {
						sendMailService.sendMailForOfferDiscount(user2, user.get(),
								ShivamWebMethodUtils.remove1StAndLastCharOfString(addedToOffer.toString()));
					}

				}
			});
			service.shutdown();
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, result.toString(), null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllOfferDiscount(String userId, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {

			List<OfferDiscountRequest> list = offerDiscountRequestRepository
					.findAllByApproveStatusOrderByCreatedDateDesc(EnumForOfferDiscountStatus.PENDING.toString());
			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
				for (OfferDiscountRequest offerDiscountRequest : list) {
					disc = new ArrayList<>();
					getDiscount(offerDiscountRequest);
					offerDiscountRequest.setPerCaratePriceNew(offerDiscountRequest.getPktMaster().getgRap()
							* (100 - offerDiscountRequest.getUserDiscount()) / 100);
					offerDiscountRequest
							.setTotalPriceNew(Double.valueOf(df.format(offerDiscountRequest.getPerCaratePriceNew()
									* offerDiscountRequest.getPktMaster().getCarat())));
				}
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all offer request", list, HttpStatus.OK,
					path);
		}
		List<OfferDiscountRequest> list = offerDiscountRequestRepository.findAllByUser(userId);
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list)) {
			for (OfferDiscountRequest offerDiscountRequest : list) {
				disc = new ArrayList<>();
				getDiscount(offerDiscountRequest);

				offerDiscountRequest.setPerCaratePriceNew(offerDiscountRequest.getPktMaster().getgRap()
						* (100 - offerDiscountRequest.getUserDiscount()) / 100);
				offerDiscountRequest.setTotalPriceNew(Double.valueOf(df.format(
						offerDiscountRequest.getPerCaratePriceNew() * offerDiscountRequest.getPktMaster().getCarat())));
			}
			double finalTCTS = totalWeight;
			double finalDiscountPercentage = (1 - (total / totalRapRate)) * 100;

			double finalRapRate = totalRapRate / finalTCTS;
			double finalPriceOrFinalCarat = (finalRapRate * (100 - finalDiscountPercentage)) / 100;
			double finalTotal = total;

			list.get(0).getPktMaster().setFinalTCTS(Double.valueOf(df.format(finalTCTS)));
			list.get(0).getPktMaster().setFinalDiscountPercentage(Double.valueOf(df.format(finalDiscountPercentage)));
			list.get(0).getPktMaster().setFinalRapRate(Double.valueOf(df.format(finalRapRate)));
			list.get(0).getPktMaster().setFinalPriceOrFinalCarat(Double.valueOf(df.format(finalPriceOrFinalCarat)));
			list.get(0).getPktMaster().setFinalTotal(Double.valueOf(df.format(finalTotal)));
			list.get(0).setTotalNoOfRecords(list.size());

		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all offer request of user", list, HttpStatus.OK,
				path);
	}

	@Override
	public ResponseWrapperDTO deleteOfferDiscount(String offerDiscountId, String path) {
		Optional<OfferDiscountRequest> offerDiscountRequest = offerDiscountRequestRepository.findById(offerDiscountId);
		if (!offerDiscountRequest.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Offer Discount request not fount.", null,
					HttpStatus.BAD_REQUEST, path);
		offerDiscountRequestRepository.deleteById(offerDiscountId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Offer Discount request deleted successfully.", null,
				HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateOfferStatus(String offerDiscountId, MyRequestBody body, String path, String ip) {
		Optional<OfferDiscountRequest> offerDiscountRequestDb = offerDiscountRequestRepository
				.findById(offerDiscountId);
		if (!offerDiscountRequestDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Offer Discount request not fount.", null,
					HttpStatus.BAD_REQUEST, path);
		String oldStatus = offerDiscountRequestDb.get().getApproveStatus();
		String userId = body.getUserId();
		Optional<User> user = userRepository.findById(userId);
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);

		OfferDiscountRequest confirmOrder = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(),
				OfferDiscountRequest.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(confirmOrder.getApproveStatus()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		offerDiscountRequestDb.get().setApproveStatus(confirmOrder.getApproveStatus());
		offerDiscountRequestRepository.saveAndFlush(offerDiscountRequestDb.get());

		if (!confirmOrder.getApproveStatus().equals(oldStatus))
			userTrackingService.saveTracking(user.get(), ip,
					EnumForUserTracking.STONEID.toString() + offerDiscountRequestDb.get().getPktMaster().getStoneId()
							+ " Offer " + confirmOrder.getApproveStatus());

		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				Set<User> users = new HashSet<>();
				users.add(offerDiscountRequestDb.get().getUser());
				Notification notification = new Notification();
				notification.setDescription("Your stone offer " + confirmOrder.getApproveStatus() + " by Admin.");
				notification.setSetOfUserObject(users);
				notification.setCategory(EnumForNotificationType.PLACE_OFFER.toString());
				notification.setIsAdmin(false);
				notification.setStoneOrUserId(offerDiscountRequestDb.get().getPktMaster().getStoneId());
				notificationService.sendNotification(notification);
			}
		});
		service.shutdown();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Offer Discount status changed to " + confirmOrder.getApproveStatus(), null, HttpStatus.OK, path);
	}

	double totalWeight = 0;
	double total = 0;
	double totalRapRate = 0;

	@Override
	public void getDiscount(OfferDiscountRequest offerDiscountRequest) {
		PktMaster responsePktMast = offerDiscountRequest.getPktMaster();
		// Set image path for web and app
				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast)) {
					ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(responsePktMast.getShape())
							.orElse(new ShapeMaster());
					responsePktMast.setShapeImage(shapeImage.getShapeImage());
				}
		
		responsePktMast.setDiscOrignal(responsePktMast.getDisc());
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
				&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
						.contains(responsePktMast.getfColor().trim().toLowerCase())) {
			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			responsePktMast.setDisc((double) 0);
		} else {
			/* Add user wise discount */
			disc = discountMasterService.getDiscountByUserId(offerDiscountRequest.getUser().getId(),
					responsePktMast.getGiDate(), responsePktMast.getCarat(), responsePktMast.getShape(), false);
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

		responsePktMast.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));

		// /* End Discount */

		totalWeight = totalWeight + responsePktMast.getCarat();
		totalRapRate = totalRapRate + (responsePktMast.getCarat() * responsePktMast.getgRap());
		responsePktMast.setDisc(perCaratDisc);
		responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
		total = total + (responsePktMast.getCarat() * responsePktMast.getPerCaratePrice()); // weight*percarat
		responsePktMast.setTotalPrice(
				Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
		///

	}
}
