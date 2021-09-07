package kr.co.solbipos.base.store.emp.cardInfo.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoService;
import kr.co.solbipos.base.store.emp.cardInfo.service.EmpCardInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EmpCardInfoServiceImpl.java
 * @Description : 기초관리 - 사원관리 - 사원카드정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.08.13  이다솜       최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.08.13
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("EmpCardInfoService")
@Transactional
public class EmpCardInfoServiceImpl implements EmpCardInfoService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final EmpCardInfoMapper empCardInfoMapper;
    private final MessageService messageService;

    @Autowired
    public EmpCardInfoServiceImpl(EmpCardInfoMapper empCardInfoMapper, MessageService messageService){

        this.empCardInfoMapper = empCardInfoMapper;
        this.messageService = messageService;
    }

    /** 사원카드정보 조회 */
    @Override
    public List<DefaultMap<Object>> getEmpCardInfo(EmpCardInfoVO empCardInfoVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getStoreCd());
        }

        return empCardInfoMapper.getEmpCardInfo(empCardInfoVO);
    }

    /** 사원카드정보 전체 삭제 */
    @Override
    public int deleteEmpCardInfo(EmpCardInfoVO empCardInfoVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getStoreCd());
        }

        int result = empCardInfoMapper.deleteEmpCardInfo(empCardInfoVO);
        if (result < 0) {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
        return result;
    }

    /** 사원카드정보 저장 */
    @Override
    public int saveEmpCardInfo(EmpCardInfoVO[] empCardInfoVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for (EmpCardInfoVO empCardInfoVO : empCardInfoVOs) {

            if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
                empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getHqOfficeCd());
            }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
                empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getStoreCd());
            }
            empCardInfoVO.setUseFg("1"); // 사용
            empCardInfoVO.setRegDt(currentDt);
            empCardInfoVO.setRegId(sessionInfoVO.getUserId());
            empCardInfoVO.setModDt(currentDt);
            empCardInfoVO.setModId(sessionInfoVO.getUserId());

            // "'" 제거
            empCardInfoVO.setEmployeeCardNo(empCardInfoVO.getEmployeeCardNo() != null ? empCardInfoVO.getEmployeeCardNo().replaceAll("'","") : "");
            empCardInfoVO.setEmployeeNo(empCardInfoVO.getEmployeeNo() != null ? empCardInfoVO.getEmployeeNo().replaceAll("'","") : "");

            // 추가 또는 수정
            if ( empCardInfoVO.getStatus() == GridDataFg.INSERT || empCardInfoVO.getStatus() == GridDataFg.UPDATE ) {

                result = empCardInfoMapper.insertEmpCardInfo(empCardInfoVO);
                if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

            } else if ( empCardInfoVO.getStatus() == GridDataFg.DELETE ) { // 삭제

                result = empCardInfoMapper.deleteSelEmpCardInfo(empCardInfoVO);
                if(result < 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
            }

            procCnt ++;
        }

        if (procCnt == empCardInfoVOs.length) {
            return procCnt;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }

    }

    /** 사원카드번호 중복체크 */
    @Override
    public List<DefaultMap<Object>> getChkEmpCardNo(EmpCardInfoVO empCardInfoVO, SessionInfoVO sessionInfoVO) {

        if(sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getHqOfficeCd());
        }else if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            empCardInfoVO.setEmployeeOrgnCd(sessionInfoVO.getStoreCd());
        }

        // 사원카드번호 arr Set
        if(!StringUtil.getOrBlank(empCardInfoVO.getEmployeeCardNo()).equals("")) {
            empCardInfoVO.setArrEmpCardNo(empCardInfoVO.getEmployeeCardNo().split(","));
        }

        return empCardInfoMapper.getChkEmpCardNo(empCardInfoVO);
    }
}
