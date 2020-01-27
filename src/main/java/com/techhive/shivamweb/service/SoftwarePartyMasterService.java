package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SoftwarePartyMasterService {

	ResponseWrapperDTO saveSoftwarePartyMaster(MyRequestBody body, String path);

	ResponseWrapperDTO deleteSoftwarePartyMaster(String softwareSalePersonId, String path);

	ResponseWrapperDTO updateSoftwarePartyMaster(String softwareSaleKeyForUpdate, MyRequestBody body, String path);

}
