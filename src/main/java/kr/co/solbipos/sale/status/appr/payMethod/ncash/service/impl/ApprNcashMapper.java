package kr.co.solbipos.sale.status.appr.payMethod.ncash.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.ncash.service.ApprNcashVO;

@Mapper
@Repository
public interface ApprNcashMapper {
    /** 비매출현금 승인현황 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprNcashList(ApprNcashVO apprNcashVO);
    /** 비매출현금 승인현황 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprNcashExcelList(ApprNcashVO apprNcashVO);
}
