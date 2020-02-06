package kr.co.solbipos.sale.anals.store.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.anals.store.prod.service.StoreProdVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StoreProdMapper {
	
	/** 매장상품순위 - 매장상품순위 리스트 조회  */
    List<DefaultMap<String>> getStoreProdList(StoreProdVO storeProdVO);
    
}
