package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : AlimtalkSendStatusService.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface AlimtalkSendStatusService {

    /** 알림톡 전송결과 - 조회 */
    List<DefaultMap<Object>> getAlimtalkSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, SessionInfoVO sessionInfoVO);

    /** 알림톡 전송결과 - 예약취소 */
    int getAlimtalkSendStatusReserveCancelSave(AlimtalkSendStatusVO[] alimtalkSendStatusVOs, SessionInfoVO sessionInfoVO);

    /** 알림톡 일자별 전송현황 - 조회 */
    List<DefaultMap<Object>> getAlimtalkDaySendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, SessionInfoVO sessionInfoVO);
}