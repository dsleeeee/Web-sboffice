package kr.co.solbipos.adi.sms.smsCharge.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsChargeService.java
 * @Description : 부가서비스 > SMS관리 > SMS충전/KCP PG
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.09  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SmsChargeService {

    /** 충전결제 저장 */
    int getSmsChargeSaveInsert(SmsChargeVO smsChargeVO);

    /** 결제취소 저장 */
    int getSmsChargeSaveUpdate(SmsChargeVO smsChargeVO);

    /** 메세지 건당 가격안내 팝업 - 조회 */
    DefaultMap<String> getMsgOneAmtGuideList(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 잔여금액 알림 설정 팝업 - 조회 */
    DefaultMap<String> getRestSmsAmtAlimSettingList(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 잔여금액 알림 설정 팝업 - 저장 */
    int getRestSmsAmtAlimSettingSave(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 전송요금설정 - 조회 */
    List<DefaultMap<Object>> getMsgOneAmtSettingList(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 전송요금설정 - 상세 조회 */
    List<DefaultMap<Object>> getMsgOneAmtSettingDetailList(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 전송요금설정 - 상세 저장 */
    int getMsgOneAmtSettingDetailSave(SmsChargeVO[] smsChargeVOs, SessionInfoVO sessionInfoVO);

    /** 전송요금설정 - 건당금액(기본값) 조회 */
    DefaultMap<String> getMsgOneAmtBaseList(SmsChargeVO smsChargeVO, SessionInfoVO sessionInfoVO);

    /** 전송요금설정 - 화면 권한 체크 */
    String msgOneAmtSettingAuth(SessionInfoVO sessionInfoVO);
}