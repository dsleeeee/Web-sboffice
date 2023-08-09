package kr.co.solbipos.adi.sms.msgManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.msgManage.service.MsgManageService;
import kr.co.solbipos.adi.sms.msgManage.service.MsgManageVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : MsgManageServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > 메세지관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.06.22  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.06.22
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("msgManageService")
@Transactional
public class MsgManageServiceImpl implements MsgManageService {
    private final MsgManageMapper msgManageMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Constructor Injection
     */
    @Autowired
    public MsgManageServiceImpl(MsgManageMapper msgManageMapper) {
        this.msgManageMapper = msgManageMapper;
    }

    /** 메세지관리 - 그룹 조회 */
    @Override
    public List<DefaultMap<Object>> getMsgManageList(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO) {

        msgManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return msgManageMapper.getMsgManageList(msgManageVO);
    }

    /** 메세지관리 - 그룹 저장 */
    @Override
    public int getMsgManageSave(MsgManageVO[] msgManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(MsgManageVO msgManageVO : msgManageVOs) {

            msgManageVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
            msgManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
            msgManageVO.setModDt(currentDt);
            msgManageVO.setModId(sessionInfoVO.getUserId());

            if (msgManageVO.getStatus() == GridDataFg.INSERT) {
                msgManageVO.setRegDt(currentDt);
                msgManageVO.setRegId(sessionInfoVO.getUserId());

                // 그룹코드(자동 채번)
                String msgGrpCd = msgManageMapper.getMsgManageMsgGrpCd(msgManageVO);
                msgManageVO.setMsgGrpCd(msgGrpCd);

                procCnt = msgManageMapper.getMsgManageSaveInsert(msgManageVO);

            } else if(msgManageVO.getStatus() == GridDataFg.UPDATE) {
                procCnt = msgManageMapper.getMsgManageSaveUpdate(msgManageVO);

            } else if (msgManageVO.getStatus() == GridDataFg.DELETE) {
                procCnt = msgManageMapper.getMsgManageSaveDelete(msgManageVO);

                // 메세지서식 전체 삭제
                procCnt = msgManageMapper.getMsgManageDtlSaveDeleteAll(msgManageVO);
            }

            // 본사 저장시, 매장적용
            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

                // 메시지관리 - 본사 메시지그룹 매장적용
                msgManageMapper.insertMsgGrpToStore(msgManageVO);
                LOGGER.info("CALL PKG_HCS_MSGGR_T_01.SP_PKG_HCS_MSGGR_T_001 결과 : " + msgManageVO.getResult());
            }
        }

        return procCnt;
    }

    /** 메세지관리 - 메세지서식 조회 */
    @Override
    public List<DefaultMap<Object>> getMsgManageDtlList(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO) {

        msgManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return msgManageMapper.getMsgManageDtlList(msgManageVO);
    }

    /** 메세지관리 - 메세지서식 저장 */
    @Override
    public int getMsgManageDtlSave(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        msgManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        msgManageVO.setModDt(currentDt);
        msgManageVO.setModId(sessionInfoVO.getUserId());

        if (msgManageVO.getStatus() == GridDataFg.INSERT) {
            msgManageVO.setRegDt(currentDt);
            msgManageVO.setRegId(sessionInfoVO.getUserId());

            // SEQ_NO(자동 채번)
            String seqNo = msgManageMapper.getMsgManageMsgSeqNo(msgManageVO);
            msgManageVO.setSeqNo(seqNo);

            procCnt = msgManageMapper.getMsgManageDtlSaveInsert(msgManageVO);

        } else if(msgManageVO.getStatus() == GridDataFg.UPDATE) {
            procCnt = msgManageMapper.getMsgManageDtlSaveUpdate(msgManageVO);

        } else if (msgManageVO.getStatus() == GridDataFg.DELETE) {
            procCnt = msgManageMapper.getMsgManageDtlSaveDelete(msgManageVO);
        }

        // 본사 저장시, 매장적용
        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {

            // 메시지관리 - 본사 메시지 매장적용
            msgManageMapper.insertMsgSmsToStore(msgManageVO);
            LOGGER.info("CALL PKG_HCS_SMSMS_T_01.SP_PKG_HCS_SMSMS_T_001 결과 : " + msgManageVO.getResult());
        }

        return procCnt;
    }

    /** 메세지관리 매장적용 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getMsgManageStoreRegistList(MsgManageVO msgManageVO, SessionInfoVO sessionInfoVO) {

        msgManageVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        return msgManageMapper.getMsgManageStoreRegistList(msgManageVO);
    }

    /** 메세지관리 매장적용 팝업 - 저장 */
    @Override
    public int getMsgManageStoreRegistSave(MsgManageVO[] msgManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(MsgManageVO msgManageVO : msgManageVOs) {

            msgManageVO.setRegDt(currentDt);
            msgManageVO.setRegId(sessionInfoVO.getUserId());
            msgManageVO.setModDt(currentDt);
            msgManageVO.setModId(sessionInfoVO.getUserId());

            msgManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            // 메세지그룹
            procCnt = msgManageMapper.getMsgManageStoreRegistSaveInsert(msgManageVO);

            // 메세지서식
            procCnt = msgManageMapper.getMsgManageStoreRegistDtlSaveInsert(msgManageVO);
        }

        return procCnt;
    }
}