package kr.co.solbipos.mobile.application.direct.service.impl;

import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.application.direct.service.MobileDirectService;
import kr.co.solbipos.mobile.application.direct.service.MobileDirectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class Name : MobileDirectServiceImpl.java
 * @Description : (모바일) 매출현황 > 일별매출현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.11.26  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2021.11.26
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileDirectService")
@Transactional
public class MobileDirectServiceImpl implements MobileDirectService {
    private final MobileDirectMapper mobileDirectMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileDirectServiceImpl(MobileDirectMapper mobileDirectMapper) {
        this.mobileDirectMapper = mobileDirectMapper;
    }

    @Override
    public String getAccess(MobileDirectVO mobileDirectVO, SessionInfoVO sessionInfoVO) {
        return mobileDirectMapper.getAccess(mobileDirectVO);
    }
}