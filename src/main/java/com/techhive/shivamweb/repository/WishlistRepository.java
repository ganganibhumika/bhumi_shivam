package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.model.Wishlist;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, String>{
	@Query(value="select wl from Wishlist as wl inner join User as u on wl.user.id=u.id inner join PktMaster as pkt on wl.pktMaster.id=pkt.id where u.id=?1 and pkt.isSold=false and pkt.id=?2")
	Optional<Wishlist> findByUserAndPkt(String userId, String pktMasterId);

	@Query(value="select wl from Wishlist as wl inner join User as u on wl.user.id=u.id inner join PktMaster as pkt on wl.pktMaster.id=pkt.id where u.id=?1  and pkt.isSold=false order by wl.createdDate DESC")
	List<Wishlist>  findAllByUser(String userId);
	
	@Query(value="select count(*) from Wishlist as wl inner join User as u on wl.user.id=u.id inner join PktMaster as pkt on wl.pktMaster.id=pkt.id where u.id=?1  and pkt.isSold=false")
	Integer countforUser(String userId);

}
