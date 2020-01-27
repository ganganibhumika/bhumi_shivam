package com.techhive.shivamweb.service;

import java.util.List;
import java.util.Map;

import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;
import com.techhive.shivamweb.response.payload.TodaysPopupFair;

public interface OtherService {
	public ResponseWrapperDTO getAllMasterDetails();

	/**
	 * @author neel
	 * @param tabName name of tab from angular
	 * @param path 
	 * @return stone id if there is any missing detail on url
	 */
	public ResponseWrapperDTO getAllStonesForVideoImageCsv(String tabName, String path);

	public ResponseWrapperDTO Test(String servletPath);

	/***
	 * @author neel update
	 * get todays upcoming fair along with todays popup type in enum
	 */
	public List<TodaysPopupFair> getTodaysUpcomingFairPopup();

	public Map<String, String> getCounterForDashboard(String userId);
}
