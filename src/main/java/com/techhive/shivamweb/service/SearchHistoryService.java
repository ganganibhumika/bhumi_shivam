package com.techhive.shivamweb.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techhive.shivamweb.model.SearchHistory;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SearchHistoryService {

	void saveSearchHistory(StoneSearchCriteria body) throws JsonProcessingException;

	ResponseWrapperDTO getAllsaveSearchHistory(Integer pageNumber, Integer noOfRecords, String path, String sortOrder,
			String searchText, String userId, String path2)
			throws JsonParseException, JsonMappingException, IOException;

	StoneSearchCriteria convertSearchHistoryToStoneSearchCriteria(SearchHistory searchHistory)
			throws JsonParseException, JsonMappingException, IOException;

	ResponseWrapperDTO getAllPastSearch(Integer pageNumber, Integer noOfRecords, String sortColumn, String sortOrder,
			String searchText, String userId, String path) throws JsonParseException, JsonMappingException, IOException;

}
