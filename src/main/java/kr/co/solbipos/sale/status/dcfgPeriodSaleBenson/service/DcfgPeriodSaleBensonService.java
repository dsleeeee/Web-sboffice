package kr.co.solbipos.sale.status.dcfgPeriodSaleBenson.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : DcfgPeriodSaleBensonService.java
 * @Description : 벤슨 > 매출분석 > 할인구분기간상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.20  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.20
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface DcfgPeriodSaleBensonService {

    /**
     * 할인구분기간상세 리스트 조회
     */
    List<DefaultMap<String>> getDcfgPeriodSaleBensonList(DcfgPeriodSaleBensonVO dcfgPeriodSaleBensonVO, SessionInfoVO sessionInfoVO);
}
