package kr.co.solbipos.sys.admin.logSend.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.accntManage.service.impl.AccntVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.admin.logSend.service.LogSendService;
import kr.co.solbipos.sys.admin.logSend.service.LogSendVO;
import kr.co.solbipos.sys.stats.webLogin.service.WebLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : LogSendServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > POS 시스템 로그 송신
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2020.08.25  이다솜      최초생성
 *
 * @author 솔비포스 백엔드 pt 이다솜
 * @since 2020. 08. 25
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("LogSendServiceImpl")
@Transactional
public class LogSendServiceImpl implements LogSendService {
    private final LogSendMapper logSendMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public LogSendServiceImpl(LogSendMapper logSendMapper) {
        this.logSendMapper = logSendMapper;
    }

    /** 매장별 포스목록 조회 */
    @Override
    public List<DefaultMap<String>> getPosList(LogSendVO logSendVO, SessionInfoVO sessionInfoVO) {
        return logSendMapper.getPosList(logSendVO);
    }

    /** [POS-DB] 간 로그 송신 구분을 등록 */
    @Override
    public int updateLogSend(LogSendVO[] logSendVOs, SessionInfoVO sessionInfoVO) {

        int resultCnt = 0;

        for(LogSendVO logSendVO : logSendVOs) {

            logSendVO.setDbSendYn("Y");
            resultCnt += logSendMapper.updateLogSend(logSendVO);
        }

        return resultCnt;
    }
}
