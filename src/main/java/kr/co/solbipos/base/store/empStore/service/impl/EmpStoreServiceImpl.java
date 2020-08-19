package kr.co.solbipos.base.store.empStore.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.base.store.empStore.service.EmpStoreService;
import kr.co.solbipos.base.store.empStore.service.EmpStoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpStoreServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 사원별매장관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.12  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.12
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("empStoreService")
@Transactional
public class EmpStoreServiceImpl implements EmpStoreService {
    private final EmpStoreMapper empStoreMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public EmpStoreServiceImpl(EmpStoreMapper empStoreMapper) {
        this.empStoreMapper = empStoreMapper;
    }

    /** 사원별탭 - 사원정보 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpStoreEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getEmpStoreEmpList(empStoreVO);
    }

    /** 사원별탭 - 관리매장 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpManageStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getEmpManageStoreList(empStoreVO);
    }

    /** 사원별탭 - 미관리매장 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpNoManageStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getEmpNoManageStoreList(empStoreVO);
    }

    /** 사원별탭 - 관리매장 추가 */
    @Override
    public int getEmpManageStoreSave(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpStoreVO empStoreVO : empStoreVOs) {

            empStoreVO.setModDt(currentDt);
            empStoreVO.setModId(sessionInfoVO.getUserId());
            empStoreVO.setRegDt(currentDt);
            empStoreVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empStoreMapper.getEmpManageStoreSave(empStoreVO);
        }

        return procCnt;
    }

    /** 사원별탭 - 관리매장 삭제 */
    @Override
    public int getEmpManageStoreDelete(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpStoreVO empStoreVO : empStoreVOs) {

            empStoreVO.setModDt(currentDt);
            empStoreVO.setModId(sessionInfoVO.getUserId());
            empStoreVO.setRegDt(currentDt);
            empStoreVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empStoreMapper.getEmpManageStoreDelete(empStoreVO);
        }

        return procCnt;
    }

    /** 매장별탭 - 매장정보 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpStoreStoreList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getEmpStoreStoreList(empStoreVO);
    }

    /** 매장별탭 - 관리사원 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreManageEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getStoreManageEmpList(empStoreVO);
    }

    /** 매장별탭 - 미관리사원 조회 */
    @Override
    public List<DefaultMap<Object>> getStoreNoManageEmpList(EmpStoreVO empStoreVO, SessionInfoVO sessionInfoVO) {

        empStoreVO.setMembrOrgnCd(sessionInfoVO.getHqOfficeCd());

        return empStoreMapper.getStoreNoManageEmpList(empStoreVO);
    }

    /** 매장별탭 - 관리사원 추가 */
    @Override
    public int getStoreManageEmpSave(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpStoreVO empStoreVO : empStoreVOs) {

            empStoreVO.setModDt(currentDt);
            empStoreVO.setModId(sessionInfoVO.getUserId());
            empStoreVO.setRegDt(currentDt);
            empStoreVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empStoreMapper.getStoreManageEmpSave(empStoreVO);
        }

        return procCnt;
    }

    /** 매장별탭 - 관리사원 삭제 */
    @Override
    public int getStoreManageEmpDelete(EmpStoreVO[] empStoreVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpStoreVO empStoreVO : empStoreVOs) {

            empStoreVO.setModDt(currentDt);
            empStoreVO.setModId(sessionInfoVO.getUserId());
            empStoreVO.setRegDt(currentDt);
            empStoreVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empStoreMapper.getStoreManageEmpDelete(empStoreVO);
        }

        return procCnt;
    }
}