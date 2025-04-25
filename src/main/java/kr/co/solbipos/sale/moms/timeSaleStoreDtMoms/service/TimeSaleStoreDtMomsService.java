package kr.co.solbipos.sale.moms.timeSaleStoreDtMoms.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : TimeSaleStoreDtMomsService.java
 * @Description : 맘스터치 > 간소화화면 > 시간대매출(매장) DT포장
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.04.18  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.04.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface TimeSaleStoreDtMomsService {

    /** 시간대매출(매장) DT포장 - 조회 */
    List<DefaultMap<Object>> getTimeSaleStoreDtMomsList(TimeSaleStoreDtMomsVO timeSaleStoreDtMomsVO, SessionInfoVO sessionInfoVO);

    /** 시간대매출(매장) DT포장 - 분할 엑셀다운로드 조회 */
    List<DefaultMap<Object>> getTimeSaleStoreMomsExcelDivisionList(TimeSaleStoreDtMomsVO timeSaleStoreDtMomsVO, SessionInfoVO sessionInfoVO);
}
