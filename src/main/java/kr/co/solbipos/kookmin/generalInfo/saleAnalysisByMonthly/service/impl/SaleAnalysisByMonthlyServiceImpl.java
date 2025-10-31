package kr.co.solbipos.kookmin.generalInfo.saleAnalysisByMonthly.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.generalInfo.dailySaleReport.service.DailySaleReportVO;
import kr.co.solbipos.kookmin.generalInfo.saleAnalysisByMonthly.service.SaleAnalysisByMonthlyService;
import kr.co.solbipos.kookmin.generalInfo.saleAnalysisByMonthly.service.SaleAnalysisByMonthlyVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : SaleAnalysisByMonthlyServiceImpl.java
 * @Description : 국민대 > 매출분석 > 월별매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.10.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("SaleAnalysisByMonthlyService")
@Transactional
public class SaleAnalysisByMonthlyServiceImpl implements SaleAnalysisByMonthlyService {

    private final SaleAnalysisByMonthlyMapper saleAnalysisByMonthlyMapper;

    /**
     * Constructor Injection
     */
    public SaleAnalysisByMonthlyServiceImpl(SaleAnalysisByMonthlyMapper saleAnalysisByMonthlyMapper) {
        this.saleAnalysisByMonthlyMapper = saleAnalysisByMonthlyMapper;
    }

    /** 월별매출분석 리스트 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleAnalysisByMonthlyList(SaleAnalysisByMonthlyVO saleAnalysisByMonthlyVO, SessionInfoVO sessionInfoVO) {
        saleAnalysisByMonthlyVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        return saleAnalysisByMonthlyMapper.getSaleAnalysisByMonthlyList(saleAnalysisByMonthlyVO);
    }

}
