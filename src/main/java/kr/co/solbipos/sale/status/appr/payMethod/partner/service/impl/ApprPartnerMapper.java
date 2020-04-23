package kr.co.solbipos.sale.status.appr.payMethod.partner.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.partner.service.ApprPartnerVO;

@Mapper
@Repository
public interface ApprPartnerMapper {
    /** 제휴카드 승인현황 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprPartnerList(ApprPartnerVO apprPartnerVO);
    /** 제휴카드 승인현황 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprPartnerExcelList(ApprPartnerVO apprPartnerVO);
}
