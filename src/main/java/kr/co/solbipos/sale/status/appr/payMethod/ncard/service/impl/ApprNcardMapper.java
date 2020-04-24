package kr.co.solbipos.sale.status.appr.payMethod.ncard.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.ncard.service.ApprNcardVO;

@Mapper
@Repository
public interface ApprNcardMapper {
	/** 비매출카드 승인현황 - 리스트 조회 */
    List<DefaultMap<String>> getApprNcardList(ApprNcardVO apprNcardVO);
    /** 비매출카드 승인현황 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprNcardExcelList(ApprNcardVO apprNcardVO);
}
