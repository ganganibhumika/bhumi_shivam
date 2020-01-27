package com.techhive.shivamweb.service;

import javax.servlet.http.HttpServletRequest;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SuggestionFeedbackService {
	/**
	 * @author neel 
	 * save data and add tracking
	 */
	ResponseWrapperDTO saveSuggestionFeedback(MyRequestBody body, HttpServletRequest request);
	/**
	 * @author neel 
	 * get all with pagination orderby created date desc
	 */
	ResponseWrapperDTO getAllSuggestionFeedback(Integer pageNumber, Integer noOfRecords, String sortColumn,
			String sortOrder, String searchText, String path);
	/**
	 * @author neel 
	 * delete data and add tracking
	 */
	ResponseWrapperDTO deleteSuggestionFeedback(String id, String loginUserId, HttpServletRequest request);

}
