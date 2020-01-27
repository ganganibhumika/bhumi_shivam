package com.techhive.shivamweb.exception;

@SuppressWarnings("serial")
public class AlreadyExistException extends RuntimeException {

	private String msg;

	public AlreadyExistException(String msg) {
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
