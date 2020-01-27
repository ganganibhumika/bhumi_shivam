package com.techhive.shivamweb.custom.repository.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.techhive.shivamweb.custom.repository.PktMasterCustomRepository;
import com.techhive.shivamweb.master.model.NewArrivalSettings;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ColorMasterRepository;
import com.techhive.shivamweb.repository.FancyColorMasterRepository;
import com.techhive.shivamweb.repository.FancyIntensityMasterRepository;
import com.techhive.shivamweb.repository.FancyOvertoneMasterRepository;
import com.techhive.shivamweb.repository.NewArrivalSettingsRepository;
import com.techhive.shivamweb.repository.PktMasterRepository;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.DiscountMasterService;
import com.techhive.shivamweb.service.NewArrivalSettingsService;
import com.techhive.shivamweb.service.SearchHistoryService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class PktMasterCustomRepositoryImpl implements PktMasterCustomRepository {

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private PktMasterRepository pktMasterRepository;

	@Autowired
	private ColorMasterRepository colorMasterRepository;

	@Autowired
	private FancyColorMasterRepository fancyColorMasterRepository;

	@Autowired
	private FancyIntensityMasterRepository fancyIntensityMasterRepository;

	@Autowired
	private FancyOvertoneMasterRepository fancyOvertoneMasterRepository;

	@Autowired
	private DiscountMasterService discountMasterService;

	@Autowired
	NewArrivalSettingsService newArrivalSettingsService;

	@Autowired
	NewArrivalSettingsRepository newArrivalSettingsRepository;

	@Autowired
	SearchHistoryService searchHistoryService;

	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	public String FROM_WEIGHT = "fromWeight";
	public String TO_WEIGHT = "toWeight";

	public int totalNoOfRecordReturn;

	@Override
	public ResponseWrapperDTO getStonesViaLazzyLoadingOfSearchCriteria(StoneSearchCriteria body)
			throws JsonProcessingException {

		List<String> listOfShapeForSearchHistory = new ArrayList<>();
		Set<String> listOfNotFoundShape = new HashSet<>();
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
			listOfShapeForSearchHistory.addAll(body.getShape());
			listOfNotFoundShape.addAll(body.getShape());
		}

		StringBuilder sbQuery = new StringBuilder();

		String resultFields = "SELECT PKT.*, SHP.shapeName,SHP.shapeImage,SHP.shapeOrder,MILK.milkMasterOrder,LAB.labMasterOrder,FLO.fluorescenceMasterOrder,FOVERTONE.fancyOvertoneOrder,FINTENSITY.fancyIntensityOrder,FCOLOR.fancyColorOrder,CUTPLISHSYM.cutPolishSymmentryMasterOrder,COUNTRY.countryMasterOrder,COLOR.colorOrder,CLARITY.clarityMasterOrder,SHADE.brownShadeMasterOrder";
		sbQuery.append(" FROM tblPktMaster PKT ");
		sbQuery.append(" LEFT JOIN tblShapeMaster SHP ON SHP.shapeName = PKT.shape ");
		sbQuery.append(" LEFT JOIN tblMilkMaster MILK ON MILK.shortName = PKT.codeOfMilky ");
		sbQuery.append(" LEFT JOIN tblLabMaster LAB ON LAB.labMasterName = PKT.lab ");
		sbQuery.append(" LEFT JOIN tblFluorescenceMaster FLO ON FLO.shortName = PKT.codeOfFluorescence ");
		sbQuery.append(" LEFT JOIN tblFancyOvertoneMaster FOVERTONE ON FOVERTONE.fancyOvertoneName = PKT.fOvertone ");
		sbQuery.append(
				"LEFT JOIN tblFancyIntensityMaster FINTENSITY ON FINTENSITY.fancyIntensityName = PKT.fIntensity ");
		sbQuery.append("LEFT JOIN tblFancyColorMaster FCOLOR ON FCOLOR.fancyColorName = PKT.fColor ");
		sbQuery.append(
				"LEFT JOIN tblCutPolishSymmetryMaster CUTPLISHSYM ON CUTPLISHSYM.cutPolishSymmentryMasterName = PKT.codeOfPolish ");
		sbQuery.append(" LEFT JOIN tblCountryMaster COUNTRY ON COUNTRY.countryMasterName = PKT.country ");
		sbQuery.append(" LEFT JOIN tblColorMaster COLOR ON COLOR.colorName = PKT.codeOfColor ");
		sbQuery.append(" LEFT JOIN tblClarityMaster CLARITY ON CLARITY.clarityMasterName = PKT.codeOfClarity ");
		sbQuery.append(" LEFT JOIN tblBrownShadeMaster SHADE ON SHADE.shortName = PKT.codeOfShade ");

		sbQuery.append(" WHERE ");

		/* if search from show */
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getShowId())) {
			sbQuery.append("PKT.showId = ").append("'" + body.getShowId() + "'").append(" AND ");
		}

		// ............... Stock..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())) {
			sbQuery.append("( PKT.stoneId IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" OR ");
			sbQuery.append("PKT.certNo IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" ) AND ");
		}

		// list of From/To weights in designe carat

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFromToWeights())) {
			sbQuery.append(" ( ");
			AtomicInteger runCount = new AtomicInteger();
			body.getListOfFromToWeights().forEach(map -> {
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT))
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT))) {
					return;
				}

				if (runCount.get() >= 1) {
					sbQuery.append(" OR ");
				}

				sbQuery.append(" (");

				double fromCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT)) ? -1
						: Double.valueOf(map.get(FROM_WEIGHT));

				double toCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT)) ? -1
						: Double.valueOf(map.get(TO_WEIGHT));

				if (fromCarat > 0 && toCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat).append(" AND ").append("PKT.carat <= ")
							.append(toCarat);
				} else if (fromCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat);
				} else {
					sbQuery.append("PKT.carat <= ").append(toCarat);
				}

				sbQuery.append(" )");

				runCount.incrementAndGet();
			});

			sbQuery.append(" ) ");
			sbQuery.append(" AND ");

		}
		// ...................stone ID.................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getStoneId())) {
			sbQuery.append("PKT.stoneId = ").append("'" + body.getStoneId() + "'").append(" AND ");
		}

		// ............... Shape........................

		/* if cut available and shape not available.. */
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			sbQuery.append("PKT.codeOfCut IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut())).append(" AND ");

		}

		/* if cut not available and shape available.. */
		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
			sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
					.append(" AND ");

		}
		// .................if cut and shape available....available..
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			if (body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase())
					|| body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase())) {

				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase());
				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase());

				sbQuery.append(" ( ");
				sbQuery.append("PKT.shape =" + "'" + ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase() + "'"
						+ " AND PKT.codeOfCut IN")
						.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut()));

				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
					sbQuery.append(" OR ");
					sbQuery.append("PKT.shape IN ")
							.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()));

				}
				sbQuery.append(" ) ").append("AND ");
			} else {

				sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
						.append(" AND ");
			}

		}

		// ............... Lab...........................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getLab())) {

			sbQuery.append("PKT.lab IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getLab()))
					.append(" AND ");
		}

		// ............... Certificate..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCertificateNo())) {
			sbQuery.append("PKT.certNo = ").append("'" + body.getCertificateNo() + "'").append(" AND ");
		}

		// ............... List of Polish..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfPolish())) {
			sbQuery.append("PKT.codeOfPolish IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfPolish())).append(" AND ");
		}

		// ............... List of Symmentry..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfSym())) {
			sbQuery.append("PKT.codeOfSymmentry IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfSym())).append(" AND ");
		}

		// ............... List of Fluorensence..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFlou())) {
			sbQuery.append("PKT.codeOfFluorescence IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfFlou())).append(" AND ");
		}

		// ............... Country ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCountry())) {
			sbQuery.append("PKT.country IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getCountry()))
					.append("AND ");
		}

		// ...............Color (color code OFcolor like [D to N])..white
		// color.................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getColor())) {
			sbQuery.append("PKT.codeOfColor IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && !body.getIsFancyColor()) {
			sbQuery.append("PKT.codeOfColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(colorMasterRepository.getArrayListForWhiteColor()))
					.append(" AND ");
		}

		// ............... Shade (list of shade codeOfshade)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getShade())) {
			sbQuery.append("PKT.codeOfShade IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShade()))
					.append(" AND ");
		}
		// ............... Milky (list of codeOfMilky)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getMilky())) {
			sbQuery.append("PKT.codeOfMilky IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getMilky()))
					.append(" AND ");
		}

		// ............... Clarity (list of clarity List )..................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getClarity())) {
			sbQuery.append("PKT.codeOfClarity IN")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getClarity())).append(" AND ");
		}
		// ............... Depth(For T.DEPTH Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ")
						.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())) {
				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ");
			} else {
				sbQuery.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			}
		}

		// ............... Table(For Table(%) Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ")
						.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ");
			} else {
				sbQuery.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			}
		}

		// ............... Crown Angle (For C.Angle Field in Design) ..................
		//
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ")
						.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			}
		}

		// ............... Crown Height (For C.Height Field in Design --db=crownHeight)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ")
						.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())) {
				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			}
		}

		// ............... Pavilion Angle (For P.Angle Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ")
						.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			}
		}

		// ............... Pavilion Height (For P.HEIGHT(%) Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

				sbQuery.append("PKT.pavilionHeight >=").append(body.getFromPavilionHeight()).append(" AND ")
						.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())) {
				sbQuery.append("PKT.pavilionHeight >= ").append(body.getFromPavilionHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			}
		}

		// ............... Length (For Length Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ").append("PKT.Length <= ")
						.append(body.getToLength()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ");
			} else {
				sbQuery.append("PKT.length <= ").append(body.getToLength()).append(" AND ");
			}
		}

		// ............... Width (For Width Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ").append("PKT.width <= ")
						.append(body.getToWidth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ");
			} else {
				sbQuery.append("PKT.width <= ").append(body.getToWidth()).append(" AND ");
			}
		}

		// ............... Height (For Height Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ").append("PKT.height <= ")
						.append(body.getToHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.height <= ").append(body.getToHeight()).append(" AND ");
			}
		}

		// ............... Girdle (For Girdle(%) Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ").append("PKT.girdle <= ")
						.append(body.getToGirdle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ");
			} else {
				sbQuery.append("PKT.girdle <= ").append(body.getToGirdle()).append(" AND ");
			}
		}

		// ............... Fancy color .................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColor())) {
			sbQuery.append(" PKT.fColor IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {
			sbQuery.append(" PKT.fColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyColorMasterRepository.getArrayListForFcolor()))
					.append(" AND ");
		}

		// ............... Fancy Intensity ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorIntensity())) {
			sbQuery.append(" PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorIntensity()))
					.append(" AND ");

		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append("PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyIntensityMasterRepository.getArrayListForfIntensity()))
					.append(" AND ");

		}

		// ............... Fancy Overtone ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorOvertone())) {
			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorOvertone())).append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyOvertoneMasterRepository.getArrayListForfOvertone()))
					.append(" AND ");
		}
		sbQuery.append("PKT.isSold = 0 ").append(" AND ");
		// after where not any iff true then

		if (sbQuery.substring(sbQuery.lastIndexOf("WHERE"), sbQuery.length()).length() <= 6) {
			sbQuery.setLength(sbQuery.length() - 6);
		}

		if (sbQuery.toString().contains("AND")) {
			sbQuery.setLength(sbQuery.length() - 4);
		}

		/* lis of not found stone */
		List<PktMaster> listOfResponsePktMastIfTotalPrice = new ArrayList<>();
		Map<String, Object> mapForReturn = new HashMap<>();

		/* End */

		/* count total no of record */

		String resultFieldsForCount = "SELECT COUNT(*)";
		Query query = entityManager.createNativeQuery(resultFieldsForCount + sbQuery.toString());
		List<Long> listForCount = query.getResultList();

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listForCount)
				&& Long.valueOf(String.valueOf(listForCount.get(0))) < 1) {
			mapForReturn.put(ShivamWebVariableUtils.KEY_FOR_CONTETNT, new ArrayList<>());
			mapForReturn.put(ShivamWebVariableUtils.KET_FOR_NOT_FOUND_SHAPE,
					ShivamWebMethodUtils.isSetNullOrEmpty(listOfNotFoundShape) ? null : listOfNotFoundShape);
			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, ShivamWebVariableUtils.RECORD_NOT_FOUND_IN_DB,
					mapForReturn, HttpStatus.OK);
		}

		// if not get total price then return more then 300 records

		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {

			if (Long.valueOf(String.valueOf(listForCount.get(0))) > Long
					.valueOf(ShivamWebVariableUtils.MAX_RECORDS_LIMITS)) {

				mapForReturn.put(ShivamWebVariableUtils.KEY_FOR_CONTETNT, new ArrayList<>());
				mapForReturn.put(ShivamWebVariableUtils.KET_FOR_NOT_FOUND_SHAPE, null);

				mapForReturn.put(ShivamWebVariableUtils.IS_MORE_THEN_300, true);

				return new ResponseWrapperDTO(HttpServletResponse.SC_MULTIPLE_CHOICES,
						ShivamWebVariableUtils.MORE_THAN_MAX_RECORDS_LIMITS, mapForReturn, HttpStatus.OK);
			}
		}

		/* ....... End .............. */

		/* Execute Query. */

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsShow()) && body.getIsShow()) {
			sbQuery.append(" order by showId desc ");
		}

		Query finalQuery = entityManager.createNativeQuery(resultFields + sbQuery.toString(),
				ShivamWebVariableUtils.SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST);

		List<PktMaster> listOfResponsePktMast = finalQuery.getResultList();

		/* Discount */

		List<NewArrivalSettings> newArrivalSettingFromDb = newArrivalSettingsRepository.findAll();

		DecimalFormat df = new DecimalFormat("####0.00");
		DecimalFormat formatterTwoDigit = new DecimalFormat("##.00");
		List<Double> disc;

		for (PktMaster responsePktMast : listOfResponsePktMast) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getGiDate())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {
				responsePktMast.setDiscOrignal(responsePktMast.getDisc());

				System.out.println("stone ID::" + responsePktMast);
				/* Set id new arrival stone */
				// && !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getGiDate())
				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(newArrivalSettingFromDb)) {
					responsePktMast.setIsNewArrival(newArrivalSettingsService
							.isNewArrival(newArrivalSettingFromDb.get(0).getNoOfDays(), responsePktMast.getGiDate()));
				}

				/* End */
				/* List of not found stone */
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {
					listOfNotFoundShape.remove(responsePktMast.getShape());
				}
				/* End */
				disc = ShivamWebMethodUtils.getDefaultDiscForCal();
				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
						&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
								.contains(responsePktMast.getfColor().trim().toLowerCase())) {
					disc = ShivamWebMethodUtils.getDefaultDiscForCal();
					responsePktMast.setDisc((double) 0);
				} else {
					/* Add user wise discount */

					disc = discountMasterService.getDiscountByUserId(body.getUserId(), responsePktMast.getGiDate(),
							responsePktMast.getCarat(), responsePktMast.getShape(), false);
					// get default discount
					if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)) {
						disc = discountMasterService.getDiscountByUserId(ShivamWebVariableUtils.DEFAULT_USER_ID,
								responsePktMast.getGiDate(), responsePktMast.getCarat(), responsePktMast.getShape(),
								true);
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

				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {

					responsePktMast.setRapRate(
							Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));
					responsePktMast.setDisc(perCaratDisc);
					responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
					responsePktMast.setTotalPrice(Double
							.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
				}
				/* End Discount */

				/* total price condition */
				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {

					if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
							&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
							&& responsePktMast.getTotalPrice() <= body.getToTotal()
							&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
						listOfResponsePktMastIfTotalPrice.add(responsePktMast);
						listOfNotFoundShape.remove(responsePktMast.getShape());

					} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
							&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
							&& responsePktMast.getTotalPrice() <= body.getToTotal()) {
						listOfResponsePktMastIfTotalPrice.add(responsePktMast);
						listOfNotFoundShape.remove(responsePktMast.getShape());
					} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
							&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
							&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
						listOfResponsePktMastIfTotalPrice.add(responsePktMast);
						listOfNotFoundShape.remove(responsePktMast.getShape());
					}

					if (listOfResponsePktMastIfTotalPrice.size() > ShivamWebVariableUtils.MAX_RECORDS_LIMITS) {
						mapForReturn.put(ShivamWebVariableUtils.KEY_FOR_CONTETNT, new ArrayList<>());
						mapForReturn.put(ShivamWebVariableUtils.KET_FOR_NOT_FOUND_SHAPE, null);

						mapForReturn.put(ShivamWebVariableUtils.IS_MORE_THEN_300, true);

						return new ResponseWrapperDTO(HttpServletResponse.SC_MULTIPLE_CHOICES,
								ShivamWebVariableUtils.MORE_THAN_MAX_RECORDS_LIMITS, mapForReturn, HttpStatus.OK);
					}

				}

				/* End total price condition */
			}
		}

		mapForReturn.put(ShivamWebVariableUtils.KET_FOR_NOT_FOUND_SHAPE,
				ShivamWebMethodUtils.isSetNullOrEmpty(listOfNotFoundShape) ? null : listOfNotFoundShape);
		mapForReturn.put(ShivamWebVariableUtils.IS_MORE_THEN_300, false);

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal()))

		{

			if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMastIfTotalPrice)) {
				listOfResponsePktMastIfTotalPrice.get(0).setTotalNoOfRecords(listOfResponsePktMastIfTotalPrice.size()); // use
			}
			// app
			mapForReturn.put(ShivamWebVariableUtils.KEY_FOR_CONTETNT,
					ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMastIfTotalPrice) ? new ArrayList<>()
							: listOfResponsePktMastIfTotalPrice);
			if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())
					&& !listOfResponsePktMastIfTotalPrice.isEmpty()
					&& listOfResponsePktMastIfTotalPrice.size() <= ShivamWebVariableUtils.MAX_RECORDS_LIMITS)

				body.setShape(!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfShapeForSearchHistory)
						? listOfShapeForSearchHistory
						: null);

			if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())
					&& !ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMastIfTotalPrice)) {
				searchHistoryService.saveSearchHistory(body);
			}

			return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Successfully got search stone detail.",
					mapForReturn, HttpStatus.OK);
		}

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMast)) {
			listOfResponsePktMast.get(0).setTotalNoOfRecords(listOfResponsePktMast.size()); // use for return
																							// totalNoOFRecors for app
		}
		mapForReturn.put(ShivamWebVariableUtils.KEY_FOR_CONTETNT,
				ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMast) ? new ArrayList<>()
						: listOfResponsePktMast);
		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId()) && !listOfResponsePktMast.isEmpty()
				&& listOfResponsePktMast.size() <= ShivamWebVariableUtils.MAX_RECORDS_LIMITS)
			body.setShape(
					!ShivamWebMethodUtils.isListIsNullOrEmpty(listOfShapeForSearchHistory) ? listOfShapeForSearchHistory
							: null);

		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(listOfResponsePktMast)) {
			searchHistoryService.saveSearchHistory(body);
		}
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Successfully got search stone detail.", mapForReturn,
				HttpStatus.OK);
	}

	/*
	 * ******************************** View Stone By ID
	 * 
	 */

	// @Override
	// public ResponseWrapperDTO viewStoneByStoneIdAndUserId(String stoneId, String
	// userId) {
	//
	// StringBuilder sbQuery = new StringBuilder();
	//
	// String resultFields = "SELECT PKT.*,
	// SHP.shapeName,SHP.shapeImage,SHP.shapeOrder,MILK.milkMasterOrder,LAB.labMasterOrder,FLO.fluorescenceMasterOrder,FOVERTONE.fancyOvertoneOrder,FINTENSITY.fancyIntensityOrder,FCOLOR.fancyColorOrder,CUTPLISHSYM.cutPolishSymmentryMasterOrder,COUNTRY.countryMasterOrder,COLOR.colorOrder,CLARITY.clarityMasterOrder,SHADE.brownShadeMasterOrder";
	// sbQuery.append(" FROM tblPktMaster PKT ");
	// sbQuery.append(" LEFT JOIN tblShapeMaster SHP ON SHP.shapeName = PKT.shape
	// ");
	// sbQuery.append("LEFT JOIN tblMilkMaster MILK ON MILK.shortName =
	// PKT.codeOfMilky ");
	// sbQuery.append("LEFT JOIN tblLabMaster LAB ON LAB.labMasterName = PKT.lab ");
	// sbQuery.append("LEFT JOIN tblFluorescenceMaster FLO ON FLO.shortName =
	// PKT.codeOfFluorescence ");
	// sbQuery.append("LEFT JOIN tblFancyOvertoneMaster FOVERTONE ON
	// FOVERTONE.fancyOvertoneName = PKT.fOvertone ");
	// sbQuery.append(
	// "LEFT JOIN tblFancyIntensityMaster FINTENSITY ON
	// FINTENSITY.fancyIntensityName = PKT.fIntensity ");
	// sbQuery.append("LEFT JOIN tblFancyColorMaster FCOLOR ON FCOLOR.fancyColorName
	// = PKT.fColor ");
	// sbQuery.append(
	// "LEFT JOIN tblCutPolishSymmetryMaster CUTPLISHSYM ON
	// CUTPLISHSYM.cutPolishSymmentryMasterName = PKT.codeOfPolish ");
	// sbQuery.append("LEFT JOIN tblCountryMaster COUNTRY ON
	// COUNTRY.countryMasterName = PKT.country ");
	// sbQuery.append("LEFT JOIN tblColorMaster COLOR ON COLOR.colorName =
	// PKT.codeOfColor ");
	// sbQuery.append("LEFT JOIN tblClarityMaster CLARITY ON
	// CLARITY.clarityMasterName = PKT.codeOfClarity ");
	// sbQuery.append("LEFT JOIN tblBrownShadeMaster SHADE ON
	// SHADE.brownShadeMasterName = PKT.codeOfShade ");
	//
	// sbQuery.append(" WHERE ");
	//
	// // ............... Stone ID..................
	// if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(stoneId)) {
	// sbQuery.append("( PKT.stoneId =" + "'" + stoneId + "'").append(" OR ");
	// sbQuery.append("PKT.certNo =" + "'" + stoneId + "'").append(" ) AND ");
	// }
	// // sbQuery.append("PKT.isSold = 0 ").append(" AND ");
	// // after where not any if true then
	//
	// if (sbQuery.substring(sbQuery.lastIndexOf("WHERE"),
	// sbQuery.length()).length() <= 6) {
	// sbQuery.setLength(sbQuery.length() - 6);
	// }
	//
	// if (sbQuery.toString().contains("AND")) {
	// sbQuery.setLength(sbQuery.length() - 4);
	// }
	//
	// /* Execute Query. */
	//
	// Query finalQuery = entityManager.createNativeQuery(resultFields +
	// sbQuery.toString(),
	// ShivamWebVariableUtils.SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST);
	//
	// List<PktMaster> listOfResponsePktMast = finalQuery.getResultList();
	//
	// /* Discount */
	// DecimalFormat df = new DecimalFormat("####0.00");
	// List<Double> disc;
	//
	// List<NewArrivalSettings> newArrivalSettingFromDb =
	// newArrivalSettingsRepository.findAll();
	// for (PktMaster responsePktMast : listOfResponsePktMast) {
	//
	// /* Set image,video url */
	// responsePktMast.setImageUrl(ShivamWebMethodUtils.setImageURL(responsePktMast.getStoneId()));
	// // responsePktMast
	// // .setVideoUrl(ShivamWebVariableUtils.VIDEO_LINK_FROM_360_VIEWER +
	// // responsePktMast.getStoneId());
	// responsePktMast.setVideoUrl(ShivamWebMethodUtils.setVideoURL(responsePktMast.getStoneId()));
	//
	// responsePktMast.setSaveVideoUrl(ShivamWebMethodUtils.setSaveVideoURL(responsePktMast.getStoneId()));
	// /* End Set image ,video url */
	//
	// responsePktMast.setInclusionPlottingUrl(ShivamWebMethodUtils.setPlottingURL(responsePktMast.getStoneId()));
	// /* Set id new arrival stone */
	// responsePktMast.setIsNewArrival(newArrivalSettingsService
	// .isNewArrival(newArrivalSettingFromDb.get(0).getNoOfDays(),
	// responsePktMast.getGiDate()));
	//
	// /* End */
	//
	// disc = ShivamWebMethodUtils.getDefaultDiscForCal();
	// if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
	// && Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
	// .contains(responsePktMast.getfColor().trim().toLowerCase())) {
	// disc = ShivamWebMethodUtils.getDefaultDiscForCal();
	// responsePktMast.setDisc((double) 0);
	// } else {
	// /* Add user wise discount */
	//
	// disc = discountMasterService.getDiscountByUserId(userId,
	// responsePktMast.getGiDate(),
	// responsePktMast.getCarat(), responsePktMast.getShape(), false);
	// // get default discount
	// if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)) {
	// disc =
	// discountMasterService.getDiscountByUserId(ShivamWebVariableUtils.DEFAULT_USER_ID,
	// responsePktMast.getGiDate(), responsePktMast.getCarat(),
	// responsePktMast.getShape(), true);
	// }
	// // END default discount get
	// }
	// Double perCaratDisc = (double) 0;
	// if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
	// && !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
	// Double perCaratDisFromDisMast = disc.get(0).doubleValue();
	// perCaratDisc = responsePktMast.getDisc() + perCaratDisFromDisMast;
	//
	// } else if (!ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
	// && ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
	// Double perCaratDisFromDisMast = disc.get(0).doubleValue();
	// perCaratDisc = 0 + perCaratDisFromDisMast;
	//
	// } else if (ShivamWebMethodUtils.isListIsNullOrEmpty(disc)
	// && !ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getDisc())) {
	// perCaratDisc = responsePktMast.getDisc() + 0;
	//
	// }
	//
	// /* .....................End get discount base on userID ................. */
	// // getgRapAsRapRate==gRape
	//
	// responsePktMast
	// .setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() *
	// responsePktMast.getgRap())));
	// responsePktMast.setDisc(perCaratDisc);
	// responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 -
	// perCaratDisc) / 100);
	// responsePktMast.setTotalPrice(
	// Double.valueOf(df.format(responsePktMast.getPerCaratePrice() *
	// responsePktMast.getCarat())));
	//
	// /* End Discount */
	//
	// }
	// return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Successfully got
	// stone detail.",
	// listOfResponsePktMast.get(0), HttpStatus.OK);
	// }

	@Override
	public Set<PktMaster> getRecomendedStone(List<String> pktidsNotToCheck, StoneSearchCriteria body) {

		StringBuilder sbQuery = new StringBuilder();

		String resultFields = "SELECT PKT.*, SHP.shapeName,SHP.shapeImage,SHP.shapeOrder,MILK.milkMasterOrder,LAB.labMasterOrder,FLO.fluorescenceMasterOrder,FOVERTONE.fancyOvertoneOrder,FINTENSITY.fancyIntensityOrder,FCOLOR.fancyColorOrder,CUTPLISHSYM.cutPolishSymmentryMasterOrder,COUNTRY.countryMasterOrder,COLOR.colorOrder,CLARITY.clarityMasterOrder,SHADE.brownShadeMasterOrder";
		sbQuery.append(" FROM tblPktMaster PKT ");
		sbQuery.append(" LEFT JOIN tblShapeMaster SHP ON SHP.shapeName = PKT.shape ");
		sbQuery.append(" LEFT JOIN tblMilkMaster MILK ON MILK.shortName = PKT.codeOfMilky ");
		sbQuery.append(" LEFT JOIN tblLabMaster LAB ON LAB.labMasterName = PKT.lab ");
		sbQuery.append(" LEFT JOIN tblFluorescenceMaster FLO ON FLO.shortName = PKT.codeOfFluorescence ");
		sbQuery.append(" LEFT JOIN tblFancyOvertoneMaster FOVERTONE ON FOVERTONE.fancyOvertoneName = PKT.fOvertone ");
		sbQuery.append(
				"LEFT JOIN tblFancyIntensityMaster FINTENSITY ON FINTENSITY.fancyIntensityName = PKT.fIntensity ");
		sbQuery.append("LEFT JOIN tblFancyColorMaster FCOLOR ON FCOLOR.fancyColorName = PKT.fColor ");
		sbQuery.append(
				"LEFT JOIN tblCutPolishSymmetryMaster CUTPLISHSYM ON CUTPLISHSYM.cutPolishSymmentryMasterName = PKT.codeOfPolish ");
		sbQuery.append(" LEFT JOIN tblCountryMaster COUNTRY ON COUNTRY.countryMasterName = PKT.country ");
		sbQuery.append(" LEFT JOIN tblColorMaster COLOR ON COLOR.colorName = PKT.codeOfColor ");
		sbQuery.append(" LEFT JOIN tblClarityMaster CLARITY ON CLARITY.clarityMasterName = PKT.codeOfClarity ");
		sbQuery.append(" LEFT JOIN tblBrownShadeMaster SHADE ON SHADE.brownShadeMasterName = PKT.codeOfShade ");

		sbQuery.append(" WHERE ");

		// ............... Stock..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())) {
			sbQuery.append("( PKT.stoneId IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" OR ");
			sbQuery.append("PKT.certNo IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" ) AND ");
		}

		// list of From/To weights in designe carat

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFromToWeights())) {
			sbQuery.append(" ( ");

			AtomicInteger runCount = new AtomicInteger();

			body.getListOfFromToWeights().forEach(map -> {
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT))
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT))) {
					return;
				}

				if (runCount.get() >= 1) {
					sbQuery.append(" OR ");
				}

				sbQuery.append(" (");

				double fromCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT)) ? -1
						: Double.valueOf(map.get(FROM_WEIGHT));

				double toCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT)) ? -1
						: Double.valueOf(map.get(TO_WEIGHT));

				if (fromCarat > 0 && toCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat).append(" AND ").append("PKT.carat <= ")
							.append(toCarat);
				} else if (fromCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat);
				} else {
					sbQuery.append("PKT.carat <= ").append(toCarat);
				}

				sbQuery.append(" )");

				runCount.incrementAndGet();
			});

			sbQuery.append(" ) ");
			sbQuery.append(" AND ");

		}
		// ...................stone ID.................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getStoneId())) {
			sbQuery.append("PKT.stoneId = ").append("'" + body.getStoneId().trim() + "'").append(" AND ");
		}

		// ............... Shape........................

		/* if cut available and shape not available.. */
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			sbQuery.append("PKT.codeOfCut IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut())).append(" AND ");

		}

		/* if cut not available and shape available.. */
		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
			sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
					.append(" AND ");

		}
		// .................if cut and shape available....available..
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			if (body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase())
					|| body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase())) {

				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase());
				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase());

				sbQuery.append(" ( ");
				sbQuery.append("PKT.shape =" + "'" + ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase() + "'"
						+ " AND PKT.codeOfCut IN")
						.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut()));

				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
					sbQuery.append(" OR ");
					sbQuery.append("PKT.shape IN ")
							.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()));

				}

				sbQuery.append(" ) ").append("AND ");

			} else {

				sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
						.append(" AND ");
			}

		}

		// ............... Lab...........................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getLab())) {

			sbQuery.append("PKT.lab IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getLab()))
					.append(" AND ");
		}

		// ............... Certificate..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCertificateNo())) {
			sbQuery.append("PKT.certNo = ").append("'" + body.getCertificateNo().trim() + "'").append(" AND ");
		}

		// ............... List of Polish..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfPolish())) {
			sbQuery.append("PKT.codeOfPolish IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfPolish())).append(" AND ");
		}

		// ............... List of Symmentry..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfSym())) {
			sbQuery.append("PKT.codeOfSymmentry IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfSym())).append(" AND ");
		}

		// ............... List of Fluorensence..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFlou())) {
			sbQuery.append("PKT.codeOfFluorescence IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfFlou())).append(" AND ");
		}

		// ............... Country ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCountry())) {
			sbQuery.append("PKT.country IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getCountry()))
					.append("AND ");
		}

		// ...............Color (color code OFcolor like [D to N])..white
		// color.................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getColor())) {
			sbQuery.append("PKT.codeOfColor IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && !body.getIsFancyColor()) {
			sbQuery.append("PKT.codeOfColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(colorMasterRepository.getArrayListForWhiteColor()))
					.append(" AND ");
		}

		// ............... Shade (list of shade codeOfshade)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getShade())) {
			sbQuery.append("PKT.codeOfShade IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShade()))
					.append(" AND ");
		}
		// ............... Milky (list of codeOfMilky)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getMilky())) {
			sbQuery.append("PKT.codeOfMilky IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getMilky()))
					.append(" AND ");
		}

		// ............... Clarity (list of clarity List )..................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getClarity())) {
			sbQuery.append("PKT.codeOfClarity IN")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getClarity())).append(" AND ");
		}
		// ............... Depth(For T.DEPTH Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ")
						.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())) {
				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ");
			} else {
				sbQuery.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			}
		}

		// ............... Table(For Table(%) Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ")
						.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ");
			} else {
				sbQuery.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			}
		}

		// ............... Crown Angle (For C.Angle Field in Design) ..................
		//
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ")
						.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			}
		}

		// ............... Crown Height (For C.Height Field in Design --db=crownHeight)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ")
						.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())) {
				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			}
		}

		// ............... Pavilion Angle (For P.Angle Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ")
						.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			}
		}

		// ............... Pavilion Height (For P.HEIGHT(%) Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

				sbQuery.append("PKT.pavilionHeight >=").append(body.getFromPavilionHeight()).append(" AND ")
						.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())) {
				sbQuery.append("PKT.pavilionHeight >= ").append(body.getFromPavilionHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			}
		}

		// ............... Length (For Length Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ").append("PKT.Length <= ")
						.append(body.getToLength()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ");
			} else {
				sbQuery.append("PKT.length <= ").append(body.getToLength()).append(" AND ");
			}
		}

		// ............... Width (For Width Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ").append("PKT.width <= ")
						.append(body.getToWidth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ");
			} else {
				sbQuery.append("PKT.width <= ").append(body.getToWidth()).append(" AND ");
			}
		}

		// ............... Height (For Height Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ").append("PKT.height <= ")
						.append(body.getToHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.height <= ").append(body.getToHeight()).append(" AND ");
			}
		}

		// ............... Girdle (For Girdle(%) Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ").append("PKT.girdle <= ")
						.append(body.getToGirdle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ");
			} else {
				sbQuery.append("PKT.girdle <= ").append(body.getToGirdle()).append(" AND ");
			}
		}

		// ............... Fancy color .................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColor())) {
			sbQuery.append(" PKT.fColor IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {
			sbQuery.append(" PKT.fColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyColorMasterRepository.getArrayListForFcolor()))
					.append(" AND ");
		}

		// ............... Fancy Intensity ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorIntensity())) {
			sbQuery.append(" PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorIntensity()))
					.append(" AND ");

		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append("PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyIntensityMasterRepository.getArrayListForfIntensity()))
					.append(" AND ");

		}

		// ............... Fancy Overtone ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorOvertone())) {
			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorOvertone())).append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyOvertoneMasterRepository.getArrayListForfOvertone()))
					.append(" AND ");
		}
		sbQuery.append("PKT.isSold = 0 ").append(" AND ");

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(pktidsNotToCheck)) {
			sbQuery.append("PKT.pktMasterId not in ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(pktidsNotToCheck)).append(" AND ");
		}
		// after where not any iff true then

		if (sbQuery.substring(sbQuery.lastIndexOf("WHERE"), sbQuery.length()).length() <= 6) {
			sbQuery.setLength(sbQuery.length() - 6);
		}

		if (sbQuery.toString().contains("AND")) {
			sbQuery.setLength(sbQuery.length() - 4);
		}

		/* ....... End .............. */

		/* Execute Query. */

		Query finalQuery = entityManager.createNativeQuery(resultFields + sbQuery.toString(),
				ShivamWebVariableUtils.SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST);

		List<PktMaster> list = finalQuery.getResultList();
		Set<PktMaster> listOfResponsePktMast = new HashSet<PktMaster>(list);

		/* Discount */

		// List<NewArrivalSettings> newArrivalSettingFromDb =
		// newArrivalSettingsRepository.findAll();

		DecimalFormat df = new DecimalFormat("####0.00");
		List<Double> disc;
		Set<PktMaster> listOfResponsePktMastIfTotalPrice = new HashSet<PktMaster>();
		for (PktMaster responsePktMast : listOfResponsePktMast) {

			// listOfResponsePktMast.parallelStream().forEach(responsePktMast -> {

			/* Set id new arrival stone */
			// responsePktMast.setIsNewArrival(newArrivalSettingsService
			// .isNewArrival(newArrivalSettingFromDb.get(0).getNoOfDays(),
			// responsePktMast.getGiDate()));

			/* End */
			/* List of not found stone */
			/* End */
			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
					&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
							.contains(responsePktMast.getfColor().trim().toLowerCase())) {
				disc = ShivamWebMethodUtils.getDefaultDiscForCal();
				responsePktMast.setDisc((double) 0);
			} else {
				/* Add user wise discount */

				disc = discountMasterService.getDiscountByUserId(body.getUserId(), responsePktMast.getGiDate(),
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
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {
				responsePktMast
						.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));
				responsePktMast.setDisc(perCaratDisc);
				responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
				responsePktMast.setTotalPrice(
						Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
			}
			/* End Discount */
			/* total price condition */
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
					|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {

				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& responsePktMast.getTotalPrice() <= body.getToTotal()
						&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& responsePktMast.getTotalPrice() <= body.getToTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				}

			}

		}
		// });
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {
			return ShivamWebMethodUtils.isSetNullOrEmpty(listOfResponsePktMastIfTotalPrice) ? new HashSet<>()
					: listOfResponsePktMastIfTotalPrice;
		}
		return ShivamWebMethodUtils.isSetNullOrEmpty(listOfResponsePktMast) ? new HashSet<>() : listOfResponsePktMast;

	}

	@Override
	public List<Integer> getCount(StoneSearchCriteria body) {

		StringBuilder sbQuery = new StringBuilder();

		String resultFields = "SELECT PKT.*, SHP.shapeName,SHP.shapeImage,SHP.shapeOrder,MILK.milkMasterOrder,LAB.labMasterOrder,FLO.fluorescenceMasterOrder,FOVERTONE.fancyOvertoneOrder,FINTENSITY.fancyIntensityOrder,FCOLOR.fancyColorOrder,CUTPLISHSYM.cutPolishSymmentryMasterOrder,COUNTRY.countryMasterOrder,COLOR.colorOrder,CLARITY.clarityMasterOrder,SHADE.brownShadeMasterOrder";
		sbQuery.append(" FROM tblPktMaster PKT ");
		sbQuery.append(" LEFT JOIN tblShapeMaster SHP ON SHP.shapeName = PKT.shape ");
		sbQuery.append(" LEFT JOIN tblMilkMaster MILK ON MILK.shortName = PKT.codeOfMilky ");
		sbQuery.append(" LEFT JOIN tblLabMaster LAB ON LAB.labMasterName = PKT.lab ");
		sbQuery.append(" LEFT JOIN tblFluorescenceMaster FLO ON FLO.shortName = PKT.codeOfFluorescence ");
		sbQuery.append(" LEFT JOIN tblFancyOvertoneMaster FOVERTONE ON FOVERTONE.fancyOvertoneName = PKT.fOvertone ");
		sbQuery.append(
				"LEFT JOIN tblFancyIntensityMaster FINTENSITY ON FINTENSITY.fancyIntensityName = PKT.fIntensity ");
		sbQuery.append("LEFT JOIN tblFancyColorMaster FCOLOR ON FCOLOR.fancyColorName = PKT.fColor ");
		sbQuery.append(
				"LEFT JOIN tblCutPolishSymmetryMaster CUTPLISHSYM ON CUTPLISHSYM.cutPolishSymmentryMasterName = PKT.codeOfPolish ");
		sbQuery.append(" LEFT JOIN tblCountryMaster COUNTRY ON COUNTRY.countryMasterName = PKT.country ");
		sbQuery.append(" LEFT JOIN tblColorMaster COLOR ON COLOR.colorName = PKT.codeOfColor ");
		sbQuery.append(" LEFT JOIN tblClarityMaster CLARITY ON CLARITY.clarityMasterName = PKT.codeOfClarity ");
		sbQuery.append(" LEFT JOIN tblBrownShadeMaster SHADE ON SHADE.shortName = PKT.codeOfShade ");
		sbQuery.append(" WHERE ");
		// sbQuery.append(" FROM tblPktMaster PKT ");
		// sbQuery.append(" WHERE ");
		// ............... Stock..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())) {
			sbQuery.append("( PKT.stoneId IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" OR ");
			sbQuery.append("PKT.certNo IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" ) AND ");
		}

		// list of From/To weights in designe carat

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFromToWeights())) {
			sbQuery.append(" ( ");

			AtomicInteger runCount = new AtomicInteger();

			body.getListOfFromToWeights().forEach(map -> {
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT))
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT))) {
					return;
				}

				if (runCount.get() >= 1) {
					sbQuery.append(" OR ");
				}

				sbQuery.append(" (");

				double fromCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT)) ? -1
						: Double.valueOf(map.get(FROM_WEIGHT));

				double toCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT)) ? -1
						: Double.valueOf(map.get(TO_WEIGHT));

				if (fromCarat > 0 && toCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat).append(" AND ").append("PKT.carat <= ")
							.append(toCarat);
				} else if (fromCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat);
				} else {
					sbQuery.append("PKT.carat <= ").append(toCarat);
				}

				sbQuery.append(" )");

				runCount.incrementAndGet();
			});

			sbQuery.append(" ) ");
			sbQuery.append(" AND ");

		}
		// ...................stone ID.................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getStoneId())) {
			sbQuery.append("PKT.stoneId = ").append("'" + body.getStoneId() + "'").append(" AND ");
		}

		// ............... Shape........................

		/* if cut available and shape not available.. */
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			sbQuery.append("PKT.codeOfCut IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut())).append(" AND ");

		}

		/* if cut not available and shape available.. */
		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
			sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
					.append(" AND ");

		}
		// .................if cut and shape available....available..
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			if (body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase())
					|| body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase())) {

				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase());
				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase());

				sbQuery.append(" ( ");
				sbQuery.append("PKT.shape =" + "'" + ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase() + "'"
						+ " AND PKT.codeOfCut IN")
						.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut()));

				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
					sbQuery.append(" OR ");
					sbQuery.append("PKT.shape IN ")
							.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()));

				}

				sbQuery.append(" ) ").append("AND ");

			} else {

				sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
						.append(" AND ");
			}

		}

		// ............... Lab...........................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getLab())) {

			sbQuery.append("PKT.lab IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getLab()))
					.append(" AND ");
		}

		// ............... Certificate..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCertificateNo())) {
			sbQuery.append("PKT.certNo = ").append("'" + body.getCertificateNo() + "'").append(" AND ");
		}

		// ............... List of Polish..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfPolish())) {
			sbQuery.append("PKT.codeOfPolish IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfPolish())).append(" AND ");
		}

		// ............... List of Symmentry..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfSym())) {
			sbQuery.append("PKT.codeOfSymmentry IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfSym())).append(" AND ");
		}

		// ............... List of Fluorensence..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFlou())) {
			sbQuery.append("PKT.codeOfFluorescence IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfFlou())).append(" AND ");
		}

		// ............... Country ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCountry())) {
			sbQuery.append("PKT.country IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getCountry()))
					.append("AND ");
		}

		// ...............Color (color code OFcolor like [D to N])..white
		// color.................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getColor())) {
			sbQuery.append("PKT.codeOfColor IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && !body.getIsFancyColor()) {
			sbQuery.append("PKT.codeOfColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(colorMasterRepository.getArrayListForWhiteColor()))
					.append(" AND ");
		}

		// ............... Shade (list of shade codeOfshade)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getShade())) {
			sbQuery.append("PKT.codeOfShade IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShade()))
					.append(" AND ");
		}
		// ............... Milky (list of codeOfMilky)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getMilky())) {
			sbQuery.append("PKT.codeOfMilky IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getMilky()))
					.append(" AND ");
		}

		// ............... Clarity (list of clarity List )..................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getClarity())) {
			sbQuery.append("PKT.codeOfClarity IN")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getClarity())).append(" AND ");
		}
		// ............... Depth(For T.DEPTH Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ")
						.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())) {
				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ");
			} else {
				sbQuery.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			}
		}

		// ............... Table(For Table(%) Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ")
						.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ");
			} else {
				sbQuery.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			}
		}

		// ............... Crown Angle (For C.Angle Field in Design) ..................
		//
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ")
						.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			}
		}

		// ............... Crown Height (For C.Height Field in Design --db=crownHeight)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ")
						.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())) {
				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			}
		}

		// ............... Pavilion Angle (For P.Angle Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ")
						.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			}
		}

		// ............... Pavilion Height (For P.HEIGHT(%) Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

				sbQuery.append("PKT.pavilionHeight >=").append(body.getFromPavilionHeight()).append(" AND ")
						.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())) {
				sbQuery.append("PKT.pavilionHeight >= ").append(body.getFromPavilionHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			}
		}

		// ............... Length (For Length Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ").append("PKT.Length <= ")
						.append(body.getToLength()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ");
			} else {
				sbQuery.append("PKT.length <= ").append(body.getToLength()).append(" AND ");
			}
		}

		// ............... Width (For Width Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ").append("PKT.width <= ")
						.append(body.getToWidth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ");
			} else {
				sbQuery.append("PKT.width <= ").append(body.getToWidth()).append(" AND ");
			}
		}

		// ............... Height (For Height Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ").append("PKT.height <= ")
						.append(body.getToHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.height <= ").append(body.getToHeight()).append(" AND ");
			}
		}

		// ............... Girdle (For Girdle(%) Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ").append("PKT.girdle <= ")
						.append(body.getToGirdle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ");
			} else {
				sbQuery.append("PKT.girdle <= ").append(body.getToGirdle()).append(" AND ");
			}
		}

		// ............... Fancy color .................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColor())) {
			sbQuery.append(" PKT.fColor IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {
			sbQuery.append(" PKT.fColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyColorMasterRepository.getArrayListForFcolor()))
					.append(" AND ");
		}

		// ............... Fancy Intensity ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorIntensity())) {
			sbQuery.append(" PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorIntensity()))
					.append(" AND ");

		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append("PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyIntensityMasterRepository.getArrayListForfIntensity()))
					.append(" AND ");

		}

		// ............... Fancy Overtone ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorOvertone())) {
			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorOvertone())).append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyOvertoneMasterRepository.getArrayListForfOvertone()))
					.append(" AND ");
		}
		sbQuery.append("PKT.isSold = 0 ").append(" AND ");
		// after where not any iff true then
		if (sbQuery.substring(sbQuery.lastIndexOf("WHERE"), sbQuery.length()).length() <= 6) {
			sbQuery.setLength(sbQuery.length() - 6);
		}

		if (sbQuery.toString().contains("AND")) {
			sbQuery.setLength(sbQuery.length() - 4);
		}

		if (ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
				&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {
			String resultFieldsForCount = "SELECT COUNT(*)";
			Query query = entityManager.createNativeQuery(resultFieldsForCount + sbQuery.toString());
			List<Integer> listForCount = query.getResultList();
			return listForCount;
		}
		/* Execute Query. */

		Query finalQuery = entityManager.createNativeQuery(resultFields + sbQuery.toString(),
				ShivamWebVariableUtils.SQL_MAPPING_CONFIG_RESPONSE_PKT_MAST);

		List<PktMaster> listOfResponsePktMast = finalQuery.getResultList();
		/* Discount */

		DecimalFormat df = new DecimalFormat("####0.00");
		List<Double> disc;
		List<PktMaster> listOfResponsePktMastIfTotalPrice = new ArrayList<>();
		for (PktMaster responsePktMast : listOfResponsePktMast) {
			responsePktMast.setDiscOrignal(responsePktMast.getDisc());

			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
					&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
							.contains(responsePktMast.getfColor().trim().toLowerCase())) {
				disc = ShivamWebMethodUtils.getDefaultDiscForCal();
				responsePktMast.setDisc((double) 0);
			} else {
				/* Add user wise discount */

				disc = discountMasterService.getDiscountByUserId(body.getUserId(), responsePktMast.getGiDate(),
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

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getgRap())) {

				responsePktMast
						.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));
				responsePktMast.setDisc(perCaratDisc);
				responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
				responsePktMast.setTotalPrice(
						Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));
			}
			/* End Discount */

			/* total price condition */
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
					|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())) {

				if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& responsePktMast.getTotalPrice() <= body.getToTotal()
						&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& responsePktMast.getTotalPrice() <= body.getToTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTotal())
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTotal())
						&& responsePktMast.getTotalPrice() >= body.getFromTotal()) {
					listOfResponsePktMastIfTotalPrice.add(responsePktMast);

				}

			}
			/* End total price condition */
		}
		List<Integer> listForCount2 = new ArrayList<>();
		listForCount2.add(listOfResponsePktMastIfTotalPrice.size());
		return listForCount2;
	}

	@Override
	public Set<PktMaster> getDemandStone(StoneSearchCriteria body, String pktId) {

		StringBuilder sbQuery = new StringBuilder();

		String resultFields = "SELECT PKT.*";
		sbQuery.append(" FROM tblPktMaster PKT ");
		sbQuery.append(" WHERE ");

		// ............... Stock..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfStoneId())) {
			sbQuery.append("( PKT.stoneId IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" OR ");
			sbQuery.append("PKT.certNo IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfStoneId())).append(" ) AND ");
		}

		// list of From/To weights in designe carat

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFromToWeights())) {
			sbQuery.append(" ( ");

			AtomicInteger runCount = new AtomicInteger();

			body.getListOfFromToWeights().forEach(map -> {
				if (ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT))
						&& ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT))) {
					return;
				}

				if (runCount.get() >= 1) {
					sbQuery.append(" OR ");
				}

				sbQuery.append(" (");

				double fromCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(FROM_WEIGHT)) ? -1
						: Double.valueOf(map.get(FROM_WEIGHT));

				double toCarat = ShivamWebMethodUtils.isObjectisNullOrEmpty(map.get(TO_WEIGHT)) ? -1
						: Double.valueOf(map.get(TO_WEIGHT));

				if (fromCarat > 0 && toCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat).append(" AND ").append("PKT.carat <= ")
							.append(toCarat);
				} else if (fromCarat > 0) {
					sbQuery.append("PKT.carat >= ").append(fromCarat);
				} else {
					sbQuery.append("PKT.carat <= ").append(toCarat);
				}

				sbQuery.append(" )");

				runCount.incrementAndGet();
			});

			sbQuery.append(" ) ");
			sbQuery.append(" AND ");

		}
		// ...................stone ID.................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getStoneId())) {
			sbQuery.append("PKT.stoneId = ").append("'" + body.getStoneId() + "'").append(" AND ");
		}

		// ............... Shape........................

		/* if cut available and shape not available.. */
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			sbQuery.append("PKT.codeOfCut IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut())).append(" AND ");

		}

		/* if cut not available and shape available.. */
		if (ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
			sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
					.append(" AND ");

		}
		// .................if cut and shape available....available..
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfCut())
				&& !ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {

			if (body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase())
					|| body.getShape().contains(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase())) {

				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toLowerCase());
				body.getShape().remove(ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase());

				sbQuery.append(" ( ");
				sbQuery.append("PKT.shape =" + "'" + ShivamWebVariableUtils.WHITE_SHAPE_ROUND.trim().toUpperCase() + "'"
						+ " AND PKT.codeOfCut IN")
						.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfCut()));

				if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getShape())) {
					sbQuery.append(" OR ");
					sbQuery.append("PKT.shape IN ")
							.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()));

				}

				sbQuery.append(" ) ").append("AND ");

			} else {

				sbQuery.append("PKT.shape IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShape()))
						.append(" AND ");
			}

		}

		// ............... Lab...........................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getLab())) {

			sbQuery.append("PKT.lab IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getLab()))
					.append(" AND ");
		}

		// ............... Certificate..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCertificateNo())) {
			sbQuery.append("PKT.certNo = ").append("'" + body.getCertificateNo() + "'").append(" AND ");
		}

		// ............... List of Polish..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfPolish())) {
			sbQuery.append("PKT.codeOfPolish IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfPolish())).append(" AND ");
		}

		// ............... List of Symmentry..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfSym())) {
			sbQuery.append("PKT.codeOfSymmentry IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfSym())).append(" AND ");
		}

		// ............... List of Fluorensence..................
		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getListOfFlou())) {
			sbQuery.append("PKT.codeOfFluorescence IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getListOfFlou())).append(" AND ");
		}

		// ............... Country ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getCountry())) {
			sbQuery.append("PKT.country IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getCountry()))
					.append("AND ");
		}

		// ...............Color (color code OFcolor like [D to N])..white
		// color.................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getColor())) {
			sbQuery.append("PKT.codeOfColor IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && !body.getIsFancyColor()) {
			sbQuery.append("PKT.codeOfColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(colorMasterRepository.getArrayListForWhiteColor()))
					.append(" AND ");
		}

		// ............... Shade (list of shade codeOfshade)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getShade())) {
			sbQuery.append("PKT.codeOfShade IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getShade()))
					.append(" AND ");
		}
		// ............... Milky (list of codeOfMilky)..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getMilky())) {
			sbQuery.append("PKT.codeOfMilky IN").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getMilky()))
					.append(" AND ");
		}

		// ............... Clarity (list of clarity List )..................

		if (!ShivamWebMethodUtils.isListIsNullOrEmpty(body.getClarity())) {
			sbQuery.append("PKT.codeOfClarity IN")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getClarity())).append(" AND ");
		}
		// ............... Depth(For T.DEPTH Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToDepth())) {

				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ")
						.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromDepth())) {
				sbQuery.append("PKT.totDepth >= ").append(body.getFromDepth()).append(" AND ");
			} else {
				sbQuery.append("PKT.totDepth <= ").append(body.getToDepth()).append(" AND ");
			}
		}

		// ............... Table(For Table(%) Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ")
						.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromTable())) {
				sbQuery.append("PKT.tablePercentage >= ").append(body.getFromTable()).append(" AND ");
			} else {
				sbQuery.append("PKT.tablePercentage <= ").append(body.getToTable()).append(" AND ");
			}
		}

		// ............... Crown Angle (For C.Angle Field in Design) ..................
		//
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ")
						.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownAngle())) {
				sbQuery.append("PKT.crownAngle >= ").append(body.getFromCrownAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownAngle <= ").append(body.getToCrownAngle()).append(" AND ");
			}
		}

		// ............... Crown Height (For C.Height Field in Design --db=crownHeight)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToCrownHeight())) {

				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ")
						.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromCrownHeight())) {
				sbQuery.append("PKT.crownHeight >= ").append(body.getFromCrownHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.crownHeight <= ").append(body.getToCrownHeight()).append(" AND ");
			}
		}

		// ............... Pavilion Angle (For P.Angle Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ")
						.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionAngle())) {
				sbQuery.append("PKT.pavilionAngle >= ").append(body.getFromPavilionAngle()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionAngle <= ").append(body.getToPavilionAngle()).append(" AND ");
			}
		}

		// ............... Pavilion Height (For P.HEIGHT(%) Field in Design)
		// ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToPavilionHeight())) {

				sbQuery.append("PKT.pavilionHeight >=").append(body.getFromPavilionHeight()).append(" AND ")
						.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromPavilionHeight())) {
				sbQuery.append("PKT.pavilionHeight >= ").append(body.getFromPavilionHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.pavilionHeight <= ").append(body.getToPavilionHeight()).append(" AND ");
			}
		}

		// ............... Length (For Length Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ").append("PKT.Length <= ")
						.append(body.getToLength()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromLength())) {
				sbQuery.append("PKT.length >= ").append(body.getFromLength()).append(" AND ");
			} else {
				sbQuery.append("PKT.length <= ").append(body.getToLength()).append(" AND ");
			}
		}

		// ............... Width (For Width Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ").append("PKT.width <= ")
						.append(body.getToWidth()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromWidth())) {
				sbQuery.append("PKT.width >= ").append(body.getFromWidth()).append(" AND ");
			} else {
				sbQuery.append("PKT.width <= ").append(body.getToWidth()).append(" AND ");
			}
		}

		// ............... Height (For Height Field in Design) ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ").append("PKT.height <= ")
						.append(body.getToHeight()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromHeight())) {
				sbQuery.append("PKT.height >= ").append(body.getFromHeight()).append(" AND ");
			} else {
				sbQuery.append("PKT.height <= ").append(body.getToHeight()).append(" AND ");
			}
		}

		// ............... Girdle (For Girdle(%) Field in Design) ..................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
				|| !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {

			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())
					&& !ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getToGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ").append("PKT.girdle <= ")
						.append(body.getToGirdle()).append(" AND ");
			} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFromGirdle())) {
				sbQuery.append("PKT.girdle >= ").append(body.getFromGirdle()).append(" AND ");
			} else {
				sbQuery.append("PKT.girdle <= ").append(body.getToGirdle()).append(" AND ");
			}
		}

		// ............... Fancy color .................

		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColor())) {
			sbQuery.append(" PKT.fColor IN ").append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColor()))
					.append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {
			sbQuery.append(" PKT.fColor IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyColorMasterRepository.getArrayListForFcolor()))
					.append(" AND ");
		}

		// ............... Fancy Intensity ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorIntensity())) {
			sbQuery.append(" PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorIntensity()))
					.append(" AND ");

		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append("PKT.fIntensity IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyIntensityMasterRepository.getArrayListForfIntensity()))
					.append(" AND ");

		}

		// ............... Fancy Overtone ..................
		if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getFancyColorOvertone())) {
			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils.getListWithSingleQuotes(body.getFancyColorOvertone())).append(" AND ");
		} else if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(body.getIsFancyColor()) && body.getIsFancyColor()) {

			sbQuery.append(" PKT.fOvertone IN ")
					.append(ShivamWebMethodUtils
							.getListWithSingleQuotes(fancyOvertoneMasterRepository.getArrayListForfOvertone()))
					.append(" AND ");
		}
		sbQuery.append("PKT.isSold = 0 ").append(" AND ");
		sbQuery.append("PKT.pktMasterId = '").append(pktId).append("'").append(" AND ");
		// after where not any iff true then

		if (sbQuery.substring(sbQuery.lastIndexOf("WHERE"), sbQuery.length()).length() <= 6) {
			sbQuery.setLength(sbQuery.length() - 6);
		}

		if (sbQuery.toString().contains("AND")) {
			sbQuery.setLength(sbQuery.length() - 4);
		}

		/* ....... End .............. */

		/* Execute Query. */

		Query finalQuery = entityManager.createNativeQuery(resultFields + sbQuery.toString());

		List<PktMaster> list = finalQuery.getResultList();
		Set<PktMaster> listOfResponsePktMast = new HashSet<PktMaster>(list);
		return ShivamWebMethodUtils.isSetNullOrEmpty(listOfResponsePktMast) ? new HashSet<>() : listOfResponsePktMast;

	}

	// Get single stone detail.. new ..
	@Override
	public ResponseWrapperDTO viewStoneByStoneIdAndUserId(String stoneId, String userId) {

		Optional<PktMaster> pktMaster = pktMasterRepository.findByStoneIdAndCertNo(stoneId);

		/* Discount */
		DecimalFormat df = new DecimalFormat("####0.00");
		List<Double> disc;

		// for (PktMaster responsePktMast : listOfResponsePktMast) {
		if (pktMaster.isPresent()) {
			PktMaster responsePktMast = pktMaster.get();
			ShapeMaster shapeImage = shapeMasterRepository.findByshapeName(pktMaster.get().getShape())
					.orElse(new ShapeMaster());
			responsePktMast.setShapeImage(shapeImage.getShapeImage());

			/* Set image,video url */
			responsePktMast.setImageUrl(ShivamWebMethodUtils.setImageURL(responsePktMast.getStoneId()));
			// responsePktMast
			// .setVideoUrl(ShivamWebVariableUtils.VIDEO_LINK_FROM_360_VIEWER +
			// responsePktMast.getStoneId());
			responsePktMast.setVideoUrl(ShivamWebMethodUtils.setVideoURL(responsePktMast.getStoneId()));

			responsePktMast.setSaveVideoUrl(ShivamWebMethodUtils.setSaveVideoURL(responsePktMast.getStoneId()));
			/* End Set image ,video url */

			responsePktMast.setInclusionPlottingUrl(ShivamWebMethodUtils.setPlottingURL(responsePktMast.getStoneId()));

			disc = ShivamWebMethodUtils.getDefaultDiscForCal();
			if (!ShivamWebMethodUtils.isObjectisNullOrEmpty(responsePktMast.getfColor())
					&& Arrays.asList(fancyColorMasterRepository.getArrayListForFcolor())
							.contains(responsePktMast.getfColor().trim().toLowerCase())) {
				disc = ShivamWebMethodUtils.getDefaultDiscForCal();
				responsePktMast.setDisc((double) 0);
			} else {
				/* Add user wise discount */

				disc = discountMasterService.getDiscountByUserId(userId, responsePktMast.getGiDate(),
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

			responsePktMast
					.setRapRate(Double.valueOf(df.format(responsePktMast.getCarat() * responsePktMast.getgRap())));
			responsePktMast.setDisc(perCaratDisc);
			responsePktMast.setPerCaratePrice(responsePktMast.getgRap() * (100 - perCaratDisc) / 100);
			responsePktMast.setTotalPrice(
					Double.valueOf(df.format(responsePktMast.getPerCaratePrice() * responsePktMast.getCarat())));

			/* End Discount */
		}

		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Get single stone detail successfully.",
				pktMaster.isPresent() ? pktMaster : new ObjectMapper().createObjectNode(), HttpStatus.OK);
	}

}
