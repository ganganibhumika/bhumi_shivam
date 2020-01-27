package com.techhive.shivamweb.service;

import java.text.ParseException;

import com.techhive.shivamweb.response.payload.ResponseWrapperDTO;

public interface ResetAllService {
	/**
	 * @author neel delete all data and insert few dummy data
	 * @param path
	 * @throws ParseException
	 */
	public ResponseWrapperDTO resetAll(String path) throws ParseException;

	public ResponseWrapperDTO resetResultScreenLabel();

	public void addUserMigration() throws ParseException;

	public ResponseWrapperDTO resetPkt();

	public ResponseWrapperDTO resetUserMigration();
}
