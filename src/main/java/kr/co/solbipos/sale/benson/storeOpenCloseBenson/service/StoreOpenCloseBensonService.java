package kr.co.solbipos.sale.benson.storeOpenCloseBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreOpenCloseBensonService.java
 * @Description : (벤슨) 매장분석 > 매장 오픈/마감 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.09.09  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreOpenCloseBensonService {

    /** 매장 오픈/마감 현황 - 일별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonDayList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 일별 탭 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonDayDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 월별 탭 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonMonthList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO);

    /** 매장 오픈/마감 현황 - 월별 탭 상세 조회 */
    List<DefaultMap<String>> getStoreOpenCloseBensonMonthDtlList(StoreOpenCloseBensonVO storeOpenCloseBensonVO, SessionInfoVO sessionInfoVO);
}
