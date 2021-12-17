package kr.co.solbipos.sale.status.saleAnalysisReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.sale.status.saleAnalysisReport.service.SaleAnalysisReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Class Name : SaleAnalysisReportMapper.java
 * @Description : 매출관리 > 매출현황2 > 중분류(매출분석) 다운로드(정직유부)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.12.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.12.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Mapper
@Repository
public interface SaleAnalysisReportMapper {

    /** 중분류(매출분석) 다운로드 - 매장 컬럼 리스트 조회 */
    List<DefaultMap<String>> getStorerColList(SaleAnalysisReportVO saleAnalysisReportVO);

    /** 중분류(매출분석) 다운로드 - 조회 */
    List<DefaultMap<Object>> getSaleAnalysisReportList(SaleAnalysisReportVO saleAnalysisReportVO);

    /** 중분류(매출분석) 다운로드 - 조회된 매장 리스트 */
    DefaultMap<String> getSaleAnalysisReportStoreList(SaleAnalysisReportVO saleAnalysisReportVO);
}