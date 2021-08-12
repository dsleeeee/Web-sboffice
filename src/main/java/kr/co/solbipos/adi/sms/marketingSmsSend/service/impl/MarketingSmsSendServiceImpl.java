package kr.co.solbipos.adi.sms.marketingSmsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MarketingSmsSendServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > 마케팅용 SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.08.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("marketingSmsSendService")
@Transactional
public class MarketingSmsSendServiceImpl implements MarketingSmsSendService {
    private final MarketingSmsSendMapper marketingSmsSendMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendServiceImpl(MarketingSmsSendMapper marketingSmsSendMapper) { this.marketingSmsSendMapper = marketingSmsSendMapper; }

    /** 메세지그룹 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMsgGrpColList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return marketingSmsSendMapper.getMsgGrpColList(marketingSmsSendVO);
    }

    /** 메세지관리 - 메세지서식 조회 */
    @Override
    public List<DefaultMap<Object>> getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return marketingSmsSendMapper.getMarketingSmsSendMsgManageDtlList(marketingSmsSendVO);
    }

    /** 마케팅용 SMS전송 - 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return marketingSmsSendMapper.getMarketingSmsSendList(marketingSmsSendVO);
    }
}