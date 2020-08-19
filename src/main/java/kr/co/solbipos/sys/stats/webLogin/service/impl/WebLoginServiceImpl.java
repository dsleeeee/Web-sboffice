package kr.co.solbipos.sys.stats.webLogin.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginService;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : WebLoginServiceImpl.java
 * @Description : 시스템관리 > 통계 > 웹로그인 현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.06.01  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.06.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("WebLoginServiceImpl")
@Transactional
public class WebLoginServiceImpl implements WebLoginService {
    private final WebLoginMapper webLoginMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public WebLoginServiceImpl(WebLoginMapper webLoginMapper) {
        this.webLoginMapper = webLoginMapper;
    }

    /** 웹로그인 현황 조회 */
    @Override
    public List<DefaultMap<Object>> getWebLoginList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO) {

        return webLoginMapper.getWebLoginList(webLoginVO);
    }

    /** 일자별 현황 조회 */
    @Override
    public List<DefaultMap<Object>> getWebLoginDayDetailList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO) {

        return webLoginMapper.getWebLoginDayDetailList(webLoginVO);
    }

    /** 로그인 현황 조회 */
    @Override
    public List<DefaultMap<Object>> getWebLoginLoginDetailList(WebLoginVO webLoginVO, SessionInfoVO sessionInfoVO) {

        return webLoginMapper.getWebLoginLoginDetailList(webLoginVO);
    }
}