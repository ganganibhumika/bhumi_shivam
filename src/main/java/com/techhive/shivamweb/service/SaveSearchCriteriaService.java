package com.techhive.shivamweb.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SaveSearchCriteriaService {
	/***
	 * @author neel save data after check already exist
	 */
	ResponseWrapperDTO saveSearchCriteriaAdd(StoneSearchCriteria body, String path) throws JsonParseException, JsonMappingException, IOException;
	/***
	 * @author neel get data by id
	 */
	ResponseWrapperDTO searchCriteriaGetById(String id, String path) throws JsonParseException, JsonMappingException, IOException;
	/***
	 * @author neel delete
	 */
	ResponseWrapperDTO deleteSearchCriteria(String searchCriteriaId, String path);
	/***
	 * @author neel update
	 */
	ResponseWrapperDTO updateSearchCriteria(String searchCriteriaId, StoneSearchCriteria body, String path) throws JsonProcessingException;

}
