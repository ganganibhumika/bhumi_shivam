package com.techhive.shivamweb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.SearchHistory;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, String> {

	@Query(value = "select sh from SearchHistory as sh inner join User as u on sh.user.id=u.id where u.id=?1")
	Page<SearchHistory> findAllByUser(String userId, Pageable request);

	@Query(value = "select sh from SearchHistory as sh inner join User as u on sh.user.id=u.id where u.id=?1 order by sh.createdDate DESC")
	List<SearchHistory> findAllOfUser(String userId, Pageable request);

}
