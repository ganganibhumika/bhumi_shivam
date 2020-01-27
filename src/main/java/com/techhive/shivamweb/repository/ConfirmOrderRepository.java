package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.ConfirmOrder;

@Repository
public interface ConfirmOrderRepository extends JpaRepository<ConfirmOrder, String>{

	@Query(value="select co from ConfirmOrder as co inner join User as u on co.user.id=u.id where u.id=?1 order by co.createdDate DESC")
	List<ConfirmOrder> findAllByUser(String userId);
	
	@Query(value="select co from ConfirmOrder as co inner join User as u on co.user.id=u.id inner join PktMaster as pkt on co.pktMaster.id=pkt.id where u.id=?1 and pkt.id=?2")
	Optional<ConfirmOrder> findAllByUserAndPkt(String userId, String pktMasterId);

	List<ConfirmOrder> findAllByOrderByCreatedDateDesc();
	
	@Query(value="select co from ConfirmOrder as co inner join User as u on co.user.id=u.id where u.id=?1 order by co.createdDate DESC")
	List<ConfirmOrder> getAllResentConfirm(String userId,Pageable page);

	@Query(value="select count(*) from ConfirmOrder as co inner join User as u on co.user.id=u.id where u.id=?1")
	Integer countforUser(String userID);

	
}
