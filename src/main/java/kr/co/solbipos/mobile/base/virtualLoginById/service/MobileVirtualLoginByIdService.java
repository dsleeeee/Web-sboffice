package kr.co.solbipos.mobile.base.virtualLoginById.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginByIdService.java
 * @Description : 기초관리_모바일 > 가상로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.07.10  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2025.07.10
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface MobileVirtualLoginByIdService {

    /** 가상로그인(아이디별) - 조회 */
    List<DefaultMap<String>> getMobileVirtualLoginByIdList(MobileVirtualLoginByIdVO mobileVirtualLoginByIdVO, SessionInfoVO sessionInfoVO);
}
