package kr.co.solbipos.sale.status.pos.prod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.prod.service.PosProdVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosProdMapper {
	/** 포스별매출 상품별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosProdVO posProdVO);

    /** 포스별매출 상품별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosProdList(PosProdVO posProdVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosProdVO posProdVO);

}
