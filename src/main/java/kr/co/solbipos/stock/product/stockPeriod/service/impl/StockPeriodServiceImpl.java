package kr.co.solbipos.stock.product.stockPeriod.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.stock.product.stockPeriod.service.StockPeriodService;
import kr.co.solbipos.stock.product.stockPeriod.service.StockPeriodVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.solbipos.application.com.griditem.enums.GridDataFg;

import java.util.List;

import static kr.co.common.utils.DateUtil.currentDateTimeString;

/**
 * @Class Name : StockPeriodServiceImpl.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장-기간별)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.12.22  김설아      최초생성
 *
 * @author 솔비포스 WEB개발팀 김설아
 * @since 2022.12.22
 * @version 1.0
 * @see
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("stockPeriodService")
@Transactional
public class StockPeriodServiceImpl implements StockPeriodService {
    private final StockPeriodMapper stockPeriodMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StockPeriodServiceImpl(StockPeriodMapper stockPeriodMapper) {
        this.stockPeriodMapper = stockPeriodMapper;
    }

    /** 재고현황(매장-기간별) - 조회 */
    @Override
    public List<DefaultMap<Object>> getStockPeriodList(StockPeriodVO stockPeriodVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        stockPeriodVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockPeriodVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            stockPeriodVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(stockPeriodVO.getStoreCd()).equals("")) {
            stockPeriodVO.setArrStoreCd(stockPeriodVO.getStoreCd().split(","));
        }

        return stockPeriodMapper.getStockPeriodList(stockPeriodVO);
    }
}