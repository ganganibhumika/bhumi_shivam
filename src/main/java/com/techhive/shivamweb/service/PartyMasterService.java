package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface PartyMasterService {

	ResponseWrapperDTO savePartyMaster(MyRequestBody body, String path);

	ResponseWrapperDTO login(String partyName, String password);

}
