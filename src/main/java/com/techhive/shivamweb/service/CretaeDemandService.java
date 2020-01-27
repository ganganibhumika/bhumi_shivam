package com.techhive.shivamweb.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techhive.shivamweb.model.CreateDemand;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface CretaeDemandService {

	ResponseWrapperDTO saveCreateDemand(StoneSearchCriteria body, String path) throws JsonProcessingException;

	ResponseWrapperDTO getAllCreatedDemandByUser(Integer pageNumber, Integer noOfRecords, String path, String sortOrder,
			String searchText, String userId, String path2) throws IOException;

	ResponseWrapperDTO deleteFromDemand(MyRequestBody body, String path);

	StoneSearchCriteria convertCreateDemandToStoneSearchCriteria(CreateDemand createDemand) throws IOException;

}
