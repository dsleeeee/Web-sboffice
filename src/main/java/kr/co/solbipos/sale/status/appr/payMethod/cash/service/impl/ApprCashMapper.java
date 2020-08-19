package kr.co.solbipos.sale.status.appr.payMethod.cash.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.cash.service.ApprCashVO;

@Mapper
@Repository
public interface ApprCashMapper {
    /** 코너별매출 일자별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprCashList(ApprCashVO apprCashVO);
    /** 코너별매출 일자별 탭 - 엑셀  조회 */
    List<DefaultMap<String>> getApprCashExcelList(ApprCashVO apprCashVO);

    List<DefaultMap<String>> getCornerNmList(ApprCashVO apprCashVO);
}
