package kr.co.solbipos.sys.admin2.authorInExRegist.service;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;

import java.util.List;

/**
 * @Class Name  : AuthorInExRegistService.java
 * @Description : 시스템관리 > 관리자기능2 > 메뉴권한임의등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.04.17  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.04.17
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
public interface AuthorInExRegistService {

    /** 사용자기준 탭 - 조회 */
    List<DefaultMap<String>> getSearchUserInfoDtlList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 사용자기준 탭 - 등록메뉴 조회 */
    List<DefaultMap<String>> getSearchUserRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 사용자기준 탭 - 등록메뉴 삭제 */
    int deleteUserMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO);

    /** 사용자기준 탭 - 미등록메뉴 조회 */
    List<DefaultMap<String>> getSearchUserNoRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 사용자기준 탭 - 메뉴 등록 */
    int insertUserMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO);

    /** 소속기준 탭 - 조회 */
    List<DefaultMap<String>> getSearchOrgnInfoDtlList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 소속기준 탭 - 등록메뉴 조회 */
    List<DefaultMap<String>> getSearchOrgnRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 소속기준 탭 - 미등록메뉴 조회 */
    List<DefaultMap<String>> getSearchOrgnNoRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO);

    /** 소속기준 탭 - 메뉴 등록 */
    int insertOrgnMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO);

    /** 소속기준 탭 - 등록메뉴 삭제 */
    int deleteOrgnMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO);
}
