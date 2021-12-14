package kr.co.solbipos.adi.sms.marketingSmsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendService;
import kr.co.solbipos.adi.sms.marketingSmsSend.service.MarketingSmsSendVO;
import kr.co.solbipos.adi.sms.smsSend.service.impl.SmsSendMapper;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final SmsSendMapper smsSendMapper; // SMS전송 팝업

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendServiceImpl(MarketingSmsSendMapper marketingSmsSendMapper, SmsSendMapper smsSendMapper) {
        this.marketingSmsSendMapper = marketingSmsSendMapper;
        this.smsSendMapper = smsSendMapper; // SMS전송 팝업
    }

    /** 메세지그룹 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMsgGrpColList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return marketingSmsSendMapper.getMsgGrpColList(marketingSmsSendVO);
    }

    /** 회원등급 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMembrClassList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        List<DefaultMap<String>> resultList = marketingSmsSendMapper.getMembrClassList(marketingSmsSendVO);

        // 등록된 회원등급이 없을때는 기본등급을 리스트에 넣어줌.
        if (resultList.size() == 0) {
            DefaultMap<String> tmpList = new DefaultMap<String>();
            tmpList.put("value", "000");
            tmpList.put("name", "기본등급");
            resultList.add(tmpList);
        }
        return resultList;
    }

    /** 메세지관리 - 메세지서식 조회(최근이력) */
    @Override
    public List<DefaultMap<Object>> getMarketingSmsSendMsgManageDtlList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        marketingSmsSendVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return marketingSmsSendMapper.getMarketingSmsSendMsgManageDtlList(marketingSmsSendVO);
    }

    /** 마케팅용 SMS전송 - 회원 조회 */
    @Override
    public List<DefaultMap<Object>> getMarketingSmsSendList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        return marketingSmsSendMapper.getMarketingSmsSendList(marketingSmsSendVO);
    }

    /** 마케팅용 SMS전송 - 검색 결과 저장 */
    @Override
    public String getMarketingSmsSendListSave(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        // 전송이력시퀀스
        String smsSendSeq = smsSendMapper.getSmsSendSeq(sessionInfoVO);
        marketingSmsSendVO.setSmsSendSeq(smsSendSeq);

        marketingSmsSendVO.setRegDt(currentDt);
        marketingSmsSendVO.setRegId(sessionInfoVO.getUserId());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());

        marketingSmsSendVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        marketingSmsSendVO.setOrgnGrpCd(sessionInfoVO.getOrgnGrpCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) { // 본사
            if (!StringUtil.isEmpties(marketingSmsSendVO.getRegStoreCd())) {
                marketingSmsSendVO.setRegStoreCds(marketingSmsSendVO.getRegStoreCd().split(","));
            }
            if (!StringUtil.isEmpties(marketingSmsSendVO.getRegUseStoreCd())) {
                marketingSmsSendVO.setRegUseStoreCds(marketingSmsSendVO.getRegUseStoreCd().split(","));
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            marketingSmsSendVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        procCnt = marketingSmsSendMapper.getMarketingSmsSendListSaveInsert(marketingSmsSendVO);

        return smsSendSeq;
    }

    /** 마케팅용 SMS전송 - 1000건 이상 전송시 전송테이블에 몇건 Insert 됬는지 조회 */
    @Override
    public DefaultMap<String> getSmsSendInsert1000Count(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        return marketingSmsSendMapper.getSmsSendInsert1000Count(marketingSmsSendVO);
    }

    /** 마케팅용 SMS전송 - 본인인증 여부 조회 */
    @Override
    public int getVerifyChk(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        return marketingSmsSendMapper.getVerifyChk(marketingSmsSendVO);
    }

    @Override
    public int saveVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
        return 0;
    }

    @Override
    public int updateVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        int cnt = 0;

        System.out.println("JH : getSmsTelNoManageUpdate 진입");
        String currentDt = currentDateTimeString();

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());

        System.out.println("JH : 본인인증 update 인덱스스");
        System.out.println("JH : 소속코드 : " + marketingSmsSendVO.getOrgnCd());
        System.out.println("JH : 전화번호 : " + marketingSmsSendVO.getTelNo());
        System.out.println("JH : 수정날짜 : " + marketingSmsSendVO.getModDt());
        System.out.println("JH : 수정자 : " + marketingSmsSendVO.getModId());
        System.out.println("JH : 요청번호 : " + marketingSmsSendVO.getCertId());
        cnt = marketingSmsSendMapper.updateVerify(marketingSmsSendVO);
        System.out.println("JH : updateVerify 결과" + cnt);
        return cnt;
    }
}