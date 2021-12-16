package kr.co.solbipos.sale.status.saleAnalysisReport.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.system.BaseEnv;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.saleAnalysisReport.service.SaleAnalysisReportService;
import kr.co.solbipos.sale.status.saleAnalysisReport.service.SaleAnalysisReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.*;

/**
 * @Class Name : SaleAnalysisReportServiceImpl.java
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
@Service("saleAnalysisReportService")
@Transactional
public class SaleAnalysisReportServiceImpl implements SaleAnalysisReportService {
    private final SaleAnalysisReportMapper saleAnalysisReportMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleAnalysisReportServiceImpl(SaleAnalysisReportMapper saleAnalysisReportMapper) {
        this.saleAnalysisReportMapper = saleAnalysisReportMapper;
    }

    /** 중분류(매출분석) 다운로드 - 매장 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getStorerColList(SaleAnalysisReportVO saleAnalysisReportVO, SessionInfoVO sessionInfoVO) {

        saleAnalysisReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleAnalysisReportMapper.getStorerColList(saleAnalysisReportVO);
    }

    /** 중분류(매출분석) 다운로드 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleAnalysisReportList(SaleAnalysisReportVO saleAnalysisReportVO, SessionInfoVO sessionInfoVO) {

        saleAnalysisReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        saleAnalysisReportVO.setArrStoreCol(saleAnalysisReportVO.getStoreCol().split(",")); // 매장 array 값 세팅

        // 쿼리문 PIVOT IN 에 들어갈 문자열 생성
        String pivotStoreCol = "";
        String arrStoreCol[] = saleAnalysisReportVO.getStoreCol().split(",");
        for(int i=0; i < arrStoreCol.length; i++) {
            pivotStoreCol += (pivotStoreCol.equals("") ? "" : ",") + "'"+arrStoreCol[i]+"'"+" AS STORE_"+arrStoreCol[i];
        }
        saleAnalysisReportVO.setPivotStoreCol(pivotStoreCol);

        return saleAnalysisReportMapper.getSaleAnalysisReportList(saleAnalysisReportVO);
    }

    /** 중분류(매출분석) 다운로드 - 조회된 매장 리스트 */
    @Override
    public DefaultMap<String> getSaleAnalysisReportStoreList(SaleAnalysisReportVO saleAnalysisReportVO, SessionInfoVO sessionInfoVO) {

        saleAnalysisReportVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return saleAnalysisReportMapper.getSaleAnalysisReportStoreList(saleAnalysisReportVO);
    }
}