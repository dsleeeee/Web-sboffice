package kr.co.solbipos.kookmin.coupon.couponInfo.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
/**
 * @Class Name  : CouponInfoService.java
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
public interface CouponInfoService {

    /** 쿠폰 정보 조회 */
    List<DefaultMap<Object>> getCouponInfoList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 - 상품 조회 */
    List<DefaultMap<Object>> getCouponSelectProdList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 - 부서 조회 */
    List<DefaultMap<Object>> getcouponSelectPartList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰 등록 */
    int getCouponRegist(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰상세정보 조회 */
    List<DefaultMap<Object>> getCouponInfoDtlList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 TMP테이블 제거 */
    int getDeleteTmpData(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 쿠폰발행 엑셀업로드 */
    int getCouponIssueExcelUpload(CouponInfoVO[] couponInfoVOs, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 쿠폰발행 저장 */
    int saveCouponIssue(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);

    /** 쿠폰적용관리 팝업 회수쿠폰 조회 */
    List<DefaultMap<Object>> getSelectSaleCouponList(CouponInfoVO couponInfoVO, SessionInfoVO sessionInfoVO);
}
