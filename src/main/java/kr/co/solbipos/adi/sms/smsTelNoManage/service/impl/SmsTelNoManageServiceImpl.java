package kr.co.solbipos.adi.sms.smsTelNoManage.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsTelNoManageServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > 발신번호관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.09.15  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.09.15
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("smsTelNoManageService")
@Transactional
public class SmsTelNoManageServiceImpl implements SmsTelNoManageService {
    private final SmsTelNoManageMapper smsTelNoManageMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SmsTelNoManageServiceImpl(SmsTelNoManageMapper smsTelNoManageMapper) { this.smsTelNoManageMapper = smsTelNoManageMapper; }

    /** 발신번호관리 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoManageList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsTelNoManageMapper.getSmsTelNoManageList(smsTelNoManageVO);
    }

    /** 발신번호관리 - 발신번호 등록 요청 저장 */
    @Override
    public int getSmsTelNoManageSave(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        procCnt = smsTelNoManageMapper.getSmsTelNoManageSave(smsTelNoManageVO);

        return procCnt;
    }

    @Override
    public int getSmsTelNoManageUpdate(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        procCnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);

        return procCnt;
    }

    /** 발신번호관리 저장 */
    @Override
    public int getSmsTelNoManageSaveUpdate(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

            smsTelNoManageVO.setModDt(currentDt);
            smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

            procCnt = smsTelNoManageMapper.getSmsTelNoManageSaveUpdate(smsTelNoManageVO);
        }

        return procCnt;
    }
}