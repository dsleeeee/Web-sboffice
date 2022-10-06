package kr.co.solbipos.sale.prod.prodClassSaleRate.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.application.session.user.enums.OrgnFg;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.ProdClassSaleRateService;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.ProdClassSaleRateVO;
import kr.co.solbipos.sale.prod.prodClassSaleRate.service.impl.ProdClassSaleRateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Class Name : ProdClassSaleRateServiceImpl.java
 * @Description : 맘스터치 > 승인관리2 > 상품군 판매비율
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
@Service("prodClassSaleRateService")
@Transactional
public class ProdClassSaleRateServiceImpl implements ProdClassSaleRateService {
    private final ProdClassSaleRateMapper prodClassSaleRateMapper;

    public ProdClassSaleRateServiceImpl(ProdClassSaleRateMapper prodClassSaleRateMapper) {
        this.prodClassSaleRateMapper = prodClassSaleRateMapper;
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdClassSaleRateList(ProdClassSaleRateVO prodClassSaleRateVO, SessionInfoVO sessionInfoVO) {

        prodClassSaleRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodClassSaleRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodClassSaleRateVO.getStoreCds().split(",");
        prodClassSaleRateVO.setStoreCdList(storeCds);

        return prodClassSaleRateMapper.getProdClassSaleRateList(prodClassSaleRateVO);
    }

    /** 조회 */
    @Override
    public List<DefaultMap<Object>> getProdClassSaleRateExcelList(ProdClassSaleRateVO prodClassSaleRateVO, SessionInfoVO sessionInfoVO) {

        prodClassSaleRateVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());

        if (sessionInfoVO.getOrgnFg() == OrgnFg.STORE ){
            prodClassSaleRateVO.setStoreCds(sessionInfoVO.getStoreCd());
        }

        // 매장 array 값 세팅
        String[] storeCds = prodClassSaleRateVO.getStoreCds().split(",");
        prodClassSaleRateVO.setStoreCdList(storeCds);

        return prodClassSaleRateMapper.getProdClassSaleRateExcelList(prodClassSaleRateVO);
    }
}