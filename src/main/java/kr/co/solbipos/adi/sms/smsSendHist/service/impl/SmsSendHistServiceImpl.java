package kr.co.solbipos.adi.sms.smsSendHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsSendHist.service.SmsSendHistService;
import kr.co.solbipos.adi.sms.smsSendHist.service.SmsSendHistVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsSendHistServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.05  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.05
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsSendHistService")
@Transactional
public class SmsSendHistServiceImpl implements SmsSendHistService {
    private final SmsSendHistMapper smsSendHistMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendHistServiceImpl(SmsSendHistMapper smsSendHistMapper) {
        this.smsSendHistMapper = smsSendHistMapper;
    }

    /** SMS전송이력 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsSendHistList(SmsSendHistVO smsSendHistVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        smsSendHistVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        smsSendHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = smsSendHistVO.getStoreCds().split(",");
            smsSendHistVO.setStoreCdList(storeCds);
        }

        return smsSendHistMapper.getSmsSendHistList(smsSendHistVO);
    }


    /** 수신자정보 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAddresseeDtlList(SmsSendHistVO smsSendHistVO, SessionInfoVO sessionInfoVO) {

        return smsSendHistMapper.getAddresseeDtlList(smsSendHistVO);
    }
}