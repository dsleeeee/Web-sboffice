package kr.co.solbipos.sys.stats.userWebHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistService;
import kr.co.solbipos.sys.stats.userWebHist.service.UserWebHistVO;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : UserWebHistServiceImpl.java
 * @Description : 시스템관리 > 통계 > 사용자웹사용이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.01.15  김유승      최초생성
 *
 * @author 솔비포스 WEB개발팀 김유승
 * @since 2024.01.15
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("UserWebHistServiceImpl")
@Transactional
public class UserWebHistServiceImpl implements UserWebHistService {
    private final UserWebHistMapper userWebHistMapper;

    /**
     * Constructor Injection
     */
    public UserWebHistServiceImpl(UserWebHistMapper userWebHistMapper) {
        this.userWebHistMapper = userWebHistMapper;
    }

    /** 사용자웹사용이력 조회 */
    @Override
    public List<DefaultMap<Object>> getUserWebHistList(UserWebHistVO userWebHistVO, SessionInfoVO sessionInfoVO) {
        return userWebHistMapper.getUserWebHistList(userWebHistVO);
    }
}
