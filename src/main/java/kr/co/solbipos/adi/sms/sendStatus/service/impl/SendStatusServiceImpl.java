package kr.co.solbipos.adi.sms.sendStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusService;
import kr.co.solbipos.adi.sms.sendStatus.service.SendStatusVO;
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

        sendStatusVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return sendStatusMapper.getSendStatusList(sendStatusVO);
    }

    /** 문자전송현황 - 예약취소 */
    @Override
    public int getSendStatusReserveCancelSave(SendStatusVO[] sendStatusVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SendStatusVO sendStatusVO : sendStatusVOs) {

            if(sendStatusVO.getGubun().equals("HCS_MSGSS_T")) {
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDelete(sendStatusVO); // HCS_MSGSS_T

            } else if(sendStatusVO.getGubun().equals("HCS_MSGKS_T")) {
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDeleteKT(sendStatusVO); // HCS_MSGKS_T

            } else if(sendStatusVO.getGubun().equals("HCS_MSGSM_T")) {
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDeleteLMS(sendStatusVO); // HCS_MSGSM_T

            } else if(sendStatusVO.getGubun().equals("HCS_MSGKM_T")) {
                procCnt = sendStatusMapper.getSendStatusReserveCancelSaveDeleteLMSKT(sendStatusVO); // HCS_MSGKM_T
            }
        }

        return procCnt;
    }
}