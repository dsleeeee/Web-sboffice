package kr.co.solbipos.sale.anals.store.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.store.month.service.StoreMonthVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreMonthMapper {
	
	/** 매장월별순위 - 매장월별순위 리스트 조회  */
    List<DefaultMap<String>> getStoreMonthList(StoreMonthVO storeMonthVO);
    
    /** 매장월별순위 - 결제수단 컬럼 리스트 조회 */
    List<DefaultMap<String>> getMonthColList(StoreMonthVO storeMonthVO);
}
