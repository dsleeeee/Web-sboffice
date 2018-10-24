package kr.co.solbipos.adi.mony.status.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.adi.mony.status.service.StatusService;
import kr.co.solbipos.adi.mony.status.service.StatusVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : DrawHistServiceImpl.java
 * @Description : 부가서비스 > 금전처리 > 금전현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.09  김태수      최초생성
 *
 * @author NHN한국사이버결제 KCP 김태수
 * @since 2018.09.09
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("StatusService")
@Transactional
public class StatusServiceImpl implements StatusService {

    private final StatusMapper StatusMapper;

    /** Constructor Injection */
    @Autowired
    public StatusServiceImpl(
        kr.co.solbipos.adi.mony.status.service.impl.StatusMapper statusMapper) {
        StatusMapper = statusMapper;
    }

    @Override
    public List<StatusVO> selectStatus(StatusVO statusVO, SessionInfoVO sessionInfoVO) {

        statusVO.setOrgnFg(sessionInfoVO.getOrgnFg());

        // 본사
        if (sessionInfoVO.getOrgnFg() == OrgnFg.HQ) {
            statusVO.setHqOfficeCd(sessionInfoVO.getOrgnCd());
            return StatusMapper.selectStatus(statusVO);
        }
        // 가맹점
        else if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            statusVO.setStoreCd(sessionInfoVO.getOrgnCd());
            return StatusMapper.selectStoreStatus(statusVO);
        }
        return null;
    }

    @Override
    public List<DefaultMap<String>> selectAccntList(StatusVO StatusVO) {

        return StatusMapper.selectAccntList(StatusVO);
    }
}

