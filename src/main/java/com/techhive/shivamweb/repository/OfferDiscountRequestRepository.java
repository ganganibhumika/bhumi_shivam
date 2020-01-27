package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.OfferDiscountRequest;

@Repository
public interface OfferDiscountRequestRepository extends JpaRepository<OfferDiscountRequest, String>{
	@Query(value="select od from OfferDiscountRequest as od inner join User as u on od.user.id=u.id inner join PktMaster as pkt on od.pktMaster.id=pkt.id where u.id=?1 and pkt.isSold=false and pkt.id=?2")
	Optional<OfferDiscountRequest> findByUserAndPkt(String userId, String pktMasterId);
	
	@Query(value="select od from OfferDiscountRequest as od inner join User as u on od.user.id=u.id inner join PktMaster as pkt on od.pktMaster.id=pkt.id where u.id=?1  and pkt.isSold=false order By od.createdDate Desc")
	List<OfferDiscountRequest>  findAllByUser(String userId);

	@Query(value="select od from OfferDiscountRequest as od inner join User as u on od.user.id=u.id inner join PktMaster as pkt on od.pktMaster.id=pkt.id and pkt.isSold=false and od.approveStatus=?1 order By od.createdDate Desc")
	List<OfferDiscountRequest> findAllByApproveStatusOrderByCreatedDateDesc(String status);

	@Query(value="select count(*) from OfferDiscountRequest as od inner join User as u on od.user.id=u.id inner join PktMaster as pkt on od.pktMaster.id=pkt.id where u.id=?1  and pkt.isSold=false")
	Integer countforUser(String userId);


}
