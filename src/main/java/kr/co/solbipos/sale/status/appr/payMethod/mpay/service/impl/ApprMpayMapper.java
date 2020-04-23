package kr.co.solbipos.sale.status.appr.payMethod.mpay.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.mpay.service.ApprMpayVO;

@Mapper
@Repository
public interface ApprMpayMapper {
    /** 승인현황 승인현황 모바일페이 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprMpayList(ApprMpayVO apprMpayVO);
    /** 승인현황 승인현황 모바일페이 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprMpayExcelList(ApprMpayVO apprMpayVO);
}
