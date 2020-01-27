package com.techhive.shivamweb.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.request.payload.StoneSearchCriteria;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface PktMasterService {
	/***
	 * @author bhumi save data after check already exist (stoneID)
	 * @param string
	 * @throws IOException 
	 */
	public ResponseWrapperDTO addStoneDetail(MyRequestBody body, String string) ;

	/***
	 * @author bhumi update stone Detail after check already exist (stoneID)
	 * @param string
	 */
	public ResponseWrapperDTO updateStoneDetail(String stoneId, MyRequestBody body, String path);

	/***
	 * @author bhumi return stone detail base on search condition
	 * @param jsonObject
	 * @throws JsonProcessingException 
	 * 
	 */
	public ResponseWrapperDTO getStonesViaLazzyLoadingOfSearchCriteria(StoneSearchCriteria body) throws JsonProcessingException;


	/***
	 * @author bhumi return stone detail base on party id discount
	 * @param Party Is as User Id
	 * 
	 */
	public List<PktMaster> getAllStoneDetailForThirdParty(String userId);

	
	/***
	 * @author bhumi 
	 * return stone detail by stone ID or list OF stone ID with image url
	 * @param userId 
	 * @param stoneId 
	 * @param Party Is as User Id
	 * 
	 */
	public ResponseWrapperDTO viewStoneByStoneIdAndUserId(String stoneId, String userId);

	/**
	 * @author neel
	 * based on is featured get pkt data if null get all which in not sold
	 * @param userId 
	 */
	public ResponseWrapperDTO getAllPktMasterByStatus(Integer pageNumber, Integer noOfRecords, String sortColumn, String sortOrder,
			String searchText, Boolean isFeatured, String path, String userId);
	/**
	 * @author neel
	 * update featured stone status
	 */
	public ResponseWrapperDTO updateFeaturedStone(MyRequestBody body, String servletPath);

	/**
	 * @author neel
	 * get 10 recommended stone based on last 2 search 
	 * @param path 
	 */
	public ResponseWrapperDTO getRecomendedStone(String userId, String path) throws JsonParseException, JsonMappingException, IOException;


	ResponseWrapperDTO getTwinStone5(String path, String userId);

	PktMaster getDiscount(PktMaster responsePktMast,String userId);



	/***
	 * @author neel
	 * @param 
	 * @return //last 10 days discount changed stones.
	 */
	public ResponseWrapperDTO getRecentDiscountChangedStones(String path,String userId);

	
	/***
	 * @author bhumi save multiple stone detail after check already exist (stoneID) 
	 * @param string
	 * @throws IOException 
	 */
	
	public ResponseWrapperDTO addStoneDetailMultiple(MyRequestBody body, String servletPath);

	
	/***
	 * @author bhumi save or update multiple stone detail after check already exist (stoneID) 
	 * @param string
	 * @throws IOException 
	 */


	public ResponseWrapperDTO addOrUpdateStoneDetail(MyRequestBody body, String servletPath);

	





}
