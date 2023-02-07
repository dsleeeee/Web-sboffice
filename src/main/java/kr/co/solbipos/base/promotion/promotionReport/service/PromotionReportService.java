package kr.co.solbipos.base.promotion.promotionReport.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PromotionReportService.java
 * @Description : 기초관리 > 프로모션관리 > 프로모션정산
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.06  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2023.02.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface PromotionReportService {

    /** 조회 */
    List<DefaultMap<Object>> getPromotionReportList(PromotionReportVO promotionReportVO, SessionInfoVO sessionInfoVO);
    List<DefaultMap<Object>> getPromotionReportExcelList(PromotionReportVO promotionReportVO, SessionInfoVO sessionInfoVO);

}