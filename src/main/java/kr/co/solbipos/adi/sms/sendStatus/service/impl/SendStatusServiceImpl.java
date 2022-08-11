package kr.co.solbipos.adi.sms.sendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SendStatusServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > 문자전송현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.18  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.18
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("sendStatusService")
@Transactional
public class SendStatusServiceImpl implements SendStatusService {
    private final SendStatusMapper sendStatusMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SendStatusServiceImpl(SendStatusMapper sendStatusMapper) {
        this.sendStatusMapper = sendStatusMapper;
    }

    /** 메세지그룹 컬럼 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getMsgGrpColList(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return sendStatusMapper.getMsgGrpColList(sendStatusVO);
    }

    /** 문자전송현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSendStatusList(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        sendStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return sendStatusMapper.getSendStatusList(sendStatusVO);
    }

    /** 문자전송현황 - 예약취소 */
    @Override
    public int getSendStatusReserveCancelSave(SendStatusVO[] sendStatusVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(SendStatusVO sendStatusVO : sendStatusVOs) {

            sendStatusVO.setModDt(currentDt);
            sendStatusVO.setModId(sessionInfoVO.getUserId());

            sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            String msgType = ""; // 메세지타입

            if(sendStatusVO.getGubun().equals("SDK_SMS_SEND_ENC")) {
                msgType = "1"; // 메세지타입
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDelete(sendStatusVO); // SDK_SMS_SEND_ENC

            } else if(sendStatusVO.getGubun().equals("SDK_LMS_SEND_ENC")) {
                msgType = "2"; // 메세지타입
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDeleteLMS(sendStatusVO); // SDK_MMS_SEND_ENC

            } else if(sendStatusVO.getGubun().equals("SDK_MMS_SEND_ENC")) {
                msgType = "3"; // 메세지타입
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDeleteLMS(sendStatusVO); // SDK_MMS_SEND_ENC
            }

            sendStatusVO.setMsgType(msgType);

            // 잔여금액 복구
            procCnt = sendStatusMapper.getSmsAmtRecoverSaveUpdate(sendStatusVO);

            // 전송이력 복구
            procCnt = sendStatusMapper.getSmsSendSeqRecoverSaveUpdate(sendStatusVO);
        }

        return procCnt;
    }

    /** 일자별 전송현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDaySendStatusList(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        sendStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return sendStatusMapper.getDaySendStatusList(sendStatusVO);
    }

    /** 기간별 전송현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getPeriodSendStatusList(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        sendStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ){
            sendStatusVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        }

        return sendStatusMapper.getPeriodSendStatusList(sendStatusVO);
    }



    /** 공통 화면 상단 SMS전송(당일) 표시 - [125 SMS전송현황표시]에 등록된 본사 하위 매장인지 조회 */
    @Override
    public DefaultMap<String> getCmmMainTopStoreCount(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        return sendStatusMapper.getCmmMainTopStoreCount(sendStatusVO);
    }

    /** 공통 화면 상단 SMS전송(당일) 표시 - 오늘 SMS전송 건수 조회 */
    @Override
    public DefaultMap<String> getCmmMainTopSmsSendCount(SendStatusVO sendStatusVO, SessionInfoVO sessionInfoVO) {

        return sendStatusMapper.getCmmMainTopSmsSendCount(sendStatusVO);
    }
}