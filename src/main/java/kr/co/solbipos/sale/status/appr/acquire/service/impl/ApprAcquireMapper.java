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
}
