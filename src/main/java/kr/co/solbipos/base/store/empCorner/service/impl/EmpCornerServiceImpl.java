package kr.co.solbipos.base.store.empCorner.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.empCorner.service.EmpCornerService;
import kr.co.solbipos.base.store.empCorner.service.EmpCornerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpCornerServiceImpl.java
 * @Description : 기초관리 > 매장관리 > 사원별코너관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.05.13  김설아      최초생성
 *
 * @author 솔비포스 개발본부 백엔드PT 김설아
 * @since 2020.05.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("empCornerService")
@Transactional
public class EmpCornerServiceImpl implements EmpCornerService {
    private final EmpCornerMapper empCornerMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public EmpCornerServiceImpl(EmpCornerMapper empCornerMapper) {
        this.empCornerMapper = empCornerMapper;
    }

    /** 사원별탭 - 사원정보 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpCornerEmpList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getEmpCornerEmpList(empCornerVO);
    }

    /** 사원별탭 - 관리코너 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpManageCornerList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getEmpManageCornerList(empCornerVO);
    }

    /** 사원별탭 - 미관리코너 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpNoManageCornerList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getEmpNoManageCornerList(empCornerVO);
    }

    /** 사원별탭 - 관리코너 추가 */
    @Override
    public int getEmpManageCornerSave(EmpCornerVO[] empCornerVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpCornerVO empCornerVO : empCornerVOs) {

            empCornerVO.setModDt(currentDt);
            empCornerVO.setModId(sessionInfoVO.getUserId());
            empCornerVO.setRegDt(currentDt);
            empCornerVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empCornerMapper.getEmpManageCornerSave(empCornerVO);
        }

        return procCnt;
    }

    /** 사원별탭 - 관리코너 삭제 */
    @Override
    public int getEmpManageCornerDelete(EmpCornerVO[] empCornerVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpCornerVO empCornerVO : empCornerVOs) {

            empCornerVO.setModDt(currentDt);
            empCornerVO.setModId(sessionInfoVO.getUserId());
            empCornerVO.setRegDt(currentDt);
            empCornerVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empCornerMapper.getEmpManageCornerDelete(empCornerVO);
        }

        return procCnt;
    }

    /** 코너별탭 - 코너정보 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpCornerCornerList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getEmpCornerCornerList(empCornerVO);
    }


    /** 코너별탭 - 관리사원 조회 */
    @Override
    public List<DefaultMap<Object>> getCornerManageEmpList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getCornerManageEmpList(empCornerVO);
    }

    /** 코너별탭 - 미관리사원 조회 */
    @Override
    public List<DefaultMap<Object>> getCornerNoManageEmpList(EmpCornerVO empCornerVO, SessionInfoVO sessionInfoVO) {

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            empCornerVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return empCornerMapper.getCornerNoManageEmpList(empCornerVO);
    }

    /** 코너별탭 - 관리사원 추가 */
    @Override
    public int getCornerManageEmpSave(EmpCornerVO[] empCornerVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpCornerVO empCornerVO : empCornerVOs) {

            empCornerVO.setModDt(currentDt);
            empCornerVO.setModId(sessionInfoVO.getUserId());
            empCornerVO.setRegDt(currentDt);
            empCornerVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empCornerMapper.getCornerManageEmpSave(empCornerVO);
        }

        return procCnt;
    }

    /** 코너별탭 - 관리사원 삭제 */
    @Override
    public int getCornerManageEmpDelete(EmpCornerVO[] empCornerVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for(EmpCornerVO empCornerVO : empCornerVOs) {

            empCornerVO.setModDt(currentDt);
            empCornerVO.setModId(sessionInfoVO.getUserId());
            empCornerVO.setRegDt(currentDt);
            empCornerVO.setRegId(sessionInfoVO.getUserId());

            procCnt = empCornerMapper.getCornerManageEmpDelete(empCornerVO);
        }

        return procCnt;
    }
}