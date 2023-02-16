package kr.co.solbipos.sale.status.saleStatusKwu.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.saleStatusKwu.service.SaleStatusKwuService;
import kr.co.solbipos.sale.status.saleStatusKwu.service.SaleStatusKwuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;
import static kr.co.common.utils.DateUtil.currentDateString;

/**
 * @Class Name : SaleStatusKwuServiceImpl.java
 * @Description : 광운대 > 리포트 > 매출현황2
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2023.02.14  김설아      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 김설아
 * @since 2023.02.14
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("saleStatusKwuService")
@Transactional
public class SaleStatusKwuServiceImpl implements SaleStatusKwuService {
    private final SaleStatusKwuMapper saleStatusKwuMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public SaleStatusKwuServiceImpl(SaleStatusKwuMapper saleStatusKwuMapper) { this.saleStatusKwuMapper = saleStatusKwuMapper; }

    /** 매출현황2 - 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleStatusKwuList(SaleStatusKwuVO saleStatusKwuVO, SessionInfoVO sessionInfoVO) {

        saleStatusKwuVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg() == OrgnFg.STORE) {
            saleStatusKwuVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        return saleStatusKwuMapper.getSaleStatusKwuList(saleStatusKwuVO);
    }
}