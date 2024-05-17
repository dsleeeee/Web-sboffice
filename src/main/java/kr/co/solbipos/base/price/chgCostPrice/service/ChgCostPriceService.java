package kr.co.solbipos.base.price.chgCostPrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : ChgCostPriceService.java
 * @Description : 기초관리 - 가격관리 - 원가임의변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.29  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.29
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

public interface ChgCostPriceService {

    /** 원가임의변경 원가 조회*/
    List<DefaultMap<String>> getCostPriceList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 엑셀다운로드 조회*/
    List<DefaultMap<String>> getCostPriceExcelList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 변경 */
    int saveCostPrice(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getCostPriceExcelUploadSampleList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 업로드 임시테이블 전체 삭제 */
    int deleteCostPriceExcelUploadCheckAll(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 업로드 임시테이블 삭제 */
    int deleteCostPriceExcelUploadCheck(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 업로드 임시테이블 저장 */
    int saveCostPriceExcelUploadCheck(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getCostPriceExcelUploadCheckList(ChgCostPriceVO chgCostPriceVO, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가  업로드 검증결과 저장 */
    int saveCostPriceExcelUploadCheckResult(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO);

    /** 원가임의변경 원가 엑셀업로드 저장 */
    int saveCostPriceExcelUpload(ChgCostPriceVO[] chgCostPriceVOs, SessionInfoVO sessionInfoVO);
}
