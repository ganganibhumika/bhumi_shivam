package com.techhive.shivamweb.service;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.model.ViewRequest;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ViewRequestService {
	/**
	 * @author neel save view request and release stone after 2 hours and set is
	 *         hold and in progress to false
	 */
	ResponseWrapperDTO saveViewRequest(MyRequestBody body, HttpServletRequest request)
			throws SchedulerException, ParseException;

	/**
	 * @author neel get all view request
	 */
	ResponseWrapperDTO getAllViewRequest(String userId, Boolean inProgress, Integer pageNumber, Integer noOfRecords,
			String sortColumn, String sortOrder, String path);

	ResponseWrapperDTO deleteViewRequest(String viewRequestId, String path);
	/***
	 * @author neel calculate discount user wise
	 */
	ViewRequest getDiscount(ViewRequest viewRequest);

}
