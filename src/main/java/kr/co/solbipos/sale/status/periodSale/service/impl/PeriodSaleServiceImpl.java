package kr.co.solbipos.sale.status.periodSale.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.status.periodSale.service.PeriodSaleService;
import kr.co.solbipos.sale.status.periodSale.service.PeriodSaleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : PeriodSaleServiceImpl.java
 * @Description : 매출관리 > 매출현황2 > 기간매출상세
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.07.01  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.07.01
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("periodSaleService")
@Transactional
public class PeriodSaleServiceImpl implements PeriodSaleService {

    private final PeriodSaleMapper periodSaleMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public PeriodSaleServiceImpl(PeriodSaleMapper periodSaleMapper) {
        this.periodSaleMapper = periodSaleMapper;
    }

    /**
     * 기간매출상세 리스트 조회
     * @param periodSaleVO
     * @param sessionInfoVO
     * @return
     */
    @Override
    public List<DefaultMap<String>> getPeriodSaleList(PeriodSaleVO periodSaleVO, SessionInfoVO sessionInfoVO) {
        periodSaleVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if(sessionInfoVO.getOrgnFg().equals(OrgnFg.STORE)){
            periodSaleVO.setStoreCd(sessionInfoVO.getStoreCd());
        }
        return periodSaleMapper.getPeriodSaleList(periodSaleVO);
    }
}
