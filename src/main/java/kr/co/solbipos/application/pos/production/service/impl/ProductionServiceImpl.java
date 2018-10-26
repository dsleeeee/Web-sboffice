package kr.co.solbipos.application.pos.production.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.pos.production.service.ProductionService;
import kr.co.solbipos.application.pos.production.service.ProductionVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Class Name : ExceptForwardServiceImpl.java
 * @Description : POS 화면에서 생산량 등록
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2018.09.16  김지은      최초생성
 *
 * @author 솔비포스 차세대개발실 김지은
 * @since 2018. 09.16
 * @version 1.0
 *
 * @Copyright (C) by SOLBIPOS CORP. All right reserved.
 */

@Service("productionService")
public class ProductionServiceImpl implements ProductionService {

    private final ProductionMapper productionMapper;

    /** Constructor Injection */
    @Autowired
    public ProductionServiceImpl(ProductionMapper productionMapper) {
        this.productionMapper = productionMapper;
    }

    /** 생산량 등록 대상상품 목록 조회*/
    @Override
    public List<DefaultMap<String>> getProductList(ProductionVO productionVO,
        SessionInfoVO sessionInfoVO) {

        productionVO.setStoreCd(sessionInfoVO.getStoreCd());

        // 목록 조회
        List<DefaultMap<String>> reultList = productionMapper.getProductList(productionVO);

        return reultList;
    }
}
