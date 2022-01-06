package kr.co.solbipos.mobile.adi.sms.sendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.sms.sendStatus.service.MobileSendStatusService;
import kr.co.solbipos.mobile.adi.sms.sendStatus.service.MobileSendStatusVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileSendStatusServiceImpl.java
 * @Description : (모바일) 부가서비스 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.04  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.01.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileSendStatusService")
@Transactional
public class MobileSendStatusServiceImpl implements MobileSendStatusService {
    private final MobileSendStatusMapper mobileSendStatusMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileSendStatusServiceImpl(MobileSendStatusMapper mobileSendStatusMapper) { this.mobileSendStatusMapper = mobileSendStatusMapper; }

}