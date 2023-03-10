package kr.co.solbipos.sys.cd.envstRemark.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.message.MessageService;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.cd.envstRemark.service.EnvstRemarkService;
import kr.co.solbipos.sys.cd.envstRemark.service.EnvstRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : EnvstRemarkService.java
 * @Description : 시스템관리 > 코드관리 > 환경설정기능설명
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.03.03  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.03.03
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("envstRemarkService")
public class EnvstRemarkServiceImpl implements EnvstRemarkService {

    private final EnvstRemarkMapper envstRemarkMapper;
    private final MessageService messageService;

    /** Constructor Injection */
    @Autowired
    public EnvstRemarkServiceImpl(EnvstRemarkMapper envstRemarkMapper, MessageService messageService) {
        this.envstRemarkMapper = envstRemarkMapper;
        this.messageService = messageService;
    }

    /** 환경그룹 목록 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstGrpList() {
        return envstRemarkMapper.getEnvstGrpList("004");
    }

    /** 대표명칭 코드목록 조회 */
    @Override
    public List<DefaultMap<String>> getEnvstList(EnvstRemarkVO envstRemarkVO) {
        return envstRemarkMapper.getEnvstList(envstRemarkVO);
    }

    /** 대표명칭 코드 저장 */
    @Override
    public int saveEnvstRemark(EnvstRemarkVO envstRemarkVO, SessionInfoVO sessionInfoVO) {

        String currentDt = currentDateTimeString();

        envstRemarkVO.setModDt(currentDt);
        envstRemarkVO.setModId(sessionInfoVO.getUserId());

        return envstRemarkMapper.saveEnvstRemark(envstRemarkVO);
    }


}
