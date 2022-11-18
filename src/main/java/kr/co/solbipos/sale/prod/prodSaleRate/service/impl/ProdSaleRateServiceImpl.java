package kr.co.solbipos.sale.prod.prodSaleRate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateService;
import kr.co.solbipos.sale.prod.prodSaleRate.service.ProdSaleRateVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdSaleRateServiceImpl.java
 * @Description : 맘스터치 > 상품매출분석 > 상품 판매 비율
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2022.10.04  권지현      최초생성
 *
 * @author 솔비포스 개발본부 WEB개발팀 권지현
 * @since 2022.10.04
 * @version 1.0
 *
 *  Copyright (C) by SOLBIPOS CORP. All right reserved.
 */
@Service("prodSaleRateService")
@Transactional
public class ProdSaleRateServiceImpl implements ProdSaleRateService {
    private final ProdSaleRateMapper prodSaleRateMapper;

    public ProdSaleRateServiceImpl(ProdSaleRateMapper prodSaleRateMapper) {
        this.prodSaleRateMapper = prodSaleRateMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleRateList(ProdSaleRateVO prodSaleRateVO, SessionInfoVO sessionInfoVO) {

        prodSaleRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodSaleRateVO.getStoreCds().split(",");
        prodSaleRateVO.setStoreCdList(storeCds);

        return prodSaleRateMapper.getProdSaleRateList(prodSaleRateVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdSaleRateExcelList(ProdSaleRateVO prodSaleRateVO, SessionInfoVO sessionInfoVO) {

        prodSaleRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodSaleRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodSaleRateVO.getStoreCds().split(",");
        prodSaleRateVO.setStoreCdList(storeCds);

        return prodSaleRateMapper.getProdSaleRateExcelList(prodSaleRateVO);
    }
}