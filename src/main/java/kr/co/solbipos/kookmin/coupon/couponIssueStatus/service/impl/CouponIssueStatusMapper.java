package kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.coupon.couponIssueStatus.service.CouponIssueStatusVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : CouponIssueStatusMapper.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰상태관리(관리자
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface CouponIssueStatusMapper {

    /** 쿠폰 정보 조회 */
    List<DefaultMap<Object>> getCouponIssueStatusList(CouponIssueStatusVO couponIssueStatusVO);

    /** 쿠폰상태변환 저장 */
    int saveCouponIssueStatus(CouponIssueStatusVO couponIssueStatusVO);
}
