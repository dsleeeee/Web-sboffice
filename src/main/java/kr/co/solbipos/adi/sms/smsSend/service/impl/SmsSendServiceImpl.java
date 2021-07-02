package kr.co.solbipos.adi.sms.smsSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendService;
import kr.co.solbipos.adi.sms.smsSend.service.SmsSendVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsSendServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS전송
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.10  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.10
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsSendService")
@Transactional
public class SmsSendServiceImpl implements SmsSendService {
    private final SmsSendMapper smsSendMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsSendServiceImpl(SmsSendMapper smsSendMapper) {
        this.smsSendMapper = smsSendMapper;
    }

    /** 발신번호 유무 체크 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoComboList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsSendMapper.getSmsTelNoComboList(smsSendVO);
    }

    /** 관리자/총판/본사/매장 명칭 조회 */
    @Override
    public DefaultMap<Object> getStoreNmList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setUserId(sessionInfoVO.getUserId());

        return smsSendMapper.getStoreNmList(smsSendVO);
    }

    /** 잔여수량 조회 */
    @Override
    public DefaultMap<Object> getSmsQtyList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsSendMapper.getSmsQtyList(smsSendVO);
    }

    /** 전송,예약 저장 */
    @Override
    public int getSmsSendReserveSave(SmsSendVO[] smsSendVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        int rowCount = 1; // 홀수,짝수

        for(SmsSendVO smsSendVO : smsSendVOs) {

            smsSendVO.setRegDt(currentDt);
            smsSendVO.setRegId(sessionInfoVO.getUserId());
            smsSendVO.setModDt(currentDt);
            smsSendVO.setModId(sessionInfoVO.getUserId());

            smsSendVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            // 송신자
            smsSendVO.setSsOrgnCd(sessionInfoVO.getOrgnCd());
            smsSendVO.setSsOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            smsSendVO.setSsUserId(sessionInfoVO.getUserId());

            // 수신자
            if(smsSendVO.getRrOrgnFg().equals("C")) {
                smsSendVO.setRrOrgnCd(sessionInfoVO.getOrgnCd());
            } else {
                smsSendVO.setRrOrgnCd(smsSendVO.getRrOrgnCd());
            }
            smsSendVO.setRrOrgnFg(smsSendVO.getRrOrgnFg());
            smsSendVO.setRrUserId(smsSendVO.getRrUserId());

            // 송신주체 소속코드
            smsSendVO.setSmsOgnCd(sessionInfoVO.getOrgnCd());

            // 회원관리주체 소속코드
            if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ || sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                if(sessionInfoVO.getHqOfficeCd().equals("00000")){
                    smsSendVO.setCstOgnCd(sessionInfoVO.getStoreCd());
                } else {
                    smsSendVO.setCstOgnCd(sessionInfoVO.getHqOfficeCd());
                }
            } else {
                smsSendVO.setCstOgnCd("*");
            }

            // 전송일시
            if(smsSendVO.getReserveYn().equals("Y")) { // 예약
                smsSendVO.setSendDate(smsSendVO.getSendDate());
            } else {
                smsSendVO.setSendDate(currentDt);
            }

            // SMS등록전 체크
            String smsChk = smsSendMapper.getSmsChk(smsSendVO);

            // SMS
            if(smsSendVO.getMsgType().equals("1")) {
                if(smsChk.equals("0")) {
                    // 홀수
                    if(rowCount%2 == 1) {
                        procCnt = smsSendMapper.getSmsSendReserveSaveInsert(smsSendVO); // HCS_MSGSS_T
                    // 짝수
                    } else if(rowCount%2 == 0) {
                        procCnt = smsSendMapper.getSmsSendReserveSaveInsertKT(smsSendVO); // HCS_MSGKS_T
                    }

                } else if(smsChk.equals("2")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsert(smsSendVO); // HCS_MSGSS_T

                } else if(smsChk.equals("1")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsertKT(smsSendVO); // HCS_MSGKS_T
                }

            // LMS
            } else if(smsSendVO.getMsgType().equals("1")) {
                if(smsChk.equals("0")) {
                    // 홀수
                    if(rowCount%2 == 1) {
                        procCnt = smsSendMapper.getSmsSendReserveSaveInsertLMS(smsSendVO); // HCS_MSGSM_T
                        // 짝수
                    } else if(rowCount%2 == 0) {
                        procCnt = smsSendMapper.getSmsSendReserveSaveInsertLMSKT(smsSendVO); // HCS_MSGKM_T
                    }

                } else if(smsChk.equals("2")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsertLMS(smsSendVO); // HCS_MSGSM_T

                } else if(smsChk.equals("1")) {
                    procCnt = smsSendMapper.getSmsSendReserveSaveInsertLMSKT(smsSendVO); // HCS_MSGKM_T
                }
            }

            // 잔여수량
            String smsQty = smsSendMapper.getSmsQty(smsSendVO);
            smsSendVO.setSmsQty(String.valueOf(smsQty));

            // 잔여수량 저장 update
            procCnt = smsSendMapper.getSmsQtySaveUpdate(smsSendVO);

            rowCount = rowCount + 1; // 홀수,짝수
        }

        return procCnt;
    }

    /** 수신자추가 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAddresseeAddList(SmsSendVO smsSendVO, SessionInfoVO sessionInfoVO) {

        smsSendVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.MASTER) {

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            smsSendVO.setAgencyCd(sessionInfoVO.getOrgnCd());

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            smsSendVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        } else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ) {
            smsSendVO.setStoreCd(sessionInfoVO.getStoreCd());
        }


        return smsSendMapper.getAddresseeAddList(smsSendVO);
    }
}