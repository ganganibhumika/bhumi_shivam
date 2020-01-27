package com.techhive.shivamweb.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techhive.shivamweb.enums.EnumForNotificationDescription;
import com.techhive.shivamweb.enums.EnumForNotificationType;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.CreateDemand;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.Notification;
import com.techhive.shivamweb.repository.CreateDemandRepository;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.CretaeDemandService;
import com.techhive.shivamweb.service.NotificationService;
import com.techhive.shivamweb.service.SendMailService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class CretaeDemandServiceImpl implements CretaeDemandService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	CreateDemandRepository createDemandRepository;

	@Autowired
	NotificationService notificationService;
	@Autowired
	SendMailService sendMailService;

	@Override
	public ResponseWrapperDTO saveCreateDemand(StoneSearchCriteria body, String path) throws JsonProcessingException {
		Optional<User> user = userRepository.findById(body.getUserId());
		if (!user.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, ShivamWebVariableUtils.USER_NOT_FOUND,
					null, HttpStatus.BAD_REQUEST);

		CreateDemand createDemand = convertStoneSearchCriteriaToCreateDemand(body);
		createDemand.setUser(user.get());

		createDemandRepository.saveAndFlush(createDemand);
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				Set<User> users = userRepository.findByisDeletedAndIsApprovedAndIsAdmin(false, true, true);
				Notification notification = new Notification();
				notification.setDescription(EnumForNotificationDescription.NEWSTONEDEMAND.toString());
				notification.setSetOfUserObject(users);
				notification.setCategory(EnumForNotificationType.DEMAND.toString());
				notification.setIsAdmin(true);
				notification.setStoneOrUserId(user.get().getUsername());
				notificationService.sendNotification(notification);
				for (User user2 : users) {
					sendMailService.sendMailForDemand(user2, user.get(),
							ShivamWebMethodUtils.remove1StAndLastCharOfString(createDemand.getShape()));
				}
			}
		});
		service.shutdown();
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Demand " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);

	}

	/* use same function in save user Demand. */
	// convert search criteria to create demand
	public CreateDemand convertStoneSearchCriteriaToCreateDemand(StoneSearchCriteria stoneSearchCriteria)
			throws JsonProcessingException {
		ObjectMapper MAPPER = new ObjectMapper();
		CreateDemand createDemand = new CreateDemand();
		createDemand.setIsFancyColor(stoneSearchCriteria.getIsFancyColor());
		createDemand.setShape(MAPPER.writeValueAsString(stoneSearchCriteria.getShape()));
		createDemand.setListOfFromToWeights(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFromToWeights()));
		createDemand.setListOfCut(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfCut()));
		createDemand.setListOfPolish(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfPolish()));
		createDemand.setListOfSym(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfSym()));
		createDemand.setListOfFlou(MAPPER.writeValueAsString(stoneSearchCriteria.getListOfFlou()));
		createDemand.setFromColor(stoneSearchCriteria.getFromColor());
		createDemand.setToColor(stoneSearchCriteria.getToColor());
		createDemand.setFromLength(stoneSearchCriteria.getFromLength());
		createDemand.setToLength(stoneSearchCriteria.getToLength());
		createDemand.setFromWidth(stoneSearchCriteria.getFromWidth());
		createDemand.setToWidth(stoneSearchCriteria.getToWidth());
		createDemand.setFromHeight(stoneSearchCriteria.getFromHeight());
		createDemand.setToHeight(stoneSearchCriteria.getToHeight());
		createDemand.setLab(MAPPER.writeValueAsString(stoneSearchCriteria.getLab()));
		createDemand.setFancyColor(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColor()));
		createDemand.setFancyColorIntensity(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorIntensity()));
		createDemand.setFancyColorOvertone(MAPPER.writeValueAsString(stoneSearchCriteria.getFancyColorOvertone()));
		createDemand.setFromDepth(stoneSearchCriteria.getFromDepth());
		createDemand.setToDepth(stoneSearchCriteria.getToDepth());
		createDemand.setFromTable(stoneSearchCriteria.getFromTable());
		createDemand.setToTable(stoneSearchCriteria.getToTable());
		// createDemand.setFromGirdleThin(stoneSearchCriteria.getFromGirdleThin());
		// createDemand.setToGirdleThin(stoneSearchCriteria.getToGirdleThin());
		// createDemand.setFromGirdleThick(stoneSearchCriteria.getFromGirdleThick());
		// createDemand.setToGirdleThick(stoneSearchCriteria.getToGirdleThick());
		// createDemand.setFromCuletSize(stoneSearchCriteria.getFromCuletSize());
		// createDemand.setToCuletSize(stoneSearchCriteria.getToCuletSize());
		createDemand.setFromTotal(stoneSearchCriteria.getFromTotal());
		createDemand.setToTotal(stoneSearchCriteria.getToTotal());
		createDemand.setFromCrownHeight(stoneSearchCriteria.getFromCrownHeight());
		createDemand.setToCrownHeight(stoneSearchCriteria.getToCrownHeight());
		createDemand.setFromCrownAngle(stoneSearchCriteria.getFromCrownAngle());
		createDemand.setToCrownAngle(stoneSearchCriteria.getToCrownAngle());
		createDemand.setFromPavilionDepth(stoneSearchCriteria.getFromPavilionDepth());
		createDemand.setToPavilionDepth(stoneSearchCriteria.getToPavilionDepth());
		createDemand.setFromPavilionAngle(stoneSearchCriteria.getFromPavilionAngle());
		createDemand.setToPavilionAngle(stoneSearchCriteria.getToPavilionAngle());
		createDemand.setFromPavilionHeight(stoneSearchCriteria.getFromPavilionHeight());
		createDemand.setToPavilionHeight(stoneSearchCriteria.getToPavilionHeight());
		createDemand.setFromGirdle(stoneSearchCriteria.getFromGirdle());
		createDemand.setToGirdle(stoneSearchCriteria.getToGirdle());
		// createDemand.setFromShade(stoneSearchCriteria.getFromShade());
		// createDemand.setToShade(stoneSearchCriteria.getToShade());
		// createDemand.setFromMilky(stoneSearchCriteria.getFromMilky());
		// createDemand.setToMilky(stoneSearchCriteria.getToMilky());
		createDemand.setColor(MAPPER.writeValueAsString(stoneSearchCriteria.getColor()));
		createDemand.setClarity(MAPPER.writeValueAsString(stoneSearchCriteria.getClarity()));
		createDemand.setMilky(MAPPER.writeValueAsString(stoneSearchCriteria.getMilky()));
		createDemand.setShade(MAPPER.writeValueAsString(stoneSearchCriteria.getShade()));
		createDemand.setCountry(MAPPER.writeValueAsString(stoneSearchCriteria.getCountry()));
		createDemand.setName(stoneSearchCriteria.getName());
		createDemand.setStarLength(stoneSearchCriteria.getStarLength());

		return createDemand;
	}

	@Override
	public StoneSearchCriteria convertCreateDemandToStoneSearchCriteria(CreateDemand createDemand) throws IOException {

		StoneSearchCriteria stoneSearchCriteria = new StoneSearchCriteria();
		ObjectMapper MAPPER = new ObjectMapper();

		stoneSearchCriteria.setIsFancyColor(createDemand.getIsFancyColor());

		stoneSearchCriteria.setShape(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getShape())
				? MAPPER.readValue(createDemand.getShape(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfFromToWeights(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfFromToWeights()) ? MAPPER.readValue(
						createDemand.getListOfFromToWeights(), new TypeReference<List<Map<String, String>>>() {
						}) : null);
		stoneSearchCriteria.setListOfCut(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfCut())
				? MAPPER.readValue(createDemand.getListOfCut(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfPolish(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfPolish())
				? MAPPER.readValue(createDemand.getListOfPolish(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfSym(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfSym())
				? MAPPER.readValue(createDemand.getListOfSym(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setListOfFlou(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfFlou())
				? MAPPER.readValue(createDemand.getListOfFlou(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFromColor(createDemand.getFromColor());
		stoneSearchCriteria.setToColor(createDemand.getToColor());
		stoneSearchCriteria.setFromLength(createDemand.getFromLength());
		stoneSearchCriteria.setToLength(createDemand.getToLength());
		stoneSearchCriteria.setFromWidth(createDemand.getFromWidth());
		stoneSearchCriteria.setToWidth(createDemand.getToWidth());
		stoneSearchCriteria.setFromHeight(createDemand.getFromHeight());
		stoneSearchCriteria.setToHeight(createDemand.getToHeight());
		stoneSearchCriteria.setLab(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getLab())
				? MAPPER.readValue(createDemand.getLab(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFancyColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColor())
				? MAPPER.readValue(createDemand.getFancyColor(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setFancyColorIntensity(
				!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColorIntensity())
						? MAPPER.readValue(createDemand.getFancyColorIntensity(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria
				.setFancyColorOvertone(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColorOvertone())
						? MAPPER.readValue(createDemand.getFancyColorOvertone(), new TypeReference<List<String>>() {
						})
						: null);
		stoneSearchCriteria.setFromDepth(createDemand.getFromDepth());
		stoneSearchCriteria.setToDepth(createDemand.getToDepth());
		stoneSearchCriteria.setFromTable(createDemand.getFromTable());
		stoneSearchCriteria.setToTable(createDemand.getToTable());
		// stoneSearchCriteria.setFromGirdleThin(searchHistory.getFromGirdleThin());
		// stoneSearchCriteria.setToGirdleThin(searchHistory.getToGirdleThin());
		// stoneSearchCriteria.setFromGirdleThick(searchHistory.getFromGirdleThick());
		// stoneSearchCriteria.setToGirdleThick(searchHistory.getToGirdleThick());
		// stoneSearchCriteria.setFromCuletSize(searchHistory.getFromCuletSize());
		// stoneSearchCriteria.setToCuletSize(searchHistory.getToCuletSize());
		stoneSearchCriteria.setFromTotal(createDemand.getFromTotal());
		stoneSearchCriteria.setToTotal(createDemand.getToTotal());
		stoneSearchCriteria.setFromCrownHeight(createDemand.getFromCrownHeight());
		stoneSearchCriteria.setToCrownHeight(createDemand.getToCrownHeight());
		stoneSearchCriteria.setFromCrownAngle(createDemand.getFromCrownAngle());
		stoneSearchCriteria.setToCrownAngle(createDemand.getToCrownAngle());
		stoneSearchCriteria.setFromPavilionDepth(createDemand.getFromPavilionDepth());
		stoneSearchCriteria.setToPavilionDepth(createDemand.getToPavilionDepth());
		stoneSearchCriteria.setFromPavilionAngle(createDemand.getFromPavilionAngle());
		stoneSearchCriteria.setToPavilionAngle(createDemand.getToPavilionAngle());
		stoneSearchCriteria.setFromPavilionHeight(createDemand.getFromPavilionHeight());
		stoneSearchCriteria.setToPavilionHeight(createDemand.getToPavilionHeight());
		stoneSearchCriteria.setFromGirdle(createDemand.getFromGirdle());
		stoneSearchCriteria.setToGirdle(createDemand.getToGirdle());
		// stoneSearchCriteria.setFromShade(searchHistory.getFromShade());
		// stoneSearchCriteria.setToShade(searchHistory.getToShade());
		// stoneSearchCriteria.setFromMilky(searchHistory.getFromMilky());
		// stoneSearchCriteria.setToMilky(searchHistory.getToMilky());
		stoneSearchCriteria.setColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getColor())
				? MAPPER.readValue(createDemand.getColor(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setClarity(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getClarity())
				? MAPPER.readValue(createDemand.getClarity(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setMilky(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getMilky())
				? MAPPER.readValue(createDemand.getMilky(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setShade(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getShade())
				? MAPPER.readValue(createDemand.getShade(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setCountry(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getCountry())
				? MAPPER.readValue(createDemand.getCountry(), new TypeReference<List<String>>() {
				})
				: null);
		stoneSearchCriteria.setName(createDemand.getName());
		stoneSearchCriteria.setStarLength(createDemand.getStarLength());
		return stoneSearchCriteria;
	}
	// convert Create demand To StoneSearchCriteria
	// public StoneSearchCriteria
	// convertCreateDemandToStoneSearchCriteria(CreateDemand createDemand)
	// throws JsonParseException, JsonMappingException, IOException {
	//
	// StoneSearchCriteria stoneSearchCriteria = new StoneSearchCriteria();
	// ObjectMapper MAPPER = new ObjectMapper();
	//
	// stoneSearchCriteria.setIsFancyColor(createDemand.getIsFancyColor());
	//
	// stoneSearchCriteria.setShape(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getShape())
	// ? MAPPER.readValue(createDemand.getShape(), new TypeReference<List<String>>()
	// {
	// })
	// : null);
	// stoneSearchCriteria.setListOfFromToWeights(
	// !ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfFromToWeights())
	// ? MAPPER.readValue(
	// createDemand.getListOfFromToWeights(), new TypeReference<List<Map<String,
	// String>>>() {
	// }) : null);
	// stoneSearchCriteria.setListOfCut(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfCut())
	// ? MAPPER.readValue(createDemand.getListOfCut(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setListOfPolish(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfPolish())
	// ? MAPPER.readValue(createDemand.getListOfPolish(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setListOfSym(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfSym())
	// ? MAPPER.readValue(createDemand.getListOfSym(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setListOfFlou(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getListOfFlou())
	// ? MAPPER.readValue(createDemand.getListOfFlou(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setFromColor(createDemand.getFromColor());
	// stoneSearchCriteria.setToColor(createDemand.getToColor());
	// stoneSearchCriteria.setFromLength(createDemand.getFromLength());
	// stoneSearchCriteria.setToLength(createDemand.getToLength());
	// stoneSearchCriteria.setFromWidth(createDemand.getFromWidth());
	// stoneSearchCriteria.setToWidth(createDemand.getToWidth());
	// stoneSearchCriteria.setFromHeight(createDemand.getFromHeight());
	// stoneSearchCriteria.setToHeight(createDemand.getToHeight());
	// stoneSearchCriteria.setLab(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getLab())
	// ? MAPPER.readValue(createDemand.getLab(), new TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setFancyColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColor())
	// ? MAPPER.readValue(createDemand.getFancyColor(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setFancyColorIntensity(
	// !ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColorIntensity())
	// ? MAPPER.readValue(createDemand.getFancyColorIntensity(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria
	// .setFancyColorOvertone(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getFancyColorOvertone())
	// ? MAPPER.readValue(createDemand.getFancyColorOvertone(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setFromDepth(createDemand.getFromDepth());
	// stoneSearchCriteria.setToDepth(createDemand.getToDepth());
	// stoneSearchCriteria.setFromTable(createDemand.getFromTable());
	// stoneSearchCriteria.setToTable(createDemand.getToTable());
	// stoneSearchCriteria.setFromGirdleThin(createDemand.getFromGirdleThin());
	// stoneSearchCriteria.setToGirdleThin(createDemand.getToGirdleThin());
	// stoneSearchCriteria.setFromGirdleThick(createDemand.getFromGirdleThick());
	// stoneSearchCriteria.setToGirdleThick(createDemand.getToGirdleThick());
	// stoneSearchCriteria.setFromCuletSize(createDemand.getFromCuletSize());
	// stoneSearchCriteria.setToCuletSize(createDemand.getToCuletSize());
	// stoneSearchCriteria.setFromTotal(createDemand.getFromTotal());
	// stoneSearchCriteria.setToTotal(createDemand.getToTotal());
	// stoneSearchCriteria.setFromCrownHeight(createDemand.getFromCrownHeight());
	// stoneSearchCriteria.setToCrownHeight(createDemand.getToCrownHeight());
	// stoneSearchCriteria.setFromCrownAngle(createDemand.getFromCrownAngle());
	// stoneSearchCriteria.setToCrownAngle(createDemand.getToCrownAngle());
	// stoneSearchCriteria.setFromPavilionDepth(createDemand.getFromPavilionDepth());
	// stoneSearchCriteria.setToPavilionDepth(createDemand.getToPavilionDepth());
	// stoneSearchCriteria.setFromPavilionAngle(createDemand.getFromPavilionAngle());
	// stoneSearchCriteria.setToPavilionAngle(createDemand.getToPavilionAngle());
	// stoneSearchCriteria.setFromPavilionHeight(createDemand.getFromPavilionHeight());
	// stoneSearchCriteria.setToPavilionHeight(createDemand.getToPavilionHeight());
	// stoneSearchCriteria.setFromGirdle(createDemand.getFromGirdle());
	// stoneSearchCriteria.setToGirdle(createDemand.getToGirdle());
	// stoneSearchCriteria.setFromShade(createDemand.getFromShade());
	// stoneSearchCriteria.setToShade(createDemand.getToShade());
	// stoneSearchCriteria.setFromMilky(createDemand.getFromMilky());
	// stoneSearchCriteria.setToMilky(createDemand.getToMilky());
	// stoneSearchCriteria.setColor(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getColor())
	// ? MAPPER.readValue(createDemand.getColor(), new TypeReference<List<String>>()
	// {
	// })
	// : null);
	// stoneSearchCriteria.setClarity(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getClarity())
	// ? MAPPER.readValue(createDemand.getClarity(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setMilky(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getMilky())
	// ? MAPPER.readValue(createDemand.getMilky(), new TypeReference<List<String>>()
	// {
	// })
	// : null);
	// stoneSearchCriteria.setShade(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getShade())
	// ? MAPPER.readValue(createDemand.getShade(), new TypeReference<List<String>>()
	// {
	// })
	// : null);
	// stoneSearchCriteria.setCountry(!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getCountry())
	// ? MAPPER.readValue(createDemand.getCountry(), new
	// TypeReference<List<String>>() {
	// })
	// : null);
	// stoneSearchCriteria.setName(createDemand.getName());
	// stoneSearchCriteria.setStarLength(createDemand.getStarLength());
	//
	// return stoneSearchCriteria;
	// }

	@Override
	public ResponseWrapperDTO getAllCreatedDemandByUser(Integer pageNumber, Integer noOfRecords, String path,
			String sortOrder, String searchText, String userId, String path2) throws IOException {

		PageRequest request = PageRequest.of(pageNumber, noOfRecords, Sort.Direction.DESC, "createdDate");
		Page<CreateDemand> listOfAllCreateDemandWithPageable = createDemandRepository.findAll(request);


		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get all Created Demand",
				listOfAllCreateDemandWithPageable, HttpStatus.OK, path);

		// for (CreateDemand createDemand : listOfCreateDemand) {
		// listOfCriteriaForDemand.add(convertCreateDemandToStoneSearchCriteria(createDemand));
		// }
		// return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "List create of
		// created demand.",
		// listOfCriteriaForDemand, HttpStatus.OK, path);

	}

	@Override
	public ResponseWrapperDTO deleteFromDemand(MyRequestBody body, String path) {
		List<CreateDemand> listOfCreateDemand = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<CreateDemand>>() {
				});
		if (ShivamWebMethodUtils.isListNullOrEmpty(listOfCreateDemand))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		for (CreateDemand createDemand : listOfCreateDemand) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(createDemand.getId())) {
				Optional<CreateDemand> createDemandObj = createDemandRepository.findById(createDemand.getId());
				if (createDemandObj.isPresent())
					createDemandRepository.deleteById(createDemand.getId());
			}
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Demand " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

}
