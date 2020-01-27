package com.techhive.shivamweb.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.Cart;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.model.ViewRequest;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.repository.ViewRequestRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.schedular.ReleseStoneSchedular;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.service.ViewRequestService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class ViewRequestServiceImpl implements ViewRequestService {

	@Autowired
	ViewRequestRepository viewRequestRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	CartRepository cartRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	DiscountMasterService discountMasterService;

	@Autowired
	NotificationService notificationService;

	
	@Autowired
	ShapeMasterRepository shapeMasterRepository;
	
	DecimalFormat df = new DecimalFormat("####0.00");
	List<Double> disc;

	double totalWeight = 0;
	double total = 0;
	double totalRapRate = 0;

	@Override
	public ResponseWrapperDTO saveViewRequest(MyRequestBody body, HttpServletRequest request)
			throws SchedulerException, ParseException {
		String path = request.getServletPath();
		String userId = body.getUserId();
		Optional<User> user = userRepository.findById(userId);
		String ipAddress = request.getRemoteAddr();
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, path);

		StringBuilder response = new StringBuilder();
		Set<String> yourviewRequest = new HashSet<>();
		Set<String> othersViewRequest = new HashSet<>();
		Set<String> successViewRequest = new HashSet<>();
		Set<String> yourConifrmOrder = new HashSet<>();
		Set<String> othersConfirm = new HashSet<>();
		List<ViewRequest> ViewRequests = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<ViewRequest>>() {
				});
		if (ShivamWebMethodUtils.isListNullOrEmpty(ViewRequests))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Date from = new Date();
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(from); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, 2); // adds one hour
		cal.getTime();
		List<ViewRequest> viewRequestList = new ArrayList<>();
		ViewRequests.forEach(ViewRequest -> {

			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(ViewRequest.getPktMasterId())) {
				Optional<PktMaster> pktMaster = pktMasterRepository.findById(ViewRequest.getPktMasterId());
				if (pktMaster.isPresent() && pktMaster.get().getIsSold() == true) {
					if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(pktMaster.get().getConfirmOrder())&&pktMaster.get().getConfirmOrder().getUser().getId().equals(userId)) {
						yourConifrmOrder.add(pktMaster.get().getStoneId());
					} else {
						othersConfirm.add(pktMaster.get().getStoneId());
					}
				}
				if (pktMaster.isPresent() && pktMaster.get().getIsSold() == false) {
					if (pktMaster.get().getIsHold() == true) {
						Optional<ViewRequest> ViewReq = viewRequestRepository.findAllByUserAndPktInProgress(userId,
								ViewRequest.getPktMasterId());
						if (ViewReq.isPresent()) {
							yourviewRequest.add(pktMaster.get().getStoneId());
						} else {
							othersViewRequest.add(pktMaster.get().getStoneId());
						}
					} else {
						ViewRequest.setUser(user.get());
						ViewRequest.setPktMaster(pktMaster.get());
						ViewRequest.setIpAddress(ipAddress);
						ViewRequest.setInProgress(true);
						ViewRequest.setEndDate(cal.getTime());
						viewRequestRepository.saveAndFlush(ViewRequest);
						viewRequestList.add(ViewRequest);
						successViewRequest.add(pktMaster.get().getStoneId());
						// remove from cart
						Optional<Cart> cartOfUser = cartRepository.findByUserAndPkt(userId,
								ViewRequest.getPktMasterId());
						if (cartOfUser.isPresent()) {
							cartRepository.deleteById(cartOfUser.get().getId());
						}
						// /// remove from wishlist
						// Optional<Wishlist> wishlistOfUser =
						// wishlistRepository.findByUserAndPkt(userId,
						// ViewRequest.getPktMasterId());
						// if (wishlistOfUser.isPresent()) {
						// wishlistRepository.deleteById(wishlistOfUser.get().getId());
						// }
						// // remove from offer
						// Optional<OfferDiscountRequest> offerDiscountRequestOfUser =
						// offerDiscountRequestRepository
						// .findByUserAndPkt(userId, ViewRequest.getPktMasterId());
						// if (offerDiscountRequestOfUser.isPresent()) {
						// offerDiscountRequestRepository.deleteById(offerDiscountRequestOfUser.get().getId());
						// }
						pktMaster.get().setIsHold(true);
						pktMasterRepository.saveAndFlush(pktMaster.get());

					}
				}
			}
		});
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(yourviewRequest) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(yourviewRequest.toString())
						+ " you have already request to view this stone(s).\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersViewRequest) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersViewRequest.toString())
						+ " this stone(s) have been requested for view by other.\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(yourConifrmOrder) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(yourConifrmOrder.toString())
						+ " you have already confirmed this stone(s).\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(othersConfirm) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(othersConfirm.toString())
						+ " this stone(s) have been confirmed by other.\n ");
		response.append(ShivamWebMethodUtils.isSetNullOrEmpty(successViewRequest) ? ""
				: ShivamWebMethodUtils.remove1StAndLastCharOfString(successViewRequest.toString())
						+ " stone(s) request to view successfully.\n");

		if (!ShivamWebMethodUtils.isSetNullOrEmpty(successViewRequest)) {
			String message = EnumForUserTracking.STONEID.toString()
					+ ShivamWebMethodUtils.remove1StAndLastCharOfString(successViewRequest.toString())
					+ EnumForUserTracking.STONEREQUESTEDFORVIEW.toString();
			userTrackingService.saveTracking(user.get(), request.getRemoteAddr(), message);

			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {

					// notification
					Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
					Notification notification = new Notification();
					notification.setDescription(EnumForNotificationDescription.NEWSTONEVIEWREQUEST.toString());
					notification.setSetOfUserObject(users);
					notification.setCategory(EnumForNotificationType.VIEWREQUEST.toString());
					notification.setIsAdmin(true);
					notification.setStoneOrUserId(user.get().getUsername());
					notificationService.sendNotification(notification);

					// send mail to user
					sendMailService.sendMailForViewRequest(user.get(), from, cal.getTime(),
							ShivamWebMethodUtils.remove1StAndLastCharOfString(successViewRequest.toString()));
					Set<User> admins = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
					if (!admins.isEmpty())
						sendMailService.sendMailForViewRequestToAdmin(admins, user.get(), from, cal.getTime(),
								ShivamWebMethodUtils.remove1StAndLastCharOfString(successViewRequest.toString()));

					// schedular
					try {
						schedular(viewRequestList);
					} catch (SchedulerException e) {
						e.printStackTrace();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			});
			service.shutdown();
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, response.toString(), null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO getAllViewRequest(String userId, Boolean inProgress, Integer pageNumber,
			Integer noOfRecords, String sortColumn, String sortOrder, String path) {
		totalWeight = 0;
		total = 0;
		totalRapRate = 0;
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			Page<ViewRequest> list = viewRequestRepository.findAllByInProgress(inProgress, request);
			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list.getContent())) {
				for (ViewRequest viewRequest : list.getContent()) {
					disc = new ArrayList<>();
					getDiscount(viewRequest);
				}
			}
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all view Request", list, HttpStatus.OK, path);
		}
		Page<ViewRequest> list = viewRequestRepository.findAllByUserAndInProgress(userId, inProgress, request);
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(list.getContent())) {
			for (ViewRequest viewRequest : list.getContent()) {
				disc = new ArrayList<>();
				getDiscount(viewRequest);
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all view Request of user", list, HttpStatus.OK,
				path);
	}

	@Override
	public ResponseWrapperDTO deleteViewRequest(String viewRequestId, String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ViewRequest getDiscount(ViewRequest viewRequest) {

		PktMaster responsePktMast = viewRequest.getPktMaster();
	

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
			disc = discountMasterService.getDiscountByUserId(viewRequest.getUser().getId(), responsePktMast.getGiDate(),
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
		viewRequest.setPktMaster(responsePktMast);
		return viewRequest;
	}

	/***
	 * @author neel
	 * @param viewRequestList
	 *            generate schedular for two hours from now for every successful
	 *            view request
	 */
	public void schedular(List<ViewRequest> viewRequestList) throws SchedulerException, ParseException {
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(viewRequestList)) {
			String expression = null;
			for (ViewRequest viewRequest : viewRequestList) {

				try {

					JobKey jobKey = JobKey.jobKey(viewRequest.getId(), "ReleseStoneSchedular");

					JobDetail job = JobBuilder.newJob(ReleseStoneSchedular.class).withIdentity(jobKey).build();
					//
					// Trigger trigger = TriggerBuilder.newTrigger()
					// .withIdentity(viewRequest.getId(), "ReleseStoneSchedular").startNow()
					// .withSchedule(CronScheduleBuilder.cronSchedule(expression)
					// .withMisfireHandlingInstructionDoNothing())
					// .build();
					//
					Calendar cal = Calendar.getInstance(); // creates calendar
					cal.setTime(viewRequest.getEndDate()); // sets calendar time/date
					expression = "* " + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.HOUR_OF_DAY) + " * * ?";
					Trigger trigger = TriggerBuilder.newTrigger()
							.withIdentity(viewRequest.getId(), "ReleseStoneSchedular").startNow()
							.withSchedule(CronScheduleBuilder.cronSchedule(expression)
									.withMisfireHandlingInstructionFireAndProceed())
							.build();
					// viewRequest.setEndDate(cal.getTime());
					// viewRequest.setPktMasterId(viewRequest.getPktMasterId());
					// viewRequestRepository.saveAndFlush(viewRequest);
					SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
					Scheduler sched = schedFact.getScheduler();

					sched.deleteJob(jobKey);
					sched.start();
					sched.scheduleJob(job, trigger);
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
