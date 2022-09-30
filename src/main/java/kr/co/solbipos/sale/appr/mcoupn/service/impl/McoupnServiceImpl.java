package kr.co.solbipos.sale.appr.mcoupn.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnService;
import kr.co.solbipos.sale.appr.mcoupn.service.McoupnVO;
import kr.co.solbipos.sale.appr.mcoupn.service.impl.McoupnMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : McoupnServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 모바일쿠폰 승인 조회
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.30  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.09.30
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("mcoupnService")
@Transactional
public class McoupnServiceImpl implements McoupnService {
    private final McoupnMapper mcoupnMapper;

    public McoupnServiceImpl(McoupnMapper mcoupnMapper) {
        this.mcoupnMapper = mcoupnMapper;
    }

    /** 모바일쿠폰입금관리 - 매장 콤보박스 조회 */
    @Override
    public List<DefaultMap<Object>> getMcoupnList(McoupnVO mcoupnVO, SessionInfoVO sessionInfoVO) {

        mcoupnVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            mcoupnVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = mcoupnVO.getStoreCds().split(",");
        mcoupnVO.setStoreCdList(storeCds);


        return mcoupnMapper.getMcoupnList(mcoupnVO);
    }

}