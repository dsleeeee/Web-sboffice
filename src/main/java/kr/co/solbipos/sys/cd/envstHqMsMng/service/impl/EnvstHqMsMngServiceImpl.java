package kr.co.solbipos.sys.cd.envstHqMsMng.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envstHqMsMng.service.EnvstHqMsMngService;
import kr.co.solbipos.sys.cd.envstHqMsMng.service.EnvstHqMsMngVO;
import kr.co.solbipos.sys.cd.envstHqMsMng.service.impl.EnvstHqMsMngMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EnvstHqMsMngService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정기능설명
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.06  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.06
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("envstHqMsMngService")
public class EnvstHqMsMngServiceImpl implements EnvstHqMsMngService {

    private final EnvstHqMsMngMapper envstHqMsMngMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public EnvstHqMsMngServiceImpl(EnvstHqMsMngMapper envstHqMsMngMapper, MessageService messageService) {
        this.envstHqMsMngMapper = envstHqMsMngMapper;
        this.messageService = messageService;
    }

    /** 사용 환경설정 조회 */
    @Override
    public List<DefaultMap<String>> getRegEnvstList(EnvstHqMsMngVO envstHqMsMngVO) {
        return envstHqMsMngMapper.getRegEnvstList(envstHqMsMngVO);
    }

    /** 미사용 환경설정 조회 */
    @Override
    public List<DefaultMap<String>> getNoRegEnvstList(EnvstHqMsMngVO envstHqMsMngVO) {
        return envstHqMsMngMapper.getNoRegEnvstList(envstHqMsMngVO);
    }

    /** 사용 환경설정 등록 */
    @Override
    public int saveEnvstHqMsMng(EnvstHqMsMngVO[] envstHqMsMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;
        String currentDt = currentDateTimeString();

        for(EnvstHqMsMngVO envstHqMsMngVO : envstHqMsMngVOs){

            envstHqMsMngVO.setRegDt(currentDt);
            envstHqMsMngVO.setRegId(sessionInfoVO.getUserId());
            envstHqMsMngVO.setModDt(currentDt);
            envstHqMsMngVO.setModId(sessionInfoVO.getUserId());

            result = envstHqMsMngMapper.saveEnvstHqMsMng(envstHqMsMngVO);
        }

        return result;
    }

    /** 미사용 환경설정 등록 */
    @Override
    public int deleteEnvstHqMsMng(EnvstHqMsMngVO[] envstHqMsMngVOs, SessionInfoVO sessionInfoVO) {

        int result = 0;

        for(EnvstHqMsMngVO envstHqMsMngVO : envstHqMsMngVOs){
            result = envstHqMsMngMapper.deleteEnvstHqMsMng(envstHqMsMngVO);
        }

        return result;
    }


}
