package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByTime.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByTimeService.java
 * @Description : 국민대 > 매출분석 > 시간대별 매출분석(요일별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.01  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SaleAnalysisByTimeService {

    /** 시간대별 매출분석 조회 */
    List<DefaultMap<Object>> getSaleAnalysisByTimeList(SaleAnalysisByTimeVO saleAnalysisByTimeVO, SessionInfoVO sessionInfoVO);
}
