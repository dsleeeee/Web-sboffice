package kr.co.solbipos.adi.sms.marketingSmsSend.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.DateUtil;
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
    private final MessageService messageService;

    /**
     * Constructor Injection
     */
    @Autowired
    public MarketingSmsSendServiceImpl(MarketingSmsSendMapper marketingSmsSendMapper, SmsSendMapper smsSendMapper, PopupMapper popupMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.marketingSmsSendMapper = marketingSmsSendMapper;
        this.smsSendMapper = smsSendMapper; // SMS전송 팝업
        this.popupMapper = popupMapper;
        this.cmmEnvUtil = cmmEnvUtil;
        this.messageService = messageService;
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

    /** 발신번호추가2 팝업 - 본인인증 통과 시 DI 저장 */
    @Override
    public int updateAddSmsNoDi(MarketingSmsSendVO marketingSmsSendVO) {
        return marketingSmsSendMapper.updateAddSmsNoDi(marketingSmsSendVO);
    }

    /**
     * KCP 거래의 주문번호와 요청자 정보로 본인인증 대기 행을 저장한다.
     */
    @Override
    public int saveVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();
        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());
        return marketingSmsSendMapper.saveVerify(marketingSmsSendVO);
    }

    /**
     * KCP 인증 결과를 기존 인증 대기 행에 갱신한다.
     */
    @Override
    public int updateVerify(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());

        return marketingSmsSendMapper.updateVerify(marketingSmsSendVO);
    }

    /**
     * 인증 결과와 DI를 {@link Transactional}로 함께 저장한다.
     */
    @Override
    public int completeVerify2(MarketingSmsSendVO verifyVO, MarketingSmsSendVO diSaveVO,
                               SessionInfoVO sessionInfoVO) {
        String currentDt = currentDateTimeString();
        verifyVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        verifyVO.setModDt(currentDt);
        verifyVO.setModId(sessionInfoVO.getUserId());

        // 인증 이력 저장
        if (marketingSmsSendMapper.updateVerify(verifyVO) != 1) {
            // RuntimeException을 던져 아래 DI 갱신을 실행하지 않고 현재 트랜잭션을 롤백한다.
            throw new IllegalStateException("본인인증 이력 저장에 실패했습니다.");
        }

        diSaveVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        diSaveVO.setUserId(sessionInfoVO.getUserId());
        // DI 저장
        if (marketingSmsSendMapper.updateAddSmsNoDi(diSaveVO) != 1) {
            // 두 번째 갱신 실패도 RuntimeException으로 처리해 바로 앞 인증이력 갱신까지 함께 롤백한다.
            throw new IllegalStateException("본인인증 DI 저장에 실패했습니다.");
        }
        return 1;
    }

    /** 마케팅용 SMS전송 - 발신번호 공통코드에 등록되 있는지 확인(특수부가사업자 승인 전 임시사용) */
//    @Override
//    public DefaultMap<String> getTelNoNmCodeChk(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
//
//        return marketingSmsSendMapper.getTelNoNmCodeChk(marketingSmsSendVO);
//    }

    /** 전송 URL 관리(화이트리스트 등록요청) - 본인 요청 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getRegSendUrlList(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {
        marketingSmsSendVO.setUserId(sessionInfoVO.getUserId());
        return marketingSmsSendMapper.getRegSendUrlList(marketingSmsSendVO);
    }

    /** 전송 URL 관리(화이트리스트 등록요청) - 요청 등록 */
    @Override
    public int saveRegSendUrl(MarketingSmsSendVO marketingSmsSendVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        marketingSmsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        marketingSmsSendVO.setUserId(sessionInfoVO.getUserId());
        marketingSmsSendVO.setApprFg("0"); // 요청

        // 사용종료일자 = 사용시작일자 + 90일
        marketingSmsSendVO.setUseEndDate(DateUtil.addDaysString(marketingSmsSendVO.getUseStartDate(), 90));

        marketingSmsSendVO.setRegDt(currentDt);
        marketingSmsSendVO.setRegId(sessionInfoVO.getUserId());
        marketingSmsSendVO.setModDt(currentDt);
        marketingSmsSendVO.setModId(sessionInfoVO.getUserId());

        // url 중복 요청 확인
        int urlDupChk = marketingSmsSendMapper.getChkSendUrl(marketingSmsSendVO);

        // url 있을 시 오류 반환
        if(urlDupChk > 0){
            throw new JsonException(Status.SERVER_ERROR, messageService.get("marketingSmsSend.dypChkSendUrl"));
        }

        return marketingSmsSendMapper.insertRegSendUrl(marketingSmsSendVO);
    }
}
