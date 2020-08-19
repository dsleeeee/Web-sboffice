package kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.appr.payMethod.mcoupon.service.ApprMcouponVO;

@Mapper
@Repository
public interface ApprMcouponMapper {
    /** 승인현황 승인현황 모바일 쿠폰 탭 - 리스트 조회 */
    List<DefaultMap<String>> getApprMcouponList(ApprMcouponVO apprMcouponVO);
    /** 승인현황 승인현황 모바일 쿠폰 탭 - 엑셀 리스트 조회 */
    List<DefaultMap<String>> getApprMcouponExcelList(ApprMcouponVO apprMcouponVO);

	List<DefaultMap<String>> getCornerNmList(ApprMcouponVO apprMcouponVO);
}
