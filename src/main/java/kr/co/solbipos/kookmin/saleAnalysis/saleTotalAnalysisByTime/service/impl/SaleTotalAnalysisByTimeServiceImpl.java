package kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service.SaleTotalAnalysisByTimeService;
import kr.co.solbipos.kookmin.saleAnalysis.saleTotalAnalysisByTime.service.SaleTotalAnalysisByTimeVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name  : SaleTotalAnalysisByTimeServiceImpl.java
 * @Description : 국민대 > 매출분석 > 시간대 매출합계분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.13  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.13
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("SaleTotalAnalysisByTimeService")
@Transactional
public class SaleTotalAnalysisByTimeServiceImpl implements SaleTotalAnalysisByTimeService {

    private final SaleTotalAnalysisByTimeMapper saleTotalAnalysisByTimeMapper;

    /**
     * Constructor Injection
     */
    public SaleTotalAnalysisByTimeServiceImpl(SaleTotalAnalysisByTimeMapper saleTotalAnalysisByTimeMapper) {
        this.saleTotalAnalysisByTimeMapper = saleTotalAnalysisByTimeMapper;
    }

    /** 시간대 매출합계분석 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleTotalAnalysisByTimeList(SaleTotalAnalysisByTimeVO saleTotalAnalysisByTimeVO, SessionInfoVO sessionInfoVO) {
        saleTotalAnalysisByTimeVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        saleTotalAnalysisByTimeVO.setYoilList(saleTotalAnalysisByTimeVO.getYoil().split(","));
        return saleTotalAnalysisByTimeMapper.getSaleTotalAnalysisByTimeList(saleTotalAnalysisByTimeVO);
    }
}
