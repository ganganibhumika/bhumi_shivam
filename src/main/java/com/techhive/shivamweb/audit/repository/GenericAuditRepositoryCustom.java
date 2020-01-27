package com.techhive.shivamweb.audit.repository;

import java.util.Optional;

public interface GenericAuditRepositoryCustom {

	public <T> void save(T t);

	public <T> Optional<T> getLastModifiedData(T t, String idOfEntity);
}
