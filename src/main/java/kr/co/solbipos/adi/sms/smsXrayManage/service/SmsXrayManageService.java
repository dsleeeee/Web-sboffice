package kr.co.solbipos.adi.sms.smsXrayManage.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsXrayManageService.java
 * @Description : 부가서비스 > SMS관리 > SMS Xray관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SmsXrayManageService {

    /** SMS Xray관리 - 목록 조회 */
    List<DefaultMap<Object>> getSmsXrayManageList(SmsXrayManageVO smsXrayManageVO, SessionInfoVO sessionInfoVO);

    /** SMS Xray관리 - 저장 */
    int saveSmsXrayManage(SmsXrayManageVO[] smsXrayManageVOs, SessionInfoVO sessionInfoVO);

    /** 탐지/차단결과 로그 - 목록 조회 */
    List<DefaultMap<Object>> getUrlBlockLogList(SmsXrayManageVO smsXrayManageVO, SessionInfoVO sessionInfoVO);
}
