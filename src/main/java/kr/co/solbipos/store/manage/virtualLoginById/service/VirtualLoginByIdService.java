package kr.co.solbipos.store.manage.virtualLoginById.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name : VirtualLoginByIdService.java
 * @Description : 기초관리 > 가상로그인 > 가상 로그인(아이디별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.02.06  김유승      최초생성
 *
 * @author 링크 WEB개발팀 김유승
 * @since 2025.02.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
public interface VirtualLoginByIdService {

    /** 가상로그인(아이디별) - 조회 */
    List<DefaultMap<String>> getVirtualLoginByIdList(VirtualLoginByIdVO virtualLoginByIdVO, SessionInfoVO sessionInfoVO);
}
