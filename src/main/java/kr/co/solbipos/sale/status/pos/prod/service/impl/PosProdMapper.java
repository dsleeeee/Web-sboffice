package kr.co.solbipos.sale.status.pos.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosProdMapper {
	/** 상품별탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO);

    /** 상품별탭 - 매장 코너 리스트 조회 */
    List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO);

    /** 상품별탭 - 조회 */
    List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO);

    /** 상품별탭 - 엑셀 조회 */
    List<DefaultMap<String>> getPosProdExcelList(PosProdVO posProdVO);
}