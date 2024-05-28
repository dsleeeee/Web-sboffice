package kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.impl;

import kr.co.common.data.enums.Status;
import kr.co.common.data.structure.DefaultMap;
import kr.co.common.exception.JsonException;
import kr.co.common.service.message.MessageService;
import kr.co.common.system.BaseEnv;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.DisuseInfoBonavieService;
import kr.co.solbipos.iostock.bonavie.disuseInfoBonavie.service.DisuseInfoBonavieVO;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.io.File;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : DisuseInfoBonavieServiceImpl.java
 * @Description : 수불관리 > 보나비 > 폐기내역조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2024.05.23  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2024.05.23
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("disuseInfoBonavieService")
@Transactional
public class DisuseInfoBonavieServiceImpl implements DisuseInfoBonavieService {
    private final DisuseInfoBonavieMapper disuseInfoBonavieMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public DisuseInfoBonavieServiceImpl(DisuseInfoBonavieMapper disuseInfoBonavieMapper) {
        this.disuseInfoBonavieMapper = disuseInfoBonavieMapper;
    }

    /** 폐기내역조회 - 조회 */
    @Override
    public List<DefaultMap<Object>> getDisuseInfoBonavieList(DisuseInfoBonavieVO disuseInfoBonavieVO, SessionInfoVO sessionInfoVO) {

        disuseInfoBonavieVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            disuseInfoBonavieVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return disuseInfoBonavieMapper.getDisuseInfoBonavieList(disuseInfoBonavieVO);
    }

    /** 폐기내역조회 - 엑셀다운로드 조회 */
    @Override
    public List<DefaultMap<Object>> getDisuseInfoBonavieExcelList(DisuseInfoBonavieVO disuseInfoBonavieVO, SessionInfoVO sessionInfoVO) {

        disuseInfoBonavieVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            disuseInfoBonavieVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return disuseInfoBonavieMapper.getDisuseInfoBonavieExcelList(disuseInfoBonavieVO);
    }
}