package com.techhive.shivamweb.filter;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.techhive.shivamweb.utils.ShivamWebVariableUtils;

public class MyJsonDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
			throws IOException, JsonProcessingException {

		String formattedDate = ShivamWebVariableUtils.SIMPLE_DATE_FORMAT_OF_DATABASE.format(date);
		gen.writeString(formattedDate);

	}

}
