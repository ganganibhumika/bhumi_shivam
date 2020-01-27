package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.ViewRequest;

@Repository
public interface ViewRequestRepository extends JpaRepository<ViewRequest, String>{


	@Query(value="select vr from ViewRequest as vr inner join User as u on vr.user.id=u.id where u.id=?1 order by vr.createdDate DESC")
	List<ViewRequest> findAllByUser(String userId);
	
	@Query(value="select vr from ViewRequest as vr inner join User as u on vr.user.id=u.id inner join PktMaster as pkt on vr.pktMaster.id=pkt.id where u.id=?1 and pkt.id=?2")
	Optional<ViewRequest> findAllByUserAndPkt(String userId, String pktMasterId);

	List<ViewRequest> findAllByOrderByCreatedDateDesc();
	
	@Query(value="select vr from ViewRequest as vr inner join User as u on vr.user.id=u.id where u.id=?1 order by vr.createdDate DESC")
	List<ViewRequest> getAllResentConfirm(String userId,Pageable page);

	Page<ViewRequest> findAllByInProgressOrderByCreatedDateDesc(Boolean inProgress, Pageable request);

	Page<ViewRequest> findAllByInProgress(Boolean inProgress, Pageable request);
	
	@Query(value="select vr from ViewRequest as vr inner join User as u on vr.user.id=u.id where u.id=?1 and vr.inProgress=?2")
	Page<ViewRequest> findAllByUserAndInProgress(String userId, Boolean inProgress, Pageable request);

	List<ViewRequest> findByInProgress(boolean b);

	@Query(value="select vr from ViewRequest as vr inner join User as u on vr.user.id=u.id inner join PktMaster as pkt on vr.pktMaster.id=pkt.id where u.id=?1 and pkt.id=?2 and vr.inProgress=true")
	Optional<ViewRequest> findAllByUserAndPktInProgress(String userId, String pktMasterId);
 
	
}
