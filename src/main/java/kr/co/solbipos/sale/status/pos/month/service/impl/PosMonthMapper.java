package kr.co.solbipos.sale.status.pos.month.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.month.service.PosMonthVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosMonthMapper {
	/** 포스별매출 월별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosMonthVO posMonthVO);

    /** 포스별매출 월별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosMonthList(PosMonthVO posMonthVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosMonthVO posMonthVO);

}
