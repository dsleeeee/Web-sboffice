package kr.co.solbipos.sale.moms.timeProdSalePmixMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.HashMap;
import java.util.List;

/**
 * @Class Name : TimeProdSalePmixMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 시간대별 상품매출(P.MIX)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2025.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeProdSalePmixMomsService {

    /** 시간대별 상품매출(P.MIX) - 조회 */
    List<DefaultMap<Object>> getTimeProdSalePmixMomsList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO, SessionInfoVO sessionInfoVO);

    /** 시간대별 상품매출(P.MIX) - 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeProdSalePmixMomsExcelList(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO, SessionInfoVO sessionInfoVO);

    /** 기간선택 두 날짜 사이 모든날짜 구하기 */
    List<HashMap<String, String>> getDateDiff(TimeProdSalePmixMomsVO timeProdSalePmixMomsVO);
}