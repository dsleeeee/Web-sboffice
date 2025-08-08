package kr.co.solbipos.sale.marketing.saleByTime.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
/**
 * @Class Name  : SaleByTimeService.java
 * @Description : 미스터피자 > 마케팅조회 > 시간대별매출
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.08.07  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SaleByTimeService {

    /** 시간대별매출 - 조회 */
    List<DefaultMap<Object>> getSaleByTimeList(SaleByTimeVO saleByTimeVO, SessionInfoVO sessionInfoVO);
}
