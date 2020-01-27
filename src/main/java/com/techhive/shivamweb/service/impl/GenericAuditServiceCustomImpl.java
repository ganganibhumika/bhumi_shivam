package com.techhive.shivamweb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.techhive.shivamweb.audit.repository.GenericAuditRepositoryCustom;
import com.techhive.shivamweb.service.GenericAuditServiceCustom;

@Service
public class GenericAuditServiceCustomImpl implements GenericAuditServiceCustom {

	@Autowired
	private GenericAuditRepositoryCustom genericAuditRepositoryCustom;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> void save(T t) {
		genericAuditRepositoryCustom.save(t);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public <T> Optional<T> getLastModifiedData(T t, String idOfEntity) {
		return genericAuditRepositoryCustom.getLastModifiedData(t, idOfEntity);
	}
}
