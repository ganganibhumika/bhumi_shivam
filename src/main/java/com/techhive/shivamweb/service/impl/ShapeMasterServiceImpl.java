package com.techhive.shivamweb.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.techhive.shivamweb.master.model.ShapeMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.repository.ShapeMasterRepository;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.service.ShapeMasterService;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

@Service
public class ShapeMasterServiceImpl implements ShapeMasterService {

	@Autowired
	ShapeMasterRepository shapeMasterRepository;

	@Override
	public ResponseWrapperDTO saveShape(MyRequestBody body, String path) {
		ShapeMaster shape = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ShapeMaster.class);
		if (ShivamWebMethodUtils.isObjectNullOrEmpty(shape.getShapeName(), shape.getShortName(),
				body.getBase64string()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ShapeMaster> shapeName = shapeMasterRepository.findByshapeName(shape.getShapeName());
		Optional<ShapeMaster> shortName = shapeMasterRepository.findByshortName(shape.getShortName());
		if (shapeName.isPresent() && shortName.isPresent())
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Shape with shape name '" + shape.getShapeName()
							+ "' and short name '" + shape.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (shapeName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Shape with shape name '" + shape.getShapeName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Shape with short name '" + shape.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		Integer order = shapeMasterRepository.getMaxOrder();
		shape.setShapeOrder(order == null ? 0 : order + 1);
		shapeMasterRepository.saveAndFlush(shape);
		uplodeImage(body.getBase64string(), shape);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Shape " + ShivamWebVariableUtils.INSERT_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	public void uplodeImage(String base64String, ShapeMaster shape) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		service.submit(new Runnable() {
			public void run() {
				try {
					String base64Image = base64String.split(",")[1];
					byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
					String rootPath = System.getProperty("catalina.home");
					File dir = new File(rootPath + File.separator + "webapps/ShivamImage/shapeImage");
					if (!dir.exists())
						dir.mkdirs();
					String path = dir.getAbsolutePath();
					String fileName = "img" + shape.getId() + ".jpg";
					BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(path + "/" + (fileName)));
					bout.write(bytes);
					bout.flush();
					bout.close();
					shape.setShapeImage(fileName);
					shapeMasterRepository.saveAndFlush(shape);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		service.shutdown();
	}

	@Override
	public ResponseWrapperDTO updateShape(String shapeId, MyRequestBody body, String path) {
		Optional<ShapeMaster> shapeFromDb = shapeMasterRepository.findById(shapeId);
		if (!shapeFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Shape not found.", null,
					HttpStatus.BAD_REQUEST, path);
		ShapeMaster shape = ShivamWebMethodUtils.MAPPER.convertValue(body.getJsonOfObject(), ShapeMaster.class);

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(shape.getShapeName(), shape.getShortName()))
			return ShivamWebMethodUtils.incompleteDataResponseWrapperDTOForServiceRepository(path);
		Optional<ShapeMaster> shapeName = shapeMasterRepository.findByshapeName(shape.getShapeName());
		Optional<ShapeMaster> shortName = shapeMasterRepository.findByshortName(shape.getShortName());
		if ((shapeName.isPresent() && !shapeName.get().getShapeName().equals(shapeFromDb.get().getShapeName()))
				&& (shortName.isPresent() && !shortName.get().getShortName().equals(shapeFromDb.get().getShortName())))
			return new ResponseWrapperDTO(
					HttpServletResponse.SC_CONFLICT, "Shape with shape name '" + shape.getShapeName()
							+ "' and short name '" + shape.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST,
					null, HttpStatus.CONFLICT, path);
		if (shapeName.isPresent() && !shapeName.get().getShapeName().equals(shapeFromDb.get().getShapeName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Shape with shape name '" + shape.getShapeName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		if (shortName.isPresent() && !shortName.get().getShortName().equals(shapeFromDb.get().getShortName()))
			return new ResponseWrapperDTO(HttpServletResponse.SC_CONFLICT,
					"Shape with short name '" + shape.getShortName() + "' " + ShivamWebVariableUtils.ALREADYEXIST, null,
					HttpStatus.CONFLICT, path);
		shapeFromDb.get().setShapeName(shape.getShapeName());
		shapeFromDb.get().setShortName(shape.getShortName());
		shapeMasterRepository.saveAndFlush(shapeFromDb.get());
		if (!ShivamWebMethodUtils.isObjectNullOrEmpty(body.getBase64string()))
			uplodeImage(body.getBase64string(), shapeFromDb.get());
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Shape " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO deleteShape(String shapeId, String path) {
		Optional<ShapeMaster> shapeFromDb = shapeMasterRepository.findById(shapeId);
		if (!shapeFromDb.isPresent())
			return new ResponseWrapperDTO(HttpServletResponse.SC_BAD_REQUEST, "Shape not found.", null,
					HttpStatus.BAD_REQUEST, path);
		shapeMasterRepository.deleteById(shapeId);
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK, "Shape " + ShivamWebVariableUtils.DELETE_SUCCESSFULLY,
				null, HttpStatus.OK, path);
	}

	@Override
	public ResponseWrapperDTO changeAllShapeOrder(MyRequestBody body, String path) {
		List<ShapeMaster> listOfShapeMaster = ShivamWebMethodUtils.MAPPER.convertValue(body.getListOfJsonObject(),
				new TypeReference<List<ShapeMaster>>() {
				});
		listOfShapeMaster.forEach(shapeMaster -> {
			shapeMasterRepository.saveAndFlush(shapeMaster);
		});
		return new ResponseWrapperDTO(HttpServletResponse.SC_OK,
				"Shape Order " + ShivamWebVariableUtils.UPDATE_SUCCESSFULLY, null, HttpStatus.OK, path);
	}

}
