package kr.co.solbipos.sys.link.kcpqrUseStatus.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.sys.link.kcpqrUseStatus.service.KcpqrUseStatusService;
import kr.co.solbipos.sys.link.kcpqrUseStatus.service.KcpqrUseStatusVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name  : KcpqrUseStatusServiceImpl.java
 * @Description : 시스템관리 > 연동 > KCPQR현황
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2026.01.22  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2026.01.22
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("KcpqrUseStatusService")
@Transactional
public class KcpqrUseStatusServiceImpl implements KcpqrUseStatusService {

    private final KcpqrUseStatusMapper kcpqrUseStatusMapper;


    public KcpqrUseStatusServiceImpl(KcpqrUseStatusMapper kcpqrUseStatusMapper) {
        this.kcpqrUseStatusMapper = kcpqrUseStatusMapper;
    }

    /** KCPQR현황 - 조회 */
    @Override
    public List<DefaultMap<String>> getSearchKcpqrStatus(KcpqrUseStatusVO kcpqrUseStatusVO, SessionInfoVO sessionInfoVO) {
        String currentDay = currentDateString();

        kcpqrUseStatusVO.setToday(currentDay);
        return kcpqrUseStatusMapper.getSearchKcpqrStatus(kcpqrUseStatusVO);
    }
}
