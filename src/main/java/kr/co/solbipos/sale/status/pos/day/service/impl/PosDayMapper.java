package kr.co.solbipos.sale.status.pos.day.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.pos.day.service.PosDayVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface PosDayMapper {
	/** 포스별매출 일자별 탭 - 매장 및 포스 리스트 조회 */
    List<DefaultMap<String>> getStorePosList(PosDayVO posDayVO);

    /** 포스별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getPosDayList(PosDayVO posDayVO);

    /** 포스별매출 - 매장 코너 리스트 조회 */
	List<DefaultMap<String>> getPosNmList(PosDayVO posDayVO);

}
