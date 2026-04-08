package kr.co.solbipos.sale.status.prepaidCardStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : PrepaidCardStatusService.java
 * @Description :  맘스터치 > 매출분석2 > 선불카드현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.02  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.04.02
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface PrepaidCardStatusService {

    /** 선불카드 충전 현황 - 조회 */
    List<DefaultMap<Object>> getPrepaidCardChargeStatus(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO);

    /** 선불카드 사용 현황 - 조회 */
    List<DefaultMap<Object>> getPrepaidCardUseStatus(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO);

    /** 선불카드 충전 현황 - 상세 조회 */
    List<DefaultMap<Object>> getPrepaidCardChargeStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO);

    /** 선불카드 사용 현황 - 상세 조회 */
    List<DefaultMap<Object>> getPrepaidCardUseStatusDtl(PrepaidCardStatusVO prepaidCardStatusVO, SessionInfoVO sessionInfoVO);
}
