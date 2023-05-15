package kr.co.solbipos.store.manage.pwdManageStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.pwdManageStore.service.PwdManageStoreService;
import kr.co.solbipos.store.manage.pwdManageStore.service.PwdManageStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : PwdManageStoreServiceImpl.java
 * @Description : 기초관리 > 비밀번호 임의변경 > 비밀번호 임의변경(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.05.11  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.05.11
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("pwdManageStoreService")
@Transactional
public class PwdManageStoreServiceImpl implements PwdManageStoreService {
    private final PwdManageStoreMapper pwdManageStoreMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PwdManageStoreServiceImpl(PwdManageStoreMapper pwdManageStoreMapper) { this.pwdManageStoreMapper = pwdManageStoreMapper; }

    /** 비밀번호 임의변경(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getPwdManageStoreList(PwdManageStoreVO pwdManageStoreVO, SessionInfoVO sessionInfoVO) {

        pwdManageStoreVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        pwdManageStoreVO.setUserId(sessionInfoVO.getUserId());

        return pwdManageStoreMapper.getPwdManageStoreList(pwdManageStoreVO);
    }

}