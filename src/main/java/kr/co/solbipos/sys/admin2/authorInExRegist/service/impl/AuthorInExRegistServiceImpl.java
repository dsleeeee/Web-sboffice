package kr.co.solbipos.sys.admin2.authorInExRegist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin2.authorInExRegist.service.AuthorInExRegistService;
import kr.co.solbipos.sys.admin2.authorInExRegist.service.AuthorInExRegistVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name  : AuthorInExRegistServiceImpl.java
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
@Service("AuthorInExRegistService")
@Transactional
public class AuthorInExRegistServiceImpl implements AuthorInExRegistService {

    private final AuthorInExRegistMapper authorInExRegistMapper;


    @Autowired
    public AuthorInExRegistServiceImpl(AuthorInExRegistMapper authorInExRegistMapper) {
        this.authorInExRegistMapper = authorInExRegistMapper;
    }

    /** 사용자기준 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchUserInfoDtlList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchUserInfoDtlList(authorInExRegistVO);
    }

    /** 사용자기준 탭 - 등록메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSearchUserRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchUserRegMenuList(authorInExRegistVO);
    }

    /** 사용자기준 탭 - 등록메뉴 삭제 */
    @Override
    public int deleteUserMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(AuthorInExRegistVO authorInExRegistVO : authorInExRegistVOS ){
            // 등록메뉴 삭제
            result = authorInExRegistMapper.deleteUserMenu(authorInExRegistVO);
        }

        return result;
    }

    /** 사용자기준 탭 - 미등록메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSearchUserNoRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchUserNoRegMenuList(authorInExRegistVO);
    }

    /** 사용자기준 탭 - 메뉴 등록 */
    @Override
    public int insertUserMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for(AuthorInExRegistVO authorInExRegistVO : authorInExRegistVOS ){

            authorInExRegistVO.setRegId(sessionInfoVO.getUserId());
            authorInExRegistVO.setRegDt(currentDt);
            authorInExRegistVO.setModId(sessionInfoVO.getUserId());
            authorInExRegistVO.setModDt(currentDt);

            // 로그인 구분
            authorInExRegistVO.setLoginFg("W");

            // 메뉴 등록
            result = authorInExRegistMapper.insertUserMenu(authorInExRegistVO);
        }

        return result;
    }

    /** 소속기준 탭 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchOrgnInfoDtlList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchOrgnInfoDtlList(authorInExRegistVO);
    }

    /** 소속기준 탭 - 등록메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSearchOrgnRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchOrgnRegMenuList(authorInExRegistVO);
    }

    /** 소속기준 탭 - 미등록메뉴 조회 */
    @Override
    public List<DefaultMap<String>> getSearchOrgnNoRegMenuList(SessionInfoVO sessionInfoVO, AuthorInExRegistVO authorInExRegistVO) {
        return authorInExRegistMapper.getSearchOrgnNoRegMenuList(authorInExRegistVO);
    }

    /** 소속기준 탭 - 메뉴 등록 */
    @Override
    public int insertOrgnMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;
        String currentDt = currentDateTimeString();

        for(AuthorInExRegistVO authorInExRegistVO : authorInExRegistVOS ){

            authorInExRegistVO.setRegId(sessionInfoVO.getUserId());
            authorInExRegistVO.setRegDt(currentDt);
            authorInExRegistVO.setModId(sessionInfoVO.getUserId());
            authorInExRegistVO.setModDt(currentDt);

            // 포함여부, 사용여부, 로그인 구분
            authorInExRegistVO.setLoginFg("W");

            // 메뉴 등록
            result = authorInExRegistMapper.insertOrgnMenu(authorInExRegistVO);
        }

        return result;
    }

    /** 소속기준 탭 - 등록메뉴 삭제 */
    @Override
    public int deleteOrgnMenu(AuthorInExRegistVO[] authorInExRegistVOS, SessionInfoVO sessionInfoVO) {
        int result = 0;

        for(AuthorInExRegistVO authorInExRegistVO : authorInExRegistVOS ){
            // 등록메뉴 삭제
            result = authorInExRegistMapper.deleteOrgnMenu(authorInExRegistVO);
        }

        return result;
    }
}
