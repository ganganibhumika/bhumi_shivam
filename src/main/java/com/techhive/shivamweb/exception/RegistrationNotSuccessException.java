package com.techhive.shivamweb.exception;

@SuppressWarnings("serial")
public class RegistrationNotSuccessException extends RuntimeException {

	private String msg;

	public RegistrationNotSuccessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
