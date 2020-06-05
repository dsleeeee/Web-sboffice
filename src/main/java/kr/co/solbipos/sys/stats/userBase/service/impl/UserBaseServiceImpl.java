package kr.co.solbipos.sys.stats.userBase.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userBase.service.UserBaseService;
import kr.co.solbipos.sys.stats.userBase.service.UserBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : UserBaseServiceImpl.java
 * @Description : 시스템관리 > 통계 > 사용자기준 사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.27  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.27
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("userBaseService")
@Transactional
public class UserBaseServiceImpl implements UserBaseService {
    private final UserBaseMapper userBaseMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public UserBaseServiceImpl(UserBaseMapper userBaseMapper) {
        this.userBaseMapper = userBaseMapper;
    }

    /** 사용자기준 사용현황 조회 */
    @Override
    public List<DefaultMap<Object>> getUserBaseList(UserBaseVO userBaseVO, SessionInfoVO sessionInfoVO) {

        return userBaseMapper.getUserBaseList(userBaseVO);
    }

    /** 사용자기준 사용현황 상세조회(사용자 탭) */
    @Override
    public List<DefaultMap<Object>> getUserList(UserBaseVO userBaseVO, SessionInfoVO sessionInfoVO) {

        return userBaseMapper.getUserList(userBaseVO);
    }

    /** 사용자기준 사용현황 상세조회(사용메뉴 탭) */
    @Override
    public List<DefaultMap<Object>> getUseMenuList(UserBaseVO userBaseVO, SessionInfoVO sessionInfoVO) {

        return userBaseMapper.getUseMenuList(userBaseVO);
    }
}