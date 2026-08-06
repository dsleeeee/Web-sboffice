package kr.co.solbipos.adi.sms.smsUserRegist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : SmsUserRegistService.java
 * @Description : 부가서비스 > SMS관리 > SMS 사용 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.03  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.03
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface SmsUserRegistService {

    /** DI로 기존 등록 사용자아이디 조회 (없으면 null) */
    String getUserIdByDi(SmsUserRegistVO smsUserRegistVO);

    /** SMS 사용 등록 - 본인인증 완료 후 저장 (기존 등록 여부에 따라 등록/수정 자동분기) */
    int saveUserRegist(SmsUserRegistVO smsUserRegistVO, SessionInfoVO sessionInfoVO);

    /** 진입시 체크 - 세션 사용자의 등록정보 조회 (없으면 null) */
    DefaultMap<Object> getUserRegistInfo(SessionInfoVO sessionInfoVO);

    /** SMS 사용등록 - SMS사용자 삭제 */
    int deleteUserRegist(SmsUserRegistVO smsUserRegistVO, SessionInfoVO sessionInfoVO);
}
