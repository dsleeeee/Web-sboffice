package kr.co.solbipos.dlvr.info.dlvrEmp.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.utils.jsp.CmmEnvUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.base.prod.kioskKeyMap.service.KioskKeyMapVO;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpService;
import kr.co.solbipos.dlvr.info.dlvrEmp.service.DlvrEmpVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DlvrEmpServiceImpl.java
 * @Description : 배달관리 > 배달정보 > 배달사원정보관리
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2021.10.20  이다솜      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 이다솜
 * @since 2021.10.20
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */


@Service("dlvrEmpService")
@Transactional
public class DlvrEmpServiceImpl implements DlvrEmpService {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private final DlvrEmpMapper dlvrEmpMapper;
    private final MessageService messageService;
    private final CmmEnvUtil cmmEnvUtil;

    /**
     * Constructor Injection
     */
    @Autowired
    public DlvrEmpServiceImpl(DlvrEmpMapper dlvrEmpMapper, CmmEnvUtil cmmEnvUtil, MessageService messageService) {
        this.dlvrEmpMapper = dlvrEmpMapper;
        this.messageService = messageService;
        this.cmmEnvUtil = cmmEnvUtil;
    }

    /** 배달사원정보 목록 조회 */
    @Override
    public List<DefaultMap<String>> getDlvrEmpList(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO) {

        dlvrEmpVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dlvrEmpMapper.getDlvrEmpList(dlvrEmpVO);
    }

    /** 배달사원정보 상세 조회 */
    @Override
    public DefaultMap<String> getDlvrEmpDtl(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO) {

        dlvrEmpVO.setStoreCd(sessionInfoVO.getStoreCd());

        return dlvrEmpMapper.getDlvrEmpDtl(dlvrEmpVO);
    }

    /** 배달사원 신규등록 */
    @Override
    public int insertDlvrEmp(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        dlvrEmpVO.setStoreCd(sessionInfoVO.getStoreCd());
        dlvrEmpVO.setRegDt(currentDt);
        dlvrEmpVO.setRegId(sessionInfoVO.getUserId());
        dlvrEmpVO.setModDt(currentDt);
        dlvrEmpVO.setModId(sessionInfoVO.getUserId());

        result = dlvrEmpMapper.insertDlvrEmp(dlvrEmpVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }

    /** 배달사원 정보수정 */
    @Override
    public int updateDlvrEmp(DlvrEmpVO dlvrEmpVO, SessionInfoVO sessionInfoVO){

        int result = 0;
        String currentDt = currentDateTimeString();

        dlvrEmpVO.setStoreCd(sessionInfoVO.getStoreCd());
        dlvrEmpVO.setModDt(currentDt);
        dlvrEmpVO.setModId(sessionInfoVO.getUserId());

        result = dlvrEmpMapper.updateDlvrEmp(dlvrEmpVO);
        if(result <= 0) throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));

        return result;
    }
}
