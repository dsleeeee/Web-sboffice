package kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.AlimtalkSendHistService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendHist.service.AlimtalkSendHistVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AlimtalkSendHistServiceImpl.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송이력
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.03.30  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2022.03.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("alimtalkSendHistService")
@Transactional
public class AlimtalkSendHistServiceImpl implements AlimtalkSendHistService {
    private final AlimtalkSendHistMapper alimtalkSendHistMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendHistServiceImpl(AlimtalkSendHistMapper alimtalkSendHistMapper) { this.alimtalkSendHistMapper = alimtalkSendHistMapper; }

    /** 알림톡 전송이력 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkSendHistList(AlimtalkSendHistVO alimtalkSendHistVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        alimtalkSendHistVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        alimtalkSendHistVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ ){
            // 매장 array 값 세팅
            String[] storeCds = alimtalkSendHistVO.getStoreCds().split(",");
            alimtalkSendHistVO.setStoreCdList(storeCds);
        }

        return alimtalkSendHistMapper.getAlimtalkSendHistList(alimtalkSendHistVO);
    }

    /** 알림톡 수신자정보 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkAddresseeDtlList(AlimtalkSendHistVO alimtalkSendHistVO, SessionInfoVO sessionInfoVO) {

        return alimtalkSendHistMapper.getAlimtalkAddresseeDtlList(alimtalkSendHistVO);
    }
}