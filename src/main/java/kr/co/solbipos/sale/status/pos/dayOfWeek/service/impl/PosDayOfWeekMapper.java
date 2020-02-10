package kr.co.solbipos.sale.status.pos.dayOfWeek.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.dayOfWeek.service.PosDayOfWeekVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosDayOfWeekMapper {
	/** 포스별매출 요일별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosDayOfWeekVO posDayOfWeekVO);

    /** 포스별매출 요일별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayOfWeekList(PosDayOfWeekVO posDayOfWeekVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosDayOfWeekVO posDayOfWeekVO);

}
