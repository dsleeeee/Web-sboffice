package kr.co.solbipos.sale.status.storeProdSaleReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.storeProdSaleReport.service.StoreProdSaleReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : StoreProdSaleReportMapper.java
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
@Mapper
@Repository
public interface StoreProdSaleReportMapper {

    /** 기간별 매장-상품 매출 다운로드 탭 - 조회 */
    List<DefaultMap<Object>> getStoreProdSaleReportList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 저장 insert */
    int getStoreProdSaleReportSaveInsert(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 삭제 */
    int getStoreProdSaleReportDel(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 기간별 매장-상품 매출 다운로드 탭 - 자료생성 요청건 존재여부 확인 */
    DefaultMap<String> getStoreProdSaleReportChk(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지사 조회 */
    List<DefaultMap<Object>> getBranchAreaList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사코드(자동채번) */
    String getBranchAreaBranchCd(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지사 저장 Insert */
    int getBranchAreaSaveInsert(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지사 저장 update */
    int getBranchAreaSaveUpdate(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지사 저장 delete */
    int getBranchAreaSaveDelete(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지사 delete 시, 지역 delete */
    int getBranchAreaDetailSaveDeleteAll(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역 조회 */
    List<DefaultMap<Object>> getBranchAreaDetailList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지역코드(자동채번) */
    String getBranchAreaAreaCd(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역 저장 Insert */
    int getBranchAreaDetailSaveInsert(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역 저장 update */
    int getBranchAreaDetailSaveUpdate(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역 저장 delete */
    int getBranchAreaDetailSaveDelete(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지역-매장관리 탭 - 지역 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지역-매장관리 탭 - 지역-매장 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingDetailList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지역-매장관리 탭 - 매장 조회 */
    List<DefaultMap<Object>> getAreaStoreMappingStoreList(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역-매장 저장 delete */
    int getAreaStoreMappingDetailSaveDelete(StoreProdSaleReportVO storeProdSaleReportVO);

    /** 지사-지역관리 탭 - 지역-매장 저장 insert */
    int getAreaStoreMappingStoreSaveInsert(StoreProdSaleReportVO storeProdSaleReportVO);
}