package com.techhive.shivamweb.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.service.UserService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@Autowired
	private SendMailService sendMailService;

	@GetMapping("getAllClient")
	public ResponseEntity<?> getAllClient(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Client",
					userRepository.findAllClient(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	
//	use for client activation page
	@GetMapping("getAllUserForClientActivation")
	public ResponseEntity<?> getAllUserForClientActivation(@RequestParam Integer pageNumber,
			@RequestParam Integer noOfRecords, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortOrder, @RequestParam(required = false) String searchText,
			@RequestParam(required = false) Boolean isApproved, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			return new ResponseEntity<>(userService.getAllUserForClientActivation(pageNumber, noOfRecords, sortColumn,
					sortOrder, searchText, isApproved, path), HttpStatus.OK);

		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("changeApproveStatus/{id}")
	private ResponseEntity<?> changeApproveStatus(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), body.getUserId(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.changeApproveStatus(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("setUserAsAdmin/{id}")
	private ResponseEntity<?> setUserAsAdmin(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {

		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), body.getUserId(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.setUserAsAdmin(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("assignSalesPerson/{id}")
	private ResponseEntity<?> assignSalesPerson(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), body.getUserId(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.assignSalesPerson(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("deleteClient/{id}")

	private ResponseEntity<?> deleteClient(@PathVariable("id") String id, @RequestParam String loginUserId,
			HttpServletRequest request) {

		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id, loginUserId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();

			response = userService.deleteClient(id, loginUserId, request);

			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("getAllApprovedUser")
	public ResponseEntity<?> getAllApprovedUser(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All User List.",
					userRepository.findAllApprovedUser(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	/* Forgot password/send mail for forgot password */

	@CrossOrigin
	@GetMapping(value = "allow/forgotPasswordRequest")
	@ResponseBody
	public ResponseEntity<?> sendMailForForgotPassword(@RequestParam String email,
			@RequestParam(required = false) String forgotPasswordUrl, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(email)) {
				return new ResponseEntity<>(
						new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
								ShivamWebVariableUtils.INCOMPLETE_DATA_FROM_ANGULAR, null, path),
						HttpStatus.BAD_REQUEST);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(forgotPasswordUrl)) {
				forgotPasswordUrl = ShivamWebVariableUtils.forgotPasswordUrlForAndroid;
			}

			response = sendMailService.sendMailForForgotPassword(email, forgotPasswordUrl, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	/* Check Reset password Request status */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@GetMapping(value = "allow/checkResetPassRequest")
	public ResponseEntity<?> checkResetPassRequest(@RequestParam String userId, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
						ShivamWebVariableUtils.INCOMPLETE_DATA_FROM_ANGULAR, null), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity(userService.checkResetPassRequest(userId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	/* Reset password */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@CrossOrigin
	@GetMapping(value = "allow/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam String newPassword, @RequestParam String userId,
			HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(newPassword, userId)) {
				return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
						ShivamWebVariableUtils.INCOMPLETE_DATA_FROM_ANGULAR, null), HttpStatus.BAD_REQUEST);

			}
			return new ResponseEntity(userService.resetPassword(newPassword, userId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	@CrossOrigin
	@PutMapping("changeClientProfile/{id}")
	private ResponseEntity<?> changeClientProfile(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.changeClientProfile(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("changePassword/{id}")
	private ResponseEntity<?> changePassword(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), body.getOtherData(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.changePassword(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getClientById")
	public ResponseEntity<?> getClientById(@RequestParam String id, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			User user = userRepository.findById(id).orElse(new User());
			user.setPassword(null);
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get By Id", user, path),
					HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("getAllUser")
	public ResponseEntity<?> getAllUser(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get All Client",
					userRepository.getAllUser(), path), HttpStatus.OK);
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PutMapping("allow/setFirebaseToken/{id}")
	public ResponseEntity<?> setFirebaseToken(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getUserId()))
			return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
		ResponseWrapperDTO response = new ResponseWrapperDTO();
		response = userService.setFirebaseToken(body.getUserId(), body.getFcmToken(), request);
		return new ResponseEntity<>(response, response.getHttpStatus());
	}

	@CrossOrigin
	@GetMapping("allow/test")
	public void test(HttpServletRequest request) {
	}

	@CrossOrigin
	@PutMapping("assignParty/{id}")
	private ResponseEntity<?> assignParty(@PathVariable("id") String id, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject(), body.getUserId(), id))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = userService.assignParty(id, body, request);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	// get tocken for app
	// @GetMapping("getNewAccessTocken")
	// public ResponseEntity<?> getNewAccessTocken(@RequestParam String userId,
	// HttpServletRequest request) {
	// String path = request.getServletPath();
	// try {
	// if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId))
	// return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
	//
	// ResponseWrapperDTO response = new ResponseWrapperDTO();
	//// response = userService.getNewAccessTocken(userId);
	// return new ResponseEntity<>(response, HttpStatus.OK);
	//
	// } catch (Exception e) {
	// return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
	// }
	// }
}
