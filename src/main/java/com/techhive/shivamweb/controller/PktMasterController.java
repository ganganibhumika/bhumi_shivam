package com.techhive.shivamweb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techhive.shivamweb.master.model.PartyMaster;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.PartyMastertRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.PktMasterService;
import com.techhive.shivamweb.service.PushNotificationService;
import com.techhive.shivamweb.service.SearchHistoryService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@RestController
@RequestMapping("/api/pktMaster")
public class PktMasterController {

	@Autowired
	private PktMasterService pktMasterService;

	@Autowired
	SearchHistoryService searchHistoryService;

	@Autowired
	private PartyMastertRepository partyMastertRepository;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	private PushNotificationService pushnotificationService;

	@Autowired
	private UserRepository userRepository;

	/* Get all stone for for third party API */

	@RequestMapping(value = "/allow/getAllStoneDetail", method = RequestMethod.GET, produces = { "application/pdf",
			"application/xml", "application/json", "application/raw" })
	public List<PktMaster> getAllStoneDetailForParty(@RequestParam(required = false) String userId,
			HttpServletRequest request) {
		List<PktMaster> listOfPkt = new ArrayList<>();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				PktMaster pkt = new PktMaster();
				pkt.setErrorMsg("Incomplete data.");
				listOfPkt.add(pkt);
				return listOfPkt;
			}
			Optional<PartyMaster> party = partyMastertRepository.findById(userId);
			if (!party.isPresent()) {
				PktMaster pkt = new PktMaster();
				pkt.setErrorMsg(ShivamWebVariableUtils.PARTY_NOT_FOUND);
				listOfPkt.add(pkt);
				return listOfPkt;
			}

