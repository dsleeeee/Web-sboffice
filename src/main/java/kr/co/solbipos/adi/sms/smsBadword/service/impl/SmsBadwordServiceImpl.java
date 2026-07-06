package kr.co.solbipos.adi.sms.smsBadword.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.adi.sms.smsBadword.service.SmsBadwordService;
import kr.co.solbipos.adi.sms.smsBadword.service.SmsBadwordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : SmsBadwordServiceImpl.java
 * @Description : 부가서비스 > SMS관리 > SMS금칙어(탭)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.07.01  이다솜      최초생성
 *
 * @author 링크 개발실 개발1팀 이다솜
 * @since 2026.07.01
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("smsBadwordService")
@Transactional
public class SmsBadwordServiceImpl implements SmsBadwordService {

    private final SmsBadwordMapper smsBadwordMapper;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SmsBadwordServiceImpl(SmsBadwordMapper smsBadwordMapper) {
        this.smsBadwordMapper = smsBadwordMapper;
    }

    /** 금칙어관리 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getBadwordManageList(SmsBadwordVO smsBadwordVO, SessionInfoVO sessionInfoVO) {
        return smsBadwordMapper.getBadwordManageList(smsBadwordVO);
    }

    /** 금칙어관리 - 저장 */
    @Override
    public int saveBadwordManage(SmsBadwordVO[] smsBadwordVOs, SessionInfoVO sessionInfoVO) {

        int procCnt = 0;
        String currentDt = currentDateTimeString();

        for (SmsBadwordVO smsBadwordVO : smsBadwordVOs) {

            smsBadwordVO.setRegDt(currentDt);
            smsBadwordVO.setRegId(sessionInfoVO.getUserId());
            smsBadwordVO.setModDt(currentDt);
            smsBadwordVO.setModId(sessionInfoVO.getUserId());

            if (smsBadwordVO.getStatus() == GridDataFg.INSERT) {
                procCnt += smsBadwordMapper.insertBadwordManage(smsBadwordVO);

            } else if (smsBadwordVO.getStatus() == GridDataFg.UPDATE) {
                procCnt += smsBadwordMapper.updateBadwordManage(smsBadwordVO);

            } else if (smsBadwordVO.getStatus() == GridDataFg.DELETE) {
                procCnt += smsBadwordMapper.deleteBadwordManage(smsBadwordVO);
            }
        }

        return procCnt;
    }

    /** 탐지/차단 결과 로그 - 목록 조회 */
    @Override
    public List<DefaultMap<Object>> getMsgBlockLogList(SmsBadwordVO smsBadwordVO) {
        return smsBadwordMapper.getMsgBlockLogList(smsBadwordVO);
    }
}
