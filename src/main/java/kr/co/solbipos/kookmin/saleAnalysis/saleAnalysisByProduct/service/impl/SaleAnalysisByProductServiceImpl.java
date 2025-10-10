package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.common.service.popup.impl.PopupMapper;
import kr.co.common.utils.CmmUtil;
import kr.co.common.utils.spring.StringUtil;
import kr.co.solbipos.application.common.service.StoreVO;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByProduct.service.SaleAnalysisByProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByProductServiceImpl.java
 * @Description : 국민대 > 매출분석 > 상품별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.09.30  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.09.30
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("SaleAnalysisByProductService")
@Transactional
public class SaleAnalysisByProductServiceImpl implements SaleAnalysisByProductService {

    private final SaleAnalysisByProductMapper saleAnalysisByProductMapper;
    private final PopupMapper popupMapper;

    @Autowired
    public SaleAnalysisByProductServiceImpl(SaleAnalysisByProductMapper saleAnalysisByProductMapper, PopupMapper popupMapper) {
        this.saleAnalysisByProductMapper = saleAnalysisByProductMapper;
        this.popupMapper = popupMapper;
    }

    /** 상품별 매출분석 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleAnalysisByProductList(SaleAnalysisByProductVO saleAnalysisByProductVO, SessionInfoVO sessionInfoVO) {
        saleAnalysisByProductVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 매장 array 값 세팅
        if(!StringUtil.getOrBlank(saleAnalysisByProductVO.getStoreCds()).equals("")) {
            StoreVO storeVO = new StoreVO();
            storeVO.setArrSplitStoreCd(CmmUtil.splitText(saleAnalysisByProductVO.getStoreCds(), 3900));
            saleAnalysisByProductVO.setStoreCdQuery(popupMapper.getSearchMultiStoreRtn(storeVO));
        }
        return saleAnalysisByProductMapper.getSaleAnalysisByProductList(saleAnalysisByProductVO);
    }
}
