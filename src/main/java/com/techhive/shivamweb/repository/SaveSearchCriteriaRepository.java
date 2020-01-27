package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.SaveSearchCriteria;

@Repository
public interface SaveSearchCriteriaRepository extends JpaRepository<SaveSearchCriteria, String>{

	@Query("select ssc from SaveSearchCriteria as ssc inner join User as u on ssc.user.id=u.id where u.id=?2 and ssc.name=?1")
	Optional<SaveSearchCriteria> findByNameAndUser(String name, String userId);

	@Query("select new com.techhive.shivamweb.model.SaveSearchCriteria(ssc.id,ssc.name) from SaveSearchCriteria as ssc inner join User as u on ssc.user.id=u.id where u.id=?1")
	List<SaveSearchCriteria> getAllSearchCriteriaOfUser(String userId);

	@Query("select ssc from SaveSearchCriteria as ssc inner join User as u on ssc.user.id=u.id where u.id=?2 and ssc.name=?1 and ssc.id <>?3")
	Optional<SaveSearchCriteria> findByNameAndUserNotIdIn(String name, String userId, String searchCriteriaId);

}
