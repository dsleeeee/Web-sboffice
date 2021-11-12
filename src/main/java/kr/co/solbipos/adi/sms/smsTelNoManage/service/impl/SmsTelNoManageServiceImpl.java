package kr.co.solbipos.adi.sms.smsTelNoManage.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageService;
import kr.co.solbipos.adi.sms.smsTelNoManage.service.SmsTelNoManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        int cnt = 0;

        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        // 중복체크
        if(smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO) == 0){
            // 기존에 등록된 번호가 아니면 update
            cnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
        } else {
            // 기존에 등록된 번호면 -1
            cnt = -1;
        }

        return cnt;
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