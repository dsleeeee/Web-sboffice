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

    /** 발신번호관리 - 기존에 등록 된 번호인지 확인 */
    @Override
    public int getSmsTelNoManageChk(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        int cnt = 0;
        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        System.out.println("JH : 본인인증 chk 인덱스스");
        System.out.println("JH : 정보 : " + smsTelNoManageVO.getOrgnCd());

        cnt = smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO);
        return cnt;
    }

    /** 발신번호관리 - 발신번호 등록 요청 저장 */
    @Override
    public int getSmsTelNoManageUpdate(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {
        int cnt = 0;
        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        System.out.println("JH : 본인인증 update 인덱스스");
        System.out.println("JH : 전화번호 : " + smsTelNoManageVO.getTelNo());
        System.out.println("JH : 수정날짜 : " + smsTelNoManageVO.getModDt());
        System.out.println("JH : 수정자 : " + smsTelNoManageVO.getModId());
        System.out.println("JH : 요청번호 : " + smsTelNoManageVO.getCertId());
        cnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
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