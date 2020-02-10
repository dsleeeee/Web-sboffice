package kr.co.solbipos.sale.anals.store.rank.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.store.rank.service.StoreRankVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreRankMapper {
	
	/** 매장순위 - 매장순위 리스트 조회  */
    List<DefaultMap<String>> getStoreRankList(StoreRankVO storeRankVO);
    
    /** 매장순위 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getPayColList(StoreRankVO storeRankVO);
}
