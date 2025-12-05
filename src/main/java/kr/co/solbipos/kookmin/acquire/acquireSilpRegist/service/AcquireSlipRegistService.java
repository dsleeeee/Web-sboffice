package kr.co.solbipos.kookmin.acquire.acquireSilpRegist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.common.popup.selectStore.service.SelectStoreVO;
import kr.co.solbipos.iostock.cmm.service.IostockCmmVO;
import kr.co.solbipos.iostock.cmmExcelUpload.excelUploadMPS.service.ExcelUploadMPSVO;

import java.util.List;
/**
 * @Class Name  : AcquireSlipRegistService.java
 * @Description : 국민대 > 매입관리 > 매입전표등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.11.21  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.11.21
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface AcquireSlipRegistService {

    /** 매입전표등록 - 거래처 입고/반출 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출정보 상세 조회 */
    DefaultMap<String> getInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출정보 저장 */
    DefaultMap<String> saveInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출정보 삭제 */
    int deleteInOutStockSlipInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출정보 진행상태 변경 */
    int saveInOutStockProcFg(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 진행구분 조회 */
    DefaultMap<String> getChkProcFg(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 저장 */
    int saveInOutStockProd(AcquireSlipRegistVO[] acquireSlipRegistVOs, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출상품 등록 리스트 조회 */
    List<DefaultMap<String>> getSearchInOutStockRegList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매장 리스트 조회 */
    List<DefaultMap<String>> getAcquireSelectStoreList(SelectStoreVO selectStoreVO, SessionInfoVO sessionInfoVO);

    /** 거래처 리스트 조회 */
    List<DefaultMap<String>> getAcquireSelectVendrList(IostockCmmVO iostockCmmVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 현재 세션ID 와 동일한 데이터 삭제 */
    int deleteExcelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 TEMP 저장 */
    int saveExcelUpload(ExcelUploadMPSVO[] excelUploadMPSVOs, SessionInfoVO sessionInfoVO);

    /** 엑셀업로드 - 엑셀업로드 된 데이터를 기반으로 상품코드 업데이트 */
    int saveUpdateProdCd(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 엑셀업로드 */
    int excelUpload(ExcelUploadMPSVO excelUploadMPSVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 반출서 반출정보 조회(반출처, 공급자 정보) */
    DefaultMap<String> getInOutStockInfo(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);

    /** 매입전표등록 - 입고/반출상품 리스트 조회 */
    List<DefaultMap<String>> getInOutStockProdList(AcquireSlipRegistVO acquireSlipRegistVO, SessionInfoVO sessionInfoVO);
}
