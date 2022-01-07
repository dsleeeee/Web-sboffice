package kr.co.solbipos.mobile.adi.sms.marketingSmsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.mobile.adi.sms.marketingSmsSend.service.MobileMarketingSmsSendService;
import kr.co.solbipos.mobile.adi.sms.marketingSmsSend.service.MobileMarketingSmsSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MobileMarketingSmsSendServiceImpl.java
 * @Description : (모바일) 부가서비스 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.01.06  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.01.06
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mobileMarketingSmsSendService")
@Transactional
public class MobileMarketingSmsSendServiceImpl implements MobileMarketingSmsSendService {
    private final MobileMarketingSmsSendMapper mobileMarketingSmsSendMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MobileMarketingSmsSendServiceImpl(MobileMarketingSmsSendMapper mobileMarketingSmsSendMapper) { this.mobileMarketingSmsSendMapper = mobileMarketingSmsSendMapper; }

}