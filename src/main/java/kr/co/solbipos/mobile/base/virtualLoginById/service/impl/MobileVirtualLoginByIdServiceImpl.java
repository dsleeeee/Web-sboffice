package kr.co.solbipos.mobile.base.virtualLoginById.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.base.virtualLoginById.service.MobileVirtualLoginByIdService;
import kr.co.solbipos.mobile.base.virtualLoginById.service.MobileVirtualLoginByIdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : MobileVirtualLoginByIdServiceImpl.java
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
@Service("MobileVirtualLoginByIdService")
public class MobileVirtualLoginByIdServiceImpl implements MobileVirtualLoginByIdService {

    private final MobileVirtualLoginByIdMapper mobileVirtualLoginByIdMapper;

    /** Constructor Injection */
    @Autowired
    public MobileVirtualLoginByIdServiceImpl(MobileVirtualLoginByIdMapper mobileVirtualLoginByIdMapper) {
        this.mobileVirtualLoginByIdMapper = mobileVirtualLoginByIdMapper;
    }

    /** 가상로그인(아이디별) - 조회 */
    @Override
    public List<DefaultMap<String>> getMobileVirtualLoginByIdList(MobileVirtualLoginByIdVO mobileVirtualLoginByIdVO, SessionInfoVO sessionInfoVO) {

        mobileVirtualLoginByIdVO.setSystemId(sessionInfoVO.getUserId());

        return mobileVirtualLoginByIdMapper.getMobileVirtualLoginByIdList(mobileVirtualLoginByIdVO);
    }
}
