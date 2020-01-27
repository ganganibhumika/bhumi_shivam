package com.techhive.shivamweb.filter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

public class MyJsonDateDeserializer extends JsonDeserializer<Date> {

	public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext)
			throws IOException, JsonProcessingException {

		String date = jsonparser.getText() + "";
		Date dateResponse = null;
		try {

			SimpleDateFormat angularDateFormat = new SimpleDateFormat(ShivamWebVariableUtils.DATE_FORMAT_FROM_ANGULAR);
			Date angularDate = angularDateFormat.parse(date);

			dateResponse = ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE
					.parse(ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE.format(angularDate));

		} catch (Exception e) {
			Date angularDate;
			SimpleDateFormat angularDateFormat = new SimpleDateFormat(ShivamWebVariableUtils.DATE_FORMAT_FROM_SHIVAM);
			try {
				angularDate = angularDateFormat.parse(date);
				dateResponse = ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE
						.parse(ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE.format(angularDate));

			} catch (ParseException e1) {
//				e1.printStackTrace();
			}

//			 e.printStackTrace();
		}

		if (ShivamWebMethodUtils.isObjectNullOrEmpty(dateResponse)) {

			// if date contains format like "EEE MMM dd yyyy hh:mm:ss aaa" then
			// below try..cache is executed. (Java date format)
			try {
				dateResponse = ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE.parse(date);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}

		return dateResponse;
	}

}
