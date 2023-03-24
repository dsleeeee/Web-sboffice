package kr.co.solbipos.sale.prod.dayProdStore.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DayProdStoreService.java
 * @Description : 맘스터치 > 상품매출분석 > 일별 상품 매출 현황(매장별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.23  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DayProdStoreService {

    /** 일별 상품 매출 현황 조회 */
    List<DefaultMap<Object>> getDayProdStoreList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getDayProdStoreExcelList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 브랜드 조회(콤보박스용) */
    List<DefaultMap<Object>> getUserBrandComboList(DayProdStoreVO dayProdStoreVO, SessionInfoVO sessionInfoVO);

    /** 사용자별 코드별 공통코드 콤보박스 조회 */
    List<DefaultMap<Object>> getUserHqNmcodeComboList(SessionInfoVO sessionInfoVO, String nmcodeGrpCd);

    /** 사용자별 지사 콤보박스 조회 */
    List<DefaultMap<Object>> getUserBranchComboList(SessionInfoVO sessionInfoVO);
}