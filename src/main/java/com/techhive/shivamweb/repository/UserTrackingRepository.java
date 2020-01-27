package com.techhive.shivamweb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.UserTracking;

@Repository
public interface UserTrackingRepository extends JpaRepository<UserTracking, String> {
	@Query("select ut from UserTracking as ut where ut.description like concat('%',?1,'%')")
	Page<UserTracking> findAllWithSearch(String searchText, Pageable request);

	@Query("select ut from UserTracking as ut inner join User as u on ut.user.id=u.id where ut.description like concat('%',?1,'%') and u.id =?2")
	Page<UserTracking> findAllWithSearchByUser(String searchText, String userId, Pageable request);

	@Query("select ut from UserTracking as ut inner join User as u on ut.user.id=u.id where  u.id =?1")
	Page<UserTracking> findAllByUser(String userId, Pageable request);

}
