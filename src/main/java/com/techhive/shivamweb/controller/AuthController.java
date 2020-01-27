package com.techhive.shivamweb.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.master.model.UserMigration;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.UserMigrationRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.UserService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserMigrationRepository userMigrationRepository;

	private boolean userMigration;

	@CrossOrigin
	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam String userNameOrEmail, @RequestParam String password,
			@RequestParam(required = false) String fcmToken, @RequestParam(required = false) Boolean isTracking,
			HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userNameOrEmail, password)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			// new with user name/email or password
			ResponseWrapperDTO response = userService.findByEmailOrUsernameAndPassword(userNameOrEmail, password,
					fcmToken, isTracking, request);

			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	// for migration send userId of migration in body.getUserId()

	@PostMapping(value = "registerUser")
	public ResponseEntity<?> registerUser(@RequestBody MyRequestBody body, HttpServletRequest request) {
		try {
			userMigration = false;
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
			String urlForEmailVerification;
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getUrlForSendMail())) {
				urlForEmailVerification = body.getUrlForSendMail();
			} else {
				urlForEmailVerification = ShivamWebVariableUtils.verifyEmailUrlForAndroid;
			}
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getEmail(), user.getUsername())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			if (userRepository.findByUsernameOrEmail(user.getUsername()).isPresent()) {

				ResponseWrapperDTO responseWrapperDTO = new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"User with username '" + user.getUsername() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
						request.getServletPath());

				return new ResponseEntity<>(responseWrapperDTO, HttpStatus.CONFLICT);
			}

			if (userRepository.findByUsernameOrEmail(user.getEmail()).isPresent()) {
				return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
						"User with email '" + user.getEmail() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
						request.getServletPath()), HttpStatus.CONFLICT);
			}
			// send body.getUserId() as old user id and on success registration delete that
			// record
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getUserId())) {
				Optional<UserMigration> migration = userMigrationRepository.findById(body.getUserId());
				if (!migration.isPresent())
					return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_ACCEPTED,
							"Migration user not found.", null, request.getServletPath()), HttpStatus.ACCEPTED);
				userMigration = true;
			}

			// if migration is false then check already exist of username and email in
			// migration table .
			if (userMigration == true) {
				if (userMigrationRepository.findByUsernameOrEmailNotIdIn(user.getUsername(), body.getUserId())
						.isPresent()) {
					return new ResponseEntity<>(
							new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
									"User with username '" + user.getUsername() + "' "
											+ ShivamWebVariableUtils.ALREADYEXIST,
									null, request.getServletPath()),
							HttpStatus.CONFLICT);
				}
				if (userMigrationRepository.findByUsernameOrEmailNotIdIn(user.getEmail(), body.getUserId())
						.isPresent()) {
					return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"User with email '" + user.getEmail() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
							request.getServletPath()), HttpStatus.CONFLICT);
				}
			} else {
				if (userMigrationRepository.findByUsernameOrEmail(user.getUsername()).isPresent()) {
					return new ResponseEntity<>(
							new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
									"User with username '" + user.getUsername() + "' "
											+ ShivamWebVariableUtils.ALREADYEXIST,
									null, request.getServletPath()),
							HttpStatus.CONFLICT);
				}
				if (userMigrationRepository.findByUsernameOrEmail(user.getEmail()).isPresent()) {
					return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
							"User with email '" + user.getEmail() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
							request.getServletPath()), HttpStatus.CONFLICT);
				}
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setIsAdmin(false);
			user.setIsApproved(false);
			user.setIsEmailVerified(false);
			user.setIsSuperAdmin(false);
			user.setIsShow(false);
			user.setIpAddress(request.getRemoteAddr());
			if (userMigration == true)
				userMigrationRepository.deleteById(body.getUserId());
			return userService.registerUser(user, body, request, urlForEmailVerification, userMigration);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}
	}

	@CrossOrigin
	@PutMapping(value = "verifyEmail/{userId}")
	public ResponseEntity<?> verifyEmail(@PathVariable(value = "userId") String userId, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.verify(userId, request);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}
	}

	/* Register User fro show */
	@PostMapping(value = "registerUserForShow")
	public ResponseEntity<?> registerUserForShow(@RequestBody MyRequestBody body, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			User user = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), User.class);
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(user.getEmail())
					|| ShivamWebMethodUtils.isObjectNullOrEmpty(user.getSalesPersonId())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}

//			user.setPassword(passwordEncoder.encode(ShivamWebVariableUtils.STATIC_PASSWORD_FOR_COMPAING_USER)); // set
			// password
//			user.setIsAdmin(false);
//			user.setIsApproved(false);
//			user.setIsEmailVerified(false);
//			user.setIsSuperAdmin(false);
//			user.setIsApproved(true);
//			user.setIsShow(true);
//			user.setIpAddress(request.getRemoteAddr());
			return userService.registerUserForShow(user, body, request);

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}
	}

}
