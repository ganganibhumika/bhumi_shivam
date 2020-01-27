package com.techhive.shivamweb.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.techhive.shivamweb.filter.MyJsonDateDeserializer;
import com.techhive.shivamweb.filter.MyJsonDateSerializer;

/**
 * 
 * @author neel this class is used as request body for post api
 */
public class MyRequestBody {

	private String id;
	private Object jsonOfObject;
	private String userId;
	private List<Object> listOfJsonObject;
	private Boolean flag;
	private String urlForSendMail;
	private String base64string;
	private String otherData;
	private String fcmToken;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date fromDate;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date toDate;

	@JsonSerialize(using = MyJsonDateSerializer.class)
	@JsonDeserialize(using = MyJsonDateDeserializer.class)
	private Date dateForCalendar;

	public Date getDateForCalendar() {
		return dateForCalendar;
	}

	public void setDateForCalendar(Date dateForCalendar) {
		this.dateForCalendar = dateForCalendar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getJsonOfObject() {
		return jsonOfObject;
	}

	public void setJsonOfObject(Object jsonOfObject) {
		this.jsonOfObject = jsonOfObject;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Object> getListOfJsonObject() {
		return listOfJsonObject;
	}

	public void setListOfJsonObject(List<Object> listOfJsonObject) {
		this.listOfJsonObject = listOfJsonObject;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getUrlForSendMail() {
		return urlForSendMail;
	}

	public void setUrlForSendMail(String urlForSendMail) {
		this.urlForSendMail = urlForSendMail;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getBase64string() {
		return base64string;
	}

	public void setBase64string(String base64string) {
		this.base64string = base64string;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

}
