package kr.co.solbipos.adi.sms.smsBadword.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsBadwordService.java
 * @Description : 부가서비스 > SMS관리 > SMS금칙어(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.01  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SmsBadwordService {

    /** 금칙어관리 - 목록 조회 */
    List<DefaultMap<Object>> getBadwordManageList(SmsBadwordVO smsBadwordVO, SessionInfoVO sessionInfoVO);

    /** 금칙어관리 - 저장 */
    int saveBadwordManage(SmsBadwordVO[] smsBadwordVOs, SessionInfoVO sessionInfoVO);

    /** 탐지/차단 결과 로그 - 목록 조회 */
    List<DefaultMap<Object>> getMsgBlockLogList(SmsBadwordVO smsBadwordVO);
}
