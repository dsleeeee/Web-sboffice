package kr.co.solbipos.naverPlace.naverPlace.naverMenuLink.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;
import java.util.Map;

/**
 * @Class Name  : NaverMenuLinkService.java
 * @Description : 네이버플레이스 > 네이버플레이스 > 네이버 메뉴 연동
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.08.19  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.08.19
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface NaverMenuLinkService {

    /**
     * 메뉴(옵션)목록 조회
     */
    List<DefaultMap<Object>> getMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 서브메뉴(옵션) 목록 조회
     */
    List<DefaultMap<Object>> getSubMenuOptionList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 상품목록조회
     */
    List<DefaultMap<Object>> getProdList(NaverMenuLinkVO naverMenuLinkVO, SessionInfoVO sessionInfoVO);

    /**
     * 메뉴 연동·해제
     */
    List<Map<String, Object>> mappingMenuOption(List<Map<String, Object>> mappingList, SessionInfoVO sessionInfoVO);

    /**
     * 서브메뉴(옵션) 연동·해제
     */
    List<Map<String, Object>> mappingSubMenuOption(List<Map<String, Object>> mappingList, SessionInfoVO sessionInfoVO);
}
