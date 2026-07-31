package kr.co.solbipos.adi.sms.smsXrayManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsXrayManage.service.SmsXrayManageService;
import kr.co.solbipos.adi.sms.smsXrayManage.service.SmsXrayManageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsXrayManageServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS Xray관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.23  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.07.23
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("smsXrayManageService")
@Transactional
public class SmsXrayManageServiceImpl implements SmsXrayManageService {

    private final SmsXrayManageMapper smsXrayManageMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SmsXrayManageServiceImpl(SmsXrayManageMapper smsXrayManageMapper) {
        this.smsXrayManageMapper = smsXrayManageMapper;
    }

    /** SMS Xray관리 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsXrayManageList(SmsXrayManageVO smsXrayManageVO, SessionInfoVO sessionInfoVO) {
        return smsXrayManageMapper.getSmsXrayManageList(smsXrayManageVO);
    }

    /** SMS Xray관리 - 저장 (승인여부 변경분만 전달됨) */
    @Override
    public int saveSmsXrayManage(SmsXrayManageVO[] smsXrayManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for (SmsXrayManageVO smsXrayManageVO : smsXrayManageVOs) {

            smsXrayManageVO.setApprId(sessionInfoVO.getUserId());
            smsXrayManageVO.setModDt(currentDt);
            smsXrayManageVO.setModId(sessionInfoVO.getUserId());

            if (smsXrayManageVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += smsXrayManageMapper.updateSmsXrayManage(smsXrayManageVO);
            }
        }

        return procCnt;
    }

    /** 탐지/차단결과 로그 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getUrlBlockLogList(SmsXrayManageVO smsXrayManageVO, SessionInfoVO sessionInfoVO) {
        return smsXrayManageMapper.getUrlBlockLogList(smsXrayManageVO);
    }
}
