package kr.co.solbipos.sale.status.appr.acquire.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.acquire.service.ApprAcquireVO;

@Mapper
@Repository
public interface ApprAcquireMapper {
    /** 승인현황 카드매입사별 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일쿠본 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMcouponList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일페이 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMpayList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일페이 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireNcardList(ApprAcquireVO apprAcquireVO);

    /** 승인현황 카드매입사별 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireExcelList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일쿠본 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMcouponExcelList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일페이 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireMpayExcelList(ApprAcquireVO apprAcquireVO);
    /** 승인현황 카드매입사별 탭 - 모바일페이 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprAcquireNcardExcelList(ApprAcquireVO apprAcquireVO);
}
