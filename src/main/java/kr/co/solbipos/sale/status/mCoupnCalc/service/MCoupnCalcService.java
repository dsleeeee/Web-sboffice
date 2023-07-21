package kr.co.solbipos.sale.status.mCoupnCalc.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MCoupnCalcService.java
 * @Description : 맘스터치 > 매출분석2 > 모바일쿠폰 정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.07.19  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2023.07.19
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MCoupnCalcService {

    /** 모바일쿠폰 정산 조회 */
    List<DefaultMap<Object>> getMCoupnCalcList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO);

    /** 모바일쿠폰 정산 조회조건 엑셀다운로드 */
    List<DefaultMap<Object>> getMCoupnCalcExcelList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO);

    /** 모바일쿠폰 정산 상세화면 조회 */
    List<DefaultMap<Object>> getMCoupnCalcDtlList(MCoupnCalcVO mCoupnCalcVO, SessionInfoVO sessionInfoVO);
}
