package kr.co.solbipos.adi.sms.marketingSmsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
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
    private final PopupMapper popupMapper;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendServiceImpl(MarketingSmsSendMapper marketingSmsSendMapper, SmsSendMapper smsSendMapper, PopupMapper popupMapper, CmmEnvUtil cmmEnvUtil) {
        this.marketingSmsSendMapper = marketingSmsSendMapper;
        this.smsSendMapper = smsSendMapper; // SMS전송 팝업
        this.popupMapper = popupMapper;
        this.cmmEnvUtil = cmmEnvUtil;
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

        // 회원등급 관리구분
        String membrClassManageFg = CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1237"), "1");

        marketingSmsSendVO.setMembrOrgnFg(sessionInfoVO.getOrgnFg());
        marketingSmsSendVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            marketingSmsSendVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        marketingSmsSendVO.setMembrClassManageFg(membrClassManageFg);
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
            StoreVO storeVO = new StoreVO();
            if(!StringUtil.getOrBlank(marketingSmsSendVO.getRegStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(marketingSmsSendVO.getRegStoreCd(), 3900));
                marketingSmsSendVO.setRegStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }

            if(!StringUtil.getOrBlank(marketingSmsSendVO.getRegUseStoreCd()).equals("")) {
               storeVO.setArrSplitStoreCd(CmmUtil.splitText(marketingSmsSendVO.getRegUseStoreCd(), 3900));
                marketingSmsSendVO.setRegUseStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
            }
        }

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            marketingSmsSendVO.setStoreCd(sessionInfoVO.getStoreCd());

            // 자기매장 회원만 보이게
            marketingSmsSendVO.setStoreMembr("true");
        }

        // 비매출회원SMS전송여부
        if ( "H".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            marketingSmsSendVO.setEnvst1273(CmmUtil.nvl(cmmEnvUtil.getHqEnvst(sessionInfoVO, "1273"), "0"));
        } else if ( "S".equals(sessionInfoVO.getOrgnFg().getCode()) ) {
            marketingSmsSendVO.setEnvst1273(CmmUtil.nvl(cmmEnvUtil.getStoreEnvst(sessionInfoVO, "1273"), "0"));
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

    /** 본인인증 요청 저장 */
    @Override
    public int saveVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();
        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());
        return marketingSmsSendMapper.saveVerify(marketingSmsSendVO);
    }

    /** 본인인증 요청 결과 update */
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

    /** 마케팅용 SMS전송 - 발신번호 공통코드에 등록되 있는지 확인(특수부가사업자 승인 전 임시사용) */
//    @Override
//    public DefaultMap<String> getTelNoNmCodeChk(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
//
//        return marketingSmsSendMapper.getTelNoNmCodeChk(marketingSmsSendVO);
//    }
}