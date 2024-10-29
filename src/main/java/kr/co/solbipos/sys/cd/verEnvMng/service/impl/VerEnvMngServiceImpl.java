package kr.co.solbipos.sys.cd.verEnvMng.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.systemcd.service.SystemCdVO;
import kr.co.solbipos.sys.cd.verEnvMng.service.VerEnvMngService;
import kr.co.solbipos.sys.cd.verEnvMng.service.VerEnvMngVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : VerEnvMngServiceImpl.java
 * @Description : 시스템관리 > 코드관리 > 버전별환경설정
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.10.23  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2024.10.23
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("verEnvMngService")
public class VerEnvMngServiceImpl implements VerEnvMngService {

    private final VerEnvMngMapper verEnvMngMapper;
    private final MessageService messageService;

    @Autowired
    public VerEnvMngServiceImpl(VerEnvMngMapper verEnvMngMapper, MessageService messageService) {
        this.verEnvMngMapper = verEnvMngMapper;
        this.messageService = messageService;
    }

    /** 버전 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getVerList(VerEnvMngVO verEnvMngVO) {
        return verEnvMngMapper.getVerList(verEnvMngVO);
    }

    /** 버전 등록 */
    @Override
    public int saveVer(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (VerEnvMngVO verEnvMngVO : verEnvMngVOs) {

            verEnvMngVO.setRegDt(currentDt);
            verEnvMngVO.setRegId(sessionInfoVO.getUserId());
            verEnvMngVO.setModDt(currentDt);
            verEnvMngVO.setModId(sessionInfoVO.getUserId());

            // 버전등록
            result += verEnvMngMapper.saveVer(verEnvMngVO);
        }

        if ( result == verEnvMngVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 대표명칭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstList(VerEnvMngVO verEnvMngVO) {
        return verEnvMngMapper.getEnvstList(verEnvMngVO);
    }

    /** 대표명칭 사용여부 저장 */
    @Override
    public int saveEnvst(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (VerEnvMngVO verEnvMngVO : verEnvMngVOs) {

            verEnvMngVO.setRegDt(currentDt);
            verEnvMngVO.setRegId(sessionInfoVO.getUserId());
            verEnvMngVO.setModDt(currentDt);
            verEnvMngVO.setModId(sessionInfoVO.getUserId());

            // 대표명칭 사용여부 저장
            result += verEnvMngMapper.saveEnvst(verEnvMngVO);
        }

        if ( result == verEnvMngVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 세부명칭 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstDtlList(VerEnvMngVO verEnvMngVO) {
        return verEnvMngMapper.getEnvstDtlList(verEnvMngVO);
    }

    /** 세부명칭 초기값여부 저장 */
    @Override
    public int saveEnvstDtl(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (VerEnvMngVO verEnvMngVO : verEnvMngVOs) {

            verEnvMngVO.setRegDt(currentDt);
            verEnvMngVO.setRegId(sessionInfoVO.getUserId());
            verEnvMngVO.setModDt(currentDt);
            verEnvMngVO.setModId(sessionInfoVO.getUserId());

            // 세부명칭 초기값여부 저장
            result += verEnvMngMapper.saveEnvstDtl(verEnvMngVO);
        }

        if ( result == verEnvMngVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }

    /** 기능구분 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFuncFgList(VerEnvMngVO verEnvMngVO) {
        return verEnvMngMapper.getFuncFgList(verEnvMngVO);
    }

    /** 기능 리스트 조회 */
    @Override
    public List<DefaultMap<String>> getFuncList(VerEnvMngVO verEnvMngVO) {
        return verEnvMngMapper.getFuncList(verEnvMngVO);
    }

    /** 기능 사용여부 저장 */
    @Override
    public int saveFunc(VerEnvMngVO[] verEnvMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for (VerEnvMngVO verEnvMngVO : verEnvMngVOs) {

            verEnvMngVO.setRegDt(currentDt);
            verEnvMngVO.setRegId(sessionInfoVO.getUserId());
            verEnvMngVO.setModDt(currentDt);
            verEnvMngVO.setModId(sessionInfoVO.getUserId());

            // 기능 사용여부 저장
            result += verEnvMngMapper.saveFunc(verEnvMngVO);
        }

        if ( result == verEnvMngVOs.length) {
            return result;
        } else {
            throw new JsonException(Status.FAIL, messageService.get("cmm.saveFail"));
        }
    }
}
