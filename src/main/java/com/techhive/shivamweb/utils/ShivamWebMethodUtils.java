package com.techhive.shivamweb.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ShivamWebMethodUtils {

	/**
	 * check given object is null or empty
	 * 
	 * @param t
	 * 
	 * @return true if object is null or empty
	 */
	@SafeVarargs
	public static <T> boolean isObjectNullOrEmpty(T... t) {
		for (T obj : t) {

			if (obj == null || obj.toString().trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author neel it converts json to pojo and vice versa..
	 */
	public static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * check list is null or empty
	 * 
	 * @param <T>
	 * 
	 * @param list
	 * 
	 * @return true if list is null or empty
	 */

	public static <T> boolean isListNullOrEmpty(List<T> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * check map is null or empty
	 * 
	 * @param mapT
	 * 
	 * @return true is map is null or empty
	 */

	public static <T> boolean isMapIsNullOrEmpty(Map<T, T> mapT) {
		if (mapT == null || mapT.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * AES encryption
	 */
	public static String encryptUsingAES(String input) {
		byte[] crypted = null;
		try {

			SecretKeySpec skey = new SecretKeySpec(ShivamWebVariableUtils.AES_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
		}
		Encoder encoder = java.util.Base64.getEncoder();

		return new String(encoder.encodeToString(crypted));
	}

	/**
	 * AES decryption
	 */
	public static String decryptUsingAES(String input) {
		byte[] output = null;
		try {
			Decoder decoder = java.util.Base64.getDecoder();
			SecretKeySpec skey = new SecretKeySpec(ShivamWebVariableUtils.AES_KEY.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(decoder.decode(input));
		} catch (Exception e) {
		}
		return new String(output);
	}

	/**
	 * check set is null or empty
	 * 
	 * @param setT
	 * @return true if set is null or empty
	 */

	public static <T> boolean isSetNullOrEmpty(Set<T> setT) {
		if (setT == null || setT.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * @author Heena set field name and order of field into map
	 * @param row
	 * @return map of field
	 */
	public static Map<String, Integer> getFieldsNameAndItsOrderInCSVFile(String[] row) {
		Map<String, Integer> mapOfFields = new HashMap();
		int position = 0;
		for (String field : row) {
			mapOfFields.put(field.trim(), position);

			position++;

		}

		return mapOfFields;
	}

	/**
	 * 
	 * @return api url like /api/country/getAllCountry
	 */

	public static String getApiPath() {
		return ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString().substring(25);

		// ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString().substring(25);
		// URL requestURL = null;
		// try {
		// requestURL = new URL((request).getRequestURL().toString());
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * @author neel
	 * @param error
	 *            e.getmessage() of exception
	 * @return responseEntity with internal server error and actual Exception
	 */

	public static ResponseEntity<ResponseWrapperDTO> exceptionResponseEntity(String error, String path) {
		return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, error, path),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @author neel
	 * @return responseEntity with Bad Request & incomplete data message for
	 *         controller
	 */
	public static ResponseEntity<ResponseWrapperDTO> incompleteDataResponseEntityForController(String path) {
		return new ResponseEntity(new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
				ShivamWebVariableUtils.INCOMPLETE_DATA_FROM_ANGULAR, null, path), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @author neel
	 * @return responseEntity with Bad Request & incomplete data message for
	 *         controller
	 */
	public static ResponseWrapperDTO incompleteDataResponseWrapperDTOForServiceRepository(String path) {
		return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST,
				ShivamWebVariableUtils.INCOMPLETE_DATA_FROM_ANGULAR, null, HttpStatus.BAD_REQUEST, path);
	}

	/*
	 * @author:bhumi generate one time OPT for first time register.
	 */
	public static String getOneTimeOTP() {
		String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String smallChars = "abcdefghijklmnopqrstuvwxyz";
		String digits = "0123456789";
		String speacialChars = "$@!%*#?&";
		// String speacialChars = "~`!@#%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String password = "";
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			password += capitalChars.charAt(random.nextInt(capitalChars.length()));
			password += smallChars.charAt(random.nextInt(smallChars.length()));
			password += digits.charAt(random.nextInt(digits.length()));
			password += speacialChars.charAt(random.nextInt(speacialChars.length()));
		}
		return password;
	}

	@SuppressWarnings("unchecked")
	public static <T> String convertObjectToStringUsingJackson(T t) {

		if (isObjectNullOrEmpty(t)) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new Hibernate5Module());
		try {
			return mapper.writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @author neel chenge time to zero if present from frontend so exactly query
	 *         date
	 */
	public static Date setTimeToZeroOfDate(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);

		return now.getTime();
	}

	/* get total days between two Date */
	/*
	 * @author: bhumi get hours between two date.
	 */
	public static Long getTotalNoOfMinut(Date fromDate) {
		// ... get No of days..

		LocalDateTime dateTime = LocalDateTime.ofInstant(fromDate.toInstant(), ZoneId.systemDefault());
		LocalDateTime dateTime2 = LocalDateTime.now();
		long diffInMinut = java.time.Duration.between(dateTime, dateTime2).toMinutes();
		return diffInMinut;
	}

	// @formatter:off
	/***
	 * This Generic method is used to check if list is empty of null.
	 * 
	 * @param <T>
	 *            indicates any type of Generic object.
	 * @param listT
	 *            indicates list.
	 * @return return true if list is null or empty, otherwise return false.
	 * @bhumi
	 */
	// @formatter:on

	public static <T> boolean isListIsNullOrEmpty(List<T> listT) {

		if (listT == null || listT.isEmpty()) {
			return true;
		}
		return false;
	}

	// @formatter:off
	/***
	 * This Generic method is used to check if list is empty of null.
	 * 
	 * @param <T>
	 *            indicates any type of Generic object.
	 * @param listT
	 *            indicates list.
	 * @return
	 * 
	 * 		return result with single quotes of array list.
	 * @bhumi
	 * 
	 */
	// @formatter:on
	public static String getListWithSingleQuotes(List<String> list) {
		String res = "(" + list.stream().map(listValue -> "'" + listValue.trim() + "'").collect(Collectors.joining(","))
				+ ")";
		return res;
	}

	// @formatter:off
	/***
	 * This generic method is used to check whether object is null or empty. if
	 * object t is null or empty then it throws NullPointerException.
	 * 
	 * @param <T>
	 *            indicates any type of Generic object.
	 * @param t
	 *            indicates any zero or more objects like String,List,Set etc.
	 * 
	 * @bhumi
	 */
	// @formatter:on
	@SuppressWarnings("unchecked")
	public static <T> boolean isObjectisNullOrEmpty(T... t) {
		for (T ob : t) {
			if (ob == null || ob.toString().trim().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @author neel
	 */
	public static String remove1StAndLastCharOfString(String string) {
		return string.substring(1, string.length() - 1);
	}

	/**
	 * @author neel
	 */
	public static String removeLastCharOfString(String string) {
		return string.substring(0, string.length() - 1);
	}

	/**
	 * @author bhumi
	 */
	public static List<Double> getDefaultDiscForCal() {
		List<Double> list = new ArrayList<>();
		list.add((double) 0);
		return list;

	}

	/**
	 * @bhumi
	 */

	/* get total days between two Date */

	public static Integer getTotalNoOfDay(Date fromDate) {
		Calendar yesterday = Calendar.getInstance();

		// yesterday.add(Calendar.DATE, -1); not required because end date is not
		// consider

		Date d = yesterday.getTime();
		long difference = d.getTime() - fromDate.getTime();
		int daysBetween = (int) (difference / (1000 * 60 * 60 * 24));
		return daysBetween;
	}

	/**
	 * @bhumi use for save search history
	 */
	/* get String from array for store array as string in SAVE_SEARCH */
	public static String getStringFromArray(List<String> list) {
		return String.join(",", list);

	}

	/**
	 * @bhumi
	 * 
	 * 		use for check particular path image exist or not
	 */
	public static String setImageURL(String stoneId) {
		String imgURL = null;
		imgURL = ShivamWebVariableUtils.FIRST_IMAGE_LINK_FROM_PEACOCK + stoneId.trim()
				+ ShivamWebVariableUtils.FIRST_IMAGE_EXTENTION.trim();
		/* check if exist or not. */

		try {
			HttpURLConnection image_path;
			HttpURLConnection secondImage_path;
			image_path = (HttpURLConnection) new URL(imgURL).openConnection();

			image_path.setRequestMethod("HEAD");

			if (image_path.getResponseCode() != HttpURLConnection.HTTP_OK) {
				imgURL = ShivamWebVariableUtils.SECOND_IMAGE_LINK_FROM_PEACOCK + stoneId.trim()
						+ ShivamWebVariableUtils.SECOND_IMAGE_EXTENTION.trim();

				secondImage_path = (HttpURLConnection) new URL(imgURL).openConnection();
				if (secondImage_path.getResponseCode() != HttpURLConnection.HTTP_OK) {
					imgURL = null;
					// imgURL="https://pckcdnsj.azureedge.net/hdfile/Movie/Viewer4/imaged/E290-79210/still.jpg";
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgURL;
	}

	/**
	 * @bhumi
	 * 
	 * 		use for check particular path video exist or not
	 */
	public static String setSaveVideoURL(String stoneId) {
		String videoURL = null;
		// videoURL = ShivamWebVariableUtils.VIDEO_LINK_FROM_PEACOCK + stoneId.trim();

		/* Set video link */
		videoURL = ShivamWebVariableUtils.FIRST_VIDEO_LINK_FOR_SAVE + stoneId.trim()
				+ ShivamWebVariableUtils.FIRST_VIDEO_LINK_SAVE_EXTENTION.trim();

		try {
			HttpURLConnection saveVideoLink = (HttpURLConnection) new URL(videoURL).openConnection();

			if (saveVideoLink.getResponseCode() != HttpURLConnection.HTTP_OK) {

				videoURL = ShivamWebVariableUtils.SECOND_VIDEO_LINK_FOR_SAVE.trim() + stoneId.trim();
				HttpURLConnection secondSaveVideoLink = (HttpURLConnection) new URL(videoURL).openConnection();
				if (secondSaveVideoLink.getResponseCode() != HttpURLConnection.HTTP_OK) {
					videoURL = null;
					// videoURL =
					// "https://pckcdnsj.azureedge.net/hdfile/Movie/Viewer4/Vision360.html?d=BL-51410";
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoURL;
	}

	/**
	 * @bhumi
	 * 
	 * 		use for check particular path video exist or not
	 */
	public static String setVideoURL(String stoneId) {
		String videoURL = null;
		// videoURL = ShivamWebVariableUtils.VIDEO_LINK_FROM_PEACOCK + stoneId.trim();
		/* Set video link */
		videoURL = ShivamWebVariableUtils.VIDEO_LINK_FROM_360_VIEWER + stoneId.trim();

		try {
			HttpURLConnection saveVideoLink = (HttpURLConnection) new URL(videoURL).openConnection();
			if (saveVideoLink.getResponseCode() != HttpURLConnection.HTTP_OK) {
				videoURL = null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return videoURL;
	}

	/**
	 * @bhumi
	 * 
	 * 		use for check particular path of plotting exist or not
	 */
	public static String setPlottingURL(String stoneId) {
		String plottingUrl = null;
		// videoURL = ShivamWebVariableUtils.VIDEO_LINK_FROM_PEACOCK + stoneId.trim();
		/* Set video link */
		plottingUrl = ShivamWebVariableUtils.INCLUSION_PLOTTING + stoneId.trim()
				+ ShivamWebVariableUtils.FIRST_IMAGE_EXTENTION.trim();

		try {
			HttpURLConnection saveVideoLink = (HttpURLConnection) new URL(plottingUrl).openConnection();
			if (saveVideoLink.getResponseCode() != HttpURLConnection.HTTP_OK) {
				plottingUrl = null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return plottingUrl;
	}

//	public static <T> T getNullObject() {
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectNode obj = mapper.createObjectNode();
//		try {
//			String s = "";
//			s = mapper.writeValueAsString(obj);
//			return s;
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

}
