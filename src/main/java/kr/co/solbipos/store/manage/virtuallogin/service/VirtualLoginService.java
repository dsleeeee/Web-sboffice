package kr.co.solbipos.store.manage.virtuallogin.service;

import java.util.List;
import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

/**
 * @Class Name : VirtualLoginService.java
 * @Description : 가맹점관리 > 매장관리 > 가상 로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.06.15  노현수      최초생성
 *
 * @author 솔비포스 차세대개발실 노현수
 * @since 2018. 05.01
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VirtualLoginService {

    /** 가상로그인 목록 조회 */
    List<DefaultMap<String>> getVirtualLoginList(VirtualLoginVO virtualLoginVO, SessionInfoVO sessionInfoVO);

    /** 가상로그인 권한 조회 */
    int checkVirtualLoginAuth(String userId);

    /** 가상로그인 권한 조회 */
    int checkVirtualLoginAuthCheck(VirtualLoginVO virtualLoginVO, SessionInfoVO sessionInfoVO);

    /** 가상로그인 이력 생성 */
    int insertLoginHistory(SessionInfoVO sessionInfoVO);

}
