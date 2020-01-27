package com.techhive.shivamweb.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.enums.EnumForUserTracking;
import com.techhive.shivamweb.master.model.AppVersionMaster;
import com.techhive.shivamweb.master.model.SalesPersonMaster;
import com.techhive.shivamweb.master.model.SoftwarePartyMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.UserMigration;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.AppVersionMasterRepository;
import com.techhive.shivamweb.repository.SalesPersonMasterRepository;
import com.techhive.shivamweb.repository.SoftwarePartyMasterRespository;
import com.techhive.shivamweb.repository.UserMigrationRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.LoginResponse;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.security.JwtTokenProvider;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.ResultScreenLabelsService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.UserService;
import com.techhive.shivamweb.service.UserTrackingService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserMigrationRepository userMigrationRepository;

	@Autowired
	UserTrackingService userTrackingService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	SendMailService sendMailService;

	@Autowired
	SalesPersonMasterRepository salesPersonMasterRepository;

	@Autowired
	ResultScreenLabelsService resultScreenLabelsService;

	@Autowired
	SoftwarePartyMasterRespository softwarePartyMasterRespository;

	@Autowired
	AppVersionMasterRepository appVersionMasterRepository;

	@Autowired
	NotificationService notificationService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseEntity<?> registerUser(User user, MyRequestBody body, HttpServletRequest request,
			String urlForEmailVerification, boolean userMigration) {
		User responseUser = userRepository.saveAndFlush(user);
		resultScreenLabelsService.saveResultScreenLableValueByUserId(responseUser.getId());
		String remoteAddres = request.getRemoteAddr();
		userTrackingService.saveTracking(responseUser, remoteAddres, EnumForUserTracking.NEWREGISTRATION.toString());
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string()))
			uplodeImage(body.getBase64string(), responseUser);
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				sendMailService.sendMailForRegistrationVerifactrion(responseUser, urlForEmailVerification);
			}
		});
		service.shutdown();
		if (userMigration == true)
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK,
					ShivamWebVariableUtils.REGISTER_OR_SENDMAIL_SUCCESS_MIGRATION, null, request.getServletPath()),
					HttpStatus.OK);

		return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				ShivamWebVariableUtils.REGISTER_OR_SENDMAIL_SUCCESS, null, request.getServletPath()), HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO findByEmailOrUsernameAndPassword(String userNameOrEmail, String password, String fcmToken,
			Boolean isTracking, HttpServletRequest request) {
		Optional<User> user = userRepository.findByUsernameOrEmail(userNameOrEmail);
		if (user.isPresent() && !user.get().getIsApproved()) {
			System.err.println("11111111111111111111111");

			return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED,
					ShivamWebVariableUtils.MSG_FOR_WAIT_ADMIN_APPROVE, null, HttpStatus.UNAUTHORIZED);
		}
		System.err.println("2222222222222222222222");
		if (user.isPresent()
				&& (user.get().getUsername() != null ? user.get().getUsername().equalsIgnoreCase(userNameOrEmail)
						: user.get().getEmail().equalsIgnoreCase(userNameOrEmail)
								|| user.get().getEmail().equalsIgnoreCase(userNameOrEmail))
				&& passwordEncoder.matches(password, user.get().getPassword()) && user.get().getIsApproved()) {
			String remoteAddres = request.getRemoteAddr();
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userNameOrEmail, password));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = tokenProvider.generateToken(authentication);
			user.get().setIsAuthenticated(true);
			LoginResponse response = new LoginResponse(user.get(), new UserMigration());
			response.setIsMigration(false);
			response.setToken(jwt);

			// get app version detail
			AppVersionMaster AppVersionMaster = new AppVersionMaster();
			if (!appVersionMasterRepository.findAll().isEmpty()) {
				AppVersionMaster = appVersionMasterRepository.findAll().get(0);
			}
			response.setAndroidVersion(AppVersionMaster.getAndroidVersion());
			response.setIosVersion(AppVersionMaster.getIosVersion());

			user.get().setFcmToken(fcmToken);
			userRepository.saveAndFlush(user.get());// save fcmToken in db
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(isTracking) && isTracking) {
				userTrackingService.saveTracking(user.get(), remoteAddres, EnumForUserTracking.LOGIN.toString());
			}

			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Welcome " + response.getFirstName(), response,
					HttpStatus.OK);
		}
		return userMigration(userNameOrEmail, password, request);
	}

	// check user name and password match on old user and response old user if
	// present
	private ResponseWrapperDTO userMigration(String userNameOrEmail, String password, HttpServletRequest request) {
		Optional<UserMigration> userMigration = userMigrationRepository.findByUsernameOrEmail(userNameOrEmail);
		if (userMigration.isPresent()) {
			if (passwordEncoder.matches(password, userMigration.get().getPassword())) {

				LoginResponse response2 = new LoginResponse(new User(), userMigration.get());
				response2.setIsMigration(true);
				return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Welcome "
						+ response2.getMigration().getFirstName()
						+ "<br>we have made new changes <br>so please provide few more details<br> and have a fresh start.",
						response2, HttpStatus.OK);
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials", null,
				HttpStatus.UNAUTHORIZED);
	}

	public ResponseWrapperDTO verify(String userId, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(userId);
		if (!userFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_NOT_ACCEPTABLE, ShivamWebVariableUtils.USER_NOT_FOUND,
					"notFound", HttpStatus.NOT_ACCEPTABLE, request.getServletPath());
		String remoteAddres = request.getRemoteAddr();

		if (userFromDb.get().getIsEmailVerified() == false) {
			userFromDb.get().setIsEmailVerified(true);
			userRepository.saveAndFlush(userFromDb.get());
			userTrackingService.saveTracking(userFromDb.get(), remoteAddres,
					EnumForUserTracking.EMAILVERIFIED.toString());
			ExecutorService service = Executors.newFixedThreadPool(4);
			service.submit(new Runnable() {
				public void run() {

					// notification
					Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
					Notification notification = new Notification();
					notification.setDescription(EnumForNotificationDescription.NEWUSERREGISTER.toString());
					notification.setSetOfUserObject(users);
					notification.setCategory(EnumForNotificationType.CLINTACTIVATION.toString());
					notification.setIsAdmin(true);
					notification.setStoneOrUserId(userFromDb.get().getUsername());
					notificationService.sendNotification(notification);
					for (User user2 : users) {
						sendMailService.sendMailToAdmin(user2, userFromDb.get());
					}
					sendMailService.sendMailToUserOfSuccess(userFromDb.get());
				}
			});
			service.shutdown();
		} else {
			return new ResponseWrapperDTO(HttpServletResponse.SC_NOT_ACCEPTABLE, "Sorry, the link has been expired.",
					"expired", HttpStatus.NOT_ACCEPTABLE, request.getServletPath());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Email verified waiting for approval by admin.",
				"Success", HttpStatus.OK, request.getServletPath());

	}

	@Override
	public ResponseWrapperDTO getAllUserForClientActivation(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, Boolean isApprove, String path) {

		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");

		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(searchText)) {
			searchText = searchText.trim();
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Client List",
					userRepository.findAllByIsEmailVerifiedSearch(searchText, request), HttpStatus.OK, path);
		}
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(isApprove)) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Client List For Activation",
					userRepository.findAllByIsEmailVerified(request), HttpStatus.OK, path);
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Client List For Activation",
				userRepository.findAllByIsEmailVerifiedAndIsApprove(isApprove, request), HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeApproveStatus(String id, MyRequestBody body, HttpServletRequest request) {

		Optional<User> userFromDb = userRepository.findById(id);
		Optional<User> loginUserFromDb = userRepository.findById(body.getUserId());
		Boolean flag = userFromDb.get().getIsApproved();
		if (!userFromDb.isPresent() || !loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getIsApproved()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		String status = null;
		if (user.getIsApproved() == true) {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(userFromDb.get().getSoftwarePartyMaster()))
				return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
						"Cannot activate assign Party to User first.", null, HttpStatus.BAD_REQUEST,
						request.getServletPath());
			status = "Activated";
			userFromDb.get().setIsApproved(true);
			userFromDb.get().setApproveDate(new Date());
		} else {
			status = "Deactivated";
			userFromDb.get().setIsApproved(false);
		}

		userRepository.saveAndFlush(userFromDb.get());
		if (user.getIsApproved() == true) {
			if (flag == false) {
				ExecutorService service = Executors.newFixedThreadPool(4);
				service.submit(new Runnable() {
					public void run() {
						userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
								userFromDb.get().getUsername() + EnumForUserTracking.CLINTACTIVATED.toString());
						sendMailService.sendMailForApproved(userFromDb);
					}
				});
				service.shutdown();
			}
		} else {
			if (flag == true) {
				userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
						userFromDb.get().getUsername() + EnumForUserTracking.CLINTDEACTIVATED.toString());
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "User " + status + " successfully.", null,
				HttpStatus.OK, request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO assignSalesPerson(String id, MyRequestBody body, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(id);
		Optional<User> loginUserFromDb = userRepository.findById(body.getUserId());
		SalesPersonMaster old = userFromDb.get().getSalesPersonMaster();
		if (!userFromDb.isPresent() || !loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getSalesPersonId()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		Optional<SalesPersonMaster> salesPerson = salesPersonMasterRepository.findById(user.getSalesPersonId());
		if (!salesPerson.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Sales Person not found.", null,
					HttpStatus.BAD_REQUEST, request.getServletPath());
		userFromDb.get().setSalesPersonMaster(salesPerson.get());
		userRepository.saveAndFlush(userFromDb.get());
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(old)) {
			userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(), salesPerson.get().getName()
					+ EnumForUserTracking.ASSIGNSALESPERSON.toString() + userFromDb.get().getUsername());
		} else {
			if (!old.getId().equals(salesPerson.get().getId())) {
				userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
						salesPerson.get().getName() + EnumForUserTracking.ASSIGNSALESPERSON.toString()
								+ userFromDb.get().getUsername());
			}
		}
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				// notification
				Set<User> users = new HashSet<>();
				users.add(userFromDb.get());
				Notification notification = new Notification();
				notification.setDescription(EnumForNotificationDescription.SALESPERSONASSIGNED.toString());
				notification.setSetOfUserObject(users);
				notification.setCategory(EnumForNotificationType.SALSPERSON.toString());
				notification.setIsAdmin(false);
				notificationService.sendNotification(notification);
			}
		});
		service.shutdown();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Sales Person Assigned successfully.", null,
				HttpStatus.OK, request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO deleteClient(String id, String loginUserId, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(id);
		Optional<User> loginUserFromDb = userRepository.findById(loginUserId);
		if (!userFromDb.isPresent() || !loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		Boolean flag = false;
		flag = userFromDb.get().getIsDeleted();
		userFromDb.get().setIsDeleted(true);
		userFromDb.get().setSoftwarePartyMaster(null);
		userRepository.saveAndFlush(userFromDb.get());
		if (flag == false) {
			userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
					userFromDb.get().getUsername() + EnumForUserTracking.CLINTDELETED.toString());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Client " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO changeClientProfile(String id, MyRequestBody body, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(id);
		if (!userFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		userFromDb.get().setPrefix(user.getPrefix());
		userFromDb.get().setFirstName(user.getFirstName());
		userFromDb.get().setLastName(user.getLastName());
		userFromDb.get().setGender(user.getGender());
		userFromDb.get().setCountry(user.getCountry());
		userFromDb.get().setState(user.getState());
		userFromDb.get().setCity(user.getCity());
		userFromDb.get().setPinCode(user.getPinCode());
		userFromDb.get().setCompanyName(user.getCompanyName());
		userFromDb.get().setCompanyAddress(user.getCompanyAddress());
		userFromDb.get().setPhoneNo(user.getPhoneNo());
		userFromDb.get().setMobileNo(user.getMobileNo());
		userFromDb.get().setMobileCCode(user.getMobileCCode());
		userFromDb.get().setTeleCCode(user.getTeleCCode());
		userFromDb.get().setTeleACode(user.getTeleACode());
		userFromDb.get().setProfile(user.getProfile());
		userRepository.saveAndFlush(userFromDb.get());
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string()))
			uplodeImage(body.getBase64string(), userFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Client Profile " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK,
				request.getServletPath());
	}

	public void uplodeImage(String base64String, User user) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					String base64Image = base64String.split(",")[1];
					byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String rootPath = System.getProperty("catalina.home");
					System.err.println("location for upload businesscard");
					File dir = new File(rootPath + File.separator + "webapps/ShivamImage/profilePicture");
					if (!dir.exists())
						dir.mkdirs();
					String path = dir.getAbsolutePath();
					String fileName = "imgProfile" + user.getId() + ".jpg";
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + (fileName)));
					bout.write(bytes);
					bout.flush();
					bout.close();
					user.setProfile(fileName);
					userRepository.saveAndFlush(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
	}

	@Override
	public ResponseWrapperDTO changePassword(String id, MyRequestBody body, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(id);
		if (!userFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getPassword()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		if (!passwordEncoder.matches(body.getOtherData(), userFromDb.get().getPassword()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED, "Incorrect old password.", null,
					HttpStatus.UNAUTHORIZED, request.getServletPath());
		userFromDb.get().setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.saveAndFlush(userFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Password changed Successfully.", null, HttpStatus.OK,
				request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO checkResetPassRequest(String userId) {
		Optional<User> userFromDB = userRepository.findById(userId);
		if (!userFromDB.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.NOT_FOUND_IN_DB,
					null, HttpStatus.BAD_REQUEST);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userFromDB.get().getPwdRequestDateTime())) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Invalid reset password link. ", false,
					HttpStatus.OK);
		}
		long totalMinut = ShivamWebMethodUtils.getTotalNoOfMinut(userFromDB.get().getPwdRequestDateTime());
		if ((60 * ShivamWebVariableUtils.RESET_PASSWORD_TIME_LIMIT) < totalMinut) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Exceed your time limit for change password. ",
					false, HttpStatus.OK);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, " Check reset password status ", true, HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO resetPassword(String newPassword, String userId) {
		Optional<User> userFromDB = userRepository.findById(userId);
		if (!userFromDB.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.NOT_FOUND_IN_DB,
					null, HttpStatus.BAD_REQUEST);

		userFromDB.get().setPassword(passwordEncoder.encode(newPassword));
		userFromDB.get().setPwdRequestDateTime(null);
		userRepository.saveAndFlush(userFromDB.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Reset password successfully.", true, HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO setFirebaseToken(String userId, String fcmToken, HttpServletRequest request) {
		Optional<User> userFromDB = userRepository.findById(userId);
		if (!userFromDB.isPresent()) {
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		}
		userFromDB.get().setFcmToken(fcmToken);
		userRepository.saveAndFlush(userFromDB.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Create/Update fcmToken successfully.", true,
				HttpStatus.OK);
	}

	@Override
	public ResponseWrapperDTO assignParty(String id, MyRequestBody body, HttpServletRequest request) {

		Optional<User> userFromDb = userRepository.findById(id);
		Optional<User> loginUserFromDb = userRepository.findById(body.getUserId());
		if (!userFromDb.isPresent() || !loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getSoftwarePartyId()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		Optional<SoftwarePartyMaster> softwareParty = softwarePartyMasterRespository
				.findById(user.getSoftwarePartyId());
		if (!softwareParty.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Party not found.", null,
					HttpStatus.BAD_REQUEST, request.getServletPath());
		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(softwareParty.get().getUser())) {
			userFromDb.get().setSoftwarePartyMaster(softwareParty.get());
			userRepository.saveAndFlush(userFromDb.get());
			userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
					softwareParty.get().getPartyName() + EnumForUserTracking.PARTYASSIGNED.toString()
							+ userFromDb.get().getUsername());
		} else {

			if (!softwareParty.get().getUser().getId().equals(userFromDb.get().getId())
					&& softwareParty.get().getUser().getIsSuperAdmin()) {
				return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"'" + softwareParty.get().getPartyName() + "'" + " party already reserve for supper admin.",
						null, HttpStatus.CONFLICT, request.getServletPath());
			}

			if (!softwareParty.get().getUser().getId().equals(userFromDb.get().getId()))
				return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"'" + softwareParty.get().getPartyName() + "'" + " party already assigned to another User.",
						null, HttpStatus.CONFLICT, request.getServletPath());

			userFromDb.get().setSoftwarePartyMaster(softwareParty.get());
			userRepository.saveAndFlush(userFromDb.get());
			userTrackingService.saveTracking(loginUserFromDb.get(), request.getRemoteAddr(),
					softwareParty.get().getPartyName() + EnumForUserTracking.PARTYASSIGNED.toString()
							+ userFromDb.get().getUsername());
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Party Assigned successfully.", null, HttpStatus.OK,
				request.getServletPath());
	}

	@Override
	public ResponseWrapperDTO setUserAsAdmin(String id, MyRequestBody body, HttpServletRequest request) {
		Optional<User> userFromDb = userRepository.findById(id);
		Optional<User> loginUserFromDb = userRepository.findById(body.getUserId());

		if (!userFromDb.isPresent() || !loginUserFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST, request.getServletPath());
		User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getIsAdmin()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(request.getServletPath());
		String status = null;
		if (user.getIsAdmin() == true) {
			status = "User set as Admin";
			userFromDb.get().setIsAdmin(true);
			userFromDb.get().setApproveDate(new Date());
		} else {
			status = "Admin set as User";
			userFromDb.get().setIsAdmin(false);
		}
		userRepository.saveAndFlush(userFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, status + " successfully.", null, HttpStatus.OK,
				request.getServletPath());
	}

	@Override
	public ResponseEntity<?> registerUserForShow(User user, MyRequestBody body, HttpServletRequest request) {

		User responseUser = new User();

		Optional<SalesPersonMaster> salesPerson = salesPersonMasterRepository.findById(user.getSalesPersonId());
		if (!salesPerson.isPresent())
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
					"Sales Person not found.", null, HttpStatus.BAD_REQUEST, request.getServletPath()), HttpStatus.OK);

		Optional<User> userFromDb = userRepository.findByUsernameOrEmail(user.getEmail());
		user.setSalesPersonMaster(salesPerson.get());

		if (!userFromDb.isPresent()) {
			System.out.println("is no present ");
			user.setPassword(passwordEncoder.encode(ShivamWebVariableUtils.STATIC_PASSWORD_FOR_COMPAING_USER)); // set
			user.setIsAdmin(false);
			user.setIsApproved(false);
			user.setIsEmailVerified(false);
			user.setIsSuperAdmin(false);
			user.setIsApproved(true);
			user.setIsShow(true);
			user.setIpAddress(request.getRemoteAddr());
			responseUser = userRepository.saveAndFlush(user);
		} else {
			System.err.println("Is present..");
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(salesPerson.get())) {
				userFromDb.get().setSalesPersonMaster(salesPerson.get());
			}
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(user.getFirstName())) {
				userFromDb.get().setFirstName(user.getFirstName());
			}
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(user.getCompanyName())) {
				userFromDb.get().setCompanyName(user.getCompanyName());
			}
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(user.getCountry())) {
				userFromDb.get().setCountry(user.getCountry());
			}
			// userFromDb.get()
			// .setPassword(passwordEncoder.encode(ShivamWebVariableUtils.STATIC_PASSWORD_FOR_COMPAING_USER));

			responseUser = userRepository.saveAndFlush(userFromDb.get());

		}

		// String remoteAddres = request.getRemoteAddr();
		// userTrackingService.saveTracking(responseUser, remoteAddres,
		// EnumForUserTracking.NEW_USER_REGISTRATION_FOR_CAMPAING.toString());

		resultScreenLabelsService.saveResultScreenLableValueByUserId(responseUser.getId());

		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string())) {
			uplodeImageForShow(body.getBase64string(), responseUser);
		}
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				user.getEmail(), ShivamWebVariableUtils.STATIC_PASSWORD_FOR_COMPAING_USER));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		user.setIsAuthenticated(true);
		LoginResponse response = new LoginResponse(responseUser, new UserMigration());
		response.setToken(jwt);

		return new ResponseEntity<>(
				new ResponseWrapperDTO(HttpServletResponse.SC_OK,
						ShivamWebVariableUtils.MSG_FOR_CAMPING_USER_REGISTERED, response, request.getServletPath()),
				HttpStatus.OK);

	}

	/* upload bussiness card at registration time use for show */
	public void uplodeImageForShow(String base64String, User user) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					String base64Image = base64String.split(",")[1];
					byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps/ShivamImage/bussinessCard");
					if (!dir.exists())
						dir.mkdirs();
					String path = dir.getAbsolutePath();
					String fileName = "imgProfile" + user.getId() + ".jpg";
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + (fileName)));
					bout.write(bytes);
					bout.flush();
					bout.close();
					user.setProfile(fileName);
					userRepository.saveAndFlush(user);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
	}

	// @Override
	// public ResponseWrapperDTO getNewAccessTocken(String userId) {
	// Optional<User> user = userRepository.findById(userId);
	//
	// if (user.isPresent() && !user.get().getIsApproved()) {
	// return new ResponseWrapperDTO(HttpServletResponse.SC_UNAUTHORIZED,
	// ShivamWebVariableUtils.MSG_FOR_WAIT_ADMIN_APPROVE, null,
	// HttpStatus.UNAUTHORIZED);
	// }
	//
	// System.err.println("user Email ::" + user.get().getEmail());
	// System.err.println("user password ::" + user.get().getPassword());
	// Authentication authentication = authenticationManager
	// .authenticate(new UsernamePasswordAuthenticationToken(user.get().getEmail(),
	// "Test@123"));
	//
	//
	// SecurityContextHolder.getContext().setAuthentication(authentication);
	//
	// String jwt = tokenProvider.generateToken(authentication);
	// user.get().setIsAuthenticated(true);
	// LoginResponse response = new LoginResponse(user.get(), new UserMigration());
	// response.setIsMigration(false);
	// response.setToken(jwt);
	//
	// // get app version detail
	//
	// List<AppVersionMaster> AppVersionMaster =
	// appVersionMasterRepository.findAll();
	//
	// if (!ShivamWebMethodUtils.isListIsNullOrEmpty(AppVersionMaster)) {
	// response.setAndroidVersion(AppVersionMaster.get(0).getAndroidVersion());
	// response.setIosVersion(AppVersionMaster.get(0).getIosVersion());
	// }
	//

	//
	// return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Welcome " +
	// response.getFirstName(), response,
	// HttpStatus.OK);
	//
	// }

}
