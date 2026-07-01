package kr.co.solbipos.adi.sms.badword.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;

/**
 * @Class Name : BadwordFilterService.java
 * @Description : 부가서비스 > SMS관리 > SMS전송(탭) > 금칙어 필터링
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.06.23  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.06.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface BadwordFilterService {

    /**
     * 메시지 본문 금칙어 필터링
     * @param msgContent 메시지 본문
     * @return FilterResult (탐지 여부 + 탐지된 금칙어 정보)
     */
    FilterResult check(String msgContent);

    /**
     * 차단 이력 저장 (MESSAGE_BLOCK_LOG INSERT)
     * @param smsSendVO  발송 요청 VO
     * @param result     필터링 결과
     * @param sessionInfoVO 세션 정보
     */
    void saveBlockLog(SmsSendVO smsSendVO, FilterResult result, SessionInfoVO sessionInfoVO);

    /**
     * 캐시된 금칙어 목록 강제 갱신
     */
    void refreshCache();
}
