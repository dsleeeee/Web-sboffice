package kr.co.solbipos.base.price.hqSplyPrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : HqSplyPriceService.java
 * @Description : 기초관리 - 가격관리 - 본사공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.04  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface HqSplyPriceService {

    /** 본사 공급가관리 조회*/
    List<DefaultMap<String>> getHqSplyPriceList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 본사 공급가관리 엑셀다운로드 조회*/
    List<DefaultMap<String>> getHqSplyPriceExcelList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 본사 공급가 저장 */
    int saveHqSplyPrice(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 본사 공급가 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getHqSplyPriceExcelUploadSampleList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 공급가 업로드 임시테이블 전체 삭제 */
    int deleteSplyPriceExcelUploadCheckAll(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 공급가 업로드 임시테이블 삭제 */
    int deleteSplyPriceExcelUploadCheck(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 공급가 업로드 임시테이블 저장 */
    int saveSplyPriceExcelUploadCheck(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 공급가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(HqSplyPriceVO hqSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 공급가 업로드 검증결과 저장 */
    int saveSplyPriceExcelUploadCheckResult(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 본사 공급가 엑셀업로드 저장 */
    int saveHqSplyPriceExcelUpload(HqSplyPriceVO[] hqSplyPriceVOs, SessionInfoVO sessionInfoVO);

}
