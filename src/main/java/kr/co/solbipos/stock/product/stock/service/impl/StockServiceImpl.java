package kr.co.solbipos.stock.product.stock.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.stock.product.stock.service.StockService;
import kr.co.solbipos.stock.product.stock.service.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : StockServiceImpl.java
 * @Description : 재고관리 > 생산관리 > 재고현황(매장)
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.09.07  이다솜      최초생성
 *
 * @author 솔비포스 WEB개발팀 이다솜
 * @since 2022.09.07
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("stockService")
@Transactional
public class StockServiceImpl implements StockService{

    private final StockMapper stockMapper;

    /**
     * Constructor Injection
     */
    @Autowired
    public StockServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    /** 재고현황(매장) - 조회 */
    @Override
    public List<DefaultMap<Object>> getStockList(StockVO stockVO, SessionInfoVO sessionInfoVO) {

        // 접속사용자의 권한(M : 시스템, A : 대리점, H : 본사, S : 매장)
        stockVO.setOrgnFg(sessionInfoVO.getOrgnFg().getCode());
        stockVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            stockVO.setStoreCd(sessionInfoVO.getStoreCd());
        }

        if(!StringUtil.getOrBlank(stockVO.getStoreCd()).equals("")) {
            stockVO.setArrStoreCd(stockVO.getStoreCd().split(","));
        }

        return stockMapper.getStockList(stockVO);
    }
}
