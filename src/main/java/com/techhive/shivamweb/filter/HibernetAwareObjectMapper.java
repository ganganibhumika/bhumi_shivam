package com.techhive.shivamweb.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

/***
 * This class is prevent all lazzy type object from output of api.
 * 
 * @author test
 *
 */
public class HibernetAwareObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HibernetAwareObjectMapper() {
		Hibernate5Module hibernat5Module = new Hibernate5Module();
		hibernat5Module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
		registerModule(hibernat5Module);

	}

}
