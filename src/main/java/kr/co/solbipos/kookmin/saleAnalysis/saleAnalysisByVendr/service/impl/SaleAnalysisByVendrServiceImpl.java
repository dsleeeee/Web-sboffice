package kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service.impl;

import kr.co.common.data.structure.DefaultMap;
import kr.co.solbipos.application.session.auth.service.SessionInfoVO;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service.SaleAnalysisByVendrService;
import kr.co.solbipos.kookmin.saleAnalysis.saleAnalysisByVendr.service.SaleAnalysisByVendrVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @Class Name  : SaleAnalysisByVendrServiceImpl.java
 * @Description : 국민대 > 매출분석 > 매입처별 매출분석
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ----------  ---------   -------------------------------
 * @ 2025.10.10  김유승      최초생성
 *
 * @author 링크 개발실 개발1팀 김유승
 * @since 2025.10.10
 * @version 1.0
 *
 *  Copyright (C) by LYNK CORP. All right reserved.
 */
@Service("SaleAnalysisByVendrService")
@Transactional
public class SaleAnalysisByVendrServiceImpl implements SaleAnalysisByVendrService {

    private final SaleAnalysisByVendrMapper saleAnalysisByVendrMapper;

    public SaleAnalysisByVendrServiceImpl(SaleAnalysisByVendrMapper saleAnalysisByVendrMapper) {
        this.saleAnalysisByVendrMapper = saleAnalysisByVendrMapper;
    }

    /** 매입처별 매출분석 조회 */
    @Override
    public List<DefaultMap<Object>> getSaleAnalysisByVendrList(SaleAnalysisByVendrVO saleAnalysisByVendrVO, SessionInfoVO sessionInfoVO) {
        saleAnalysisByVendrVO.setHqOfficeCd(sessionInfoVO.getHqOfficeCd());
        // 분류 array 값 세팅
        if (saleAnalysisByVendrVO.getProdClassCd() != null && !"".equals(saleAnalysisByVendrVO.getProdClassCd())) {
            String[] prodCdList = saleAnalysisByVendrVO.getProdClassCd().split(",");
            saleAnalysisByVendrVO.setArrProdClassCd(prodCdList);
        }
        return saleAnalysisByVendrMapper.getSaleAnalysisByVendrList(saleAnalysisByVendrVO);
    }
}
