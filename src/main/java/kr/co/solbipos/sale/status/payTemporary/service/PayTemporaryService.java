package kr.co.solbipos.sale.status.payTemporary.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PayTemporaryService.java
 * @Description : 맘스터치 > 매출분석2 > 가승인-상품권결제차액
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.20  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.03.20
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PayTemporaryService {

    /** 가승인-상품권결제차액 탭 - 조회 */
    List<DefaultMap<String>> getPayTemporaryList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getPayTemporaryExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<String>> getPayTemporaryDtlList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);

    /** 가승인-상품권결제차액 상세내역 탭 - 조회 */
    List<DefaultMap<Object>> getPayTemporaryGiftList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);

    /** 가승인-상품권결제차액 상세내역 탭 - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getPayTemporaryGiftExcelList(PayTemporaryVO payTemporaryVO, SessionInfoVO sessionInfoVO);
}