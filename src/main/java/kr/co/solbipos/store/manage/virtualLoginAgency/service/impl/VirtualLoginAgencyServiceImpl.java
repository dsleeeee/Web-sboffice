package kr.co.solbipos.store.manage.virtualLoginAgency.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.store.manage.virtualLoginAgency.service.VirtualLoginAgencyService;
import kr.co.solbipos.store.manage.virtualLoginAgency.service.VirtualLoginAgencyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : VirtualLoginAgencyServiceImpl.java
 * @Description : 기초관리 > 총판/대리점 가상로그인
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.03.31  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.03.31
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("virtualLoginAgencyService")
@Transactional
public class VirtualLoginAgencyServiceImpl implements VirtualLoginAgencyService {
    private final VirtualLoginAgencyMapper virtualLoginAgencyMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public VirtualLoginAgencyServiceImpl(VirtualLoginAgencyMapper virtualLoginAgencyMapper) { this.virtualLoginAgencyMapper = virtualLoginAgencyMapper; }

    /** 총판/대리점 가상로그인 조회 */
    @Override
    public List<DefaultMap<Object>> getVirtualLoginAgencyrList(VirtualLoginAgencyVO virtualLoginAgencyVO, SessionInfoVO sessionInfoVO) {

        return virtualLoginAgencyMapper.getVirtualLoginAgencyrList(virtualLoginAgencyVO);
    }
}