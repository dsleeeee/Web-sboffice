package kr.co.solbipos.sale.status.appr.payMethod.card.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.card.service.ApprCardVO;

@Mapper
@Repository
public interface ApprCardMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprCardList(ApprCardVO apprCardVO);
    /** 승인현황 승인현황 신용카드 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprCardExcelList(ApprCardVO apprCardVO);

	List<DefaultMap<String>> getCornerNmList(ApprCardVO apprCardVO);
}
