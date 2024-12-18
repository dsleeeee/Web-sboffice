package kr.co.solbipos.application.pos.service;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.storemanage.service.StoreEnvVO;

/**
 * @Class Name : SimpleMemberJoinService.java
 * @Description : POS 화면에서 간편 회원가입
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.08.14  안동관      최초생성
 *
 * @author 솔비포스 차세대개발실 안동관
 * @since 2018. 08.14
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface SimpleMemberJoinService {

    /** 환경변수 값 체크 */
    String getEnvstVal(StoreEnvVO storeEnvVO);

    /** 본사코드 조회 */
    String getHqOfficeCd(StoreEnvVO storeEnvVO);

    /** 회원 저장 */
    int save(MemberVO memberVO, SessionInfoVO sessionInfoVO);

}