			listOfPkt = pktMasterService.getAllStoneDetailForThirdParty(userId);
			return listOfPkt;

		} catch (Exception e) {

			// listOfPkt.get(0).setErrorMsg(e.getMessage());

			e.printStackTrace();
			return listOfPkt;

		}
	}
	/* End */

	/* Get Data for download CSV */
	@GetMapping(value = "/allow/getAllStoneDetailCSV")
	public ResponseEntity<?> getAllStoneDetailXML(@RequestParam String userId, HttpServletRequest request) {
		List<PktMaster> listOfPkt = new ArrayList<>();
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			listOfPkt = pktMasterService.getAllStoneDetailForThirdParty(userId);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Successfully got search stone detail.",
					listOfPkt, HttpStatus.OK);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
	/* End */

	@PostMapping(value = "getStonesViaLazzyLoadingOfSearchCriteria")
	public ResponseEntity<?> getStonesViaLazzyLoadingOfSearchCriteria(@RequestBody StoneSearchCriteria body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body, body.getUserId())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.getStonesViaLazzyLoadingOfSearchCriteria(body);

			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	/* add single stone */

	// @CrossOrigin
	// @PostMapping(value = "allow/addStoneDetail")
	// public ResponseEntity<?> addStoneDetail(@RequestBody MyRequestBody body,
	// HttpServletRequest request) {
	// try {
	// if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getJsonOfObject())) {
	// return
	// ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
	// }
	// ResponseWrapperDTO response = new ResponseWrapperDTO();
	// response = pktMasterService.addStoneDetail(body, request.getServletPath());
	// return new ResponseEntity<>(response, response.getHttpStatus());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(),
	// request.getServletPath());
	// }
	//
	// }

	/* add multiple stone */

	@CrossOrigin
	@PostMapping(value = "allow/addStoneDetail")
	public ResponseEntity<?> addStoneDetailMultiple(@RequestBody MyRequestBody body, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfJsonObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.addStoneDetailMultiple(body, request.getServletPath());
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}
	
	@CrossOrigin
	@PostMapping(value = "allow/addOrUpdateStoneDetail")
	public ResponseEntity<?> addOrUpdateStoneDetail(@RequestBody MyRequestBody body, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfJsonObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.addOrUpdateStoneDetail(body, request.getServletPath());
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	@CrossOrigin
	@PutMapping(value = "allow/updateStoneDetail/{stoneId}")
	public ResponseEntity<?> updateStoneDetail(@PathVariable("stoneId") String stoneId, @RequestBody MyRequestBody body,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(stoneId, body.getJsonOfObject()))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.updateStoneDetail(stoneId.trim(), body, path);
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	/* View Stone Detail */

	@GetMapping(value = "viewStoneByStoneIdAndUserId")
	public ResponseEntity<?> viewStoneByStoneIdAndUserId(@RequestParam String stoneId, @RequestParam String userId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(stoneId, userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.viewStoneByStoneIdAndUserId(stoneId, userId);
			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	// @GetMapping(value = "viewStoneByStoneIdAndUserId")
	// public ResponseEntity<?> viewStoneByStoneIdAndUserId(@RequestParam String
	// stoneId, @RequestParam String userId,
	// HttpServletRequest request) {
	// String path = request.getServletPath();
	// try {
	// if (ShivamWebMethodUtils.isObjectNullOrEmpty(stoneId, userId)) {
	// return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
	// }
	// ResponseWrapperDTO response = new ResponseWrapperDTO();
	// response = pktMasterService.viewStoneByStoneIdAndUserId(stoneId, userId);
	// return new ResponseEntity<>(response, response.getHttpStatus());
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
	// }
	// }

	@GetMapping(value = "getAllPktMasterByStatus")
	public ResponseEntity<?> getAllPktMasterByStatus(@RequestParam Integer pageNumber,
			@RequestParam Integer noOfRecords, @RequestParam(required = false) String sortColumn,
			@RequestParam(required = false) String sortOrder, @RequestParam(required = false) String searchText,
			@RequestParam(required = false) Boolean isFeatured, @RequestParam(required = false) String userId,
			HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(pageNumber, noOfRecords))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			if (!ShivamWebMethodUtils.isObjectNullOrEmpty(searchText)) {
				searchText = searchText.trim();
			}
			return new ResponseEntity<>(pktMasterService.getAllPktMasterByStatus(pageNumber, noOfRecords, sortColumn,
					sortOrder, searchText, isFeatured, path, userId), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@CrossOrigin
	@PostMapping(value = "updateFeaturedStone")
	public ResponseEntity<?> updateFeaturedStone(@RequestBody MyRequestBody body, HttpServletRequest request) {
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(body.getListOfJsonObject())) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(request.getServletPath());
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.updateFeaturedStone(body, request.getServletPath());
			return new ResponseEntity<>(response, response.getHttpStatus());
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), request.getServletPath());
		}

	}

	@GetMapping(value = "getRecomendedStone")
	public ResponseEntity<?> getRecomendedStone(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.getRecomendedStone(userId, path);

			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping(value = "downloadInventory")
	public ResponseEntity<?> downloadInventory(HttpServletRequest request) {
		List<PktMaster> listOfPkt = new ArrayList<>();
		String path = request.getServletPath();
		try {
			listOfPkt = pktMasterRepository.findAllByParam();
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = new ResponseWrapperDTO(HttpServletResponse.SC_OK, "download inventory", listOfPkt, path);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping(value = "getTwinStone")
	public ResponseEntity<?> getTwinStone(String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			}
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.getTwinStone5(path, userId);

			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping(value = "getRecentDiscountChangedStones")
	public ResponseEntity<?> getRecentDiscountChangedStones(@RequestParam String userId, HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			if (ShivamWebMethodUtils.isObjectisNullOrEmpty(userId))
				return ShivamWebMethodUtils.incompleteDataResponseEntityForController(path);
			ResponseWrapperDTO response = new ResponseWrapperDTO();
			response = pktMasterService.getRecentDiscountChangedStones(path, userId);

			return new ResponseEntity<>(response, response.getHttpStatus());

		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}
}
