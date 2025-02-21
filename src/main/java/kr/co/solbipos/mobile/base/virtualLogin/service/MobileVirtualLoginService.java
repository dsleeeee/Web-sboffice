package kr.co.solbipos.mobile.base.virtualLogin.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginService.java
 * @Description : 모바일 > 기초관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileVirtualLoginService {

    /** (모바일)가상로그인 - 조회 */
    List<DefaultMap<String>> getMobileVirtualLoginList(MobileVirtualLoginVO mobileVirtualLoginVO, SessionInfoVO sessionInfoVO);

    /** (모바일)가상로그인 - 권한 조회 */
    int checkMobileVirtualLoginAuth(String userId);

    /** (모바일)가상로그인 - 권한 조회 */
    int checkMobileVirtualLoginAuthCheck(MobileVirtualLoginVO mobileVirtualLoginVO, SessionInfoVO sessionInfoVO);

    /** (모바일)가상로그인 - 이력 생성 */
    int insertMobileLoginHistory(SessionInfoVO sessionInfoVO);
}
