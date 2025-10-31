package kr.co.solbipos.kookmin.coupon.couponInfo.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.kookmin.coupon.couponInfo.service.CouponInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @Class Name  : CouponInfoMapper.java
 * @Description : 국민대 > 쿠폰관리 > 쿠폰정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Mapper
@Repository
public interface CouponInfoMapper {

    /** 쿠폰 정보 조회 */
    List<DefaultMap<Object>> getCouponInfoList(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 팝업 - 상품 조회 */
    List<DefaultMap<Object>> getCouponSelectProdList(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 팝업 - 부서 조회 */
    List<DefaultMap<Object>> getcouponSelectPartList(CouponInfoVO couponInfoVO);

    /** 쿠폰코드 최대값 조회 */
    DefaultMap<Object> getMaxCoupnCd(CouponInfoVO couponInfoVO);

    /** 쿠폰 등록 */
    int getCouponRegist(CouponInfoVO couponInfoVO);

    /** 쿠폰상세정보 등록 */
    int getCouponInfoRegist(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 팝업 상세정보 조회 */
    List<DefaultMap<Object>> getCouponInfoDtlList(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 엑셀업로드 TMP 저장 */
    int saveCouponIssueTmp(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 TMP 테이블 값 제거 */
    int getDeleteTmpData(CouponInfoVO delCouponInfoVO);

    /** 쿠폰적용관리 엑셀업로드 건수 조회 */
    int getCountCouponIssueTmp(CouponInfoVO delCouponInfoVO);

    /** 쿠폰적용관리 쿠폰 발행 */
    int saveCouponIssue(CouponInfoVO delCouponInfoVO);

    /** 쿠폰적용관리 쿠폰 발행 시 쿠폰코드 중복 체크*/
    int getCoupnSerNoDupChk(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 팝업 업데이트 */
    int updateCouponStatus(CouponInfoVO couponInfoVO);

    /** 쿠폰적용관리 팝업 회수쿠폰 조회 */
    List<DefaultMap<Object>> getSelectSaleCouponList(CouponInfoVO couponInfoVO);
}
