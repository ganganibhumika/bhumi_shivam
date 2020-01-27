package com.techhive.shivamweb.service;

import java.util.Optional;

public interface GenericAuditServiceCustom {

	public <T> void save(T t);

	 public <T> Optional<T> getLastModifiedData(T t, String idOfEntity);
}
