package kr.co.solbipos.sale.status.prod.pos.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.prod.pos.service.ProdPosVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProdPosMapper {
	/** 포스별탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(ProdPosVO prodPosVO);

    /** 포스별탭 - 매장 코너 리스트 조회 */
    List<DefaultMap<String>> getPosNmList(ProdPosVO prodPosVO);

    /** 포스별탭 - 조회 */
    List<DefaultMap<String>> getProdPosList(ProdPosVO prodPosVO);

    /** 포스별탭 - 엑셀 조회 */
	List<DefaultMap<String>> getProdPosExcelList(ProdPosVO prodPosVO);
}
