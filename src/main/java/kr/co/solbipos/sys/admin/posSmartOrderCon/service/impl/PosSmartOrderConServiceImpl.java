package kr.co.solbipos.sys.admin.posSmartOrderCon.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sys.admin.posSmartOrderCon.service.PosSmartOrderConService;
import kr.co.solbipos.sys.admin.posSmartOrderCon.service.PosSmartOrderConVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PosSmartOrderConServiceImpl.java
 * @Description : 시스템관리 > 관리자기능 > 스마트오더연결상태
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.06.20  권지현      최초생성
 *
 * @author 솔비포스 WEB개발팀 권지현
 * @since 2023.06.20
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("PosSmartOrderConServiceImpl")
@Transactional
public class PosSmartOrderConServiceImpl implements PosSmartOrderConService {

    private final PosSmartOrderConMapper posSmartOrderConMapper;

    public PosSmartOrderConServiceImpl(PosSmartOrderConMapper posSmartOrderConMapper) {
            this.posSmartOrderConMapper = posSmartOrderConMapper;
    }

    /** 매장코드 조회 */
    @Override
    public List<DefaultMap<String>> getPosSmartOrderConList(PosSmartOrderConVO posSmartOrderConVO, SessionInfoVO sessionInfoVO) {
        if (sessionInfoVO.getOrgnFg().equals(OrgnFg.HQ)) {
            posSmartOrderConVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        } else if (sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)) {
            posSmartOrderConVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
            posSmartOrderConVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return posSmartOrderConMapper.getPosSmartOrderConList(posSmartOrderConVO);
    }
}
