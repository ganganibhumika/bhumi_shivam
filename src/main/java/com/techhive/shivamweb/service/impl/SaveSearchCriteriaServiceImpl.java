package com.techhive.shivamweb.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.SaveSearchCriteria;
import com.techhive.shivamweb.repository.SaveSearchCriteriaRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.SaveSearchCriteriaService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class SaveSearchCriteriaServiceImpl implements SaveSearchCriteriaService {

	@Autowired
	SaveSearchCriteriaRepository saveSearchCriteriaRepository;
	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseWrapperDTO saveSearchCriteriaAdd(StoneSearchCriteria body, String path)
			throws JsonParseException, JsonMappingException, IOException {
		Optional<User> user = userRepository.findById(body.getUserId());
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST);
		Optional<SaveSearchCriteria> saveSearchCriteriaOfUser = saveSearchCriteriaRepository
				.findByNameAndUser(body.getName(), body.getUserId());
		if (saveSearchCriteriaOfUser.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Search Criteria with name '" + body.getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT);
		SaveSearchCriteria saveSearchCriteria = convertStoneSearchCriteriaToSaveSearchCriteria(body);
		saveSearchCriteria.setUser(user.get());
		saveSearchCriteriaRepository.saveAndFlush(saveSearchCriteria);

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Search Criteria " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	// convert StoneSearchCriteria To SaveSearchCriteria
	public SaveSearchCriteria convertStoneSearchCriteriaToSaveSearchCriteria(StoneSearchCriteria stoneSearchCriteria)
			throws JsonProcessingException {
		ObjectMapper MAPPER = new ObjectMapper();
		SaveSearchCriteria saveSearchCriteria = new SaveSearchCriteria();
		saveSearchCriteria.setIsFancyColor(stoneSearchCriteria.getIsFancyColor());
		saveSearchCriteria.setStartPosition(stoneSearchCriteria.getStartPosition());
		saveSearchCriteria.setNoOfRecords(stoneSearchCriteria.getNoOfRecords());
		saveSearchCriteria.setShape(MAPPER.writeValueAsString(stoneSearchCriteria.getShape()));
		saveSearchCriteria
				.setListOfFromToWeights(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFromToWeights()));
		saveSearchCriteria.setListOfCut(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfCut()));
		saveSearchCriteria.setListOfPolish(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfPolish()));
		saveSearchCriteria.setListOfSym(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfSym()));
		saveSearchCriteria.setListOfFlou(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFlou()));
		saveSearchCriteria.setFromColor(stoneSearchCriteria.getFromColor());
		saveSearchCriteria.setToColor(stoneSearchCriteria.getToColor());
		saveSearchCriteria.setFromLength(stoneSearchCriteria.getFromLength());
		saveSearchCriteria.setToLength(stoneSearchCriteria.getToLength());
		saveSearchCriteria.setFromWidth(stoneSearchCriteria.getFromWidth());
		saveSearchCriteria.setToWidth(stoneSearchCriteria.getToWidth());
		saveSearchCriteria.setFromHeight(stoneSearchCriteria.getFromHeight());
		saveSearchCriteria.setToHeight(stoneSearchCriteria.getToHeight());
		saveSearchCriteria.setLab(MAPPER.writeValueAsString(stoneSearchCriteria.getLab()));
		saveSearchCriteria.setFancyColor(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColor()));
		saveSearchCriteria
				.setFancyColorIntensity(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorIntensity()));
		saveSearchCriteria
				.setFancyColorOvertone(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorOvertone()));
		saveSearchCriteria.setFromDepth(stoneSearchCriteria.getFromDepth());
		saveSearchCriteria.setToDepth(stoneSearchCriteria.getToDepth());
		saveSearchCriteria.setFromTable(stoneSearchCriteria.getFromTable());
		saveSearchCriteria.setToTable(stoneSearchCriteria.getToTable());
