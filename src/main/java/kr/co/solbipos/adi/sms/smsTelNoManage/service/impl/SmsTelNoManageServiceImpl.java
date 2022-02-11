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

        System.out.println("JH : getSmsTelNoManageChk 진입");
        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        int cnt = 0;

        System.out.println("JH : 전화번호 : " + smsTelNoManageVO.getTelNo());
        System.out.println("JH : 소속코드 : " + smsTelNoManageVO.getOrgnCd());
        cnt = smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO);
        System.out.println("JH : getSmsTelNoManageChk 결과" + cnt);

        return cnt;
    }

    /** 발신번호관리 - 발신번호 등록 요청 저장 */
    @Override
    public int getSmsTelNoManageUpdate(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        int cnt = 0;

        System.out.println("JH : getSmsTelNoManageUpdate 진입");
        String currentDt = currentDateTimeString();

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());
        smsTelNoManageVO.setModDt(currentDt);
        smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

        System.out.println("JH : 본인인증 update 인덱스스");
        System.out.println("JH : 소속코드 : " + smsTelNoManageVO.getOrgnCd());
        System.out.println("JH : 전화번호 : " + smsTelNoManageVO.getTelNo());
        System.out.println("JH : 수정날짜 : " + smsTelNoManageVO.getModDt());
        System.out.println("JH : 수정자 : " + smsTelNoManageVO.getModId());
        System.out.println("JH : 요청번호 : " + smsTelNoManageVO.getCertId());
        cnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
        System.out.println("JH : getSmsTelNoManageUpdate 결과" + cnt);
        return cnt;
    }

    /** 발신번호관리 - 저장 */
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

    /** HCS_CRTLG_T.OGN_CD 값 가져옴 */
    @Override
    public String getOrdrIdxx(SmsTelNoManageVO smsTelNoManageVO) {
        String ordrIdxx = smsTelNoManageMapper.getOrdrIdxx(smsTelNoManageVO);
        System.out.println("JH : HCS_CRTLG_T.OGN_CD값 : " + ordrIdxx);
        return ordrIdxx;
    }

    /** 발신번호차단 탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsTelNoStopList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        smsTelNoManageVO.setOrgnCd(sessionInfoVO.getOrgnCd());

        return smsTelNoManageMapper.getSmsTelNoStopList(smsTelNoManageVO);
    }

    /** 발신번호차단 탭 - 저장 */
    @Override
    public int getSmsTelNoStopSaveUpdate(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            smsTelNoManageVO.setModDt(currentDt);
            smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

            procCnt = smsTelNoManageMapper.getSmsTelNoStopSaveUpdate(smsTelNoManageVO);
        }

        return procCnt;
    }

    /** 일반번호 인증요청 처리 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSmsGeneralNoManageList(SmsTelNoManageVO smsTelNoManageVO, SessionInfoVO sessionInfoVO) {

        return smsTelNoManageMapper.getSmsGeneralNoManageList(smsTelNoManageVO);
    }

    /** 일반번호 인증요청 처리 팝업 - 저장 */
    @Override
    public int getSmsGeneralNoManageSave(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        String currentDt = currentDateTimeString();

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {

            smsTelNoManageVO.setRegDt(currentDt);
            smsTelNoManageVO.setRegId(sessionInfoVO.getUserId());
            smsTelNoManageVO.setModDt(currentDt);
            smsTelNoManageVO.setModId(sessionInfoVO.getUserId());

            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 관리요청번호 : " + smsTelNoManageVO.getCertId());
            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정전 처리구분 : " + smsTelNoManageVO.getBackAddProcFg());
            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 신규 처리구분 : " + smsTelNoManageVO.getAddProcFg());
            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정전 발신번호 : " + smsTelNoManageVO.getBackTelNo());
            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 신규 발신번호 : " + smsTelNoManageVO.getTelNo());

            // 수정전 처리구분이 완료이면서
            // && 수정전 처리구분 == 신규 처리구분
            // && 수정전 발신번호 == 신규 발신번호
            if( "2".equals(smsTelNoManageVO.getBackAddProcFg())
                && smsTelNoManageVO.getBackAddProcFg().equals(smsTelNoManageVO.getAddProcFg())
                && smsTelNoManageVO.getBackTelNo().equals(smsTelNoManageVO.getTelNo()) ) {

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 안탐");

            } else {
                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 start");

                // 수정전 발신번호 삭제
                procCnt = smsTelNoManageMapper.getGeneralNoSmsNoSaveDelete(smsTelNoManageVO);

                // 발신번호 등록 요청 저장(발신번호 등록전 상태로)
                procCnt = smsTelNoManageMapper.getSmsGeneralNoSmsNoSaveInsert(smsTelNoManageVO);

                // 완료
                if("2".equals(smsTelNoManageVO.getAddProcFg())) {
                    // 발신번호 등록 요청 승인(발신번호 등록)
                    smsTelNoManageVO.setResCd("0000");
                    procCnt = smsTelNoManageMapper.getSmsTelNoManageUpdate(smsTelNoManageVO);
                }

                System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 발신번호 삭제 및 등록 로직 end");
            }

            System.out.println("WEB_SMS >>> 일반번호 인증요청 처리 >>> 저장(수정) >>> 수정된 정보 저장");
            // 일반번호 인증요청 처리 update
            procCnt = smsTelNoManageMapper.getSmsGeneralNoManageSaveUpdate(smsTelNoManageVO);
        }

        return procCnt;
    }

    /** 일반번호 인증요청 처리 팝업 - 발신번호 중복체크 */
    @Override
    public int getSmsGeneralNoManageCount(SmsTelNoManageVO[] smsTelNoManageVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;

        for(SmsTelNoManageVO smsTelNoManageVO : smsTelNoManageVOs) {
            int chkTelNo = 0;

            // 완료
            if("2".equals(smsTelNoManageVO.getAddProcFg())) {
                chkTelNo = smsTelNoManageMapper.getSmsTelNoManageChk(smsTelNoManageVO);
            }

            procCnt = procCnt + chkTelNo; // 중복된 번호 건수
        }

        return procCnt;
    }
}