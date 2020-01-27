package com.techhive.shivamweb.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.naming.directory.SearchControls;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhive.shivamweb.custom.repository.PktMasterCustomRepository;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.SearchHistory;
import com.techhive.shivamweb.repository.SearchHistoryRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SearchHistoryService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	SearchHistoryRepository searchHistoryRepository;

	@Autowired
	PktMasterCustomRepository pktMasterCustomRepository;

	private static DecimalFormat df2 = new DecimalFormat(".##");

	@Override
	public void saveSearchHistory(StoneSearchCriteria body) throws JsonProcessingException {

		Optional<User> user = userRepository.findById(body.getUserId());
		if (user.isPresent()) {
			SearchHistory searchHistory = convertStoneSearchCriteriaToSearchHistory(body);
			searchHistory.setUser(user.get());
			searchHistoryRepository.saveAndFlush(searchHistory);
		}
	}

	/* use same function in save user Demand. */
	// convert search criteria to create demand
	public SearchHistory convertStoneSearchCriteriaToSearchHistory(StoneSearchCriteria stoneSearchCriteria)
			throws JsonProcessingException {
		ObjectMapper MAPPER = new ObjectMapper();
		SearchHistory searchHistory = new SearchHistory();
		searchHistory.setIsFancyColor(stoneSearchCriteria.getIsFancyColor());
		searchHistory.setShape(MAPPER.writeValueAsString(stoneSearchCriteria.getShape()));
		searchHistory.setListOfFromToWeights(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFromToWeights()));
		searchHistory.setListOfCut(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfCut()));
		searchHistory.setListOfPolish(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfPolish()));
		searchHistory.setListOfSym(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfSym()));
		searchHistory.setListOfFlou(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFlou()));
		searchHistory.setFromColor(stoneSearchCriteria.getFromColor());
		searchHistory.setToColor(stoneSearchCriteria.getToColor());
		searchHistory.setFromLength(stoneSearchCriteria.getFromLength());
		searchHistory.setToLength(stoneSearchCriteria.getToLength());
		searchHistory.setFromWidth(stoneSearchCriteria.getFromWidth());
		searchHistory.setToWidth(stoneSearchCriteria.getToWidth());
		searchHistory.setFromHeight(stoneSearchCriteria.getFromHeight());
		searchHistory.setToHeight(stoneSearchCriteria.getToHeight());
		searchHistory.setLab(MAPPER.writeValueAsString(stoneSearchCriteria.getLab()));
		searchHistory.setFancyColor(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColor()));
		searchHistory.setFancyColorIntensity(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorIntensity()));
		searchHistory.setFancyColorOvertone(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorOvertone()));
		searchHistory.setFromDepth(stoneSearchCriteria.getFromDepth());
		searchHistory.setToDepth(stoneSearchCriteria.getToDepth());
		searchHistory.setFromTable(stoneSearchCriteria.getFromTable());
		searchHistory.setToTable(stoneSearchCriteria.getToTable());
		// searchHistory.setFromGirdleThin(stoneSearchCriteria.getFromGirdleThin());
		// searchHistory.setToGirdleThin(stoneSearchCriteria.getToGirdleThin());
		// searchHistory.setFromGirdleThick(stoneSearchCriteria.getFromGirdleThick());
		// searchHistory.setToGirdleThick(stoneSearchCriteria.getToGirdleThick());
		// searchHistory.setFromCuletSize(stoneSearchCriteria.getFromCuletSize());
		// searchHistory.setToCuletSize(stoneSearchCriteria.getToCuletSize());
		searchHistory.setFromTotal(stoneSearchCriteria.getFromTotal());
		searchHistory.setToTotal(stoneSearchCriteria.getToTotal());
		searchHistory.setFromCrownHeight(stoneSearchCriteria.getFromCrownHeight());
		searchHistory.setToCrownHeight(stoneSearchCriteria.getToCrownHeight());
		searchHistory.setFromCrownAngle(stoneSearchCriteria.getFromCrownAngle());
		searchHistory.setToCrownAngle(stoneSearchCriteria.getToCrownAngle());
		searchHistory.setFromPavilionDepth(stoneSearchCriteria.getFromPavilionDepth());
		searchHistory.setToPavilionDepth(stoneSearchCriteria.getToPavilionDepth());
		searchHistory.setFromPavilionAngle(stoneSearchCriteria.getFromPavilionAngle());
		searchHistory.setToPavilionAngle(stoneSearchCriteria.getToPavilionAngle());
		searchHistory.setFromPavilionHeight(stoneSearchCriteria.getFromPavilionHeight());
		searchHistory.setToPavilionHeight(stoneSearchCriteria.getToPavilionHeight());
		searchHistory.setFromGirdle(stoneSearchCriteria.getFromGirdle());
		searchHistory.setToGirdle(stoneSearchCriteria.getToGirdle());

		// searchHistory.setFromShade(stoneSearchCriteria.getFromShade());
		// searchHistory.setToShade(stoneSearchCriteria.getToShade());
		// searchHistory.setFromMilky(stoneSearchCriteria.getFromMilky());
		// searchHistory.setToMilky(stoneSearchCriteria.getToMilky());

		searchHistory.setColor(MAPPER.writeValueAsString(stoneSearchCriteria.getColor()));
		searchHistory.setClarity(MAPPER.writeValueAsString(stoneSearchCriteria.getClarity()));
		searchHistory.setMilky(MAPPER.writeValueAsString(stoneSearchCriteria.getMilky()));
		searchHistory.setShade(MAPPER.writeValueAsString(stoneSearchCriteria.getShade()));
		searchHistory.setCountry(MAPPER.writeValueAsString(stoneSearchCriteria.getCountry()));
		searchHistory.setName(stoneSearchCriteria.getName());
		searchHistory.setStarLength(stoneSearchCriteria.getStarLength());

		searchHistory.setIs3EX(stoneSearchCriteria.getIs3EX());
		searchHistory.setIs3VG(stoneSearchCriteria.getIs3VG());
		searchHistory.setIsNOBGM(stoneSearchCriteria.getIsNOBGM());

		searchHistory.setStoneId(stoneSearchCriteria.getStoneId());
		searchHistory.setCertificateNo(stoneSearchCriteria.getCertificateNo());

		return searchHistory;

	}

	@Override
	public ResponseWrapperDTO getAllsaveSearchHistory(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String userId, String path)
			throws JsonParseException, JsonMappingException, IOException {

		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(userId)) {
			Page<SearchHistory> listOfAllSearchHistoryWithPageable = searchHistoryRepository.findAll(request);
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all Search History",
					listOfAllSearchHistoryWithPageable, HttpStatus.OK, path);
		}

		Page<SearchHistory> listOfAllSearchHistoryWithPageable = searchHistoryRepository.findAllByUser(userId, request);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all Search History",
				listOfAllSearchHistoryWithPageable, HttpStatus.OK, path);
	}

	// convert Search history To StoneSearchCriteria
	public StoneSearchCriteria convertSearchHistoryToStoneSearchCriteria(SearchHistory searchHistory)
			throws JsonParseException, JsonMappingException, IOException {

		StoneSearchCriteria stoneSearchCriteria = new StoneSearchCriteria();
		ObjectMapper MAPPER = new ObjectMapper();

		stoneSearchCriteria.setIsFancyColor(searchHistory.getIsFancyColor());

		stoneSearchCriteria.setShape(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getShape())
				? MAPPER.readValue(searchHistory.getShape(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfFromToWeights(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfFromToWeights()) ? MAPPER.readValue(
						searchHistory.getListOfFromToWeights(), new TypeReference<List<Map<String, String>>>() {
						}) : null);
		stoneSearchCriteria.setListOfCut(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfCut())
				? MAPPER.readValue(searchHistory.getListOfCut(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfPolish(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfPolish())
				? MAPPER.readValue(searchHistory.getListOfPolish(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfSym(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfSym())
				? MAPPER.readValue(searchHistory.getListOfSym(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfFlou(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfFlou())
				? MAPPER.readValue(searchHistory.getListOfFlou(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFromColor(searchHistory.getFromColor());
		stoneSearchCriteria.setToColor(searchHistory.getToColor());
		stoneSearchCriteria.setFromLength(searchHistory.getFromLength());
		stoneSearchCriteria.setToLength(searchHistory.getToLength());
		stoneSearchCriteria.setFromWidth(searchHistory.getFromWidth());
		stoneSearchCriteria.setToWidth(searchHistory.getToWidth());
		stoneSearchCriteria.setFromHeight(searchHistory.getFromHeight());
		stoneSearchCriteria.setToHeight(searchHistory.getToHeight());
		stoneSearchCriteria.setLab(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getLab())
				? MAPPER.readValue(searchHistory.getLab(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFancyColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColor())
				? MAPPER.readValue(searchHistory.getFancyColor(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFancyColorIntensity(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColorIntensity())
						? MAPPER.readValue(searchHistory.getFancyColorIntensity(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setFancyColorOvertone(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColorOvertone())
						? MAPPER.readValue(searchHistory.getFancyColorOvertone(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setFromDepth(searchHistory.getFromDepth());
		stoneSearchCriteria.setToDepth(searchHistory.getToDepth());
		stoneSearchCriteria.setFromTable(searchHistory.getFromTable());
		stoneSearchCriteria.setToTable(searchHistory.getToTable());

		stoneSearchCriteria.setFromTotal(searchHistory.getFromTotal());
		stoneSearchCriteria.setToTotal(searchHistory.getToTotal());
		stoneSearchCriteria.setFromCrownHeight(searchHistory.getFromCrownHeight());
		stoneSearchCriteria.setToCrownHeight(searchHistory.getToCrownHeight());
		stoneSearchCriteria.setFromCrownAngle(searchHistory.getFromCrownAngle());
		stoneSearchCriteria.setToCrownAngle(searchHistory.getToCrownAngle());
		stoneSearchCriteria.setFromPavilionDepth(searchHistory.getFromPavilionDepth());
		stoneSearchCriteria.setToPavilionDepth(searchHistory.getToPavilionDepth());
		stoneSearchCriteria.setFromPavilionAngle(searchHistory.getFromPavilionAngle());
		stoneSearchCriteria.setToPavilionAngle(searchHistory.getToPavilionAngle());
		stoneSearchCriteria.setFromPavilionHeight(searchHistory.getFromPavilionHeight());
		stoneSearchCriteria.setToPavilionHeight(searchHistory.getToPavilionHeight());
		stoneSearchCriteria.setFromGirdle(searchHistory.getFromGirdle());
		stoneSearchCriteria.setToGirdle(searchHistory.getToGirdle());

		stoneSearchCriteria.setStoneId(searchHistory.getStoneId());
		stoneSearchCriteria.setCertificateNo(searchHistory.getCertificateNo());

		stoneSearchCriteria.setColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getColor())
				? MAPPER.readValue(searchHistory.getColor(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setClarity(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getClarity())
				? MAPPER.readValue(searchHistory.getClarity(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setMilky(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getMilky())
				? MAPPER.readValue(searchHistory.getMilky(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setShade(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getShade())
				? MAPPER.readValue(searchHistory.getShade(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setCountry(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getCountry())
				? MAPPER.readValue(searchHistory.getCountry(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setName(searchHistory.getName());
		stoneSearchCriteria.setStarLength(searchHistory.getStarLength());
		stoneSearchCriteria.setIs3EX(searchHistory.getIs3EX());
		stoneSearchCriteria.setIs3VG(searchHistory.getIs3VG());
		stoneSearchCriteria.setIsNOBGM(searchHistory.getIsNOBGM());
		return stoneSearchCriteria;
	}

	@Override
	public ResponseWrapperDTO getAllPastSearch(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String userId, String path) {
		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		Page<SearchHistory> listOfAllSearchHistoryWithPageable = searchHistoryRepository.findAllByUser(userId, request);
		List<SearchHistory> listOfAllSearchHistory = new ArrayList<>();
		ObjectMapper MAPPER = new ObjectMapper();
		listOfAllSearchHistoryWithPageable.getContent().forEach(searchHistory -> {
			StringBuilder stringData = new StringBuilder();
			SearchHistory sh = new SearchHistory();
			// stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getIsFancyColor())
			// ? searchHistory.getIsFancyColor() + ","
			// : "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getShape())
					&& !searchHistory.getShape().equalsIgnoreCase("[]")
					&& !searchHistory.getShape().equalsIgnoreCase("null") ? " " + searchHistory.getShape() + "," : "");
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfFromToWeights())
					&& !searchHistory.getListOfFromToWeights().equalsIgnoreCase("null")) {
				try {
					stringData.append(" ");
					List<Map<String, String>> mapList = MAPPER.readValue(searchHistory.getListOfFromToWeights(),
							new TypeReference<List<Map<String, String>>>() {
							});
					mapList.forEach(l -> {
						stringData.append(String.format("%.2f", Double.parseDouble(l.get("fromWeight")))).append(" to ")
								.append(String.format("%.2f", Double.parseDouble(l.get("toWeight")))).append(" carat");
						stringData.append(",");
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfCut())
					&& !searchHistory.getListOfCut().equalsIgnoreCase("null") ? " " + searchHistory.getListOfCut() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfPolish())
					&& !searchHistory.getListOfPolish().equalsIgnoreCase("null")
							? " " + searchHistory.getListOfPolish() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfSym())
					&& !searchHistory.getListOfSym().equalsIgnoreCase("null") ? " " + searchHistory.getListOfSym() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getListOfFlou())
					&& !searchHistory.getListOfFlou().equalsIgnoreCase("null")
							? " " + searchHistory.getListOfFlou() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromColor())
					? " " + searchHistory.getFromColor() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromColor())
					? " " + searchHistory.getToColor() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromLength())
					? " " + searchHistory.getFromLength() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToLength())
					? " " + searchHistory.getToLength() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromWidth())
					? " " + searchHistory.getFromWidth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToWidth())
					? " " + searchHistory.getToWidth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromHeight())
					? " " + searchHistory.getFromHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToHeight())
					? " " + searchHistory.getToHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getLab())
					&& !searchHistory.getLab().equalsIgnoreCase("null") ? " " + searchHistory.getLab() + "," : "");

			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColor())
					&& !searchHistory.getFancyColor().equalsIgnoreCase("null")
							? " " + searchHistory.getFancyColor() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColorIntensity())
					&& !searchHistory.getFancyColorIntensity().equalsIgnoreCase("null")
							? " " + searchHistory.getFancyColorIntensity() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFancyColorOvertone())
					&& !searchHistory.getFancyColorOvertone().equalsIgnoreCase("null")
							? " " + searchHistory.getFancyColorOvertone() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromDepth())
					? " " + searchHistory.getFromDepth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToDepth())
					? " " + searchHistory.getToDepth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromTable())
					? " " + searchHistory.getFromTable() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToTable())
					? " " + searchHistory.getToTable() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromTotal())
					? " " + searchHistory.getFromTotal() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToTotal())
					? " " + searchHistory.getToTotal() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromCrownHeight())
					? " " + searchHistory.getFromCrownHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToCrownHeight())
					? " " + searchHistory.getToCrownHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromCrownAngle())
					? " " + searchHistory.getFromCrownAngle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToCrownAngle())
					? " " + searchHistory.getToCrownAngle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromPavilionDepth())
					? " " + searchHistory.getFromPavilionDepth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToPavilionDepth())
					? " " + searchHistory.getToPavilionDepth() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromPavilionAngle())
					? " " + searchHistory.getFromPavilionAngle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToPavilionAngle())
					? " " + searchHistory.getToPavilionAngle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromPavilionHeight())
					? " " + searchHistory.getFromPavilionHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToPavilionHeight())
					? " " + searchHistory.getToPavilionHeight() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getFromGirdle())
					? " " + searchHistory.getFromGirdle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getToGirdle())
					? " " + searchHistory.getToGirdle() + ","
					: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getColor())
					&& !searchHistory.getColor().equalsIgnoreCase("null") ? " " + searchHistory.getColor() + "," : "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getClarity())
					&& !searchHistory.getClarity().equalsIgnoreCase("null") ? " " + searchHistory.getClarity() + ","
							: "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getMilky())
					&& !searchHistory.getMilky().equalsIgnoreCase("null") ? " " + searchHistory.getMilky() + "," : "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getShade())
					&& !searchHistory.getShade().equalsIgnoreCase("null") ? " " + searchHistory.getShade() + "," : "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getCountry())
					&& !searchHistory.getCountry().equalsIgnoreCase("null") ? " " + searchHistory.getCountry() + ","
							: "");

			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getStoneId())
					? " " + searchHistory.getStoneId() + ","
					: "");

			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getCertificateNo())
					? " " + searchHistory.getCertificateNo() + ","
					: "");

			// stringData.append(
			// !ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getName()) ?
			// searchHistory.getName() + ","
			// : "");
			stringData.append(!ShivamWebMethodUtils.isObjectisNullOrEmpty(searchHistory.getStarLength())
					? searchHistory.getStarLength()
					: "");
			if (stringData.toString().endsWith(",")) {
				String res = stringData.toString().substring(0, stringData.toString().length() - 1);
				searchHistory.setStringData(res);
			} else {
				searchHistory.setStringData(stringData.toString());
			}
			sh.setId(searchHistory.getId());

			sh.setCreatedDate(searchHistory.getCreatedDate());
			// set date format for app
			sh.setSearchDate(searchHistory.getCreatedDate());

			sh.setStringData(removeAnyQuots(searchHistory.getStringData()));
			List<Integer> cnt;
			Integer finalCnt = 0;
			try {

				cnt = pktMasterCustomRepository.getCount(convertSearchHistoryToStoneSearchCriteria(searchHistory));
				finalCnt = !cnt.isEmpty() ? cnt.get(0) : 0;
				// finalCnt=(int) (!cnt.isEmpty()? cnt.get(0): 0);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sh.setPktCnt(finalCnt);

			listOfAllSearchHistory.add(sh);
		});
		Page<SearchHistory> pageFinal = new PageImpl<SearchHistory>(listOfAllSearchHistory, request,
				listOfAllSearchHistoryWithPageable.getTotalElements());

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "get All PastSearch of user", pageFinal, HttpStatus.OK,
				path);
	}

	// public String removeQuots(String string) {
	// String sf = "";
	// String[] data = string.split(",");
	//
	// for (String s : data) {
	// sf = s.substring(1, s.length() - 1);
	// }
	// return sf;
	// }
	public String removeAnyQuots(String string) {
		// String sf = string.replaceAll("\"", "");
		String sf;
		sf = string.replaceAll("[\\[\\]\"]", "");
		// sf = string.replaceAll("]", "");
		return sf;
	}
}
