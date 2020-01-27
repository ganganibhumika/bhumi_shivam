package com.techhive.shivamweb.service;

import java.util.Date;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface NewArrivalSettingsService {


	ResponseWrapperDTO updateNewArrivalSettings(String id, MyRequestBody body, String path);

	/*
	 * @bhumi
	 * use for set arrival 
	 * */
	Boolean isNewArrival(Integer integer, Date giDate);

	ResponseWrapperDTO addNewArrivalSettings(MyRequestBody body, String path);

}
