package kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusService;
import kr.co.solbipos.adi.alimtalk.alimtalkSendStatus.service.AlimtalkSendStatusVO;
import kr.co.solbipos.adi.sms.sendStatus.service.impl.SendStatusMapper;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : AlimtalkSendStatusServiceImpl.java
 * @Description : 부가서비스 > 알림톡관리 > 알림톡 전송결과
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
@Service("alimtalkSendStatusService")
@Transactional
public class AlimtalkSendStatusServiceImpl implements AlimtalkSendStatusService {
    private final AlimtalkSendStatusMapper alimtalkSendStatusMapper;
    private final SendStatusMapper sendStatusMapper; // SMS 문자전송현황

    /**
     * Constructor Injection
     */
    @Autowired
    public AlimtalkSendStatusServiceImpl(AlimtalkSendStatusMapper alimtalkSendStatusMapper, SendStatusMapper sendStatusMapper) {
        this.alimtalkSendStatusMapper = alimtalkSendStatusMapper;
        this.sendStatusMapper = sendStatusMapper; // SMS 문자전송현황
    }

    /** 알림톡 전송결과 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkSendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        alimtalkSendStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        alimtalkSendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendStatusMapper.getAlimtalkSendStatusList(alimtalkSendStatusVO);
    }

    /** 알림톡 전송결과 - 예약취소 */
    @Override
    public int getAlimtalkSendStatusReserveCancelSave(AlimtalkSendStatusVO[] alimtalkSendStatusVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(AlimtalkSendStatusVO alimtalkSendStatusVO : alimtalkSendStatusVOs) {

            alimtalkSendStatusVO.setModDt(currentDt);
            alimtalkSendStatusVO.setModId(sessionInfoVO.getUserId());

            alimtalkSendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            String msgType = ""; // 메세지타입

            if(alimtalkSendStatusVO.getGubun().equals("TB_AL_ALIMTALK_SEND_ENC")) {
                msgType = "4"; // 메세지타입
                procCnt = alimtalkSendStatusMapper.getAlimtalkSendStatusReserveCancelSaveDelete(alimtalkSendStatusVO); // TB_AL_ALIMTALK_SEND_ENC
            }

            alimtalkSendStatusVO.setMsgType(msgType);

            // 잔여금액 복구
            SendStatusVO sendStatusVO = new SendStatusVO();
            sendStatusVO.setModDt(currentDt);
            sendStatusVO.setModId(sessionInfoVO.getUserId());
            sendStatusVO.setOrgnCd(alimtalkSendStatusVO.getOrgnCd());
            sendStatusVO.setMsgType(alimtalkSendStatusVO.getMsgType());
            procCnt = sendStatusMapper.getSmsAmtRecoverSaveUpdate(sendStatusVO);

            // 알림톡 전송이력 복구
            procCnt = alimtalkSendStatusMapper.getAlkSendSeqRecoverSaveUpdate(alimtalkSendStatusVO);
        }

        return procCnt;
    }

    /** 알림톡 일자별 전송현황 - 조회 */
    @Override
    public List<DefaultMap<Object>> getAlimtalkDaySendStatusList(AlimtalkSendStatusVO alimtalkSendStatusVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        alimtalkSendStatusVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        alimtalkSendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return alimtalkSendStatusMapper.getAlimtalkDaySendStatusList(alimtalkSendStatusVO);
    }
}