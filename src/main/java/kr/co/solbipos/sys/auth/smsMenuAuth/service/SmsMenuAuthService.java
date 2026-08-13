package kr.co.solbipos.sys.auth.smsMenuAuth.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : SmsMenuAuthService.java
 * @Description : 시스템관리 > 권한관리 > SMS화면관리(관리자)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SmsMenuAuthService {

    /** SMS 메뉴 조회 */
    List<DefaultMap<String>> getSmsMenuList(SessionInfoVO sessionInfoVO, SmsMenuAuthVO smsMenuAuthVO);

    /** SMS 메뉴에 등록된 사용자 조회 */
    List<DefaultMap<String>> getRegUserList(SessionInfoVO sessionInfoVO, SmsMenuAuthVO smsMenuAuthVO);

    /** SMS 메뉴에 미등록된 사용자 조회 */
    List<DefaultMap<String>> getNoRegUserList(SessionInfoVO sessionInfoVO, SmsMenuAuthVO smsMenuAuthVO);

    /** 사용자 SMS 메뉴 권한 등록 */
    int insertSmsMenuAuth(SmsMenuAuthVO[] smsMenuAuthVOS, SessionInfoVO sessionInfoVO);

    /** 사용자 SMS 메뉴 권한 삭제 */
    int deleteSmsMenuAuth(SmsMenuAuthVO[] smsMenuAuthVOS, SessionInfoVO sessionInfoVO);
}
