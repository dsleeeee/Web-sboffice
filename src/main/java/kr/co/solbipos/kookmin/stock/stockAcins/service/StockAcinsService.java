package kr.co.solbipos.kookmin.stock.stockAcins.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : StockAcinsService.java
 * @Description : 국민대 > 재고관리 > 재고실사
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.12.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.12.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface StockAcinsService {

    /** 실사사유 조회 */
    List<DefaultMap<String>> getSearchAcinsReason(SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사관리 리스트 조회 */
    List<DefaultMap<String>> getSearchAcinsList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사 삭제 */
    int deleteAcins(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사등록 상품 리스트 조회*/
    List<DefaultMap<String>> getSearchAcinsRegistList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사상품 저장 */
    int saveAcins(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사 진행구분 및 제목 조회 */
    DefaultMap<String> getProcFgCheck(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사등록시 상품정보 조회 */
    DefaultMap<String> getAcinsInfo(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사 상세 상품 리스트 조회 */
    List<DefaultMap<String>> getSearchAcinsDtlList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 실사 상세 상품 저장 */
    int saveAcinsDtl(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 TEMP 저장 */
    int saveExcelUpload(StockAcinsVO[] stockAcinsVOS, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int saveUpdateProdCd(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 실패내역 조회 */
    List<DefaultMap<String>> getExcelUploadErrInfoList(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);

    /** 재고실사 - 엑셀업로드 */
    int excelUpload(StockAcinsVO stockAcinsVO, SessionInfoVO sessionInfoVO);
}
