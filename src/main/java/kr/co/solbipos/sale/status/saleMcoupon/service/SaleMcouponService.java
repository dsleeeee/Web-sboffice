package kr.co.solbipos.sale.status.saleMcoupon.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SaleMcouponService.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.04.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.04.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SaleMcouponService {

    /** 모바일쿠폰 현황 - 조회 */
    List<DefaultMap<Object>> getSaleMcouponList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO);

    /** 모바일쿠폰 현황 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getSaleMcouponExcelList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO);

    /** 모바일쿠폰 현황 상세 팝업 - 조회 */
    List<DefaultMap<Object>> getSaleMcouponDtlList(SaleMcouponVO saleMcouponVO, SessionInfoVO sessionInfoVO);
}