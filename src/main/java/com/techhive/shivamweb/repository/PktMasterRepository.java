package com.techhive.shivamweb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.techhive.shivamweb.master.model.PktMaster;
import com.techhive.shivamweb.master.model.DTO.PktMasterDto;

@Repository
public interface PktMasterRepository extends JpaRepository<PktMaster, String> {

	Optional<PktMaster> findByStoneId(String stoneId);

	// @Query(value = "SELECT * FROM tblPktMaster pkt WHERE pkt.carat = 0",
	// nativeQuery = true)
	// public List<PktMaster>
	// getStonesViaLazzyLoadingOfSearchCriteria(StoneSearchCriteria body, Pageable
	// pageable);

	@Query(value = "SELECT new com.techhive.shivamweb.master.model.DTO.PktMasterDto( pkt.id,pkt.stoneId,pkt.isHold) FROM PktMaster as pkt")
	List<PktMasterDto> findAllIdAndIsHold();

	Page<PktMaster> findAllByIsSold(Boolean b, Pageable request);

	List<PktMaster> findAllByIsSold(Boolean b);

	Page<PktMaster> findAllByisFeaturedAndIsSold(Boolean isFeatured, Boolean b, Pageable request);

	@Query(value = "select Top(20) e1.pktMasterId\r\n" + "  from [tblPktMaster] as e1\r\n"
			+ "  inner join [tblPktMaster] as e2\r\n" + "    on e1.[carat] = e2.[carat]\r\n"
			+ "   and e1.[width] = e2.[width]\r\n" + "   and e1.pktMasterId != e2.pktMasterId\r\n"
			+ "  group by e1.pktMasterId, e1.[carat], e1.[width]\r\n"
			+ "  order by e1.[carat], e1.[width]", nativeQuery = true)
	List<String> findAllTwin();

	@Query(value = "select new com.techhive.shivamweb.master.model.PktMaster ( id,  carat,  certNo,  codeOfClarity,  codeOfColor,\r\n"
			+ "			 codeOfCulet,  codeOfCut,  codeOfFluorescence,  codeOfLuster,  codeOfMilky,\r\n"
			+ "			 codeOfPolish,  codeOfShade,  codeOfShape,  codeOfSymmentry,  comment,\r\n"
			+ "			 country,  crownAngle,  crownHeight,  diameter,  disc,  efCode,\r\n"
			+ "			 eyeClean,  fColor,  fIntensity,  fOvertone,  gRap,  gRate,  giDate,\r\n"
			+ "			 girdle,  height,  isFeatured,  isHold,  isSold,  keyToSym,\r\n"
			+ "			 lab,  length,  lrHalf,  pavilionAngle,  pavilionHeight,  sbinName,\r\n"
			+ "			 seqNo,  shape,  sinName,  soinName,  stoneId,  strLn,\r\n"
			+ "			 tablePercentage,  tbinName,  tinName,  toinName,  totDepth,  width) from PktMaster where isSold=false")
	List<PktMaster> findAllByParam();

	@Query(value = "select new com.techhive.shivamweb.master.model.PktMaster ( id,  carat,  certNo,  codeOfClarity,  codeOfColor,\r\n"
			+ "			 codeOfCulet,  codeOfCut,  codeOfFluorescence,  codeOfLuster,  codeOfMilky,\r\n"
			+ "			 codeOfPolish,  codeOfShade,  codeOfShape,  codeOfSymmentry,  comment,\r\n"
			+ "			 country,  crownAngle,  crownHeight,  diameter,  disc,  efCode,\r\n"
			+ "			 eyeClean,  fColor,  fIntensity,  fOvertone,  gRap,  gRate,  giDate,\r\n"
			+ "			 girdle,  height,  isFeatured,  isHold,  isSold,  keyToSym,\r\n"
			+ "			 lab,  length,  lrHalf,  pavilionAngle,  pavilionHeight,  sbinName,\r\n"
			+ "			 seqNo,  shape,  sinName,  soinName,  stoneId,  strLn,\r\n"
			+ "			 tablePercentage,  tbinName,  tinName,  toinName,  totDepth,  width) from PktMaster where isSold=false and id not in ?1")
	List<PktMaster> findAllByParamNotIdIn(Set<String> pktIdNotToCheck);

	@Query(value = "select p from PktMaster as p where  p.isSold=false and p.discountUpdateDate  between  CAST(GETDATE()-10 AS date)  and CAST(GETDATE()-1 AS date)")
	List<PktMaster> getRecentDiscountChangedStones();

	@Query(value = "select p from PktMaster as p where  p.isSold=false and (p.certNo=?1 OR p.stoneId=?1)")
	Optional<PktMaster> findByStoneIdAndCertNo(String stoneId);

}
