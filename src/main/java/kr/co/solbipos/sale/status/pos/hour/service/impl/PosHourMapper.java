package kr.co.solbipos.sale.status.pos.hour.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.hour.service.PosHourVO;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PosHourMapper {
	/** 포스별매출 시간대별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosHourVO posHourVO);

    /** 포스별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosHourVO posHourVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosHourVO posHourVO);

}
