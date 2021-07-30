package kr.co.solbipos.sale.dlvr.dlvrFg.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DlvrFgService.java
 * @Description : 매출관리 > 배달현황 > 내점/배달/포장 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.05.21  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2021.05.21
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface DlvrFgService {

    /** 배달구분 */
    String getDlvrFg(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);

    /** 배달구분 콤보박스 데이터 */
    List<DefaultMap<String>> getDlvrFgData(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getOrderFg(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getProd(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);

    /** 상품별탭 - 유형별 */
    List<DefaultMap<Object>> getProdDtl(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);

    /** 상품-영수별매출상세 */
    List<DefaultMap<Object>> getSaleDtl(DlvrFgVO dlvrFgVO, SessionInfoVO sessionInfoVO);
}
