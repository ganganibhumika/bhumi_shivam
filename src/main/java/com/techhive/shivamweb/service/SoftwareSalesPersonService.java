package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface SoftwareSalesPersonService {

	ResponseWrapperDTO saveSoftwareSalesPersonMaster(MyRequestBody body, String path);

	ResponseWrapperDTO updateSoftwareSalesPersonMaster(String softwareSalePersonId, MyRequestBody body, String path);

	ResponseWrapperDTO deleteSoftwareSalesPersonMaster(String softwareSalePersonId, String path);



}
