package com.techhive.shivamweb.custom.repository;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface PktMasterCustomRepository {

	ResponseWrapperDTO getStonesViaLazzyLoadingOfSearchCriteria(StoneSearchCriteria body) throws JsonProcessingException;

	ResponseWrapperDTO viewStoneByStoneIdAndUserId(String stoneId, String userId);

	Set<PktMaster> getRecomendedStone(List<String> pktidsNotToCheck, StoneSearchCriteria body);

	List<Integer> getCount(StoneSearchCriteria body);

	// ResponseWrapperDTO getAllStoneDetailForThirdParty(String userId);

	Set<PktMaster> getDemandStone(StoneSearchCriteria body, String pktId);



}
