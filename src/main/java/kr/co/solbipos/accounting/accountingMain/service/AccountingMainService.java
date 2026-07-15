package kr.co.solbipos.accounting.accountingMain.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AccountingMainService.java
 * @Description : 벤슨 > 회계관리 > 회계관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AccountingMainService {

    /** 일별전송 탭 - 조회 */
    List<DefaultMap<String>> getAcDayTransferList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO);

    /** 월별전송 탭 - 조회 */
    List<DefaultMap<String>> getAcMonthTransferList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO);

    /** 매장별 항목관리 탭 - 조회 */
    List<DefaultMap<String>> getAcStoreOptionList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO);

    /** 매장별 항목관리 탭 - 저장 */
    int saveAcStoreOption(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO);

    /** 매장별 항목관리 탭 - 삭제 */
    int delAcStoreOption(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 조회 */
    List<DefaultMap<String>> getAcComCodeList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO);

    /** 공통코드관리 탭 - 상세코드(우측) 조회 */
    List<DefaultMap<String>> getAcComCodeDtlList(AccountingMainVO accountingMainVO, SessionInfoVO sessionInfoVO);

    /** 공통코드관리 탭 - 공통코드(좌측) 저장 (I/U/D 일괄처리) */
    int saveAcComCode(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO);

    /** 공통코드관리 탭 - 상세코드(우측) 저장 (I/U/D 일괄처리) */
    int saveAcComCodeDtl(AccountingMainVO[] accountingMainVOs, SessionInfoVO sessionInfoVO);

}
