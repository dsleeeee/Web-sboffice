package kr.co.solbipos.sys.stats.menuBase.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.stats.menuBase.service.MenuBaseService;
import kr.co.solbipos.sys.stats.menuBase.service.MenuBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MenuBaseServiceImpl.java
 * @Description : 시스템관리 > 통계 > 메뉴기준 사용현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("menuBaseService")
@Transactional
public class MenuBaseServiceImpl implements MenuBaseService {
    private final MenuBaseMapper menuBaseMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MenuBaseServiceImpl(MenuBaseMapper menuBaseMapper) {
        this.menuBaseMapper = menuBaseMapper;
    }


    /** 메뉴기준 사용현황 조회 */
    @Override
    public List<DefaultMap<Object>> getMenuBaseList(MenuBaseVO menuBaseVO, SessionInfoVO sessionInfoVO) {

        return menuBaseMapper.getMenuBaseList(menuBaseVO);
    }

    /** 메뉴기준 사용현황 상세조회 */
    @Override
    public List<DefaultMap<Object>> getMenuBaseDetailList(MenuBaseVO menuBaseVO, SessionInfoVO sessionInfoVO) {

        return menuBaseMapper.getMenuBaseDetailList(menuBaseVO);
    }
}