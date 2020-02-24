package kr.co.solbipos.sale.anals.store.fg.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sale.anals.store.fg.service.StoreFgVO;

@Mapper
@Repository
public interface StoreFgMapper {
	
	/** 매장형태별 매출 - 매장형태별 매출 리스트 조회  */
    List<DefaultMap<String>> getStoreFgList(StoreFgVO storeFgVO);

}