//		saveSearchCriteria.setFromGirdleThin(stoneSearchCriteria.getFromGirdleThin());
//		saveSearchCriteria.setToGirdleThin(stoneSearchCriteria.getToGirdleThin());
//		saveSearchCriteria.setFromGirdleThick(stoneSearchCriteria.getFromGirdleThick());
//		saveSearchCriteria.setToGirdleThick(stoneSearchCriteria.getToGirdleThick());
//		saveSearchCriteria.setFromCuletSize(stoneSearchCriteria.getFromCuletSize());
//		saveSearchCriteria.setToCuletSize(stoneSearchCriteria.getToCuletSize());
		saveSearchCriteria.setFromTotal(stoneSearchCriteria.getFromTotal());
		saveSearchCriteria.setToTotal(stoneSearchCriteria.getToTotal());
		saveSearchCriteria.setFromCrownHeight(stoneSearchCriteria.getFromCrownHeight());
		saveSearchCriteria.setToCrownHeight(stoneSearchCriteria.getToCrownHeight());
		saveSearchCriteria.setFromCrownAngle(stoneSearchCriteria.getFromCrownAngle());
		saveSearchCriteria.setToCrownAngle(stoneSearchCriteria.getToCrownAngle());
		saveSearchCriteria.setFromPavilionDepth(stoneSearchCriteria.getFromPavilionDepth());
		saveSearchCriteria.setToPavilionDepth(stoneSearchCriteria.getToPavilionDepth());
		saveSearchCriteria.setFromPavilionAngle(stoneSearchCriteria.getFromPavilionAngle());
		saveSearchCriteria.setToPavilionAngle(stoneSearchCriteria.getToPavilionAngle());
		saveSearchCriteria.setFromPavilionHeight(stoneSearchCriteria.getFromPavilionHeight());
		saveSearchCriteria.setToPavilionHeight(stoneSearchCriteria.getToPavilionHeight());
		saveSearchCriteria.setFromGirdle(stoneSearchCriteria.getFromGirdle());
		saveSearchCriteria.setToGirdle(stoneSearchCriteria.getToGirdle());
