package com.techhive.shivamweb.response.payload;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

public class ResponseWrapperDTO {

	private int status;
	private String message;
	private String error;
	private Object data;
	private String path;
	private long timeStamp;
	@JsonIgnore
	private HttpStatus httpStatus;

	public ResponseWrapperDTO(int status, String message, Object data, HttpStatus httpStatus) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.path = ShivamWebMethodUtils.getApiPath();
		this.timeStamp = new Date().getTime();
		this.httpStatus = httpStatus;
	}

	public ResponseWrapperDTO() {
		super();
	}

	/**
	 * user for catch block error
	 * 
	 * @param status
	 * @param error
	 * @return status,error,path,time stamp
	 */
	public ResponseWrapperDTO(int status, String error, String path) {
		super();
		this.status = status;
		this.error = error;
		this.path = path;
		this.timeStamp = new Date().getTime();
	}

	/**
	 * use for send api response
	 * 
	 * @param status
	 * @param message
	 * @param data
	 * @return status,message,data,path,time stamp
	 */

	public ResponseWrapperDTO(int status, String message, Object data, String path) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.path = path;
		this.timeStamp = new Date().getTime();
	}

	/**
	 * @author neel
	 * @return this ResponseWrapperDTO to be used when we want to return
	 *         ResponseWrapperDTO from inside the SERVICE or DTO
	 */
	public ResponseWrapperDTO(int status, String message, Object data, HttpStatus httpStatus, String path) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
		this.path = path;
		this.timeStamp = new Date().getTime();
		this.httpStatus = httpStatus;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
