package kr.co.solbipos.store.manage.envConfgBatchChange.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.store.manage.envConfgBatchChange.service.EnvConfgBatchChangeService;
import kr.co.solbipos.store.manage.envConfgBatchChange.service.EnvConfgBatchChangeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : EnvConfgBatchChangeServiceImpl.java
 * @Description : 기초관리 > 매장정보관리 > 환경변수일괄변경
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.02.17  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2021.02.17
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("envConfgBatchChangeService")
@Transactional
public class EnvConfgBatchChangeServiceImpl implements EnvConfgBatchChangeService {
    private final EnvConfgBatchChangeMapper envConfgBatchChangeMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public EnvConfgBatchChangeServiceImpl(EnvConfgBatchChangeMapper envConfgBatchChangeMapper) { this.envConfgBatchChangeMapper = envConfgBatchChangeMapper; }

    /** 본사환경탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getEnvConfgBatchChangeHqList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {

        envConfgBatchChangeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            envConfgBatchChangeVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return envConfgBatchChangeMapper.getEnvConfgBatchChangeHqList(envConfgBatchChangeVO);
    }

    /** 환경설정 조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchEnvConfgList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {

        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        if(envConfgBatchChangeVO.getGubun().equals(("HQ"))) {
            result = envConfgBatchChangeMapper.getSearchEnvConfgList(envConfgBatchChangeVO);

        } else if(envConfgBatchChangeVO.getGubun().equals(("STORE"))) {
            result = envConfgBatchChangeMapper.getSearchEnvConfgStoreList(envConfgBatchChangeVO);

        } else if(envConfgBatchChangeVO.getGubun().equals(("STORE_POS"))) {
            result = envConfgBatchChangeMapper.getSearchEnvConfgStorePosList(envConfgBatchChangeVO);
        }

        return result;
    }

    /** 본사환경탭 - 환경변수값 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getEnvstValComboList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {

        return envConfgBatchChangeMapper.getEnvstValComboList(envConfgBatchChangeVO);
    }

    /** 본사환경탭 - 저장 */
    @Override
    public int getEnvConfgBatchChangeHqSave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EnvConfgBatchChangeVO envConfgBatchChangeVO : envConfgBatchChangeVOs) {

            envConfgBatchChangeVO.setModDt(currentDt);
            envConfgBatchChangeVO.setModId(sessionInfoVO.getUserId());
            envConfgBatchChangeVO.setRegDt(currentDt);
            envConfgBatchChangeVO.setRegId(sessionInfoVO.getUserId());

            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeHqSave(envConfgBatchChangeVO);

            // 대상구분 - H:본사전용, C:공통(매장에서변경가능), N:No, S:매장전용, X:공통(매장에서사용하지만_본사기준), Z:본사+단독매장
            if(envConfgBatchChangeVO.getTargtFg().equals("X")) {

                // 매장코드 조회
                List<DefaultMap<String>> storeList = envConfgBatchChangeMapper.getStoreCdList(envConfgBatchChangeVO);

                if (storeList.size() > 0) {
                    for(int i = 0; i < storeList.size(); i++) {
                        envConfgBatchChangeVO.setStoreCd(storeList.get(i).get("storeCd"));

                        procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeHqStoreSave(envConfgBatchChangeVO);
                    }
                }
            }
        }

        return procCnt;
    }

    /** 매장환경탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getEnvConfgBatchChangeStoreList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {

        envConfgBatchChangeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            envConfgBatchChangeVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return envConfgBatchChangeMapper.getEnvConfgBatchChangeStoreList(envConfgBatchChangeVO);
    }

    /** 매장환경탭 - 저장 */
    @Override
    public int getEnvConfgBatchChangeStoreSave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EnvConfgBatchChangeVO envConfgBatchChangeVO : envConfgBatchChangeVOs) {

            envConfgBatchChangeVO.setModDt(currentDt);
            envConfgBatchChangeVO.setModId(sessionInfoVO.getUserId());
            envConfgBatchChangeVO.setRegDt(currentDt);
            envConfgBatchChangeVO.setRegId(sessionInfoVO.getUserId());

            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeHqStoreSave(envConfgBatchChangeVO);
        }

        return procCnt;
    }

    /** 매장포스환경탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getEnvConfgBatchChangeStorePosList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {

        envConfgBatchChangeVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());

        // 총판인 경우, session의 AgencyCode 값 넣기
        if (sessionInfoVO.getOrgnFg() == OrgnFg.AGENCY) {
            envConfgBatchChangeVO.setAgencyCd(sessionInfoVO.getOrgnCd());
        }

        return envConfgBatchChangeMapper.getEnvConfgBatchChangeStorePosList(envConfgBatchChangeVO);
    }

    /** 매장포스환경탭 - 저장 */
    @Override
    public int getEnvConfgBatchChangeStorePosSave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EnvConfgBatchChangeVO envConfgBatchChangeVO : envConfgBatchChangeVOs) {

            envConfgBatchChangeVO.setModDt(currentDt);
            envConfgBatchChangeVO.setModId(sessionInfoVO.getUserId());
            envConfgBatchChangeVO.setRegDt(currentDt);
            envConfgBatchChangeVO.setRegId(sessionInfoVO.getUserId());

            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeStorePosSave(envConfgBatchChangeVO);
        }

        return procCnt;
    }

    /** 기능키 조회 팝업 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSearchFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        result = envConfgBatchChangeMapper.getSearchFnkeyList(envConfgBatchChangeVO);

        return result;
    }

    /** 기능키명칭탭 - 조회 */
    @Override
    public List<DefaultMap<Object>> getEnvConfgBatchChangeFnkeyList(EnvConfgBatchChangeVO envConfgBatchChangeVO, SessionInfoVO sessionInfoVO) {
        List<DefaultMap<Object>> result = new ArrayList<DefaultMap<Object>>();

        result = envConfgBatchChangeMapper.getEnvConfgBatchChangeFnkeyList(envConfgBatchChangeVO);

        return result;
    }

    /** 기능키명칭탭 - 저장 */
    @Override
    public int getEnvConfgBatchChangeFnkeySave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EnvConfgBatchChangeVO envConfgBatchChangeVO : envConfgBatchChangeVOs) {

            envConfgBatchChangeVO.setModDt(currentDt);
            envConfgBatchChangeVO.setModId(sessionInfoVO.getUserId());
            envConfgBatchChangeVO.setRegDt(currentDt);
            envConfgBatchChangeVO.setRegId(sessionInfoVO.getUserId());

            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeFnkeySave(envConfgBatchChangeVO);
        }

        return procCnt;
    }

    @Override
    public int getEnvConfgBatchChangeEnvSettingSave(EnvConfgBatchChangeVO[] envConfgBatchChangeVOs, SessionInfoVO sessionInfoVO) {
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EnvConfgBatchChangeVO envConfgBatchChangeVO : envConfgBatchChangeVOs) {

            envConfgBatchChangeVO.setModDt(currentDt);
            envConfgBatchChangeVO.setModId(sessionInfoVO.getUserId());

            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeEnvSettingSaveHq(envConfgBatchChangeVO);
            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeEnvSettingSaveMs(envConfgBatchChangeVO);
            procCnt = envConfgBatchChangeMapper.getEnvConfgBatchChangeEnvSettingSavePos(envConfgBatchChangeVO);
        }

        return procCnt;
    }
}