//		saveSearchCriteria.setFromShade(stoneSearchCriteria.getFromShade());
//		saveSearchCriteria.setToShade(stoneSearchCriteria.getToShade());
//		saveSearchCriteria.setFromMilky(stoneSearchCriteria.getFromMilky());
//		saveSearchCriteria.setToMilky(stoneSearchCriteria.getToMilky());
		saveSearchCriteria.setColor(MAPPER.writeValueAsString(stoneSearchCriteria.getColor()));
		saveSearchCriteria.setClarity(MAPPER.writeValueAsString(stoneSearchCriteria.getClarity()));
		saveSearchCriteria.setMilky(MAPPER.writeValueAsString(stoneSearchCriteria.getMilky()));
		saveSearchCriteria.setShade(MAPPER.writeValueAsString(stoneSearchCriteria.getShade()));
		saveSearchCriteria.setCountry(MAPPER.writeValueAsString(stoneSearchCriteria.getCountry()));
		saveSearchCriteria.setName(stoneSearchCriteria.getName());
		saveSearchCriteria.setStarLength(stoneSearchCriteria.getStarLength());
		// saveSearchCriteria.setLowerHalf(stoneSearchCriteria.getLowerHalf());
		// saveSearchCriteria.setDiameter(stoneSearchCriteria.getDiameter());
		saveSearchCriteria.setIs3EX(stoneSearchCriteria.getIs3EX());
		saveSearchCriteria.setIs3VG(stoneSearchCriteria.getIs3VG());
		saveSearchCriteria.setIsNOBGM(stoneSearchCriteria.getIsNOBGM());

		return saveSearchCriteria;
	}

	// convert stoneSearchCriteria To StoneSearchCriteria
	public StoneSearchCriteria convertstoneSearchCriteriaToStoneSearchCriteria(SaveSearchCriteria saveSearchCriteria)
			throws JsonParseException, JsonMappingException, IOException {
		StoneSearchCriteria stoneSearchCriteria = new StoneSearchCriteria();
		ObjectMapper MAPPER = new ObjectMapper();

		stoneSearchCriteria.setIsFancyColor(saveSearchCriteria.getIsFancyColor());
		stoneSearchCriteria.setStartPosition(saveSearchCriteria.getStartPosition());
		stoneSearchCriteria.setNoOfRecords(saveSearchCriteria.getNoOfRecords());
		stoneSearchCriteria.setShape(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getShape())
				? MAPPER.readValue(saveSearchCriteria.getShape(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfFromToWeights(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getListOfFromToWeights())
						? MAPPER.readValue(saveSearchCriteria.getListOfFromToWeights(),
								new TypeReference<List<Map<String, String>>>() {
								})
						: null);
		stoneSearchCriteria.setListOfCut(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getListOfCut())
				? MAPPER.readValue(saveSearchCriteria.getListOfCut(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria
				.setListOfPolish(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getListOfPolish())
						? MAPPER.readValue(saveSearchCriteria.getListOfPolish(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setListOfSym(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getListOfSym())
				? MAPPER.readValue(saveSearchCriteria.getListOfSym(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria
				.setListOfFlou(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getListOfFlou())
						? MAPPER.readValue(saveSearchCriteria.getListOfFlou(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setFromColor(saveSearchCriteria.getFromColor());
		stoneSearchCriteria.setToColor(saveSearchCriteria.getToColor());
		stoneSearchCriteria.setFromLength(saveSearchCriteria.getFromLength());
		stoneSearchCriteria.setToLength(saveSearchCriteria.getToLength());
		stoneSearchCriteria.setFromWidth(saveSearchCriteria.getFromWidth());
		stoneSearchCriteria.setToWidth(saveSearchCriteria.getToWidth());
		stoneSearchCriteria.setFromHeight(saveSearchCriteria.getFromHeight());
		stoneSearchCriteria.setToHeight(saveSearchCriteria.getToHeight());
		stoneSearchCriteria.setLab(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getLab())
				? MAPPER.readValue(saveSearchCriteria.getLab(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria
				.setFancyColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getFancyColor())
						? MAPPER.readValue(saveSearchCriteria.getFancyColor(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setFancyColorIntensity(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getFancyColorIntensity()) ? MAPPER
						.readValue(saveSearchCriteria.getFancyColorIntensity(), new TypeReference<List<String>>() {
						}) : null);
		stoneSearchCriteria.setFancyColorOvertone(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getFancyColorOvertone()) ? MAPPER
						.readValue(saveSearchCriteria.getFancyColorOvertone(), new TypeReference<List<String>>() {
						}) : null);
		stoneSearchCriteria.setFromDepth(saveSearchCriteria.getFromDepth());
		stoneSearchCriteria.setToDepth(saveSearchCriteria.getToDepth());
		stoneSearchCriteria.setFromTable(saveSearchCriteria.getFromTable());
		stoneSearchCriteria.setToTable(saveSearchCriteria.getToTable());
//		stoneSearchCriteria.setFromGirdleThin(saveSearchCriteria.getFromGirdleThin());
//		stoneSearchCriteria.setToGirdleThin(saveSearchCriteria.getToGirdleThin());
//		stoneSearchCriteria.setFromGirdleThick(saveSearchCriteria.getFromGirdleThick());
//		stoneSearchCriteria.setToGirdleThick(saveSearchCriteria.getToGirdleThick());
//		stoneSearchCriteria.setFromCuletSize(saveSearchCriteria.getFromCuletSize());
//		stoneSearchCriteria.setToCuletSize(saveSearchCriteria.getToCuletSize());
		stoneSearchCriteria.setFromTotal(saveSearchCriteria.getFromTotal());
		stoneSearchCriteria.setToTotal(saveSearchCriteria.getToTotal());
		stoneSearchCriteria.setFromCrownHeight(saveSearchCriteria.getFromCrownHeight());
		stoneSearchCriteria.setToCrownHeight(saveSearchCriteria.getToCrownHeight());
		stoneSearchCriteria.setFromCrownAngle(saveSearchCriteria.getFromCrownAngle());
		stoneSearchCriteria.setToCrownAngle(saveSearchCriteria.getToCrownAngle());
		stoneSearchCriteria.setFromPavilionDepth(saveSearchCriteria.getFromPavilionDepth());
		stoneSearchCriteria.setToPavilionDepth(saveSearchCriteria.getToPavilionDepth());
		stoneSearchCriteria.setFromPavilionAngle(saveSearchCriteria.getFromPavilionAngle());
		stoneSearchCriteria.setToPavilionAngle(saveSearchCriteria.getToPavilionAngle());
		stoneSearchCriteria.setFromPavilionHeight(saveSearchCriteria.getFromPavilionHeight());
		stoneSearchCriteria.setToPavilionHeight(saveSearchCriteria.getToPavilionHeight());
		stoneSearchCriteria.setFromGirdle(saveSearchCriteria.getFromGirdle());
		stoneSearchCriteria.setToGirdle(saveSearchCriteria.getToGirdle());
//		stoneSearchCriteria.setFromShade(saveSearchCriteria.getFromShade());
//		stoneSearchCriteria.setToShade(saveSearchCriteria.getToShade());
//		stoneSearchCriteria.setFromMilky(saveSearchCriteria.getFromMilky());
//		stoneSearchCriteria.setToMilky(saveSearchCriteria.getToMilky());
		stoneSearchCriteria.setColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getColor())
				? MAPPER.readValue(saveSearchCriteria.getColor(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setClarity(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getClarity())
				? MAPPER.readValue(saveSearchCriteria.getClarity(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setMilky(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getMilky())
				? MAPPER.readValue(saveSearchCriteria.getMilky(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setShade(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getShade())
				? MAPPER.readValue(saveSearchCriteria.getShade(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setCountry(!ShivamWebMethodUtils.isObjectisNullOrEmpty(saveSearchCriteria.getCountry())
				? MAPPER.readValue(saveSearchCriteria.getCountry(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setName(saveSearchCriteria.getName());
		stoneSearchCriteria.setStarLength(saveSearchCriteria.getStarLength());
		// stoneSearchCriteria.setLowerHalf(saveSearchCriteria.getLowerHalf());
		// stoneSearchCriteria.setDiameter(saveSearchCriteria.getDiameter());
		stoneSearchCriteria.setIs3EX(saveSearchCriteria.getIs3EX());
		stoneSearchCriteria.setIs3VG(saveSearchCriteria.getIs3VG());
		stoneSearchCriteria.setIsNOBGM(saveSearchCriteria.getIsNOBGM());
		return stoneSearchCriteria;
	}

	@Override
	public ResponseWrapperDTO searchCriteriaGetById(String id, String path) throws IOException {
		Optional<SaveSearchCriteria> saveSearchCriteriaOfUser = saveSearchCriteriaRepository.findById(id);
		if (!saveSearchCriteriaOfUser.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Search Criteria not found.", null,
					HttpStatus.BAD_REQUEST);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Search criteria by Id.",
				convertstoneSearchCriteriaToStoneSearchCriteria(saveSearchCriteriaOfUser.get()), HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteSearchCriteria(String searchCriteriaId, String path) {
		Optional<SaveSearchCriteria> saveSearchCriteriaOfUser = saveSearchCriteriaRepository.findById(searchCriteriaId);
		if (!saveSearchCriteriaOfUser.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Search Criteria not found.", null,
					HttpStatus.BAD_REQUEST);
		saveSearchCriteriaRepository.deleteById(searchCriteriaId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Search Criteria " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO updateSearchCriteria(String searchCriteriaId, StoneSearchCriteria body, String path)
			throws JsonProcessingException {
		Optional<SaveSearchCriteria> saveSearchCriteriaFromDb = saveSearchCriteriaRepository.findById(searchCriteriaId);
		if (!saveSearchCriteriaFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Search Criteria not found.", null,
					HttpStatus.BAD_REQUEST);
		Optional<User> user = userRepository.findById(body.getUserId());
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST);
		Optional<SaveSearchCriteria> saveSearchCriteriaOfUser = saveSearchCriteriaRepository
				.findByNameAndUserNotIdIn(body.getName(), body.getUserId(), searchCriteriaId);
		if (saveSearchCriteriaOfUser.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Search Criteria with name '" + body.getName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT);
		SaveSearchCriteria saveSearchCriteria = convertStoneSearchCriteriaToSaveSearchCriteria(body);
		saveSearchCriteria.setId(searchCriteriaId);
		saveSearchCriteria.setUser(user.get());
		saveSearchCriteria.setCreatedBy(saveSearchCriteriaFromDb.get().getCreatedBy());
		saveSearchCriteria.setCreatedDate(saveSearchCriteriaFromDb.get().getCreatedDate());
		saveSearchCriteriaRepository.saveAndFlush(saveSearchCriteria);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Search Criteria " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

}
