package com.techhive.shivamweb.audit.repository.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.audit.repository.GenericAuditRepositoryCustom;
import com.techhive.shivamweb.master.model.history.UserHistory_;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@Repository
public class GenericAuditRepositoryCustomImpl implements GenericAuditRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public <T> void save(T t) {
		entityManager.persist(t);
		entityManager.flush();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> Optional<T> getLastModifiedData(T t, String idOfEntity) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
		Root<T> root = (Root<T>) query.from(t.getClass());
		Path<String> pathForId = root.get(UserHistory_.id.getName());
		Predicate predicate = criteriaBuilder.equal(root.get(UserHistory_.idOfEntity.getName()), idOfEntity);
		query.multiselect(pathForId).where(predicate);
		query.orderBy(criteriaBuilder.desc(root.get(UserHistory_.createdDate.getName())));
		TypedQuery<T> typeQuery = entityManager.createQuery(query);
		List<T> listOfT = typeQuery.getResultList();
		
		return ShivamWebMethodUtils.isListNullOrEmpty(listOfT) ? Optional.ofNullable(null)
				: Optional.of(listOfT.get(0));

	}

}
