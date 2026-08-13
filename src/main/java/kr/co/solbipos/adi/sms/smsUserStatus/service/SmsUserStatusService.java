package kr.co.solbipos.adi.sms.smsUserStatus.service;

import kr.co.common.data.structure.DefaultMap;

import java.util.List;

/**
 * @Class Name : SmsUserStatusService.java
 * @Description : 부가서비스 > SMS관리 > SMS사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.07  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.07
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SmsUserStatusService {

    /** SMS사용자 - 목록 조회 */
    List<DefaultMap<Object>> getSmsUserList(SmsUserStatusVO smsUserStatusVO);

    /** 발신번호 - 목록 조회 */
    List<DefaultMap<Object>> getSendTelNoList(SmsUserStatusVO smsUserStatusVO);

    /** 충전현황 - 목록 조회 */
    List<DefaultMap<Object>> getSmsChargeStatusList(SmsUserStatusVO smsUserStatusVO);

    /** 전송이력 - 목록 조회 */
    List<DefaultMap<Object>> getSmsSendHistList(SmsUserStatusVO smsUserStatusVO);
}
