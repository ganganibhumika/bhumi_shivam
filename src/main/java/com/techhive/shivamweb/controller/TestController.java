package com.techhive.shivamweb.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techhive.shivamweb.custom.repository.PktMasterCustomRepository;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.SoftwareSalePersonMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.CreateDemand;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.CartRepository;
import com.techhive.shivamweb.repository.CreateDemandRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.SoftwareSalesPersonRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.PasswordEncryption;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.CretaeDemandService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.impl.ResetAllServiceImpl;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@RestController
@RequestMapping("api/test")
public class TestController {

	@Autowired
	ResetAllServiceImpl resetAllServiceImpl;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	NotificationService notificationService;

	@Autowired
	CreateDemandRepository createDemandRepository;

	@Autowired
	CretaeDemandService cretaeDemandService;
	@Autowired
	UserRepository userRepository;

	@Autowired
	PktMasterRepository pktMasterRepository;

	@Autowired
	private PktMasterCustomRepository pktMasterCustomRepository;

	@PostMapping("userMigration")
	public ResponseEntity<?> userMigration(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			resetAllServiceImpl.addUserMigration();
			return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "suc", null, path),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@PostMapping("encryp")
	public ResponseEntity<?> encryp(HttpServletRequest request) {
		String path = request.getServletPath();
		try {
			final String url = "http://shivamjewels.in/shivam_app.asmx/APP_Decrypt";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add("password", "DzKsLYeyxmSFrA2X3q8X2MMKxfYZ6VuLDNEaNnUdYv=");
			RestTemplate restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, String>> request1 = new HttpEntity<MultiValueMap<String, String>>(map,
					headers);
			ResponseEntity<PasswordEncryption> response = restTemplate.postForEntity(url, request1,
					PasswordEncryption.class);
			PasswordEncryption res = response.getBody();
			return new ResponseEntity<>(
					new ResponseWrapperDTO(HttpServletResponse.SC_OK, "suc" + res.getResult(), null, path),
					HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ShivamWebMethodUtils.exceptionResponseEntity(e.getMessage(), path);
		}
	}

	@GetMapping("userAbandandCart")
	public ResponseEntity<?> userAbandandCart(HttpServletRequest request) {
		String path = request.getServletPath();
		Set<User> users = cartRepository.findAllUserWithAbandondStone();

		Notification notification = new Notification();
		notification.setDescription(EnumForNotificationDescription.CONFIRMTOADMIN.toString());
		notification.setSetOfUserObject(users);
		notification.setCategory(EnumForNotificationType.CONFIRMORDER.toString());
		notification.setIsAdmin(true);
		return new ResponseEntity<>(
				new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", notification.getSetOfUserObject(), path),
				HttpStatus.OK);
	}

	@GetMapping("demand")
	public ResponseEntity<?> demand(HttpServletRequest request, @RequestParam String stoneId) {
		String path = request.getServletPath();
		String shape = "HEART";
		List<CreateDemand> demands = createDemandRepository.findAllByShape(shape);
		Set<User> users = new HashSet<>();
		for (CreateDemand createDemand : demands) {
			try {
				Set<PktMaster> pkt = pktMasterCustomRepository.getDemandStone(
						cretaeDemandService.convertCreateDemandToStoneSearchCriteria(createDemand), stoneId);
				if (!pkt.isEmpty()) {
					users.add(createDemand.getUser());
					// createDemandRepository.delete(createDemand);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Notification notification = new Notification();
		notification.setDescription(EnumForNotificationDescription.NEWSTONEDEMANDFULFILLED.toString());
		notification.setSetOfUserObject(users);
		notification.setCategory(EnumForNotificationType.DEMAND.toString());
		notification.setIsAdmin(true);
		notificationService.sendNotification(notification);

		return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", users, path),
				HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("twintest")
	public ResponseEntity<?> demand(HttpServletRequest request) {
		String path = request.getServletPath();
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
		PktMaster p = pktMasterRepository.findById("402882216893b6920168944b184b000b").orElse(null);
		PktMaster pktt = pktMasterRepository.findById("402882216893b6920168944b3b79000d").orElse(null);
		// pktt.setCodeOfColor(p.getCodeOfColor());
		Set<String> pktIdNotToCheck = new HashSet<>();
		pktIdNotToCheck.add("402882216893b6920168944b184b000b");
		boolean res;
		boolean res2;
		if (p.getCodeOfClarity().equals(pktt.getCodeOfClarity()) && p.getCodeOfColor().equals(pktt.getCodeOfColor())
				&& Double.compare(pktt.getTablePercentage(), p.getTablePercentage()) == 0
				&& Double.compare(pktt.getWidth(), p.getWidth()) == 0
				&& Double.compare(pktt.getTotDepth(), p.getTotDepth()) == 0
				&& Double.compare(pktt.getLength(), p.getLength()) == 0 && !pktIdNotToCheck.contains(pktt.getId())) {
			res = true;
		} else {
			res = false;
		}

		if ((!generateCarityList(p.getCodeOfClarity()).isEmpty()
				&& generateCarityList(p.getCodeOfClarity()).contains(pktt.getCodeOfClarity()))
				&& (!generateColourList(p.getCodeOfColor()).isEmpty()
						&& generateColourList(p.getCodeOfColor()).contains(pktt.getCodeOfColor()))
				&& (pktt.getTablePercentage() >= Math.abs(p.getTablePercentage() - 1)
						&& pktt.getTablePercentage() <= p.getTablePercentage() + 1)
				&& (pktt.getWidth() >= p.getWidth() - (p.getWidth() * (1 / 100.0f))
						&& pktt.getWidth() <= (p.getWidth() * (1 / 100.0f)) + p.getWidth())
				&& (pktt.getTotDepth() >= Math.abs(p.getTotDepth() - 2) && pktt.getTotDepth() <= p.getTotDepth() + 2)
				&& (pktt.getLength() >= p.getLength() - (p.getLength() * (1 / 100.0f))
						&& pktt.getLength() <= (p.getLength() * (1 / 100.0f)) + p.getLength())
				&& !pktIdNotToCheck.contains(pktt.getId())) {
			res2 = true;
		} else {
			res2 = false;
		}
		System.err.println(p.getCodeOfClarity() + "p's");
		System.err.println(pktt.getCodeOfClarity() + "pktt's");
		System.err.println(getClarityOrder(p.getCodeOfClarity(), "<") + " lessthan");
		System.err.println(getClarityOrder(p.getCodeOfClarity(), ">") + " greater");
		System.err.println(generateCarityList("I6"));
		System.err.println(generateColourList("N"));
		System.err.println(res);
		System.err.println(res2 + "2nd");
		return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", res, path), HttpStatus.OK);
	}

	Map<String, Integer> clarityOrder = new HashMap<>();
	Map<String, Integer> colorOrder = new HashMap<>();
	Set<String> clarityList = new HashSet<>();
	Set<String> colorList = new HashSet<>();

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

	public String getColorOrder(String color, String greaterOrLess) {
		if (greaterOrLess.equals("<")) {
			if (colorOrder.containsKey(color) && colorOrder.get(color) != 1) {
				// System.err.println(getKey(colorOrder, (colorOrder.get(color) - 1)));
				return getKey(colorOrder, (colorOrder.get(color) - 1));
			} else {

				return color;
			}
		} else {
			if (colorOrder.containsKey(color) && colorOrder.get(color) != 16) {
				// System.err.println(getKey(colorOrder, (colorOrder.get(color) + 1)));
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

	@GetMapping("checkMail")
	public ResponseEntity<?> checkMail(HttpServletRequest request) {
		String path = request.getServletPath();
		Calendar cal = Calendar.getInstance(); // creates calendar
		cal.setTime(new Date()); // sets calendar time/date
		cal.add(Calendar.HOUR_OF_DAY, 6); // adds one hour
		cal.getTime();
		Date from = new Date();
		System.err.println(from);
		System.err.println(cal.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		String res = "<div style=\"padding:0;margin:0 auto;width:100%!important;font-family:'Helvetica Neue',Helvetica,Arial,sans-serif\">\r\n"
				+ "    <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#EDF0F3\" style=\"background-color:#edf0f3;table-layout:fixed\">\r\n"
				+ "        <tbody>\r\n" + "            <tr>\r\n" + "                <td align=\"center\">\r\n"
				+ "                    <center style=\"width:100%\">\r\n"
				+ "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"512\" bgcolor=\"#FFFFFF\" style=\"background-color: #ffffff;\r\n"
				+ "                        margin: 50px auto;box-shadow: 0 3px 8px 0 rgba(0, 0, 0, 0.08);\r\n"
				+ "                        max-width: 512px;\r\n" + "                        width: inherit;\r\n"
				+ "                        border-radius: 10px;\">\r\n" + "                            <tbody>\r\n"
				+ "                                <tr>\r\n"
				+ "                                    <td bgcolor=\"#F6F8FA\" style=\"background-color:#f6f8fa;padding:12px;border-bottom:1px solid #ececec;    border-top-left-radius: 10px;\r\n"
				+ "                                    border-top-right-radius: 10px;\">\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100%!important;min-width:100%!important\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td align=\"center\" valign=\"middle\">\r\n"
				+ "                                                        <a href=\"\" style=\"color:#23295E;display:inline-block;\" target=\"_blank\">\r\n"
				+ "                                                            <img border=\"0\" src=\"http://www.techhive.co.in/demo/shivam/logo.png\" style=\"outline:none;color:#ffffff;text-decoration:none\"\r\n"
				+ "                                                                class=\"CToWUd\">\r\n"
				+ "                                                        </a>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                                <tr>\r\n" + "                                    <td>\r\n"
				+ "                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                            <tbody>\r\n"
				+ "                                                <tr>\r\n"
				+ "                                                    <td style=\"padding:20px 24px 32px 24px\">\r\n"
				+ "                                                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\r\n"
				+ "                                                            <tbody>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <h2 style=\"margin:0;color:#262626;font-weight:500;font-size:20px;line-height:1.2\">Hi, FirstName LastName,</h2>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">You have requested for Viewing the Stone.\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            Stone View request list are as follows:\r\n"
				+ "                                                                            <b>“E-12345, E-926354”</b>\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            Stone View Request Date&Time: "
				+ sdf.format(from) + " to " + sdf.format(cal.getTime()) + " </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n"
				+ "                                                                <tr>\r\n"
				+ "                                                                    <td style=\"padding-bottom:20px\">\r\n"
				+ "                                                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">\r\n"
				+ "                                                                            You must be complete Stone View Request Process within 2 hour as per given time.\r\n"
				+ "                                                                        </p>\r\n"
				+ "                                                                    </td>\r\n"
				+ "                                                                </tr>\r\n" + "\r\n" + "\r\n"
				+ "                                                            </tbody>\r\n"
				+ "                                                        </table>\r\n"
				+ "                                                    </td>\r\n"
				+ "                                                </tr>\r\n"
				+ "                                            </tbody>\r\n"
				+ "                                        </table>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n" + "\r\n"
				+ "                                <tr>\r\n" + "\r\n"
				+ "                                    <td style=\"padding:20px 24px 32px 24px;border-top:1px solid #dddddd;\">\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Thanks,</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Shivam Jewels</p>\r\n"
				+ "                                        <p style=\"margin:0;color:#4c4c4c;font-weight:400;font-size:16px;line-height:1.25\">Copyright © 2018</p>\r\n"
				+ "                                    </td>\r\n" + "                                </tr>\r\n"
				+ "                            </tbody>\r\n" + "                        </table>\r\n"
				+ "                    </center>\r\n" + "                </td>\r\n" + "            </tr>\r\n"
				+ "        </tbody>\r\n" + "    </table>\r\n" + "</div>";
		return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", res, path), HttpStatus.OK);

	}

	@Autowired
	SoftwareSalesPersonRepository softwareSalesPersonRepository;
	@CrossOrigin
	@PutMapping("checkJson")
	public ResponseEntity<?> CheckJson(@RequestBody String json, HttpServletRequest request) {
		String path = request.getServletPath();
		System.err.println("---------------> "+json);
		return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", json, path), HttpStatus.OK);

	}

	@PostMapping("createJson")
	public ResponseEntity<?> createJson(HttpServletRequest request) throws JsonProcessingException, JSONException {
	
		Optional<SoftwareSalePersonMaster> s=softwareSalesPersonRepository.findByUser("402882216830b08a01683135bfd200cc");
		System.err.println(s.get().getName());
		String path = request.getServletPath();
		String url = "http://103.54.99.76:8123/api/UpdateStone/Edit";
		RestTemplate restTemplate = new RestTemplate();

//		Map<String, List<Object>> map = new HashMap<>();
//		List<Object> l = new ArrayList<Object>();
//		l.add(new User("test", "neel"));
//		map.put("jsonOfObject", l);
//		System.err.println(ShivamWebMethodUtils.MAPPER.writeValueAsString(map));
		
		
		////
//		JSONObject json = new JSONObject();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
	

		Map<String, List<Object>> map = new HashMap<>();
		Map<String, Object> innerMap = new HashMap<>();
		List<Object> l = new ArrayList<Object>();
		innerMap.put("stoneId","324-84121kkkk");
		innerMap.put("jangadDate",new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date()) );
//		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		innerMap.put("partyName","AARANA EXPORT");
		innerMap.put("salesPerson","AATISH");
		innerMap.put("APer",5);
		innerMap.put("Offer_Per",2);
		innerMap.put("Offer_Rate",5200);

		l.add(innerMap);
		
		map.put("jsonOfObject",l);
//		System.out.println(map);
//		System.err.println(ShivamWebMethodUtils.MAPPER.writeValueAsString(map));
		HttpEntity<Map<String, List<Object>>> httpEntity = new HttpEntity<Map<String, List<Object>>>(map,
				headers);
//		restTemplate.put(url, map);
		System.err.println(httpEntity);
		ResponseEntity<String> response = null; 
		try {
			response = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, String.class);
		} catch (Exception e) {
		}
		if(!ShivamWebMethodUtils.isObjectisNullOrEmpty(response))
		 System.err.println(response.getBody()+"status:-> "+response.getStatusCode());
		 return new ResponseEntity<>(new ResponseWrapperDTO(HttpServletResponse.SC_OK, "ok", path, path), HttpStatus.OK);

	}
}
