package com.techhive.shivamweb.service;

import com.techhive.shivamweb.model.MyRequestBody;
import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface AppVersionMasterService {

	ResponseWrapperDTO updateAppVersion(String id, MyRequestBody body, String path);

	

}
