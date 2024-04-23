package kr.co.solbipos.base.price.storeSplyPrice.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreSplyPriceService.java
 * @Description : 기초관리 - 가격관리 - 매장공급가관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.04.16  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2024.04.16
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreSplyPriceService {

    /** 매장공급가관리 - 상품의 본사 가격정보 조회 */
    DefaultMap<String> getProdPriceInfo(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 상품별 공급가관리 리스트 조회 */
    List<DefaultMap<String>> getByProdSplyPriceList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 상품별 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByProdSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 매장별 공급가관리 리스트 조회 */
    List<DefaultMap<String>> getByStoreSplyPriceList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 매장별 공급가관리 엑셀다운로드 조회 */
    List<DefaultMap<String>> getByStoreSplyPriceExcelList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 매장공급가 저장 */
    int saveStoreSplyPrice(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 매장공급가 복사 */
    int copyStoreSplyPrice(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 엑셀 양식다운로드 조회 */
    List<DefaultMap<String>> getStoreSplyPriceExcelUploadSampleList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 전체 삭제 */
    int deleteSplyPriceExcelUploadCheckAll(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 삭제 */
    int deleteSplyPriceExcelUploadCheck(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 저장 */
    int saveSplyPriceExcelUploadCheck(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 임시테이블 데이터 조회 */
    List<DefaultMap<String>> getSplyPriceExcelUploadCheckList(StoreSplyPriceVO storeSplyPriceVO, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 공급가 업로드 검증결과 저장 */
    int saveSplyPriceExcelUploadCheckResult(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO);

    /** 매장공급가관리 - 엑셀업로드 매장 공급가 엑셀업로드 저장 */
    int saveStoreSplyPriceExcelUpload(StoreSplyPriceVO[] storeSplyPriceVOs, SessionInfoVO sessionInfoVO);
}
