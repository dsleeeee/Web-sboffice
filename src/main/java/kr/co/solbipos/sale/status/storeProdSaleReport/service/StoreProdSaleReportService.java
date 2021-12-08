package kr.co.solbipos.sale.status.storeProdSaleReport.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : StoreProdSaleReportService.java
 * @Description : 매출관리 > 매출현황2 > 기간별 매장-상품 매출 다운로드(고봉민)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface StoreProdSaleReportService {

    /** 기간별 매장-상품 매출 다운로드 탭 - 조회 */
    List<DefaultMap<Object>> getStoreProdSaleReportList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 저장 */
    int getStoreProdSaleReportSave(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 삭제 */
    int getStoreProdSaleReportDel(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
    DefaultMap<String> getStoreProdSaleReportChk(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지사 조회 */
    List<DefaultMap<Object>> getBranchAreaList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지사 저장 */
    int getBranchAreaSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지역 조회 */
    List<DefaultMap<Object>> getBranchAreaDetailList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지역 저장 */
    int getBranchAreaDetailSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO);

    /** 지역-매장관리 탭 - 지역 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지역-매장관리 탭 - 지역-매장 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingDetailList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지역-매장관리 탭 - 매장 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingStoreList(StoreProdSaleReportVO storeProdSaleReportVO, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지역-매장 저장 delete */
    int getAreaStoreMappingDetailSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO);

    /** 지사-지역관리 탭 - 지역-매장 저장 */
    int getAreaStoreMappingStoreSave(StoreProdSaleReportVO[] storeProdSaleReportVOs, SessionInfoVO sessionInfoVO);
}